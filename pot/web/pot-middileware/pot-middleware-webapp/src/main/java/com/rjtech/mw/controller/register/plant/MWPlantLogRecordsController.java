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
import com.rjtech.register.plant.req.PlantDeactivateReq;
import com.rjtech.register.plant.req.PlantLogRecordsSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantLogRecordsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantLogRecordsController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_LOG_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<PlantLogRecordsResp> getPlantLogRecords(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantLogRecordsResp resp = mwPlantRegisterService.getPlantLogRecords(plantProjectDtlGetReq);
        return new ResponseEntity<PlantLogRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_LOG_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<PlantLogRecordsResp> savePlantLogRecords(
            @RequestBody PlantLogRecordsSaveReq plantLogRecordsSaveReq) {
        PlantLogRecordsResp resp = mwPlantRegisterService.savePlantLogRecords(plantLogRecordsSaveReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PlantLogRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_LOG_RECORDS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<PlantLogRecordsResp> plantLogRecordsDeactivate(
            @RequestBody PlantDeactivateReq plantDeactivateReq) {
        return new ResponseEntity<PlantLogRecordsResp>(
                mwPlantRegisterService.plantLogRecordsDeactivate(plantDeactivateReq), HttpStatus.OK);
    }
}
