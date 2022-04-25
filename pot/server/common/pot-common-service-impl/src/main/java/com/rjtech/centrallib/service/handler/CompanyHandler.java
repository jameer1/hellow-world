package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AddressTO;
import com.rjtech.centrallib.dto.CompanyProjectsTO;
import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.centrallib.dto.ContactsTO;
import com.rjtech.centrallib.dto.ProcurementCompanyTO;
import com.rjtech.centrallib.model.CmpAddressEntity;
import com.rjtech.centrallib.model.CmpContactsEntity;
import com.rjtech.centrallib.model.CmpCurrentProjsEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.req.CompanySaveReq;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.centrallib.resp.ProcurementCompanyResp;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.centrallib.model.CmpBankAccountEntity;
import com.rjtech.centrallib.dto.BankTO;
public class CompanyHandler {

    public static CompanyResp populateCompanyies(List<CompanyMstrEntity> entities) {
        CompanyResp companyResp = new CompanyResp();
        CompanyTO CompanyTO = null;

        for (CompanyMstrEntity companyMstrEntity : entities) {
            CompanyTO = convertCmpEntityFromPOJO(companyMstrEntity);
            companyResp.getCompanyTOs().add(CompanyTO);
        }
        return companyResp;
    }

    public static CompanyTO convertCmpEntityFromPOJO(CompanyMstrEntity companyMstrEntity) {
        CompanyTO companyTO = new CompanyTO();
        companyTO.setId(companyMstrEntity.getId());
        ClientRegEntity client = companyMstrEntity.getClientId();
        if (client != null) {
            companyTO.setClientId(client.getClientId());
        }
        companyTO.setCmpName(companyMstrEntity.getName());

        companyTO.setRegNo(companyMstrEntity.getRegNo());
        companyTO.setTaxFileNo(companyMstrEntity.getTaxFileNo());

        companyTO.setCmpCode(companyMstrEntity.getCode());
        companyTO.setCmpActivity(companyMstrEntity.getCmpActivity());

        companyTO.setStatus(companyMstrEntity.getStatus());
        companyTO.setBusinessCategory(companyMstrEntity.getBusinessCategory());
        companyTO.setCompanyCatagory(companyMstrEntity.getCompanyCategory());
        return companyTO;
    }

    public static ProcurementCompanyResp populateCompanyDetailsForProcurement(List<CompanyMstrEntity> entities) {
        ProcurementCompanyResp companyResp = new ProcurementCompanyResp();
        ProcurementCompanyTO companyTO = null;
        AddressTO addressTO = null;
        ContactsTO contactsTO = null;
        for (CompanyMstrEntity companyMstrEntity : entities) {
            companyTO = convertEntityFromPOJO(companyMstrEntity);

            for (CmpAddressEntity cmpAddressEntity : companyMstrEntity.getCmpAddressEntities()) {
                if (StatusCodes.ACTIVE.getValue().equals(cmpAddressEntity.getStatus())) {
                    addressTO = getAddressTO(cmpAddressEntity);
                    companyTO.getAddressMap().put(addressTO.getAddressId(), addressTO);
                }

            }
            for (CmpContactsEntity cmpContactsEntity : companyMstrEntity.getCmpContactsEntities()) {
                if (StatusCodes.ACTIVE.getValue().equals(cmpContactsEntity.getStatus())) {
                    contactsTO = getContactsTO(cmpContactsEntity);
                    companyTO.getContactsMap().put(contactsTO.getContactId(), contactsTO);
                }
            }
            companyResp.getCompanyMap().put(companyTO.getId(), companyTO);
        }
        return companyResp;
    }

    public static ProcurementCompanyTO convertEntityFromPOJO(CompanyMstrEntity companyMstrEntity) {
        ProcurementCompanyTO companyTO = new ProcurementCompanyTO();
        companyTO.setId(companyMstrEntity.getId());
        ClientRegEntity client = companyMstrEntity.getClientId();
        if (client != null)
            companyTO.setClientId(client.getClientId());
        companyTO.setCmpName(companyMstrEntity.getName());

        companyTO.setRegNo(companyMstrEntity.getRegNo());
        companyTO.setTaxFileNo(companyMstrEntity.getTaxFileNo());

        companyTO.setCmpCode(companyMstrEntity.getCode());
        companyTO.setCmpActivity(companyMstrEntity.getCmpActivity());
        companyTO.setStatus(companyMstrEntity.getStatus());
        companyTO.setBusinessCategory(companyMstrEntity.getBusinessCategory());
        companyTO.setCompanyCategory(companyMstrEntity.getCompanyCategory());

        return companyTO;
    }

