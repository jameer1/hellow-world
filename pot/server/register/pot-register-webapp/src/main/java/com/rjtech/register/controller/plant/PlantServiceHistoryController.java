package com.rjtech.register.controller.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantServiceHistorySaveReq;
import com.rjtech.register.plant.resp.PlantServiceHistoryResp;
import com.rjtech.register.service.plant.PlantServiceHistoryService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantServiceHistoryController {

    @Autowired
    private PlantServiceHistoryService plantServiceHistoryService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_SERVICE_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceHistoryResp> getPlantServiceHistory(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        return new ResponseEntity<PlantServiceHistoryResp>(
                plantServiceHistoryService.getPlantServiceHistory(plantProjectDtlGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_SERVICE_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceHistoryResp> savePlantServiceHistory(
            @RequestBody PlantServiceHistorySaveReq plantServiceHistorySaveReq) {

        plantServiceHistoryService.savePlantServiceHistory(plantServiceHistorySaveReq);
        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(plantServiceHistorySaveReq.getPlantId());
        plantProjectDtlGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantServiceHistoryResp plantServiceHistoryResp = plantServiceHistoryService
                .getPlantServiceHistory(plantProjectDtlGetReq);
        return new ResponseEntity<PlantServiceHistoryResp>(plantServiceHistoryResp, HttpStatus.OK);
    }
}
