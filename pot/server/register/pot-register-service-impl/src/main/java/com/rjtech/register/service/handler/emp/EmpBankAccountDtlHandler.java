package com.rjtech.register.service.handler.emp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpBankAccountDtlTO;
import com.rjtech.register.emp.model.EmpBankAccountDtlEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpBankAccountDtlHandler {

    public static EmpBankAccountDtlTO convertEntityToPOJO(EmpBankAccountDtlEntity entity) {
        EmpBankAccountDtlTO empBankAccountDtlTO = new EmpBankAccountDtlTO();
        empBankAccountDtlTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empBankAccountDtlTO.setEmpRegDtlId(entity.getEmpRegisterDtlEntity().getId());
        }
        empBankAccountDtlTO.setBankName(entity.getBankName());
        empBankAccountDtlTO.setAddress(entity.getAddress());
        empBankAccountDtlTO.setIfscCode(entity.getIfscCode());
        empBankAccountDtlTO.setAccName(entity.getAccName());
        empBankAccountDtlTO.setAccNumber(entity.getAccNumber());
        empBankAccountDtlTO.setAccType(entity.getAccType());
        empBankAccountDtlTO.setAccStatus(entity.getAccStatus());
        empBankAccountDtlTO.setAccComments(entity.getAccComments());
        empBankAccountDtlTO.setStatus(entity.getStatus());
        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empBankAccountDtlTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        return empBankAccountDtlTO;
    }

    public static List<EmpBankAccountDtlEntity> convertPOJOsToEntities(List<EmpBankAccountDtlTO> empRegisterDtlTOs,
            EmpRegisterRepository empRegisterRepository) {
        List<EmpBankAccountDtlEntity> empBankAccountDtlEntites = new ArrayList<EmpBankAccountDtlEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (EmpBankAccountDtlTO empBankAccountDtlTO : empRegisterDtlTOs) {
            empBankAccountDtlEntites.add(convertPOJOToEntity(empBankAccountDtlTO, empRegisterRepository));
        }
        return empBankAccountDtlEntites;
    }

    public static EmpBankAccountDtlEntity convertPOJOToEntity(EmpBankAccountDtlTO empBankAccountDtlTO,
            EmpRegisterRepository empRegisterRepository) {
        EmpBankAccountDtlEntity entity = new EmpBankAccountDtlEntity();
        if (CommonUtil.isNonBlankLong(empBankAccountDtlTO.getId())) {
            entity.setId(empBankAccountDtlTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empBankAccountDtlTO.getEmpRegDtlId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(empBankAccountDtlTO.getEmpRegDtlId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        entity.setBankName(empBankAccountDtlTO.getBankName());
        entity.setAddress(empBankAccountDtlTO.getAddress());
        entity.setIfscCode(empBankAccountDtlTO.getIfscCode());
        entity.setAccName(empBankAccountDtlTO.getAccName());
        entity.setAccNumber(empBankAccountDtlTO.getAccNumber());
        entity.setAccType(empBankAccountDtlTO.getAccType());
        entity.setAccStatus(empBankAccountDtlTO.getAccStatus());
        entity.setAccComments(empBankAccountDtlTO.getAccComments());
        entity.setStatus(empBankAccountDtlTO.getStatus());
        if (CommonUtil.isNotBlankStr(empBankAccountDtlTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empBankAccountDtlTO.getFromDate()));
        }
        return entity;
    }

}
