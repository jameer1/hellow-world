package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpLeaveAttendanceSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpLeaveAttendanceYearResp;

public interface EmpLeaveAttendenceService {

    EmpLeaveAttendanceYearResp getEmpLeaveAttendanceDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    void saveEmpLeaveAttendanceDetails(EmpLeaveAttendanceSaveReq empLeaveAttendanceSaveReq);

}
