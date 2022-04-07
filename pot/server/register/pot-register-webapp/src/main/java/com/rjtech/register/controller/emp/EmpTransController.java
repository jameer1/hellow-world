package com.rjtech.register.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.emp.req.EmpTransReq;
import com.rjtech.register.emp.req.EmpTransSaveReq;
import com.rjtech.register.emp.resp.EmpNotificationsResp;
import com.rjtech.register.emp.resp.EmpTransResp;
import com.rjtech.register.service.emp.EmpTransferReqApprService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpTransController {

    @Autowired
    private EmpTransferReqApprService empTransferReqApprService;

    @PostMapping(value = RegisterURLConstants.GET_EMP_TRANSFERS)
    public ResponseEntity<EmpTransResp> getEmpReqTrans(@RequestBody EmpTransReq empTransReq) {
        EmpTransResp empTransResp = empTransferReqApprService.getEmpTranfers(empTransReq);
        return new ResponseEntity<>(empTransResp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.GET_EMP_TRANSFER_DETAILS)
    public ResponseEntity<EmpTransResp> getEmpTranferDetails(@RequestBody EmpTransReq empTransReq) {
        EmpTransResp empTransResp = empTransferReqApprService.getEmpTranferReqDetails(empTransReq);
        return new ResponseEntity<>(empTransResp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.GET_EMP_TRANSFER_REQUEST_DETAILS)
    public ResponseEntity<LabelKeyTOResp> getEmpTransferReqDetails(@RequestBody EmpTransReq empTransReq) {
        return new ResponseEntity<>(empTransferReqApprService.getEmpTransferReqDetails(empTransReq), HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_EMP_TRANSFERS)
    public ResponseEntity<EmpTransResp> saveEmpTranfers(@RequestBody EmpTransSaveReq empTransSaveReq) {
        EmpTransResp empTransResp = empTransferReqApprService.saveEmpTranfers(empTransSaveReq);
        return new ResponseEntity<>(empTransResp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.GET_EMP_NOTIFICATIONS)
    public ResponseEntity<EmpNotificationsResp> getEmpNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<>(empTransferReqApprService.getEmpNotifications(notificationsGetReq), HttpStatus.OK);
    }

}
