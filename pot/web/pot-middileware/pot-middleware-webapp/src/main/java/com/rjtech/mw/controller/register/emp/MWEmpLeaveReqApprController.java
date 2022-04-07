package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.constants.EmpLeaveType;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpLeaveReq;
import com.rjtech.register.emp.req.EmpLeaveReqApprSaveReq;
import com.rjtech.register.emp.resp.EmpLeaveReqApprResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpLeaveReqApprController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_LEAVE_REQ_APPROVALS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveReqApprResp> getEmpLeaveReqApprovals(@RequestBody EmpLeaveReq empLeaveReq) {
        EmpLeaveReqApprResp empLeaveReqApprResp = new EmpLeaveReqApprResp();
        empLeaveReqApprResp = mwRegisterService.getEmpLeaveReqApprovals(empLeaveReq);
        if (empLeaveReq.isOnload()) {
            UserReq userReq = new UserReq();
            userReq.setStatus(StatusCodes.ACTIVE.getValue());
            for (EmpLeaveType leaveType : EmpLeaveType.values()) {
                empLeaveReqApprResp.getLeaveCodeMap().put(leaveType.name(), leaveType.getDesc());
            }
        }
        return new ResponseEntity<EmpLeaveReqApprResp>(empLeaveReqApprResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_LEAVE_REQ_APPROVAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveReqApprResp> getEmpLeaveReqApprovalDetails(@RequestBody EmpLeaveReq empLeaveReq) {
        return new ResponseEntity<EmpLeaveReqApprResp>(mwRegisterService.getEmpLeaveReqApprovalDetails(empLeaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_LEAVE_REQ_APPROVALS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveReqApprResp> saveEmpLeaveReqApprovals(
            @RequestBody EmpLeaveReqApprSaveReq empLeaveReqApprSaveReq) {
        return new ResponseEntity<EmpLeaveReqApprResp>(
                mwRegisterService.saveEmpLeaveReqApprovals(empLeaveReqApprSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_SETTINGS_EMP_LEAVE_CHECK, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsEmpLeaveCheck(@RequestBody EmpLeaveReq empLeaveReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwRegisterService.getProjSettingsEmpLeaveCheck(empLeaveReq),
                HttpStatus.OK);
    }
}
