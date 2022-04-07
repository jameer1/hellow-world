package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.PersonalTaxTO;
import com.rjtech.finance.model.PersonalTaxEntity;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;

public class PersonalTaxHandler {

    public static PersonalTaxTO convertEntityToPOJO(PersonalTaxEntity personalTaxEntity) {
        PersonalTaxTO personalTaxTO = new PersonalTaxTO();
        personalTaxTO.setId(personalTaxEntity.getId());
        if (CommonUtil.objectNotNull(personalTaxEntity.getTaxCodeCountryProvisionEntity())) {
            personalTaxTO.setTaxCodeCountryProvisionId(personalTaxEntity.getTaxCodeCountryProvisionEntity().getId());
        }
        personalTaxTO.setAnnualMinTax(personalTaxEntity.getAnnualMinTax());
        personalTaxTO.setAnnualMaxTax(personalTaxEntity.getAnnualMaxTax());
        personalTaxTO.setFixedTax(personalTaxEntity.getFixedTax());
        personalTaxTO.setVariableTax(personalTaxEntity.getVariableTax());
        personalTaxTO.setComments(personalTaxEntity.getComments());

        personalTaxTO.setStatus(personalTaxEntity.getStatus());
        return personalTaxTO;
    }

    public static List<PersonalTaxEntity> convertPOJOToEntity(List<PersonalTaxTO> personalTaxTOs) {
        List<PersonalTaxEntity> personalTaxEntities = new ArrayList<PersonalTaxEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        PersonalTaxEntity personalTaxEntity = null;
        for (PersonalTaxTO personalTaxTO : personalTaxTOs) {
            personalTaxEntity = convertPOJOToEntity(personalTaxTO);
            personalTaxEntities.add(personalTaxEntity);
        }
        return personalTaxEntities;

    }

    public static PersonalTaxEntity convertPOJOToEntity(PersonalTaxTO personalTaxTO) {
        PersonalTaxEntity personalTaxEntity;
        personalTaxEntity = new PersonalTaxEntity();
        if (CommonUtil.isNonBlankLong(personalTaxTO.getId())) {
            personalTaxEntity.setId(personalTaxTO.getId());
        }
        if (CommonUtil.isNonBlankLong(personalTaxTO.getTaxCodeCountryProvisionId())) {
            TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity = new TaxCodeCountryProvisionEntity();
            taxCodeCountryProvisionEntity.setId(personalTaxTO.getTaxCodeCountryProvisionId());
            personalTaxEntity.setTaxCodeCountryProvisionEntity(taxCodeCountryProvisionEntity);
        }
        personalTaxEntity.setAnnualMinTax(personalTaxTO.getAnnualMinTax());
        personalTaxEntity.setAnnualMaxTax(personalTaxTO.getAnnualMaxTax());
        personalTaxEntity.setFixedTax(personalTaxTO.getFixedTax());
        personalTaxEntity.setVariableTax(personalTaxTO.getVariableTax());
        personalTaxEntity.setComments(personalTaxTO.getComments());

        personalTaxEntity.setStatus(personalTaxTO.getStatus());
        return personalTaxEntity;
    }

}
