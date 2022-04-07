package com.rjtech.register.service.handler.emp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpNokTO;
import com.rjtech.register.emp.model.EmpNokEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpNokHandler {

    public static EmpNokTO convertEntityToPOJO(EmpNokEntity entity) {

        EmpNokTO empNokTO = new EmpNokTO();

        empNokTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empNokTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        empNokTO.setContactType(entity.getContactType());
        empNokTO.setFirstName(entity.getFirstName());
        empNokTO.setLastName(entity.getLastName());
        empNokTO.setResidentialAddr(entity.getResidentialAddr());
        empNokTO.setRelationWithEmp(entity.getRelationWithEmp());
        empNokTO.setPostalAddr(entity.getPostalAddr());
        empNokTO.setPhoneNumber(entity.getPhoneNumber());
        empNokTO.setAlternatePhoneNumber(entity.getAlternatePhoneNumber());
        empNokTO.setEmail(entity.getEmail());
        empNokTO.setStatus(entity.getStatus());
        return empNokTO;

    }

    public static List<EmpNokEntity> convertPOJOsToEntitys(List<EmpNokTO> empNokTOs,
            EmpRegisterRepository empRegisterRepository) {
        List<EmpNokEntity> empNokEntites = new ArrayList<EmpNokEntity>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (EmpNokTO empNokTO : empNokTOs) {
            empNokEntites.add(convertPOJOToEntity(empNokTO, empRegisterRepository));
        }
        return empNokEntites;
    }

    public static EmpNokEntity convertPOJOToEntity(EmpNokTO empNokTO, EmpRegisterRepository empRegisterRepository) {
        EmpNokEntity entity = new EmpNokEntity();
        if (CommonUtil.isNonBlankLong(empNokTO.getId())) {
            entity.setId(empNokTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empNokTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne(empNokTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        entity.setContactType(empNokTO.getContactType());
        entity.setFirstName(empNokTO.getFirstName());
        entity.setLastName(empNokTO.getLastName());
        entity.setResidentialAddr(empNokTO.getResidentialAddr());
        entity.setRelationWithEmp(empNokTO.getRelationWithEmp());
        entity.setPostalAddr(empNokTO.getPostalAddr());
        entity.setPhoneNumber(empNokTO.getPhoneNumber());
        entity.setAlternatePhoneNumber(empNokTO.getAlternatePhoneNumber());
        entity.setEmail(empNokTO.getEmail());
        entity.setStatus(empNokTO.getStatus());
        return entity;
    }

}