    public static CompanyResp populateCompanyDetails(List<CompanyMstrEntity> entities) {
        CompanyResp companyResp = new CompanyResp();
        CompanyTO companyTO = null;
        AddressTO addressTO = null;
        ContactsTO contactsTO = null;
        BankTO  bankTO = null;
        CompanyProjectsTO companyCurrentProjectsTO = null;
        CompanyProjectsTO companyClosedProjectsTO = null;
        for (CompanyMstrEntity companyMstrEntity : entities) {
            companyTO = convertCmpEntityFromPOJO(companyMstrEntity);

            for (CmpAddressEntity cmpAddressEntity : companyMstrEntity.getCmpAddressEntities()) {
                if (StatusCodes.ACTIVE.getValue().equals(cmpAddressEntity.getStatus())) {
                    addressTO = getAddressTO(cmpAddressEntity);
                    companyTO.getAddressList().add(addressTO);
                }

            }
            for (CmpContactsEntity cmpContactsEntity : companyMstrEntity.getCmpContactsEntities()) {
                if (StatusCodes.ACTIVE.getValue().equals(cmpContactsEntity.getStatus())) {
                    contactsTO = getContactsTO(cmpContactsEntity);
                    companyTO.getContacts().add(contactsTO);
                }

            }
            
            for(CmpBankAccountEntity cmpBankAccountEntity : companyMstrEntity.getCmpBankAccountEntities()) {
             if (StatusCodes.ACTIVE.getValue().equals(cmpBankAccountEntity.getStatus())) {
            	bankTO = getBankTO(cmpBankAccountEntity);
            	companyTO.getBankList().add(bankTO);
               }	            
            }
            
            for (CmpCurrentProjsEntity cmpCurrentProjsEntity : companyMstrEntity.getCmpCurrentProjsEntities()) {
                if (StatusCodes.ACTIVE.getValue().equals(cmpCurrentProjsEntity.getStatus())) {
                    companyCurrentProjectsTO = getCurrentProjectsTO(cmpCurrentProjsEntity);
                    if (companyCurrentProjectsTO.isClosed()) {
                        companyTO.getClosedProjs().add(companyCurrentProjectsTO);
                    } else {
                        companyTO.getCurrentProjs().add(companyCurrentProjectsTO);
                    }

                }
            }
            companyResp.getCompanyTOs().add(companyTO);
        }
        return companyResp;
    }

    public static CompanyProjectsTO getCurrentProjectsTO(CmpCurrentProjsEntity cmpCurrentProjsEntity) {
        CompanyProjectsTO companyCurrentProjectsTO = new CompanyProjectsTO();
        companyCurrentProjectsTO.setCmpId(cmpCurrentProjsEntity.getCompanyMstrEntity().getId());
        ProjMstrEntity ProjMstrEntity = cmpCurrentProjsEntity.getProjMstrEntity();
        companyCurrentProjectsTO.setId(cmpCurrentProjsEntity.getId());
        companyCurrentProjectsTO.setContractValue(cmpCurrentProjsEntity.getContractValue());
        companyCurrentProjectsTO.setPerformance(cmpCurrentProjsEntity.getPerformance());
        companyCurrentProjectsTO.setStartDate(CommonUtil.convertDateToString(cmpCurrentProjsEntity.getStartDate()));
        companyCurrentProjectsTO.setFinishDate(CommonUtil.convertDateToString(cmpCurrentProjsEntity.getFinishDate()));
        companyCurrentProjectsTO.setProjId(ProjMstrEntity.getProjectId());
        companyCurrentProjectsTO.setProjCode(ProjMstrEntity.getCode());
        companyCurrentProjectsTO.setProjName(ProjMstrEntity.getProjName());
        companyCurrentProjectsTO.setStatus(cmpCurrentProjsEntity.getStatus());
        companyCurrentProjectsTO.setClosed(cmpCurrentProjsEntity.isClosed());

        return companyCurrentProjectsTO;
    }

