package com.rjtech.register.service.handler.emp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpTransReqApprDetailTO;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.model.EmpTransReqApprDetailEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpTransReqApprDetailHandler {

    private static final Logger log = LoggerFactory.getLogger(EmpTransReqApprDetailHandler.class);

    public static EmpTransReqApprDetailTO convertEntityToPOJO(EmpTransReqApprDetailEntity entity) {

        EmpTransReqApprDetailTO empTransReqApprDetailTO = new EmpTransReqApprDetailTO();
        empTransReqApprDetailTO.setId(entity.getId());
        empTransReqApprDetailTO.setEmpTransId(entity.getEmpTransferReqApprEntity().getId());
        empTransReqApprDetailTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        if (CommonUtil.isNotBlankDate(entity.getTransDate())) {
            empTransReqApprDetailTO.setTransDate(CommonUtil.convertDateToString(entity.getTransDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getExpectedTransDate())) {
            empTransReqApprDetailTO.setExpectedTransDate(CommonUtil.convertDateToString(entity.getExpectedTransDate()));
        }
        empTransReqApprDetailTO.setComments(entity.getComments());
        empTransReqApprDetailTO.setApprStatus(entity.getApprStatus());

        EmpRegisterDtlEntity empReg = entity.getEmpRegisterDtlEntity();
        if (CommonUtil.objectNotNull(empReg)) {
            empTransReqApprDetailTO.setEmpFirstName(empReg.getFirstName());
            empTransReqApprDetailTO.setEmpLastName(empReg.getLastName());
            empTransReqApprDetailTO.setEmpCode(empReg.getCode());
            if (CommonUtil.objectNotNull(empReg.getCompanyMstrEntity()))
                empTransReqApprDetailTO.setEmpCmpName(empReg.getCompanyMstrEntity().getName());
            if (CommonUtil.objectNotNull(empReg.getEmpClassMstrEntity()))
                empTransReqApprDetailTO.setEmpClassType(empReg.getEmpClassMstrEntity().getName());
        }
        empTransReqApprDetailTO.setStatus(entity.getStatus());
        return empTransReqApprDetailTO;
    }

    public static EmpTransReqApprDetailEntity convertPOJOToEntity(EmpTransReqApprDetailTO empTransReqApprDetailTO,
            EmpRegisterRepository empRegisterRepository) {
        EmpTransReqApprDetailEntity entity = new EmpTransReqApprDetailEntity();
        if (CommonUtil.isNonBlankLong(empTransReqApprDetailTO.getId())) {
            entity.setId(empTransReqApprDetailTO.getId());
        }
        if (CommonUtil.isNotBlankStr(empTransReqApprDetailTO.getTransDate())) {
            entity.setTransDate(CommonUtil.convertStringToDate(empTransReqApprDetailTO.getTransDate()));
        }

        if (CommonUtil.isNotBlankStr(empTransReqApprDetailTO.getExpectedTransDate())) {
            entity.setExpectedTransDate(CommonUtil.convertStringToDate(empTransReqApprDetailTO.getExpectedTransDate()));
        }

        if (CommonUtil.isNonBlankLong(empTransReqApprDetailTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(empTransReqApprDetailTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }

        entity.setComments(empTransReqApprDetailTO.getComments());
        entity.setApprStatus(empTransReqApprDetailTO.getApprStatus());

        entity.setStatus(empTransReqApprDetailTO.getStatus());
        return entity;
    }

}
