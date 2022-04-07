package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpLeaveAttendanceYearTO;
import com.rjtech.register.emp.model.EmpLeaveAttendanceYearEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpLeaveAttendanceYearHandler {

    public static EmpLeaveAttendanceYearTO convertEntityToPOJO(EmpLeaveAttendanceYearEntity entity) {

        EmpLeaveAttendanceYearTO empLeaveAttendanceYearTO = new EmpLeaveAttendanceYearTO();

        empLeaveAttendanceYearTO.setId(entity.getId());
        empLeaveAttendanceYearTO.setEmpRegId(entity.getEmpRegId().getId());
        empLeaveAttendanceYearTO.setYear(entity.getYear());
        empLeaveAttendanceYearTO.setEndDate(CommonUtil.convertDateToString(entity.getEndDate()));
        empLeaveAttendanceYearTO.setTotalLeaves(entity.getTotalLeaves());
        empLeaveAttendanceYearTO.setUsedLeaves(entity.getUsedLeaves());
        empLeaveAttendanceYearTO.setStatus(entity.getStatus());

        return empLeaveAttendanceYearTO;
    }

    public static EmpLeaveAttendanceYearEntity convertPOJOToEntity(EmpLeaveAttendanceYearTO empLeaveAttendanceTO,
            EmpRegisterRepository empRegisterRepository) {
        EmpLeaveAttendanceYearEntity entity = new EmpLeaveAttendanceYearEntity();

        if (CommonUtil.isNonBlankLong(empLeaveAttendanceTO.getId())) {
            entity.setId(empLeaveAttendanceTO.getId());
        }
        entity.setEmpRegId(empRegisterRepository.findOne(empLeaveAttendanceTO.getEmpRegId()));
        entity.setYear(empLeaveAttendanceTO.getYear());
        entity.setEndDate(CommonUtil.convertStringToDate(empLeaveAttendanceTO.getEndDate()));
        entity.setTotalLeaves(empLeaveAttendanceTO.getTotalLeaves());
        entity.setUsedLeaves(empLeaveAttendanceTO.getUsedLeaves());
        entity.setStatus(empLeaveAttendanceTO.getStatus());

        return entity;
    }

}
