package com.rjtech.register.service.handler.fixedassets;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.RevenueSalesDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.RevenueSalesDtlEntity;
import com.rjtech.register.fixedassets.req.RevenueSalesSaveReq;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;

public class RevenueSalesDtlHandler {

    public static RevenueSalesDtlTO convertEntityToPOJO(RevenueSalesDtlEntity entity) {
        RevenueSalesDtlTO revenueSalesDtlTO = new RevenueSalesDtlTO();
        revenueSalesDtlTO.setId(entity.getId());
        revenueSalesDtlTO.setEffectiveDate(CommonUtil.convertDateToString(entity.getEffectiveDate()));
        revenueSalesDtlTO.setBuyerName(entity.getBuyerName());
        revenueSalesDtlTO.setBuyerAddress(entity.getBuyerAddress());
        revenueSalesDtlTO.setTotalSaleAmount(entity.getTotalSaleAmount());
        revenueSalesDtlTO.setInitialDepositAmount(entity.getInitialDepositAmount());
        revenueSalesDtlTO.setRemainingSaleAmount(entity.getRemainingSaleAmount());
        revenueSalesDtlTO.setPaymentTermsForRemainingAmount(entity.getPaymentTermsForRemainingAmount());
        revenueSalesDtlTO.setDueDateForSinglePayent(CommonUtil.convertDateToString(entity.getDueDateForSinglePayent()));
        revenueSalesDtlTO.setNumberOfPartPayments(entity.getNumberOfPartPayments());
        revenueSalesDtlTO.setPaymentCycleForInstallments(entity.getPaymentCycleForInstallments());
        revenueSalesDtlTO.setDueDatePerCycle(CommonUtil.convertDateToString(entity.getDueDatePerCycle()));
        revenueSalesDtlTO
                .setFirstInstallmentDueDate(CommonUtil.convertDateToString(entity.getFirstInstallmentDueDate()));
        revenueSalesDtlTO
                .setLastInstallmentsDueDate(CommonUtil.convertDateToString(entity.getLastInstallmentsDueDate()));
        revenueSalesDtlTO.setPrincipalAmountPerCycle(entity.getPrincipalAmountPerCycle());
        revenueSalesDtlTO.setRateOfInterestPerAmount(entity.getRateOfInterestPerAmount());
        revenueSalesDtlTO.setGrossAmountDuePerCycle(entity.getGrossAmountDuePerCycle());
        revenueSalesDtlTO.setPayerBankName(entity.getPayerBankName());
        revenueSalesDtlTO.setPayerBankCode(entity.getPayerBankCode());
        revenueSalesDtlTO.setPayerBankAccount(entity.getPayerBankAccount());
        revenueSalesDtlTO.setAccountStatus(entity.getAccountStatus());
        ClientRegEntity clientRegEntity = entity.getClientId();
        if (null != clientRegEntity) {
            revenueSalesDtlTO.setClientId(clientRegEntity.getClientId());
        }

        FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

        if (null != fixedAssetsRegisterDtlEntity) {
            revenueSalesDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
        }

        return revenueSalesDtlTO;
    }

    public static List<RevenueSalesDtlEntity> populateEntitisFromPOJO(RevenueSalesSaveReq revenueSalesSaveReq) {
        List<RevenueSalesDtlEntity> entities = new ArrayList<RevenueSalesDtlEntity>();
        RevenueSalesDtlEntity entity = null;
        for (RevenueSalesDtlTO revenueSalesDtlTO : revenueSalesSaveReq.getRevenueSalesDtlTOs()) {
            entity = new RevenueSalesDtlEntity();
            converRevenueSalesPOJOToEntity(entity, revenueSalesDtlTO);
            entities.add(entity);
        }
        return entities;
    }

