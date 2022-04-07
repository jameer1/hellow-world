package com.rjtech.register.controller.fixedassets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.dto.OccupancyUtilisationRecordsDtlTO;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsDeleteReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsGetReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.OccupancyUtilisationRecordsResp;
import com.rjtech.register.service.fixedassets.OccupancyUtilisationRecordsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class OccupancyUtilisationRecordsController {

    @Autowired
    private OccupancyUtilisationRecordsService occupancyUtilisationRecordsService;

    @RequestMapping(value = RegisterURLConstants.GET_OCCUPANCY_UTILISATION_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<OccupancyUtilisationRecordsResp> getOccupancyUtilisationRecords(
            @RequestBody OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq) {
        OccupancyUtilisationRecordsResp resp = occupancyUtilisationRecordsService
                .getOccupancyUtilisationRecords(occupancyUtilisationRecordsGetReq);
        return new ResponseEntity<OccupancyUtilisationRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_OCCUPANCY_UTILISATION_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<OccupancyUtilisationRecordsResp> saveOccupancyUtilisationRecords(
            @RequestBody OccupancyUtilisationRecordsSaveReq occupancyUtilisationRecordsSaveReq) {
        occupancyUtilisationRecordsService.saveOccupancyUtilisationRecords(occupancyUtilisationRecordsSaveReq);

        List<OccupancyUtilisationRecordsDtlTO> occupancyUtilisationRecordsDtlTO = occupancyUtilisationRecordsSaveReq
                .getOccupancyUtilisationRecordsDtlTOs();
        OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq = new OccupancyUtilisationRecordsGetReq();
        for (OccupancyUtilisationRecordsDtlTO occupancyUtilisationRecordsDtlTOs : occupancyUtilisationRecordsDtlTO) {
            occupancyUtilisationRecordsGetReq.setSubid(occupancyUtilisationRecordsDtlTOs.getSubid());
            occupancyUtilisationRecordsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        }

        OccupancyUtilisationRecordsResp resp = occupancyUtilisationRecordsService
                .getOccupancyUtilisationRecords(occupancyUtilisationRecordsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<OccupancyUtilisationRecordsResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.OCCUPANCY_UTILISATION_RECORDS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<OccupancyUtilisationRecordsResp> occupancyUtilisationRecordsDelete(
            @RequestBody OccupancyUtilisationRecordsDeleteReq occupancyUtilisationRecordsDeleteReq) {
        occupancyUtilisationRecordsService.occupancyUtilisationRecordsDelete(occupancyUtilisationRecordsDeleteReq);

        OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq = new OccupancyUtilisationRecordsGetReq();
        occupancyUtilisationRecordsGetReq.setSubid(occupancyUtilisationRecordsDeleteReq.getSubid());
        occupancyUtilisationRecordsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        OccupancyUtilisationRecordsResp occupancyUtilisationRecordsResp = occupancyUtilisationRecordsService
                .getOccupancyUtilisationRecords(occupancyUtilisationRecordsGetReq);

        occupancyUtilisationRecordsResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<OccupancyUtilisationRecordsResp>(occupancyUtilisationRecordsResp, HttpStatus.OK);
    }

}