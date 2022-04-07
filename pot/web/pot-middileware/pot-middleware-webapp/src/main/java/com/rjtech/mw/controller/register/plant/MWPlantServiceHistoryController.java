package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantServiceHistorySaveReq;
import com.rjtech.register.plant.resp.PlantServiceHistoryResp;
import com.rjtech.register.plant.resp.PlantServiceOnLoadResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantServiceHistoryController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_SERVICE_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceOnLoadResp> savePlantServiceHistory(
            @RequestBody PlantServiceHistorySaveReq plantServiceHistorySaveReq) {
        PlantServiceOnLoadResp plantServiceOnLoadResp = new PlantServiceOnLoadResp();

        PlantServiceHistoryResp plantServiceHistoryResp = mwPlantRegisterService
                .savePlantServiceHistory(plantServiceHistorySaveReq);
        plantServiceOnLoadResp.setPlantServiceHistoryTOs(plantServiceHistoryResp.getPlantServiceHistoryTOs());
        plantServiceOnLoadResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(plantServiceOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_SERVICE_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceOnLoadResp> plantServiceOnload(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantServiceOnLoadResp plantServiceOnLoadResp = new PlantServiceOnLoadResp();
        PlantServiceHistoryResp plantServiceHistoryResp = mwPlantRegisterService
                .getPlantServiceHistory(plantProjectDtlGetReq);
        plantServiceOnLoadResp.setPlantServiceHistoryTOs(plantServiceHistoryResp.getPlantServiceHistoryTOs());
        plantServiceOnLoadResp.setMessage(plantServiceHistoryResp.getMessage());
        plantServiceOnLoadResp.setStatus(plantServiceHistoryResp.getStatus());
        return new ResponseEntity<>(plantServiceOnLoadResp, HttpStatus.OK);
    }

}