    private static RevenueSalesDtlEntity converRevenueSalesPOJOToEntity(RevenueSalesDtlEntity entity,
            RevenueSalesDtlTO revenueSalesDtlTO) {

        if (CommonUtil.isNonBlankLong(revenueSalesDtlTO.getId())) {
            entity.setId(revenueSalesDtlTO.getId());
        }
        entity.setEffectiveDate(CommonUtil.convertDDMMYYYYStringToDate(revenueSalesDtlTO.getEffectiveDate()));

        entity.setBuyerName(revenueSalesDtlTO.getBuyerName());
        entity.setBuyerAddress(revenueSalesDtlTO.getBuyerAddress());
        entity.setTotalSaleAmount(revenueSalesDtlTO.getTotalSaleAmount());
        entity.setInitialDepositAmount(revenueSalesDtlTO.getInitialDepositAmount());
        entity.setRemainingSaleAmount(revenueSalesDtlTO.getRemainingSaleAmount());
        entity.setPaymentTermsForRemainingAmount(revenueSalesDtlTO.getPaymentTermsForRemainingAmount());
        entity.setDueDateForSinglePayent(
                CommonUtil.convertDDMMYYYYStringToDate(revenueSalesDtlTO.getDueDateForSinglePayent()));
        entity.setNumberOfPartPayments(revenueSalesDtlTO.getNumberOfPartPayments());
        entity.setPaymentCycleForInstallments(revenueSalesDtlTO.getPaymentCycleForInstallments());
        entity.setDueDatePerCycle(CommonUtil.convertDDMMYYYYStringToDate(revenueSalesDtlTO.getDueDatePerCycle()));
        entity.setFirstInstallmentDueDate(
                CommonUtil.convertDDMMYYYYStringToDate(revenueSalesDtlTO.getFirstInstallmentDueDate()));
        entity.setLastInstallmentsDueDate(
                CommonUtil.convertDDMMYYYYStringToDate(revenueSalesDtlTO.getLastInstallmentsDueDate()));
        entity.setPrincipalAmountPerCycle(revenueSalesDtlTO.getPrincipalAmountPerCycle());
        entity.setRateOfInterestPerAmount(revenueSalesDtlTO.getRateOfInterestPerAmount());
        entity.setGrossAmountDuePerCycle(revenueSalesDtlTO.getGrossAmountDuePerCycle());

        /*if (CommonUtil.isNonBlankLong(AppUserUtils.getClientId())) {
        	ClientRegEntity clientRegMstrEntity = new ClientRegEntity();
        	clientRegMstrEntity.setClientId(AppUserUtils.getClientId());
        	entity.setClientRegEntity(clientRegMstrEntity);
        }*/
        if (CommonUtil.isNonBlankLong(revenueSalesDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = new FixedAssetsRegisterDtlEntity();
            fixedAssetsRegisterDtlEntity.setFixedAssetid(revenueSalesDtlTO.getFixedAssetid());
            entity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }

        entity.setStatus(revenueSalesDtlTO.getStatus());
        entity.setPayerBankName(revenueSalesDtlTO.getPayerBankName());
        entity.setPayerBankCode(revenueSalesDtlTO.getPayerBankCode());
        entity.setPayerBankAccount(revenueSalesDtlTO.getPayerBankAccount());
        entity.setAccountStatus(revenueSalesDtlTO.getAccountStatus());

        return entity;
    }

    public static RevenueSalesDtlEntity convertPOJOToEntity(RevenueSalesDtlEntity entity,
            RevenueSalesDtlTO revenueSalesDtlTO, FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {

        if (CommonUtil.isNonBlankLong(revenueSalesDtlTO.getId())) {
            entity.setId(revenueSalesDtlTO.getId());
        }
        entity.setEffectiveDate(CommonUtil.convertStringToDate(revenueSalesDtlTO.getEffectiveDate()));

        entity.setBuyerName(revenueSalesDtlTO.getBuyerName());
        entity.setBuyerAddress(revenueSalesDtlTO.getBuyerAddress());
        entity.setTotalSaleAmount(revenueSalesDtlTO.getTotalSaleAmount());
        entity.setInitialDepositAmount(revenueSalesDtlTO.getInitialDepositAmount());
        entity.setRemainingSaleAmount(revenueSalesDtlTO.getRemainingSaleAmount());
        entity.setPaymentTermsForRemainingAmount(revenueSalesDtlTO.getPaymentTermsForRemainingAmount());
        entity.setDueDateForSinglePayent(CommonUtil.convertStringToDate(revenueSalesDtlTO.getDueDateForSinglePayent()));
        entity.setNumberOfPartPayments(revenueSalesDtlTO.getNumberOfPartPayments());
        entity.setPaymentCycleForInstallments(revenueSalesDtlTO.getPaymentCycleForInstallments());
        entity.setDueDatePerCycle(CommonUtil.convertStringToDate(revenueSalesDtlTO.getDueDatePerCycle()));
        entity.setFirstInstallmentDueDate(
                CommonUtil.convertStringToDate(revenueSalesDtlTO.getFirstInstallmentDueDate()));
        entity.setLastInstallmentsDueDate(
                CommonUtil.convertStringToDate(revenueSalesDtlTO.getLastInstallmentsDueDate()));
        entity.setPrincipalAmountPerCycle(revenueSalesDtlTO.getPrincipalAmountPerCycle());
        entity.setRateOfInterestPerAmount(revenueSalesDtlTO.getRateOfInterestPerAmount());
        entity.setGrossAmountDuePerCycle(revenueSalesDtlTO.getGrossAmountDuePerCycle());
        entity.setStatus(revenueSalesDtlTO.getStatus());

        entity.setPayerBankName(revenueSalesDtlTO.getPayerBankName());
        entity.setPayerBankCode(revenueSalesDtlTO.getPayerBankCode());
        entity.setPayerBankAccount(revenueSalesDtlTO.getPayerBankAccount());
        entity.setAccountStatus(revenueSalesDtlTO.getAccountStatus());

        if (CommonUtil.isNonBlankLong(revenueSalesDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(revenueSalesDtlTO.getFixedAssetid());
            entity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }

        return entity;

    }

}
