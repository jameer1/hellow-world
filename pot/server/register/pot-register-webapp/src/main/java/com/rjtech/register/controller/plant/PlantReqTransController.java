package com.rjtech.register.controller.plant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantTranferReqApprSaveReq;
import com.rjtech.register.plant.req.PlantTransReq;
import com.rjtech.register.plant.resp.PlantNotificationResp;
import com.rjtech.register.plant.resp.PlantTransferReqApprResp;
import com.rjtech.register.resp.StatusTypesResp;
import com.rjtech.register.service.plant.PlantReqTransReqApprService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantReqTransController {

    @Autowired
    private PlantReqTransReqApprService plantReqTransService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_TRANSFERS, method = RequestMethod.POST)
    public ResponseEntity<PlantTransferReqApprResp> getPlantTransfers(@RequestBody PlantTransReq plantTransReq) {
        return new ResponseEntity<>(plantReqTransService.getPlantTransfers(plantTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_TRANSFER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PlantTransferReqApprResp> getPlantTransferDetails(@RequestBody PlantTransReq plantTransReq) {
        return new ResponseEntity<PlantTransferReqApprResp>(plantReqTransService.getPlantTransferDetails(plantTransReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_TRANSFER_REQUEST_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantTransferReqDetails(@RequestBody PlantTransReq plantTransReq) {
        return new ResponseEntity<>(plantReqTransService.getPlantTransferReqDetails(plantTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_TRANSFERS, method = RequestMethod.POST)
    public ResponseEntity<PlantTransferReqApprResp> savePlantTransfers(
            @RequestBody PlantTranferReqApprSaveReq plantTranferReqApprSaveReq) {
        PlantTransferReqApprResp plantReqForTransResp = plantReqTransService
                .savePlantTransfers(plantTranferReqApprSaveReq);
        plantReqForTransResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(plantReqForTransResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<PlantNotificationResp> getPlantNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<PlantNotificationResp>(
                plantReqTransService.getPlantNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_STATUS_TYPES, method = RequestMethod.POST)
    public ResponseEntity<StatusTypesResp> getStatusTypes(@RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        StatusTypesResp statusTypesResp = new StatusTypesResp();
        List<LabelKeyTO> statusTypeLabelKeyTOs = plantReqTransService.getStatusTypes();
        statusTypesResp.setStatusTypeLabelKeyTOs(statusTypeLabelKeyTOs);
        return new ResponseEntity<StatusTypesResp>(statusTypesResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_SETTINGS_PLANT_TRANSFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsPlantTransferCheck(@RequestBody PlantTransReq plantTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(plantReqTransService.getProjSettingsPlantTransferCheck(plantTransReq),
                HttpStatus.OK);
    }
}
