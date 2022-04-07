package com.rjtech.register.service.handler.emp;

import com.rjtech.common.constants.EmpLeaveType;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpLeaveAttendanceDtlTO;
import com.rjtech.register.emp.model.EmpLeaveAttendanceDtlEntity;

public class EmpLeaveAttendanceDtlHandler {

    public static EmpLeaveAttendanceDtlTO convertEntityToPOJO(EmpLeaveAttendanceDtlEntity entity) {

        EmpLeaveAttendanceDtlTO empLeaveAttendanceTO = new EmpLeaveAttendanceDtlTO();

        empLeaveAttendanceTO.setId(entity.getId());
        empLeaveAttendanceTO.setLeaveCode(entity.getEmpLeaveType().name());
        empLeaveAttendanceTO.setLeaveType(entity.getEmpLeaveType().getDesc());
        empLeaveAttendanceTO.setTotalLeaves(entity.getTotalLeaves());
        empLeaveAttendanceTO.setUsedLeaves(entity.getUsedLeaves());
        empLeaveAttendanceTO.setStatus(entity.getStatus());

        return empLeaveAttendanceTO;
    }

    public static EmpLeaveAttendanceDtlEntity convertPOJOToEntity(EmpLeaveAttendanceDtlTO empLeaveAttendanceTO) {
        EmpLeaveAttendanceDtlEntity entity = new EmpLeaveAttendanceDtlEntity();

        if (CommonUtil.isNonBlankLong(empLeaveAttendanceTO.getId())) {
            entity.setId(empLeaveAttendanceTO.getId());
        }
        entity.setEmpLeaveType(EmpLeaveType.valueOf(empLeaveAttendanceTO.getLeaveCode()));
        entity.setTotalLeaves(empLeaveAttendanceTO.getTotalLeaves());
        entity.setUsedLeaves(empLeaveAttendanceTO.getUsedLeaves());
        entity.setStatus(empLeaveAttendanceTO.getStatus());

        return entity;
    }

}
