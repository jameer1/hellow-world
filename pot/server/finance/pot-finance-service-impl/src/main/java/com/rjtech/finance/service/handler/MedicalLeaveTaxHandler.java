package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.MedicalLeaveTaxTO;
import com.rjtech.finance.model.MedicalTaxEntity;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;

public class MedicalLeaveTaxHandler {

    public static MedicalLeaveTaxTO convertEntityToPOJO(MedicalTaxEntity medicalTaxEntity) {
        MedicalLeaveTaxTO medicalLeaveTaxTO = new MedicalLeaveTaxTO();
        medicalLeaveTaxTO.setId(medicalTaxEntity.getId());

        if (CommonUtil.objectNotNull(medicalTaxEntity.getTaxCodeCountryProvisionEntity())) {
            medicalLeaveTaxTO.setTaxCodeCountryProvisionId(medicalTaxEntity.getTaxCodeCountryProvisionEntity().getId());
        }
        medicalLeaveTaxTO.setAnnualMinTax(medicalTaxEntity.getAnnualMinTax());
        medicalLeaveTaxTO.setAnnualMaxTax(medicalTaxEntity.getAnnualMaxTax());
        medicalLeaveTaxTO.setFixedTax(medicalTaxEntity.getFixedTax());
        medicalLeaveTaxTO.setVariableTax(medicalTaxEntity.getVariableTax());
        medicalLeaveTaxTO.setComments(medicalTaxEntity.getComments());

        medicalLeaveTaxTO.setStatus(medicalTaxEntity.getStatus());
        return medicalLeaveTaxTO;
    }

    public static List<MedicalTaxEntity> convertPOJOToEntity(List<MedicalLeaveTaxTO> medicalLeaveTaxTOs) {
        List<MedicalTaxEntity> medicalTaxEntities = new ArrayList<MedicalTaxEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        MedicalTaxEntity medicalTaxEntity = null;
        for (MedicalLeaveTaxTO medicalLeaveTaxTO : medicalLeaveTaxTOs) {
            medicalTaxEntity = convertPOJOToEntity(medicalLeaveTaxTO);
            medicalTaxEntities.add(medicalTaxEntity);
        }
        return medicalTaxEntities;

    }

    public static MedicalTaxEntity convertPOJOToEntity(MedicalLeaveTaxTO medicalLeaveTaxTO) {
        MedicalTaxEntity medicalTaxEntity;
        medicalTaxEntity = new MedicalTaxEntity();
        if (CommonUtil.isNonBlankLong(medicalLeaveTaxTO.getId())) {
            medicalTaxEntity.setId(medicalLeaveTaxTO.getId());
        }
        if (CommonUtil.isNonBlankLong(medicalLeaveTaxTO.getTaxCodeCountryProvisionId())) {
            TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity = new TaxCodeCountryProvisionEntity();
            taxCodeCountryProvisionEntity.setId(medicalLeaveTaxTO.getTaxCodeCountryProvisionId());
            medicalTaxEntity.setTaxCodeCountryProvisionEntity(taxCodeCountryProvisionEntity);
        }
        medicalTaxEntity.setAnnualMinTax(medicalLeaveTaxTO.getAnnualMinTax());
        medicalTaxEntity.setAnnualMaxTax(medicalLeaveTaxTO.getAnnualMaxTax());
        medicalTaxEntity.setFixedTax(medicalLeaveTaxTO.getFixedTax());
        medicalTaxEntity.setVariableTax(medicalLeaveTaxTO.getVariableTax());
        medicalTaxEntity.setComments(medicalLeaveTaxTO.getComments());

        medicalTaxEntity.setStatus(medicalLeaveTaxTO.getStatus());
        return medicalTaxEntity;
    }

}
