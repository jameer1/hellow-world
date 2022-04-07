package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxOnSuperFundTaxTO;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;
import com.rjtech.finance.model.TaxOnSuperFundEntity;

public class SuperFundTaxHandler {

    public static TaxOnSuperFundTaxTO convertEntityToPOJO(TaxOnSuperFundEntity taxOnSuperFundEntity) {
        TaxOnSuperFundTaxTO taxOnSuperFundTO = new TaxOnSuperFundTaxTO();
        taxOnSuperFundTO.setId(taxOnSuperFundEntity.getId());
        if (CommonUtil.objectNotNull(taxOnSuperFundEntity.getTaxCodeCountryProvisionEntity())) {
            taxOnSuperFundTO
                    .setTaxCodeCountryProvisionId(taxOnSuperFundEntity.getTaxCodeCountryProvisionEntity().getId());
        }
        taxOnSuperFundTO.setLimitIncome(taxOnSuperFundEntity.getLimitIncome());
        taxOnSuperFundTO.setFundAmount(taxOnSuperFundEntity.getFundAmount());
        taxOnSuperFundTO.setTaxRate(taxOnSuperFundEntity.getTaxRate());
        taxOnSuperFundTO.setComments(taxOnSuperFundEntity.getComments());

        taxOnSuperFundTO.setStatus(taxOnSuperFundEntity.getStatus());
        return taxOnSuperFundTO;
    }

    public static List<TaxOnSuperFundEntity> convertPOJOToEntity(List<TaxOnSuperFundTaxTO> taxOnSuperFundTOs) {
        List<TaxOnSuperFundEntity> taxOnSuperFundEntities = new ArrayList<TaxOnSuperFundEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        TaxOnSuperFundEntity superFundEntity = null;
        for (TaxOnSuperFundTaxTO taxOnSuperFundTO : taxOnSuperFundTOs) {
            superFundEntity = convertPOJOToEntity(taxOnSuperFundTO);
            taxOnSuperFundEntities.add(superFundEntity);
        }
        return taxOnSuperFundEntities;

    }

    public static TaxOnSuperFundEntity convertPOJOToEntity(TaxOnSuperFundTaxTO taxOnSuperFundTO) {
        TaxOnSuperFundEntity superFundEntity;
        superFundEntity = new TaxOnSuperFundEntity();
        if (CommonUtil.isNonBlankLong(taxOnSuperFundTO.getId())) {
            superFundEntity.setId(taxOnSuperFundTO.getId());
        }
        if (CommonUtil.isNonBlankLong(taxOnSuperFundTO.getTaxCodeCountryProvisionId())) {
            TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity = new TaxCodeCountryProvisionEntity();
            taxCodeCountryProvisionEntity.setId(taxOnSuperFundTO.getTaxCodeCountryProvisionId());
            superFundEntity.setTaxCodeCountryProvisionEntity(taxCodeCountryProvisionEntity);
        }
        superFundEntity.setLimitIncome(taxOnSuperFundTO.getLimitIncome());
        superFundEntity.setFundAmount(taxOnSuperFundTO.getFundAmount());
        superFundEntity.setTaxRate(taxOnSuperFundTO.getTaxRate());
        superFundEntity.setComments(taxOnSuperFundTO.getComments());

        superFundEntity.setStatus(taxOnSuperFundTO.getStatus());
        return superFundEntity;
    }

}
