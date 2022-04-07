package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.CompanyTaxTO;
import com.rjtech.finance.model.CompanyTaxEntity;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;

public class CompanyTaxHandler {

    public static CompanyTaxTO convertEntityToPOJO(CompanyTaxEntity companyTaxEntity) {
        CompanyTaxTO companyTaxTO = new CompanyTaxTO();
        companyTaxTO.setId(companyTaxEntity.getId());
        if (CommonUtil.objectNotNull(companyTaxEntity.getTaxCodeCountryProvisionEntity())) {
            companyTaxTO.setTaxCodeCountryProvisionId(companyTaxEntity.getTaxCodeCountryProvisionEntity().getId());
        }

        companyTaxTO.setAnnualMinTax(companyTaxEntity.getAnnualMinTax());
        companyTaxTO.setAnnualMaxTax(companyTaxEntity.getAnnualMaxTax());
        companyTaxTO.setFixedTax(companyTaxEntity.getFixedTax());
        companyTaxTO.setVariableTax(companyTaxEntity.getVariableTax());
        companyTaxTO.setComments(companyTaxEntity.getComments());

        companyTaxTO.setStatus(companyTaxEntity.getStatus());
        return companyTaxTO;
    }

    public static List<CompanyTaxEntity> convertPOJOToEntity(List<CompanyTaxTO> companyTaxTOs) {
        List<CompanyTaxEntity> companyTaxEntities = new ArrayList<CompanyTaxEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        CompanyTaxEntity companyTaxEntity = null;
        for (CompanyTaxTO companyTaxTO : companyTaxTOs) {
            companyTaxEntity = new CompanyTaxEntity();
            if (CommonUtil.isNonBlankLong(companyTaxTO.getId())) {
                companyTaxEntity.setId(companyTaxTO.getId());
            }
            if (CommonUtil.isNonBlankLong(companyTaxTO.getTaxCodeCountryProvisionId())) {
                TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity = new TaxCodeCountryProvisionEntity();
                taxCodeCountryProvisionEntity.setId(companyTaxTO.getTaxCodeCountryProvisionId());
                companyTaxEntity.setTaxCodeCountryProvisionEntity(taxCodeCountryProvisionEntity);
            }
            companyTaxEntity.setAnnualMinTax(companyTaxTO.getAnnualMinTax());
            companyTaxEntity.setAnnualMaxTax(companyTaxTO.getAnnualMaxTax());
            companyTaxEntity.setFixedTax(companyTaxTO.getFixedTax());
            companyTaxEntity.setVariableTax(companyTaxTO.getVariableTax());
            companyTaxEntity.setComments(companyTaxTO.getComments());

            companyTaxEntity.setStatus(companyTaxTO.getStatus());
            companyTaxEntities.add(companyTaxEntity);
        }
        return companyTaxEntities;

    }

}
