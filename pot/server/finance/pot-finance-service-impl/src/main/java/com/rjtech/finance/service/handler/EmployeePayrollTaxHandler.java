package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.EmployeePayRollTaxTO;
import com.rjtech.finance.model.EmployeePayRollTaxEntity;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;

public class EmployeePayrollTaxHandler {

    public static EmployeePayRollTaxTO convertEntityToPOJO(EmployeePayRollTaxEntity employeePayRollEntity) {
        EmployeePayRollTaxTO employeePayRollTaxTO = new EmployeePayRollTaxTO();
        employeePayRollTaxTO.setId(employeePayRollEntity.getId());
        if (CommonUtil.objectNotNull(employeePayRollEntity.getTaxCodeCountryProvisionEntity())) {
            employeePayRollTaxTO
                    .setTaxCodeCountryProvisionId(employeePayRollEntity.getTaxCodeCountryProvisionEntity().getId());
        }
        employeePayRollTaxTO.setAnnualMinTax(employeePayRollEntity.getAnnualMinTax());
        employeePayRollTaxTO.setAnnualMaxTax(employeePayRollEntity.getAnnualMaxTax());
        employeePayRollTaxTO.setFixedTax(employeePayRollEntity.getFixedTax());
        employeePayRollTaxTO.setVariableTax(employeePayRollEntity.getVariableTax());
        employeePayRollTaxTO.setComments(employeePayRollEntity.getComments());

        employeePayRollTaxTO.setStatus(employeePayRollEntity.getStatus());
        return employeePayRollTaxTO;
    }

    public static List<EmployeePayRollTaxEntity> convertPOJOToEntity(List<EmployeePayRollTaxTO> employeePayRollTaxTOs) {
        List<EmployeePayRollTaxEntity> employeePayRollEntities = new ArrayList<EmployeePayRollTaxEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        EmployeePayRollTaxEntity employeePayRollEntity = null;
        for (EmployeePayRollTaxTO employeePayRollTaxTO : employeePayRollTaxTOs) {
            employeePayRollEntity = convertPOJOToEntity(employeePayRollTaxTO);
            employeePayRollEntities.add(employeePayRollEntity);
        }
        return employeePayRollEntities;

    }

    public static EmployeePayRollTaxEntity convertPOJOToEntity(EmployeePayRollTaxTO employeePayRollTaxTO) {
        EmployeePayRollTaxEntity employeePayRollEntity;
        employeePayRollEntity = new EmployeePayRollTaxEntity();
        if (CommonUtil.isNonBlankLong(employeePayRollTaxTO.getId())) {
            employeePayRollEntity.setId(employeePayRollTaxTO.getId());
        }
        if (CommonUtil.isNonBlankLong(employeePayRollTaxTO.getTaxCodeCountryProvisionId())) {
            TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity = new TaxCodeCountryProvisionEntity();
            taxCodeCountryProvisionEntity.setId(employeePayRollTaxTO.getTaxCodeCountryProvisionId());
            employeePayRollEntity.setTaxCodeCountryProvisionEntity(taxCodeCountryProvisionEntity);
        }
        employeePayRollEntity.setAnnualMinTax(employeePayRollTaxTO.getAnnualMinTax());
        employeePayRollEntity.setAnnualMaxTax(employeePayRollTaxTO.getAnnualMaxTax());
        employeePayRollEntity.setFixedTax(employeePayRollTaxTO.getFixedTax());
        employeePayRollEntity.setVariableTax(employeePayRollTaxTO.getVariableTax());
        employeePayRollEntity.setComments(employeePayRollTaxTO.getComments());

        employeePayRollEntity.setStatus(employeePayRollTaxTO.getStatus());
        return employeePayRollEntity;
    }

}
