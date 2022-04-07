package com.rjtech.register.service.handler.emp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpContactDtlTO;
import com.rjtech.register.emp.model.EmpContactDtlEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpContactDtlHandler {

    public static EmpContactDtlTO convertEntityToPOJO(EmpContactDtlEntity entity) {

        EmpContactDtlTO empContactDtlTO = new EmpContactDtlTO();

        empContactDtlTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empContactDtlTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empContactDtlTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        empContactDtlTO.setResidentAddr(entity.getResidentAddr());
        empContactDtlTO.setPostalAddr(entity.getPostalAddr());
        empContactDtlTO.setPhoneNumber(entity.getPhoneNumber());
        empContactDtlTO.setAlternatePhone(entity.getAlternatePhone());
        empContactDtlTO.setEmail(entity.getEmail());

        empContactDtlTO.setStatus(entity.getStatus());

        return empContactDtlTO;
    }

    public static List<EmpContactDtlEntity> convertPOJOsToEntities(List<EmpContactDtlTO> empContactDtlTOs,
            EmpRegisterRepository empRegisterRepository) {
        List<EmpContactDtlEntity> empContactDtlEntites = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (EmpContactDtlTO empContactDtlTO : empContactDtlTOs) {

            empContactDtlEntites.add(convertPOJOToEntity(empContactDtlTO, empRegisterRepository));
        }
        return empContactDtlEntites;
    }

    public static EmpContactDtlEntity convertPOJOToEntity(EmpContactDtlTO empContactDtlTO,
            EmpRegisterRepository empRegisterRepository) {
        EmpContactDtlEntity entity = new EmpContactDtlEntity();

        if (CommonUtil.isNonBlankLong(empContactDtlTO.getId())) {
            entity.setId(empContactDtlTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empContactDtlTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne(empContactDtlTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNotBlankStr(empContactDtlTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empContactDtlTO.getFromDate()));
        }
        entity.setResidentAddr(empContactDtlTO.getResidentAddr());
        entity.setPostalAddr(empContactDtlTO.getPostalAddr());
        entity.setPhoneNumber(empContactDtlTO.getPhoneNumber());
        entity.setAlternatePhone(empContactDtlTO.getAlternatePhone());
        entity.setEmail(empContactDtlTO.getEmail());

        entity.setStatus(empContactDtlTO.getStatus());

        return entity;
    }

}
