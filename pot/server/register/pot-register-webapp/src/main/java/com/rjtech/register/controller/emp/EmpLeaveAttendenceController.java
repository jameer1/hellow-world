package com.rjtech.register.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpLeaveAttendanceSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpLeaveAttendanceYearResp;
import com.rjtech.register.service.emp.EmpLeaveAttendenceService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpLeaveAttendenceController {

    @Autowired
    private EmpLeaveAttendenceService empLeaveAttendenceService;

    @PostMapping(value = RegisterURLConstants.GET_EMP_LEAVE_ATTENDENCE_DTLS)
    public ResponseEntity<EmpLeaveAttendanceYearResp> getEmpLeaveAttendanceDetails(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        return new ResponseEntity<>(
                empLeaveAttendenceService.getEmpLeaveAttendanceDetails(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_EMP_LEAVE_ATTENDENCE_DTLS)
    public ResponseEntity<EmpLeaveAttendanceYearResp> saveEmpLeaveAttendanceDetails(
            @RequestBody EmpLeaveAttendanceSaveReq empLeaveAttendanceSaveReq) {
        empLeaveAttendenceService.saveEmpLeaveAttendanceDetails(empLeaveAttendanceSaveReq);
        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setEmpId(empLeaveAttendanceSaveReq.getEmpRegId());
        projEmpRegisterGetReq.setStatus(projEmpRegisterGetReq.getStatus());
        EmpLeaveAttendanceYearResp empLeaveAttendanceResp = empLeaveAttendenceService.getEmpLeaveAttendanceDetails(projEmpRegisterGetReq);
        return new ResponseEntity<>(empLeaveAttendanceResp, HttpStatus.OK);
    }

}
