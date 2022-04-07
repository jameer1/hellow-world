package com.rjtech.register.service.emp;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.emp.req.EmpTransReq;
import com.rjtech.register.emp.req.EmpTransSaveReq;
import com.rjtech.register.emp.resp.EmpNotificationsResp;
import com.rjtech.register.emp.resp.EmpTransResp;

public interface EmpTransferReqApprService {

    EmpTransResp getEmpTranfers(EmpTransReq empTransReq);

    EmpTransResp getEmpTranferReqDetails(EmpTransReq empTransReq);

    EmpTransResp saveEmpTranfers(EmpTransSaveReq empReqForTransSaveReq);

    LabelKeyTOResp getEmpTransferReqDetails(EmpTransReq empTransReq);

    EmpNotificationsResp getEmpNotifications(NotificationsGetReq notificationsGetReq);

}
