package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.EmployeeWiseCycleTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.EmployeeWiseCycleEntity;
import com.rjtech.finance.repository.PayPeriodCycleRepository;

public class EmployeeTypeHandler {

    public static EmployeeWiseCycleTO convertEntityToPOJO(EmployeeWiseCycleEntity employeeWiseCycleEntity) {
        EmployeeWiseCycleTO employeeWiseCycleTO = new EmployeeWiseCycleTO();
        employeeWiseCycleTO.setId(employeeWiseCycleEntity.getId());

        if (CommonUtil.objectNotNull(employeeWiseCycleEntity.getCodeTypesEntity())) {
            employeeWiseCycleTO.setCodeTypeCountryProvisionId(employeeWiseCycleEntity.getCodeTypesEntity().getId());
        }
        employeeWiseCycleTO.setTaxCode(employeeWiseCycleEntity.getTaxCode());
        employeeWiseCycleTO.setTaxDesc(employeeWiseCycleEntity.getTaxDesc());
        employeeWiseCycleTO.setProcureMstrId(employeeWiseCycleEntity.getProcureCategoryId().getId());
        employeeWiseCycleTO.setPayRollCycleMstrId(employeeWiseCycleEntity.getPayRollEntityId().getId());
        employeeWiseCycleTO.setComments(employeeWiseCycleEntity.getComments());
        employeeWiseCycleTO.setEmpCategory(employeeWiseCycleEntity.getEmpCategory());
        LabelKeyTO procureCatgLabelKey = new LabelKeyTO();
        procureCatgLabelKey.setId(employeeWiseCycleEntity.getProcureCategoryId().getId());
        employeeWiseCycleTO.setProcureCatgLabelKey(procureCatgLabelKey);

        LabelKeyTO payRollCycleLabelKeyTO = new LabelKeyTO();
        payRollCycleLabelKeyTO.setId(employeeWiseCycleEntity.getPayRollEntityId().getId());
        employeeWiseCycleTO.setPayRollCycleLabelKeyTO(payRollCycleLabelKeyTO);

        employeeWiseCycleTO.setStatus(employeeWiseCycleEntity.getStatus());
        return employeeWiseCycleTO;
    }

    public static List<EmployeeWiseCycleEntity> convertPOJOToEntity(List<EmployeeWiseCycleTO> employeeWiseCycleTOs,
            ProcureCatgRepository procureCatgRepository, PayPeriodCycleRepository payPeriodCycleRepository) {
        List<EmployeeWiseCycleEntity> employeeWiseCycleEntities = new ArrayList<EmployeeWiseCycleEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        EmployeeWiseCycleEntity employeeWiseCycleEntity = null;
        for (EmployeeWiseCycleTO employeeWiseCycleTO : employeeWiseCycleTOs) {
            employeeWiseCycleEntity = convertPOJOToEntity(employeeWiseCycleTO, procureCatgRepository,
                    payPeriodCycleRepository);
            employeeWiseCycleEntities.add(employeeWiseCycleEntity);
        }
        return employeeWiseCycleEntities;

    }

    public static EmployeeWiseCycleEntity convertPOJOToEntity(EmployeeWiseCycleTO employeeWiseCycleTO,
            ProcureCatgRepository procureCatgRepository, PayPeriodCycleRepository payPeriodCycleRepository) {
        EmployeeWiseCycleEntity employeeWiseCycleEntity = new EmployeeWiseCycleEntity();
        if (CommonUtil.isNonBlankLong(employeeWiseCycleTO.getId())) {
            employeeWiseCycleEntity.setId(employeeWiseCycleTO.getId());
        }

        if (CommonUtil.isNonBlankLong(employeeWiseCycleTO.getCodeTypeCountryProvisionId())) {
            CodeTypesEntity codeTypesEntity = new CodeTypesEntity();
            codeTypesEntity.setId(employeeWiseCycleTO.getCodeTypeCountryProvisionId());
            employeeWiseCycleEntity.setCodeTypesEntity(codeTypesEntity);
        }

        employeeWiseCycleEntity.setTaxCode(employeeWiseCycleTO.getTaxCode());
        employeeWiseCycleEntity.setTaxDesc(employeeWiseCycleTO.getTaxDesc());
        employeeWiseCycleEntity.setEmpCategory(employeeWiseCycleTO.getEmpCategory());
        employeeWiseCycleEntity.setProcureCategoryId(
                procureCatgRepository.findOne(employeeWiseCycleTO.getProcureCatgLabelKey().getId()));
        employeeWiseCycleEntity.setPayRollEntityId(
                payPeriodCycleRepository.findOne(employeeWiseCycleTO.getPayRollCycleLabelKeyTO().getId()));
        employeeWiseCycleEntity.setComments(employeeWiseCycleTO.getComments());

        employeeWiseCycleEntity.setStatus(employeeWiseCycleTO.getStatus());
        return employeeWiseCycleEntity;
    }

}
