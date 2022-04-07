package com.rjtech.mw.controller.register.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceDeleteReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceGetReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceSaveReq;
import com.rjtech.register.fixedassets.resp.PeriodicalScheduleMaintenanceResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPeriodicalScheduleMaintenanceController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_PERIODICAL_SCHEDULE_MAINTENANCE, method = RequestMethod.POST)
    public ResponseEntity<PeriodicalScheduleMaintenanceResp> getPeriodicalScheduleMaintenance(
            @RequestBody PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq) {
        PeriodicalScheduleMaintenanceResp resp = mwFixedAssetsService
                .getPeriodicalScheduleMaintenance(periodicalScheduleMaintenanceGetReq);
        return new ResponseEntity<PeriodicalScheduleMaintenanceResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PERIODICAL_SCHEDULE_MAINTENANCE, method = RequestMethod.POST)
    public ResponseEntity<PeriodicalScheduleMaintenanceResp> savePeriodicalScheduleMaintenance(
            MultipartFile planFile, MultipartFile actualFile, String periodicalScheduleMaintenanceSaveReq) {
        return new ResponseEntity<PeriodicalScheduleMaintenanceResp>(
                mwFixedAssetsService.savePeriodicalScheduleMaintenance(planFile, actualFile, AppUtils
                        .fromJson(periodicalScheduleMaintenanceSaveReq, PeriodicalScheduleMaintenanceSaveReq.class)),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PERIODICAL_SCHEDULE_MAINTENANCE_DELETE, method = RequestMethod.POST)
    public ResponseEntity<PeriodicalScheduleMaintenanceResp> periodicalScheduleMaintenanceDelete(
            @RequestBody PeriodicalScheduleMaintenanceDeleteReq periodicalScheduleMaintenanceDeleteReq) {
        return new ResponseEntity<PeriodicalScheduleMaintenanceResp>(
                mwFixedAssetsService.periodicalScheduleMaintenanceDelete(periodicalScheduleMaintenanceDeleteReq),
                HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.PERIODICAL_SCHEDULE_MAINTENANCE_DOC_DOWNLOAD_FILE)
    public ResponseEntity<Void> periodicalScheduleCompletionDocDownloadFile(@RequestParam("fileId") Long fileId) {
        mwFixedAssetsService.periodicalScheduleCompletionDocDownloadFile(fileId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

    }

    @GetMapping(value = RegisterURLConstants.PERIODICAL_SCHEDULE_COMPLETION_DOC_DOWNLOAD_FILE)
    public ResponseEntity<Void> periodicalScheduleMaintenanceDocDownloadFile(@RequestParam("fileId") Long fileId) {
        mwFixedAssetsService.periodicalScheduleMaintenanceDocDownloadFile(fileId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

    }

}
