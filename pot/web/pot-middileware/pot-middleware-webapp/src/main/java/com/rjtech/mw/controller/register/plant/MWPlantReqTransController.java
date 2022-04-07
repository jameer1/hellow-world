package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.plant.req.PlantTranferReqApprSaveReq;
import com.rjtech.register.plant.req.PlantTransReq;
import com.rjtech.register.plant.resp.PlantNotificationResp;
import com.rjtech.register.plant.resp.PlantTransferReqApprResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantReqTransController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_TRANSFERS, method = RequestMethod.POST)
    public ResponseEntity<PlantTransferReqApprResp> getPlantTransfers(@RequestBody PlantTransReq plantTransReq) {
        PlantTransferReqApprResp plantTransferReqApprResp = mwPlantRegisterService.getPlantTransfers(plantTransReq);
        return new ResponseEntity<PlantTransferReqApprResp>(plantTransferReqApprResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_TRANSFER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PlantTransferReqApprResp> getPlantTransferDetails(@RequestBody PlantTransReq plantTransReq) {
        return new ResponseEntity<PlantTransferReqApprResp>(
                mwPlantRegisterService.getPlantTransferDetails(plantTransReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_TRANSFER_REQUEST_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantTransferReqDetails(@RequestBody PlantTransReq plantTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwPlantRegisterService.getPlantTransferReqDetails(plantTransReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_TRANSFERS, method = RequestMethod.POST)
    public ResponseEntity<PlantTransferReqApprResp> savePlantTransfers(
            @RequestBody PlantTranferReqApprSaveReq plantReqForTransSaveReq) {
        return new ResponseEntity<PlantTransferReqApprResp>(
                mwPlantRegisterService.savePlantTransfers(plantReqForTransSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<PlantNotificationResp> getPlantNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<PlantNotificationResp>(
                mwPlantRegisterService.getPlantNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_SETTINGS_PLANT_TRANSFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsPlantTransferCheck(@RequestBody PlantTransReq plantTransReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                mwPlantRegisterService.getProjSettingsPlantTransferCheck(plantTransReq), HttpStatus.OK);
    }
}
