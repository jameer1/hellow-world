package com.rjtech.mw.controller.register.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.mw.service.register.MWMaterialService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.material.req.MaterialTransReq;
import com.rjtech.register.material.req.MaterialTransferReqApprSaveReq;
import com.rjtech.register.material.resp.MaterialNotificationResp;
import com.rjtech.register.material.resp.MaterialTransferReqApprResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWMaterialTranesferReqApprController {

    @Autowired
    private MWMaterialService mwMaterialService;

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_TRANESFERS, method = RequestMethod.POST)
    public ResponseEntity<MaterialTransferReqApprResp> getMaterialTransfers(
            @RequestBody MaterialTransReq materialTransReq) {
        MaterialTransferReqApprResp materialTransferReqApprResp = mwMaterialService
                .getMaterialTransfers(materialTransReq);
        return new ResponseEntity<MaterialTransferReqApprResp>(materialTransferReqApprResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_TRANESFER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<MaterialTransferReqApprResp> getMaterialTransferDetails(
            @RequestBody MaterialTransReq materialTransReq) {
        return new ResponseEntity<MaterialTransferReqApprResp>(
                mwMaterialService.getMaterialTransferDetails(materialTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_DETAILS_FOR_TRANESFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialDetailsForTransfer(
            @RequestBody MaterialTransReq materialTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwMaterialService.getMaterialDetailsForTransfer(materialTransReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_MATERIAL_TRANESFERS, method = RequestMethod.POST)
    public ResponseEntity<MaterialTransferReqApprResp> saveMaterialTransfers(
            @RequestBody MaterialTransferReqApprSaveReq materialTransferReqApprSaveReq) {
        return new ResponseEntity<MaterialTransferReqApprResp>(
                mwMaterialService.saveMaterialTransfers(materialTransferReqApprSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<MaterialNotificationResp> getMaterialNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<MaterialNotificationResp>(
                mwMaterialService.getMaterialNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_SETTINGS_MATERIAL_TRANSFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsMaterialTransferCheck(
            @RequestBody MaterialTransReq materialTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                mwMaterialService.getProjSettingsMaterialTransferCheck(materialTransReq), HttpStatus.OK);
    }

}
