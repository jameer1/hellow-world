package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpLeaveAttendanceSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpLeaveAttendanceYearResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpLeaveAttendenceController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_LEAVE_ATTENDENCE_DTLS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveAttendanceYearResp> getEmpLeaveAttendanceDetails(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpLeaveAttendanceYearResp empLeaveAttendanceYearResp = new EmpLeaveAttendanceYearResp();
        empLeaveAttendanceYearResp = mwRegisterService.getEmpLeaveAttendanceDetails(projEmpRegisterGetReq);
        UserReq userReq = new UserReq();
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<EmpLeaveAttendanceYearResp>(empLeaveAttendanceYearResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_LEAVE_ATTENDENCE_DTLS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveAttendanceYearResp> saveEmpLeaveAttendanceDetails(
            @RequestBody EmpLeaveAttendanceSaveReq empLeaveAttendanceSaveReq) {
        return new ResponseEntity<EmpLeaveAttendanceYearResp>(
                mwRegisterService.saveEmpLeaveAttendanceDetails(empLeaveAttendanceSaveReq), HttpStatus.OK);
    }

}
