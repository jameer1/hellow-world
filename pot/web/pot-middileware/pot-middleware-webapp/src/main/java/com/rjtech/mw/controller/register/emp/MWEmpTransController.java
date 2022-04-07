package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.emp.req.EmpTransReq;
import com.rjtech.register.emp.req.EmpTransSaveReq;
import com.rjtech.register.emp.resp.EmpNotificationsResp;
import com.rjtech.register.emp.resp.EmpTransResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpTransController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @PostMapping(value = RegisterURLConstants.GET_EMP_TRANSFERS)
    public ResponseEntity<EmpTransResp> getEmpTransfers(@RequestBody EmpTransReq empTransReq) {
        EmpTransResp empReqForTransResp = mwRegisterService.getEmpTransfers(empTransReq);
        return new ResponseEntity<>(empReqForTransResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_TRANSFER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpTransResp> getEmpTranferReqDetails(@RequestBody EmpTransReq empTransReq) {
        EmpTransResp empTransResp = mwRegisterService.getEmpTranferReqDetails(empTransReq);
        return new ResponseEntity<EmpTransResp>(empTransResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_TRANSFER_REQUEST_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantTransferReqDetails(@RequestBody EmpTransReq empTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwRegisterService.getEmpTransferReqDetails(empTransReq),
                HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_EMP_TRANSFERS)
    public ResponseEntity<EmpTransResp> saveEmpTransfers(@RequestBody EmpTransSaveReq empTransSaveReq) {
        return new ResponseEntity<>(mwRegisterService.saveEmpTransfers(empTransSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<EmpNotificationsResp> getEmpNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<EmpNotificationsResp>(mwRegisterService.getEmpNotifications(notificationsGetReq),
                HttpStatus.OK);
    }

}
