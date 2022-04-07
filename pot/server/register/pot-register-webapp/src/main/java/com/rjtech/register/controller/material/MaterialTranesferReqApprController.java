package com.rjtech.register.controller.material;

import java.util.ArrayList;
import java.util.List;

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
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.material.dto.MaterialProjDocketTO;
import com.rjtech.register.material.req.MaterialNotificationReq;
import com.rjtech.register.material.req.MaterialTransReq;
import com.rjtech.register.material.req.MaterialTransferReqApprSaveReq;
import com.rjtech.register.material.resp.MaterialNotificationResp;
import com.rjtech.register.material.resp.MaterialProjDocketResp;
import com.rjtech.register.material.resp.MaterialTransferReqApprResp;
import com.rjtech.register.repository.material.MaterialDockSchItemRepository;
import com.rjtech.register.service.material.MaterialTransferReqApprService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MaterialTranesferReqApprController {

    @Autowired
    private MaterialTransferReqApprService materialTransferReqApprService;

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_TRANESFERS, method = RequestMethod.POST)
    public ResponseEntity<MaterialTransferReqApprResp> getMaterialTransfers(
            @RequestBody MaterialTransReq materialTransReq) {
        return new ResponseEntity<MaterialTransferReqApprResp>(
                materialTransferReqApprService.getMaterialTransfers(materialTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_TRANESFER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<MaterialTransferReqApprResp> getMaterialTransferDetails(
            @RequestBody MaterialTransReq materialTransReq) {
        return new ResponseEntity<MaterialTransferReqApprResp>(
                materialTransferReqApprService.getMaterialTransferDetails(materialTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_DETAILS_FOR_TRANESFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialDetailsForTransfer(
            @RequestBody MaterialTransReq materialTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                materialTransferReqApprService.getMaterialDetailsForTransfer(materialTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_MATERIAL_TRANESFERS, method = RequestMethod.POST)
    public ResponseEntity<MaterialTransferReqApprResp> saveMaterialTranesFer(
            @RequestBody MaterialTransferReqApprSaveReq materialTransferReqApprSaveReq) {
        materialTransferReqApprService.saveMaterialTransfers(materialTransferReqApprSaveReq);
        MaterialTransferReqApprResp materialTransferResp = new MaterialTransferReqApprResp();
        materialTransferResp.cloneAppResp(CommonUtil.getSaveAppResp());
        materialTransferResp = materialTransferReqApprService
                .getMaterialTransfers(materialTransferReqApprSaveReq.getMaterialTransReq());
        return new ResponseEntity<MaterialTransferReqApprResp>(materialTransferResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<MaterialNotificationResp> getMaterialNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<MaterialNotificationResp>(
                materialTransferReqApprService.getMaterialNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_SETTINGS_MATERIAL_TRANSFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsMaterialTransferCheck(
            @RequestBody MaterialTransReq materialTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                materialTransferReqApprService.getProjSettingsMaterialTransferCheck(materialTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIALS_FOR_PROJ_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> getMaterialsForProjDocket(
            @RequestBody MaterialNotificationReq materialNotificationReq) {
        MaterialProjDocketResp materialProjDocketResp = new MaterialProjDocketResp();
        List<MaterialProjDocketTO> materialProjDocketTOs = new ArrayList<MaterialProjDocketTO>();
        materialProjDocketTOs.add(materialTransferReqApprService.getMaterialsForProjDocket(materialNotificationReq));
        materialProjDocketResp.setMaterialProjDocketTOs(materialProjDocketTOs);
        return new ResponseEntity<MaterialProjDocketResp>(materialProjDocketResp, HttpStatus.OK);
    }
}
