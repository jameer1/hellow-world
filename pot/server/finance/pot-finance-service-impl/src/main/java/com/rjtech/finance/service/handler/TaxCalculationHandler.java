package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.dto.CodeTypesTO;
import com.rjtech.finance.dto.TaxCodeCountryProvisionTO;
import com.rjtech.finance.dto.TaxCodesTO;
import com.rjtech.finance.dto.TaxCountryProvisionTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;
import com.rjtech.finance.model.TaxCodesEntity;
import com.rjtech.finance.model.TaxCountryProvisionEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class TaxCalculationHandler {

    public static TaxCodesTO convertTaxCodeEntityToPOJO(TaxCodesEntity taxCodesEntity) {
        TaxCodesTO taxCalculationMstrTO = new TaxCodesTO();
        taxCalculationMstrTO.setId(taxCodesEntity.getId());
        taxCalculationMstrTO.setCode(taxCodesEntity.getCode());
        taxCalculationMstrTO.setName(taxCodesEntity.getName());
        taxCalculationMstrTO.setTaxType(taxCodesEntity.getTaxType());
        taxCalculationMstrTO.setStatus(taxCodesEntity.getStatus());
        return taxCalculationMstrTO;
    }

    public static CodeTypesTO convertCodeTypesEntityToPOJO(CodeTypesEntity codeTypesEntity) {
        CodeTypesTO codeTypesTO = new CodeTypesTO();
        codeTypesTO.setId(codeTypesEntity.getId());
        codeTypesTO.setFinanceCodeType(codeTypesEntity.getFinanceCodeType());
        codeTypesTO.setTaxType(codeTypesEntity.getType());
        if (CommonUtil.objectNotNull(codeTypesEntity.getTaxCountryProvisionEntity())) {
            codeTypesTO.setTaxCountryProvsionId(codeTypesEntity.getTaxCountryProvisionEntity().getId());
        }
        codeTypesTO.setStatus(codeTypesEntity.getStatus());
        return codeTypesTO;
    }

    public static CodeTypesEntity convertCodeTypePOJOToEntity(CodeTypesTO codeTypesTO) {
        CodeTypesEntity codeTypesEntity = new CodeTypesEntity();
        if (CommonUtil.isNonBlankLong(codeTypesTO.getId())) {
            codeTypesEntity.setId(codeTypesTO.getId());
        }
        codeTypesEntity.setFinanceCodeType(codeTypesTO.getFinanceCodeType());
        if (CommonUtil.isNonBlankLong(codeTypesTO.getTaxCountryProvsionId())) {
            TaxCountryProvisionEntity taxCountryProvisionEntity = new TaxCountryProvisionEntity();
            taxCountryProvisionEntity.setId(codeTypesTO.getTaxCountryProvsionId());
            codeTypesEntity.setTaxCountryProvisionEntity(taxCountryProvisionEntity);
        }
        codeTypesEntity.setType(codeTypesTO.getTaxType());
        codeTypesEntity.setStatus(codeTypesTO.getStatus());
        return codeTypesEntity;

    }

    public static TaxCountryProvisionTO convertTaxCountryProvisionEntityToPOJO(
            TaxCountryProvisionEntity taxCountryProvisionEntity) {

        TaxCountryProvisionTO taxCountryProvisionTO = new TaxCountryProvisionTO();
        taxCountryProvisionTO.setId(taxCountryProvisionEntity.getId());
        taxCountryProvisionTO.setCode(taxCountryProvisionEntity.getCode());

        taxCountryProvisionTO.setClientId(taxCountryProvisionEntity.getClientId().getClientId());

        LabelKeyTO countryLabelKeyTO = new LabelKeyTO();
        countryLabelKeyTO.setName(taxCountryProvisionEntity.getCountryName());
        taxCountryProvisionTO.setCountryLabelKeyTO(countryLabelKeyTO);

        LabelKeyTO provisionLabelKeyTO = new LabelKeyTO();
        provisionLabelKeyTO.setName(taxCountryProvisionEntity.getProvision());
        taxCountryProvisionTO.setProvisionLabelKeyTO(provisionLabelKeyTO);

        taxCountryProvisionTO.setFromDate(CommonUtil.convertDateToString(taxCountryProvisionEntity.getFromDate()));
        taxCountryProvisionTO.setToDate(CommonUtil.convertDateToString(taxCountryProvisionEntity.getToDate()));
        taxCountryProvisionTO
                .setEffectiveDate(CommonUtil.convertDateToString(taxCountryProvisionEntity.getEffectiveDate()));
        taxCountryProvisionTO.setStatus(taxCountryProvisionEntity.getStatus());

        return taxCountryProvisionTO;
    }

    public static TaxCodeCountryProvisionTO convertTaxCodeCountryProvisionEntityToPOJO(
            TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity) {
        TaxCodeCountryProvisionTO taxCodeCountryProvisionTO = new TaxCodeCountryProvisionTO();
        taxCodeCountryProvisionTO.setId(taxCodeCountryProvisionEntity.getId());
        if (CommonUtil.objectNotNull(taxCodeCountryProvisionEntity.getTaxCodesEntity())) {
            taxCodeCountryProvisionTO
                    .setTaxCodesTO(convertTaxCodeEntityToPOJO(taxCodeCountryProvisionEntity.getTaxCodesEntity()));
        }

        if (CommonUtil.objectNotNull(taxCodeCountryProvisionEntity.getTaxCountryProvisionEntity())) {
            taxCodeCountryProvisionTO
                    .setTaxCountryProvsionId(taxCodeCountryProvisionEntity.getTaxCountryProvisionEntity().getId());
        }

        taxCodeCountryProvisionTO.setPeriodCycle(taxCodeCountryProvisionEntity.getPeriodCycle());
        if (CommonUtil.isNotBlankDate(taxCodeCountryProvisionEntity.getDueDate())) {
            taxCodeCountryProvisionTO
                    .setDueDate(CommonUtil.convertDateToString(taxCodeCountryProvisionEntity.getDueDate()));
        }
        taxCodeCountryProvisionTO.setStatus(taxCodeCountryProvisionEntity.getStatus());
        return taxCodeCountryProvisionTO;
    }

    public static List<TaxCodesEntity> populateTaxCodeEntities(List<TaxCodesTO> taxCodesTOs,
            ClientRegRepository clientRegRepository) {
        List<TaxCodesEntity> taxCodesEntities = new ArrayList<TaxCodesEntity>();
        TaxCodesEntity taxCodesEntity = null;
        for (TaxCodesTO taxCodesTO : taxCodesTOs) {
            taxCodesEntity = convertPOJOToEntity(taxCodesTO, clientRegRepository);
            taxCodesEntities.add(taxCodesEntity);
        }
        return taxCodesEntities;
    }

    public static TaxCodesEntity convertPOJOToEntity(TaxCodesTO taxCodesTO, ClientRegRepository clientRegRepository) {
        TaxCodesEntity taxCodesEntity;
        taxCodesEntity = new TaxCodesEntity();
        if (CommonUtil.isNonBlankLong(taxCodesTO.getId())) {
            taxCodesEntity.setId(taxCodesTO.getId());
        }

        if (CommonUtil.isNonBlankLong(taxCodesTO.getClientId()))
            taxCodesEntity.setClientId(clientRegRepository.findOne(taxCodesTO.getClientId()));

        taxCodesEntity.setCode(taxCodesTO.getCode());
        taxCodesEntity.setName(taxCodesTO.getName());
        taxCodesEntity.setTaxType(taxCodesTO.getTaxType());
        taxCodesEntity.setStatus(taxCodesTO.getStatus());
        return taxCodesEntity;
    }

    public static TaxCountryProvisionEntity convertTaxCountryProvisionPOJOToEntity(
            TaxCountryProvisionTO taxCountryProvisionTO, ClientRegRepository clientRegRepository) {
        TaxCountryProvisionEntity taxCountryProvisionEntity = new TaxCountryProvisionEntity();
        taxCountryProvisionEntity = new TaxCountryProvisionEntity();
        if (CommonUtil.isNonBlankLong(taxCountryProvisionTO.getId())) {
            taxCountryProvisionEntity.setId(taxCountryProvisionTO.getId());
        }

        taxCountryProvisionEntity.setCountryName(taxCountryProvisionTO.getCountryLabelKeyTO().getName());
        taxCountryProvisionEntity
                .setCode(CommonUtil.generateCountryTaxCode(taxCountryProvisionTO.getCountryLabelKeyTO().getCode(),
                        taxCountryProvisionTO.getProvisionLabelKeyTO().getCode(), taxCountryProvisionTO.getFromDate()));
        taxCountryProvisionEntity.setProvision(taxCountryProvisionTO.getProvisionLabelKeyTO().getName());
        taxCountryProvisionEntity.setFromDate(CommonUtil.convertStringToDate(taxCountryProvisionTO.getFromDate()));
        taxCountryProvisionEntity.setToDate(CommonUtil.convertStringToDate(taxCountryProvisionTO.getToDate()));
        taxCountryProvisionEntity
                .setEffectiveDate(CommonUtil.convertStringToDate(taxCountryProvisionTO.getEffectiveDate()));

        taxCountryProvisionEntity.setClientId(clientRegRepository.findOne(AppUserUtils.getClientId()));
        taxCountryProvisionEntity.setStatus(taxCountryProvisionTO.getStatus());
        return taxCountryProvisionEntity;
    }

    public static TaxCodeCountryProvisionEntity convertTaxCodeCountryProvisionPOJOToEntity(
            TaxCodeCountryProvisionTO taxCodeCountryProvisionTO) {
        TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity = new TaxCodeCountryProvisionEntity();

        taxCodeCountryProvisionEntity.setId(taxCodeCountryProvisionTO.getId());
        if (CommonUtil.objectNotNull(taxCodeCountryProvisionTO.getTaxCodesTO())) {
            TaxCodesEntity taxCodesEntity = new TaxCodesEntity();
            taxCodesEntity.setId(taxCodeCountryProvisionTO.getTaxCodesTO().getId());
            taxCodeCountryProvisionEntity.setTaxCodesEntity(taxCodesEntity);
        }

        if (CommonUtil.isNonBlankLong(taxCodeCountryProvisionTO.getTaxCountryProvsionId())) {
            TaxCountryProvisionEntity taxCountryProvisionEntity = new TaxCountryProvisionEntity();
            taxCountryProvisionEntity.setId(taxCodeCountryProvisionTO.getTaxCountryProvsionId());
            taxCodeCountryProvisionEntity.setTaxCountryProvisionEntity(taxCountryProvisionEntity);
        }

        taxCodeCountryProvisionEntity.setPeriodCycle(taxCodeCountryProvisionTO.getPeriodCycle());
        if (CommonUtil.isNotBlankStr(taxCodeCountryProvisionTO.getDueDate())) {
            taxCodeCountryProvisionEntity
                    .setDueDate(CommonUtil.convertStringToDate(taxCodeCountryProvisionTO.getDueDate()));
        }
        taxCodeCountryProvisionEntity.setStatus(taxCodeCountryProvisionTO.getStatus());
        return taxCodeCountryProvisionEntity;

    }

}
