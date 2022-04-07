package com.rjtech.register.service.handler.emp;

import com.rjtech.common.constants.EmpLeaveType;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpLeaveReqApprDetailTO;
import com.rjtech.register.emp.model.EmpLeaveReqApprDetailEntity;
import com.rjtech.register.emp.model.EmpLeaveReqApprEntity;

public class EmpLeaveReqApprDetailHandler {

    public static EmpLeaveReqApprDetailTO convertEntityToPOJO(EmpLeaveReqApprDetailEntity entity) {

        EmpLeaveReqApprDetailTO empLeaveReqApprDetailTO = new EmpLeaveReqApprDetailTO();
        empLeaveReqApprDetailTO.setId(entity.getId());
        empLeaveReqApprDetailTO.setEmpLeaveReqApprId(entity.getEmpLeaveReqApprEntity().getId());
        empLeaveReqApprDetailTO.setTotalDays(entity.getTotalDays());
        empLeaveReqApprDetailTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        empLeaveReqApprDetailTO.setToDate(CommonUtil.convertDateToString(entity.getToDate()));
        empLeaveReqApprDetailTO.setLeaveCode(entity.getLeaveType().getDesc());
        empLeaveReqApprDetailTO.setLeaveType(entity.getLeaveType().name());
        empLeaveReqApprDetailTO.setComments(entity.getComments());
        empLeaveReqApprDetailTO.setStatus(entity.getStatus());

        return empLeaveReqApprDetailTO;
    }

    public static EmpLeaveReqApprDetailEntity convertPOJOToEntity(EmpLeaveReqApprDetailTO empLeaveReqApprDetailTO) {
        EmpLeaveReqApprDetailEntity entity = new EmpLeaveReqApprDetailEntity();

        if (CommonUtil.isNonBlankLong(empLeaveReqApprDetailTO.getId())) {
            entity.setId(empLeaveReqApprDetailTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empLeaveReqApprDetailTO.getEmpLeaveReqApprId())) {
            EmpLeaveReqApprEntity empLeaveReqApprEntity = new EmpLeaveReqApprEntity();
            empLeaveReqApprEntity.setId(empLeaveReqApprDetailTO.getEmpLeaveReqApprId());
            entity.setEmpLeaveReqApprEntity(empLeaveReqApprEntity);
        }
        entity.setLeaveType(EmpLeaveType.valueOf(empLeaveReqApprDetailTO.getLeaveType()));
        entity.setFromDate(CommonUtil.convertStringToDate(empLeaveReqApprDetailTO.getFromDate()));
        entity.setToDate(CommonUtil.convertStringToDate(empLeaveReqApprDetailTO.getToDate()));
        entity.setTotalDays(empLeaveReqApprDetailTO.getTotalDays());
        entity.setComments(empLeaveReqApprDetailTO.getComments());
        entity.setStatus(empLeaveReqApprDetailTO.getStatus());

        entity.setStatus(empLeaveReqApprDetailTO.getStatus());
        return entity;
    }

}