    public static ContactsTO getContactsTO(CmpContactsEntity cmpContactsEntity) {
        ContactsTO contactsTO = new ContactsTO();
        contactsTO.setCompanyId(cmpContactsEntity.getCompanyMstrEntity().getId());
        contactsTO.setContactId(cmpContactsEntity.getId());
        contactsTO.setContactName(cmpContactsEntity.getName());
        contactsTO.setEmail(cmpContactsEntity.getEmail());
        /* contactsTO.setFax(cmpContactsEntity.getf); */
        contactsTO.setDefaultContact(cmpContactsEntity.isDefaultContact());
        contactsTO.setMobile(cmpContactsEntity.getMobileNo());
        contactsTO.setPhone(cmpContactsEntity.getPhoneNo());
        contactsTO.setFax(cmpContactsEntity.getFax());
        contactsTO.setDesignation(cmpContactsEntity.getDesig());
        contactsTO.setStatus(cmpContactsEntity.getStatus());
        return contactsTO;
    }

    public static AddressTO getAddressTO(CmpAddressEntity cmpAddressEntity) {
        AddressTO addressTO = new AddressTO();

        addressTO.setCompanyId(cmpAddressEntity.getCompanyMstrEntity().getId());
        addressTO.setAddressId(cmpAddressEntity.getId());
        addressTO.setAddress(cmpAddressEntity.getAddress());
        addressTO.setCity(cmpAddressEntity.getCity());
        addressTO.setState(cmpAddressEntity.getState());
        addressTO.setPincode(cmpAddressEntity.getPincode());
        addressTO.setDefaultAddress(addressTO.isDefaultAddress());
        addressTO.setStatus(cmpAddressEntity.getStatus());
        return addressTO;
    }
    
    public static BankTO getBankTO(CmpBankAccountEntity cmpBankAccountEntity) {
    	BankTO bankTO = new BankTO();

    	bankTO.setCompanyId(cmpBankAccountEntity.getCompanyMstrEntity().getId());
    	bankTO.setBankAccountId(cmpBankAccountEntity.getBankAccountId());
    	bankTO.setAccountName(cmpBankAccountEntity.getAccName());
    	bankTO.setBankName(cmpBankAccountEntity.getBankName());
    	bankTO.setBankCode(cmpBankAccountEntity.getBankCode());
    	bankTO.setAccountNumber(cmpBankAccountEntity.getBankAccNo());
    	bankTO.setBankAddress(cmpBankAccountEntity.getBankAddr());
    	bankTO.setStatus(cmpBankAccountEntity.getStatus());
    	if(cmpBankAccountEntity.getIsLatest() != null) {
    		if(cmpBankAccountEntity.getIsLatest().equals(1)) {
        		bankTO.setBankStatus("Active");
        	}else
        		bankTO.setBankStatus("InActive");
    	}	
        return bankTO;
    }
    
    
    
    
    public static List<CompanyMstrEntity> convertPOJOToEntity(CompanySaveReq companySaveReq) {
        List<CompanyMstrEntity> CompanyMstrEntitys = new ArrayList<CompanyMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        CompanyMstrEntity CompanyMstrEntity = null;

        for (CompanyTO companyTO : companySaveReq.getCompanyTOs()) {
            CompanyMstrEntity = new CompanyMstrEntity();
            if (CommonUtil.isNonBlankLong(companyTO.getId())) {
                CompanyMstrEntity.setId(companyTO.getId());
            }
            CompanyMstrEntity.setCompanyCategory(companyTO.getCompanyCatagory());
            CompanyMstrEntity.setName(companyTO.getCmpName());
            // client id will set from interceptor
            CompanyMstrEntity.setBusinessCategory(companyTO.getBusinessCategory());
            CompanyMstrEntity.setCode(companyTO.getCmpCode());
            CompanyMstrEntity.setCmpActivity(companyTO.getCmpActivity());

            CompanyMstrEntity.setRegNo(companyTO.getRegNo());
            CompanyMstrEntity.setTaxFileNo(companyTO.getTaxFileNo());
            CompanyMstrEntity.setStatus(companyTO.getStatus());
            CompanyMstrEntitys.add(CompanyMstrEntity);
        }
        return CompanyMstrEntitys;
    }

    public static LabelKeyTO convertCmpEntityToLabelKeyTo(CompanyMstrEntity companyMstrEntity) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(companyMstrEntity.getId());
        labelKeyTO.setCode(companyMstrEntity.getCode());
        labelKeyTO.setName(companyMstrEntity.getName());
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.COMPANY_CATG_NAME, companyMstrEntity.getCompanyCategory());

        return labelKeyTO;
    }

}
