package com.rjtech.register.service.emp;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.emp.req.EmpLeaveReq;
import com.rjtech.register.emp.req.EmpLeaveReqApprSaveReq;
import com.rjtech.register.emp.resp.EmpLeaveReqApprResp;

public interface EmpLeaveReqApprService {

    EmpLeaveReqApprResp getEmpLeaveReqApprovals(EmpLeaveReq empLeaveReq);

    EmpLeaveReqApprResp getEmpLeaveApprovals(EmpLeaveReq empLeaveReq);

    EmpLeaveReqApprResp getEmpLeaveReqApprovalDetails(EmpLeaveReq empLeaveReq);

    EmpLeaveReqApprResp saveEmpLeaveReqApprovals(EmpLeaveReqApprSaveReq empLeaveAttendenceSaveReq);

    public LabelKeyTOResp getProjSettingsEmpLeaveCheck(EmpLeaveReq empLeaveReq);

}
