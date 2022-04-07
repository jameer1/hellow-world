package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ServiceTaxTO;
import com.rjtech.finance.model.ServiceTaxEntity;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;

public class ServiceTaxHandler {

    public static ServiceTaxTO convertEntityToPOJO(ServiceTaxEntity serviceTaxEntity) {
        ServiceTaxTO serviceTaxTO = new ServiceTaxTO();
        serviceTaxTO.setId(serviceTaxEntity.getId());
        if (CommonUtil.objectNotNull(serviceTaxEntity.getTaxCodeCountryProvisionEntity())) {
            serviceTaxTO.setTaxCodeCountryProvisionId(serviceTaxEntity.getTaxCodeCountryProvisionEntity().getId());
        }
        serviceTaxTO.setInvoiceAmount(serviceTaxEntity.getInvoiceAmount());
        serviceTaxTO.setTaxRate(serviceTaxEntity.getTaxRate());
        serviceTaxTO.setComments(serviceTaxEntity.getComments());

        serviceTaxTO.setStatus(serviceTaxEntity.getStatus());
        return serviceTaxTO;
    }

    public static List<ServiceTaxEntity> convertPOJOToEntity(List<ServiceTaxTO> serviceTaxTOs) {
        List<ServiceTaxEntity> serviceTaxEntities = new ArrayList<ServiceTaxEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ServiceTaxEntity serviceTaxEntity = null;
        for (ServiceTaxTO serviceTaxTO : serviceTaxTOs) {
            serviceTaxEntity = converPOJOToEntity(serviceTaxTO);
            serviceTaxEntities.add(serviceTaxEntity);
        }
        return serviceTaxEntities;

    }

    public static ServiceTaxEntity converPOJOToEntity(ServiceTaxTO serviceTaxTO) {
        ServiceTaxEntity serviceTaxEntity;
        serviceTaxEntity = new ServiceTaxEntity();
        if (CommonUtil.isNonBlankLong(serviceTaxTO.getId())) {
            serviceTaxEntity.setId(serviceTaxTO.getId());
        }
        if (CommonUtil.isNonBlankLong(serviceTaxTO.getTaxCodeCountryProvisionId())) {
            TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity = new TaxCodeCountryProvisionEntity();
            taxCodeCountryProvisionEntity.setId(serviceTaxTO.getTaxCodeCountryProvisionId());
            serviceTaxEntity.setTaxCodeCountryProvisionEntity(taxCodeCountryProvisionEntity);
        }
        serviceTaxEntity.setInvoiceAmount(serviceTaxTO.getInvoiceAmount());
        serviceTaxEntity.setTaxRate(serviceTaxTO.getTaxRate());
        serviceTaxEntity.setComments(serviceTaxTO.getComments());

        serviceTaxEntity.setStatus(serviceTaxTO.getStatus());
        return serviceTaxEntity;
    }

}
