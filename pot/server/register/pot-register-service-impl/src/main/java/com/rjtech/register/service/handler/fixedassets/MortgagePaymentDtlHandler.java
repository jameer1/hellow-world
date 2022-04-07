package com.rjtech.register.service.handler.fixedassets;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.MortgagePaymentDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.MortgagePaymentDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;

public class MortgagePaymentDtlHandler {

    public static MortgagePaymentDtlTO convertEntityToPOJO(MortgagePaymentDtlEntity entity) {

        MortgagePaymentDtlTO mortgagePaymentDtlTO = new MortgagePaymentDtlTO();
        mortgagePaymentDtlTO.setId(entity.getId());
        mortgagePaymentDtlTO.setEffectiveDate(CommonUtil.convertDateToString(entity.getEffectiveDate()));
        mortgagePaymentDtlTO.setMortgageeName(entity.getMortgageeName());
        mortgagePaymentDtlTO.setMortgageeAdress(entity.getMortgageeAdress());
        mortgagePaymentDtlTO.setOriginalLoanPrincipalAmount(entity.getOriginalLoanPrincipalAmount());
        mortgagePaymentDtlTO.setRemainingLoanPrinicipalAmount(entity.getRemainingLoanPrinicipalAmount());
        mortgagePaymentDtlTO.setRateOfInterestPerAnnum(entity.getRateOfInterestPerAnnum());
        mortgagePaymentDtlTO.setPaymentCycle(entity.getPaymentCycle());
   //     mortgagePaymentDtlTO.setPaymentCycleDueDate(CommonUtil.convertDateToString(entity.getPaymentCycleDueDate()));
        mortgagePaymentDtlTO.setPaymentCycleDueDate(entity.getPaymentCycleDueDate());
        mortgagePaymentDtlTO.setPaymentAmountPerCycel(entity.getPaymentAmountPerCycel());
        mortgagePaymentDtlTO.setTaxAmount(entity.getTaxAmount());
        mortgagePaymentDtlTO.setNetAmount(entity.getNetAmount());
        mortgagePaymentDtlTO.setReceiverBank(entity.getReceiverBank());
        mortgagePaymentDtlTO.setReceiverBankCode(entity.getReceiverBankCode());
        mortgagePaymentDtlTO.setReceiverBankAC(entity.getReceiverBankAC());
        mortgagePaymentDtlTO.setAccountStatus(entity.getAccountStatus());
        ClientRegEntity clientRegEntity = entity.getClientId();
        if (null != clientRegEntity) {
            mortgagePaymentDtlTO.setClientId(clientRegEntity.getClientId());
        }
        FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();
        if (null != fixedAssetsRegisterDtlEntity) {
            mortgagePaymentDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
        }
        return mortgagePaymentDtlTO;
    }


    public static MortgagePaymentDtlEntity convertPOJOToEntity(MortgagePaymentDtlEntity entity,
            MortgagePaymentDtlTO mortgageePaymentsDtlTO, FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {
        if (CommonUtil.isNonBlankLong(mortgageePaymentsDtlTO.getId())) {
            entity.setId(mortgageePaymentsDtlTO.getId());
        }
        entity.setEffectiveDate(CommonUtil.convertStringToDate(mortgageePaymentsDtlTO.getEffectiveDate()));
        entity.setMortgageeName(mortgageePaymentsDtlTO.getMortgageeName());
        entity.setMortgageeAdress(mortgageePaymentsDtlTO.getMortgageeAdress());
        entity.setOriginalLoanPrincipalAmount(mortgageePaymentsDtlTO.getOriginalLoanPrincipalAmount());
        entity.setRemainingLoanPrinicipalAmount(mortgageePaymentsDtlTO.getRemainingLoanPrinicipalAmount());
        entity.setRateOfInterestPerAnnum(mortgageePaymentsDtlTO.getRateOfInterestPerAnnum());
        entity.setPaymentCycle(mortgageePaymentsDtlTO.getPaymentCycle());
    //    entity.setPaymentCycleDueDate(CommonUtil.convertStringToDate(mortgageePaymentsDtlTO.getPaymentCycleDueDate()));
        entity.setPaymentCycleDueDate(mortgageePaymentsDtlTO.getPaymentCycleDueDate());
        entity.setPaymentAmountPerCycel(mortgageePaymentsDtlTO.getPaymentAmountPerCycel());
        entity.setTaxAmount(mortgageePaymentsDtlTO.getTaxAmount());
        entity.setNetAmount(mortgageePaymentsDtlTO.getNetAmount());
        entity.setReceiverBank(mortgageePaymentsDtlTO.getReceiverBank());
        entity.setReceiverBankCode(mortgageePaymentsDtlTO.getReceiverBankCode());
        entity.setReceiverBankAC(mortgageePaymentsDtlTO.getReceiverBankAC());
        entity.setAccountStatus(mortgageePaymentsDtlTO.getAccountStatus());
        entity.setStatus(mortgageePaymentsDtlTO.getStatus());
        if (CommonUtil.isNonBlankLong(mortgageePaymentsDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(mortgageePaymentsDtlTO.getFixedAssetid());
            entity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }

        return entity;

    }

}
