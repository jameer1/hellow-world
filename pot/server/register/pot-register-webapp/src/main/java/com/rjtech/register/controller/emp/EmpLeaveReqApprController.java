package com.rjtech.register.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpLeaveReq;
import com.rjtech.register.emp.req.EmpLeaveReqApprSaveReq;
import com.rjtech.register.emp.resp.EmpLeaveReqApprResp;
import com.rjtech.register.service.emp.EmpLeaveReqApprService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpLeaveReqApprController {

    @Autowired
    private EmpLeaveReqApprService empLeaveReqApprService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_LEAVE_REQ_APPROVALS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveReqApprResp> getEmpLeaveReqApprovals(@RequestBody EmpLeaveReq empLeaveReq) {
        if (empLeaveReq.isFromApproval()) {
            return new ResponseEntity<EmpLeaveReqApprResp>(empLeaveReqApprService.getEmpLeaveApprovals(empLeaveReq),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<EmpLeaveReqApprResp>(empLeaveReqApprService.getEmpLeaveReqApprovals(empLeaveReq),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_LEAVE_REQ_APPROVAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveReqApprResp> getEmpLeaveReqApprovalDetails(@RequestBody EmpLeaveReq empLeaveReq) {
        return new ResponseEntity<>(empLeaveReqApprService.getEmpLeaveReqApprovalDetails(empLeaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_LEAVE_REQ_APPROVALS, method = RequestMethod.POST)
    public ResponseEntity<EmpLeaveReqApprResp> saveEmpLeaveReqApprovals(
            @RequestBody EmpLeaveReqApprSaveReq empLeaveReqApprSaveReq) {
        empLeaveReqApprService.saveEmpLeaveReqApprovals(empLeaveReqApprSaveReq);
        EmpLeaveReqApprResp empLeaveAttendenceDtlsResp = empLeaveReqApprService
                .getEmpLeaveReqApprovals(empLeaveReqApprSaveReq.getEmpLeaveReq());
        empLeaveAttendenceDtlsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(empLeaveAttendenceDtlsResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_SETTINGS_EMP_LEAVE_CHECK, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsEmpLeaveCheck(@RequestBody EmpLeaveReq empLeaveReq) {
        return new ResponseEntity<LabelKeyTOResp>(empLeaveReqApprService.getProjSettingsEmpLeaveCheck(empLeaveReq),
                HttpStatus.OK);
    }
}