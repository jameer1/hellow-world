package com.rjtech.mw.controller.register.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsDeleteReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsGetReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.OccupancyUtilisationRecordsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWOccupancyUtilisationRecordsController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_OCCUPANCY_UTILISATION_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<OccupancyUtilisationRecordsResp> getOccupancyUtilisationRecords(
            @RequestBody OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq) {

        OccupancyUtilisationRecordsResp resp = mwFixedAssetsService
                .getOccupancyUtilisationRecords(occupancyUtilisationRecordsGetReq);
        return new ResponseEntity<OccupancyUtilisationRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_OCCUPANCY_UTILISATION_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<OccupancyUtilisationRecordsResp> saveOccupancyUtilisationRecords(
            @RequestBody OccupancyUtilisationRecordsSaveReq occupancyUtilisationRecordsSaveReq) {
        OccupancyUtilisationRecordsResp resp = mwFixedAssetsService
                .saveOccupancyUtilisationRecords(occupancyUtilisationRecordsSaveReq);
        return new ResponseEntity<OccupancyUtilisationRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.OCCUPANCY_UTILISATION_RECORDS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<OccupancyUtilisationRecordsResp> occupancyUtilisationRecordsDelete(
            @RequestBody OccupancyUtilisationRecordsDeleteReq occupancyUtilisationRecordsDeleteReq) {
        OccupancyUtilisationRecordsResp resp = mwFixedAssetsService
                .occupancyUtilisationRecordsDelete(occupancyUtilisationRecordsDeleteReq);
        return new ResponseEntity<OccupancyUtilisationRecordsResp>(resp, HttpStatus.OK);
    }

}
