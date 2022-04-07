package com.rjtech.centrallib.service.handler;

import java.util.List;

import com.rjtech.centrallib.model.CountryProvinceCodeEntity;
import com.rjtech.centrallib.model.FinancialHalfYearEntity;
import com.rjtech.centrallib.model.FinancialQuarterYearEntity;
import com.rjtech.centrallib.model.FinancialYearEntity;
import com.rjtech.centrallib.req.CountryProvinceCodeSaveReq;
import com.rjtech.centrallib.resp.CountryProvinceCodeResp;
import com.rjtech.common.dto.CountryProvinceCodeTo;
import com.rjtech.common.dto.FinancialHalfYearTo;
import com.rjtech.common.dto.FinancialQuarterYearTo;
import com.rjtech.common.dto.FinancialYearTo;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;

public class CountryProvinceCodeHandler {

    public static CountryProvinceCodeResp convertEntityToPOJO(List<CountryProvinceCodeEntity> entities) {
        CountryProvinceCodeResp countryProvinceCodeResp = new CountryProvinceCodeResp();
        for (CountryProvinceCodeEntity entity : entities) {
            CountryProvinceCodeTo countryProvinceCodeTo = new CountryProvinceCodeTo();
            countryProvinceCodeTo.setId(entity.getId());
            countryProvinceCodeTo.setCountryCode(entity.getCountrycode());
            countryProvinceCodeTo.setCountryName(entity.getCountryName());
            countryProvinceCodeTo.setProvisionName(entity.getProvisionName());
            countryProvinceCodeTo.setStatus(entity.getStatus());
            FinancialYearEntity financialYearData = entity.getFinancialYearEntity();
            if (null != financialYearData) {
                FinancialYearTo financialYearTo =new FinancialYearTo();
            
                financialYearTo.setFromDate(CommonUtil.convertDateToString(financialYearData.getFromDate()));
                financialYearTo.setToDate(CommonUtil.convertDateToString(financialYearData.getToDate()));
                countryProvinceCodeTo.setFinancialYearData(financialYearTo);
            }
            FinancialHalfYearEntity financialHalfYearData = entity.getFinancialHalfYearEntity();
            if (null != financialHalfYearData) {
                FinancialHalfYearTo financialHalfYearTo = new FinancialHalfYearTo();
                financialHalfYearTo
                        .setFirstFromDate(CommonUtil.convertDateToString(financialHalfYearData.getFirstFromDate()));
                financialHalfYearTo
                        .setFirstToDate(CommonUtil.convertDateToString(financialHalfYearData.getFirstToDate()));
                financialHalfYearTo
                        .setSecondFromDate(CommonUtil.convertDateToString(financialHalfYearData.getSecondFromDate()));
                financialHalfYearTo
                        .setSecondToDate(CommonUtil.convertDateToString(financialHalfYearData.getSecondToDate()));
                countryProvinceCodeTo.setFinancialHalfYearData(financialHalfYearTo);
            }

            FinancialQuarterYearEntity financialQuarterYearData = entity.getFinancialQuarterYearEntity();
            if (null != financialQuarterYearData) {
                FinancialQuarterYearTo financialQuarterYearTo = new FinancialQuarterYearTo();
                financialQuarterYearTo.setQuarterOneFromDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterFirstFromDate()));
                financialQuarterYearTo.setQuarterOneToDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterFirstToDate()));
                financialQuarterYearTo.setQuarterSecondFromDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterSecondFromDate()));
                financialQuarterYearTo.setQuarterSecondToDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterSecondToDate()));
                financialQuarterYearTo.setQuarterThirdFromDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterThirdFromDate()));
                financialQuarterYearTo.setQuarterThirdToDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterThirdFromDate()));
                financialQuarterYearTo.setQuarterFourthFromDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterFourthFromDate()));
                financialQuarterYearTo.setQuarterFourthToDate(
                        CommonUtil.convertDateToString(financialQuarterYearData.getQuarterFourthToDate()));
                countryProvinceCodeTo.setFinancialQuarterYearData(financialQuarterYearTo);
            }

            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                countryProvinceCodeTo.setClientId(clientRegEntity.getClientId());
            }
            countryProvinceCodeResp.getCountryProvinceCodeToTOs().add(countryProvinceCodeTo);
        }
        return countryProvinceCodeResp;
    }

    public static CountryProvinceCodeEntity convertCountryProvincePOJOToEntity(
            CountryProvinceCodeSaveReq countryProvinceCodeSaveReq, CountryProvinceCodeEntity entity,
            FinancialYearEntity financialYearEntity, FinancialHalfYearEntity financialHalfYearEntity,
            FinancialQuarterYearEntity financialQuarterYearEntity) {

        if (CommonUtil.objectNotNull(countryProvinceCodeSaveReq)
                && CommonUtil.isNonBlankLong(countryProvinceCodeSaveReq.getId())) {
            entity.setId(countryProvinceCodeSaveReq.getId());
        }

        entity.setCountrycode(countryProvinceCodeSaveReq.getCountryCode());
        entity.setCountryName(countryProvinceCodeSaveReq.getCountryName());
        entity.setProvisionName(countryProvinceCodeSaveReq.getProvisionName());
        entity.setStatus(countryProvinceCodeSaveReq.getStatus());

       FinancialYearTo financialYearData = countryProvinceCodeSaveReq.getFinancialYearData();
        if (null != financialYearData) {
            financialYearEntity.setFromDate(CommonUtil.convertStringToDate(financialYearData.getFromDate()));
            financialYearEntity.setToDate(CommonUtil.convertStringToDate(financialYearData.getToDate()));
            entity.setFinancialYearEntity(financialYearEntity);
        }
        FinancialHalfYearTo financialHalfYearData = countryProvinceCodeSaveReq.getFinancialHalfYearData();
        if (null != financialHalfYearData) {
            financialHalfYearEntity
                    .setFirstFromDate(CommonUtil.convertStringToDate(financialHalfYearData.getFirstFromDate()));
            financialHalfYearEntity
                    .setFirstToDate(CommonUtil.convertStringToDate(financialHalfYearData.getFirstToDate()));
            financialHalfYearEntity
                    .setSecondFromDate(CommonUtil.convertStringToDate(financialHalfYearData.getSecondFromDate()));
            financialHalfYearEntity
                    .setSecondToDate(CommonUtil.convertStringToDate(financialHalfYearData.getSecondToDate()));
            entity.setFinancialHalfYearEntity(financialHalfYearEntity);
        }

        FinancialQuarterYearTo financialQuarterYearData = countryProvinceCodeSaveReq.getFinancialQuarterYearData();
        if (null != financialHalfYearData) {
            financialQuarterYearEntity.setQuarterFirstFromDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterOneFromDate()));
            financialQuarterYearEntity.setQuarterFirstToDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterOneToDate()));
            financialQuarterYearEntity.setQuarterSecondFromDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterSecondFromDate()));
            financialQuarterYearEntity.setQuarterSecondToDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterSecondToDate()));
            financialQuarterYearEntity.setQuarterThirdFromDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterThirdFromDate()));
            financialQuarterYearEntity.setQuarterThirdToDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterThirdFromDate()));
            financialQuarterYearEntity.setQuarterFourthFromDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterFourthFromDate()));
            financialQuarterYearEntity.setQuarterFourthToDate(
                    CommonUtil.convertStringToDate(financialQuarterYearData.getQuarterFourthToDate()));
            entity.setFinancialQuarterYearEntity(financialQuarterYearEntity);
        }

        return entity;
    }

}
