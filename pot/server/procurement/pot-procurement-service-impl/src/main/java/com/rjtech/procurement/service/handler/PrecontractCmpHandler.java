package com.rjtech.procurement.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.centrallib.model.CmpAddressEntity;
import com.rjtech.centrallib.model.CmpContactsEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.repository.AddressRepository;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.ContactsRepository;
import com.rjtech.centrallib.service.handler.CompanyHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.WorkFlowStatus;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.resp.PreContractCmpResp;

public class PrecontractCmpHandler {

    public static PreContractCmpResp populatePreContractCmpResp(List<PreContractsCmpEntity> preContractsCmpEntities) {
        PreContractCmpResp preContractCmpResp = new PreContractCmpResp();
        for (PreContractsCmpEntity preContractsCmpEntity : preContractsCmpEntities) {
            preContractCmpResp.getPreContractCmpTOs().add(convertEntityToPOJO(preContractsCmpEntity));
        }
        return preContractCmpResp;
    }

    public static List<PreContractCmpTO> populatePreContractCompanies(
            List<PreContractsCmpEntity> preContractsCmpEntities) {
        List<PreContractCmpTO> preContractCmpTOs = new ArrayList<PreContractCmpTO>();
        for (PreContractsCmpEntity contractsCmpEntity : preContractsCmpEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(contractsCmpEntity.getStatus())
                    && CommonConstants.BIDDING_CLOSED.equalsIgnoreCase(contractsCmpEntity.getBiddingStatus())) {
                preContractCmpTOs.add(convertEntityToPOJO(contractsCmpEntity));
            }
        }
        return preContractCmpTOs;
    }

    public static List<PreContractsCmpEntity> preContractsCmpDtlEntities(List<PreContractCmpTO> preContractCmpTOs,
            Integer apprStatus, CompanyRepository companyRepository, AddressRepository addressRepository,
            ContactsRepository contactsRepository) {
        List<PreContractsCmpEntity> preContractsCmpEntities = new ArrayList<PreContractsCmpEntity>();
        for (PreContractCmpTO preContractCmpTO : preContractCmpTOs) {
            preContractsCmpEntities.add(convertPOJOToEntity(preContractCmpTO, apprStatus, companyRepository,
                    addressRepository, contactsRepository));
        }
        return preContractsCmpEntities;
    }

    public static PreContractsCmpEntity convertPOJOToEntity(PreContractCmpTO preContractCmpTO, Integer apprStatus,
            CompanyRepository companyRepository, AddressRepository addressRepository,
            ContactsRepository contactsRepository) {
        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();

        if (CommonUtil.isNonBlankLong(preContractCmpTO.getId())) {
            preContractsCmpEntity.setId(preContractCmpTO.getId());
        }
        if (CommonUtil.objectNotNull(preContractCmpTO.getPreContractTO())
                && CommonUtil.isNonBlankLong(preContractCmpTO.getPreContractTO().getId())) {
            PreContractEntity preContractEntity = new PreContractEntity();
            preContractEntity.setId(preContractCmpTO.getPreContractTO().getId());
            preContractsCmpEntity.setPreContractEntity(preContractEntity);
        }
        preContractsCmpEntity.setRfqCode(ModuleCodesPrefixes.RFQ_CODE.getDesc() + "-" + preContractCmpTO.getRfqCode());
        if (CommonUtil.isNotBlankStr(preContractCmpTO.getRfqIssueDate())) {
            preContractsCmpEntity.setRfqIssueDate(CommonUtil.convertStringToDate(preContractCmpTO.getRfqIssueDate()));
        } else {
            preContractsCmpEntity.setRfqIssueDate(new Date());
        }

        if (CommonUtil.isNotBlankStr(preContractCmpTO.getQuotedDate())) {
            preContractsCmpEntity.setRfqIssueDate(CommonUtil.convertStringToDate(preContractCmpTO.getQuotedDate()));
        }

        if (CommonUtil.isNonBlankLong(preContractCmpTO.getCompanyId()))
            preContractsCmpEntity.setCompanyId(companyRepository.findOne(preContractCmpTO.getCompanyId()));

        if (CommonUtil.isNonBlankInteger(apprStatus)) {
            preContractsCmpEntity.setCmpStatus(apprStatus);
        }
        preContractsCmpEntity.setVendorCode(preContractCmpTO.getVendorCode());
        preContractsCmpEntity.setQuoteRefCode(preContractCmpTO.getQuoteRefCode());

        if (CommonUtil.isNonBlankLong(preContractCmpTO.getAddressId()))
            preContractsCmpEntity.setAddressId(addressRepository.findOne(preContractCmpTO.getAddressId()));

        if (CommonUtil.isNonBlankLong(preContractCmpTO.getContactId()))
            preContractsCmpEntity.setContactId(contactsRepository.findOne(preContractCmpTO.getContactId()));

        if (CommonUtil.isNotBlankStr(preContractCmpTO.getBiddingStatus())) {
            preContractsCmpEntity.setBiddingStatus(preContractCmpTO.getBiddingStatus());
        } else {
            preContractsCmpEntity.setBiddingStatus(CommonConstants.BIDDING_OPEN);
        }
        preContractsCmpEntity.setStatus(preContractCmpTO.getStatus());

        return preContractsCmpEntity;
    }

    public static PreContractCmpTO convertEntityToPOJO(PreContractsCmpEntity preContractsCmpEntity) {
        PreContractCmpTO preContractCmpTO = new PreContractCmpTO();

        preContractCmpTO.setId(preContractsCmpEntity.getId());
        CompanyMstrEntity company = preContractsCmpEntity.getCompanyId();
        if (AppUtils.isNotNull(company))
            preContractCmpTO.setCompanyId(company.getId());
        if (preContractsCmpEntity.getCompanyId() != null)
            preContractCmpTO.setCompanyTO(
                    CompanyHandler.convertCmpEntityFromPOJO(preContractsCmpEntity.getCompanyMstrEntity()));

        CmpAddressEntity address = preContractsCmpEntity.getAddressId();
        if (AppUtils.isNotNull(address))
            preContractCmpTO.setAddressId(address.getId());

        CmpContactsEntity contact = preContractsCmpEntity.getContactId();
        if (AppUtils.isNotNull(contact))
            preContractCmpTO.setContactId(contact.getId());

        preContractCmpTO.setQuoteRefCode(preContractsCmpEntity.getQuoteRefCode());
        preContractCmpTO.setRfqCode(preContractsCmpEntity.getRfqCode());

        if (CommonUtil.isNotBlankDate(preContractsCmpEntity.getQuotedDate())) {
            preContractCmpTO.setQuotedDate(CommonUtil.convertDateToString(preContractsCmpEntity.getQuotedDate()));
        }
        preContractCmpTO.setCmpStatus(preContractsCmpEntity.getCmpStatus());
        preContractCmpTO.setPreContractTO(
                PrecontractHandler.populatePrecontractTO(preContractsCmpEntity.getPreContractEntity()));
        preContractCmpTO.setBiddingStatus(preContractsCmpEntity.getBiddingStatus());
        if (CommonUtil.isNotBlankDate(preContractsCmpEntity.getRfqIssueDate())) {
            preContractCmpTO.setRfqIssueDate(CommonUtil.convertDateToString(preContractsCmpEntity.getRfqIssueDate()));
        }
        LabelKeyTO preContractLabelKeyTO = null;
        PreContractEntity precontract = preContractsCmpEntity.getPreContractEntity();
        if (CommonUtil.objectNotNull(precontract)) {
            preContractLabelKeyTO = new LabelKeyTO();
            preContractLabelKeyTO.setId(precontract.getId());
            preContractLabelKeyTO.setCode(PrecontractHandler.generatePrecontractCode(precontract));
            preContractLabelKeyTO.setName(precontract.getDesc());
        }
        preContractCmpTO.setStatus(preContractsCmpEntity.getStatus());
        return preContractCmpTO;
    }

}
