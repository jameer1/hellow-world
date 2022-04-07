package com.rjtech.register.controller.plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.req.PlantDeactivateReq;
import com.rjtech.register.plant.req.PlantLogRecordsSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantLogRecordsResp;
import com.rjtech.register.service.plant.PlantDeploymentService;
import com.rjtech.register.service.plant.PlantLogRecordService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantLogRecordsController {

    @Autowired
    private PlantLogRecordService plantLogRecordsService;

    @Autowired
    private PlantDeploymentService plantDeploymentService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_LOG_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<PlantLogRecordsResp> getPlantLogRecords(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantLogRecordsResp plantLogRecordsResp = plantLogRecordsService.getPlantLogRecords(plantProjectDtlGetReq);
        PlantRegProjTO plantRegProjTO = plantDeploymentService
                .getLatestPlantDeployment(plantProjectDtlGetReq.getPlantId());
        Map<Long, Boolean> exitingDeploymentMap = new HashMap<>();
   // note: for reference      
        if (CommonUtil.isListHasData(plantLogRecordsResp.getPlantLogRecordsTOs())) {
            for (PlantLogRecordsTO logRecordsTO : plantLogRecordsResp.getPlantLogRecordsTOs()) {
                exitingDeploymentMap.put(logRecordsTO.getPlantRegProjTO().getId(), true);
            }
        }
        if (!CommonUtil.isListHasData(plantLogRecordsResp.getPlantLogRecordsTOs())) {
            PlantLogRecordsTO plantLogRecordsTO = new PlantLogRecordsTO();
            plantLogRecordsTO.setLatest(true);
            plantLogRecordsTO.setFromDate(plantRegProjTO.getMobDate());
            plantRegProjTO.setLatest(CommonConstants.SET_IS_LATEST_Y);
            plantLogRecordsTO.setPlantRegProjTO(plantRegProjTO);
            List<PlantLogRecordsTO> plantLogRecordsTOs = new ArrayList<>();
            plantLogRecordsTOs.add(plantLogRecordsTO);
            plantLogRecordsTOs.addAll(plantLogRecordsResp.getPlantLogRecordsTOs());
            plantLogRecordsResp.setPlantLogRecordsTOs(plantLogRecordsTOs);
        }
        return new ResponseEntity<>(plantLogRecordsResp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_PLANT_LOG_RECORDS)
    public ResponseEntity<PlantLogRecordsResp> savePlantLogRecords(
            @RequestBody PlantLogRecordsSaveReq plantLogRecordsSaveReq) {
        plantLogRecordsService.savePlantLogRecords(plantLogRecordsSaveReq);
        PlantLogRecordsResp resp = new PlantLogRecordsResp();
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_LOG_RECORDS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<PlantLogRecordsResp> plantLogRecordsDeactivate(
            @RequestBody PlantDeactivateReq plantDeactivateReq) {
        plantLogRecordsService.plantLogRecordsDeactivate(plantDeactivateReq);
        PlantLogRecordsResp resp = getPlantRecords(plantDeactivateReq.getPlantRegProjTO().getPlantId(),
                plantDeactivateReq.getPlantRegProjTO().getId());
        resp.setPlantRegProjTO(plantDeactivateReq.getPlantRegProjTO());
        return new ResponseEntity<PlantLogRecordsResp>(resp, HttpStatus.OK);
    }

    private PlantLogRecordsResp getPlantRecords(Long plantId, Long plantProjId) {
        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(plantId);
        plantProjectDtlGetReq.setPlantProjId(plantProjId);
        plantProjectDtlGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        PlantLogRecordsResp plantLogRecordsResp = plantLogRecordsService.getPlantLogRecords(plantProjectDtlGetReq);
        return plantLogRecordsResp;
    }
}
