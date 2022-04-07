package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.BankTO;
import com.rjtech.centrallib.model.CmpBankAccountEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.req.CompanyBankSaveReq;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class BankAccountHandler {
    public static List<CmpBankAccountEntity> convertPOJOToEntity(CompanyBankSaveReq companyBankSaveReq) {
        List<CmpBankAccountEntity> cmpBankAccountEntitys = new ArrayList<CmpBankAccountEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        CmpBankAccountEntity cmpBankAccountEntity = null;
        CompanyMstrEntity companyMstrEntity = null;
     //   UserMstrEntity userMstrEntity = null;
    //    ClientRegEntity clientRegEntity = null;
        for (BankTO bankTO : companyBankSaveReq.getBankTOs()) {
        	System.out.println("bankTO.getBankAccountId()"+bankTO.getBankAccountId());
        	cmpBankAccountEntity = new CmpBankAccountEntity();
            if (CommonUtil.isNonBlankLong(bankTO.getBankAccountId())) {
            	cmpBankAccountEntity.setBankAccountId(bankTO.getBankAccountId());
            }
            companyMstrEntity = new CompanyMstrEntity();

            companyMstrEntity.setId(bankTO.getCompanyId());
            cmpBankAccountEntity.setCompanyMstrEntity(companyMstrEntity);
            cmpBankAccountEntity.setAccName(bankTO.getAccountName());
            cmpBankAccountEntity.setBankName(bankTO.getBankName());
            cmpBankAccountEntity.setBankCode(bankTO.getBankCode());
            cmpBankAccountEntity.setBankAccNo(bankTO.getAccountNumber());
          //  cmpBankAccountEntity.setCreatedOn(companyBankDetailsTO.getState());
            cmpBankAccountEntity.setBankAddr(bankTO.getBankAddress());
            cmpBankAccountEntity.setStatus(bankTO.getStatus());
            
            cmpBankAccountEntitys.add(cmpBankAccountEntity);
        }
        return cmpBankAccountEntitys;
    }

}
