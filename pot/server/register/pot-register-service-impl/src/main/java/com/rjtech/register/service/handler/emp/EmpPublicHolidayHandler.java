package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpPublicHolidayTO;
import com.rjtech.register.emp.model.EmpLeaveReqApprEntity;
import com.rjtech.register.emp.model.EmpPublicHolidayEntity;

public class EmpPublicHolidayHandler {

    public static EmpPublicHolidayTO convertEntityToPOJO(EmpPublicHolidayEntity entity) {

        EmpPublicHolidayTO empPublicHolidayTO = new EmpPublicHolidayTO();

        empPublicHolidayTO.setId(entity.getId());
        empPublicHolidayTO.setType(entity.getType());
        empPublicHolidayTO.setDesc(entity.getDesc());
        empPublicHolidayTO.setEmpLeaveReqApprId(entity.getEmpLeaveReqApprEntity().getId());
        empPublicHolidayTO.setDate(CommonUtil.convertDateToString(entity.getDate()));
        empPublicHolidayTO.setStatus(entity.getStatus());

        return empPublicHolidayTO;
    }

    public static EmpPublicHolidayEntity convertPOJOToEntity(EmpPublicHolidayTO empPublicHolidayTO) {
        EmpPublicHolidayEntity entity = new EmpPublicHolidayEntity();

        if (CommonUtil.isNonBlankLong(empPublicHolidayTO.getId())) {
            entity.setId(empPublicHolidayTO.getId());
        }

        entity.setType(empPublicHolidayTO.getType());
        if (CommonUtil.isNonBlankLong(empPublicHolidayTO.getEmpLeaveReqApprId())) {
            EmpLeaveReqApprEntity empLeaveReqApprEntity = new EmpLeaveReqApprEntity();
            empLeaveReqApprEntity.setId(empPublicHolidayTO.getEmpLeaveReqApprId());
            entity.setEmpLeaveReqApprEntity(empLeaveReqApprEntity);
        }
        entity.setDesc(empPublicHolidayTO.getDesc());
        entity.setDate(CommonUtil.convertStringToDate(empPublicHolidayTO.getDate()));
        entity.setStatus(empPublicHolidayTO.getStatus());
        return entity;
    }

}
