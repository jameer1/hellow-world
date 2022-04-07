package com.rjtech.register.controller.fixedassets;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.dto.PeriodicalScheduleMaintenanceDtlTO;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsGetReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceDeleteReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceGetReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetPurachseAcqulistionResp;
import com.rjtech.register.fixedassets.resp.PeriodicalScheduleMaintenanceResp;
import com.rjtech.register.service.fixedassets.PeriodicalScheduleMaintenanceService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PeriodicalScheduleMaintenanceController {

    @Autowired
    private PeriodicalScheduleMaintenanceService periodicalScheduleMaintenanceService;

    @RequestMapping(value = RegisterURLConstants.GET_PERIODICAL_SCHEDULE_MAINTENANCE, method = RequestMethod.POST)
    public ResponseEntity<PeriodicalScheduleMaintenanceResp> getPeriodicalScheduleMaintenance(
            @RequestBody PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq) {
        PeriodicalScheduleMaintenanceResp resp = periodicalScheduleMaintenanceService
                .getPeriodicalScheduleMaintenance(periodicalScheduleMaintenanceGetReq);
        return new ResponseEntity<PeriodicalScheduleMaintenanceResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PERIODICAL_SCHEDULE_MAINTENANCE, method = RequestMethod.POST)
    public ResponseEntity<PeriodicalScheduleMaintenanceResp> savePeriodicalScheduleMaintenance(
            MultipartFile planFile, MultipartFile actualFile, String clientRegReq)
            throws IOException {
        PeriodicalScheduleMaintenanceSaveReq periodicalScheduleMaintenanceSave = AppUtils.fromJson(clientRegReq,
                PeriodicalScheduleMaintenanceSaveReq.class);
        periodicalScheduleMaintenanceService.saveFixedAssetsPeriodicals(planFile,actualFile,periodicalScheduleMaintenanceSave);
        PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq = new PeriodicalScheduleMaintenanceGetReq();
        periodicalScheduleMaintenanceGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PeriodicalScheduleMaintenanceResp resp = periodicalScheduleMaintenanceService
                .getPeriodicalScheduleMaintenance(periodicalScheduleMaintenanceGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PeriodicalScheduleMaintenanceResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PERIODICAL_SCHEDULE_MAINTENANCE_DELETE, method = RequestMethod.POST)
    public ResponseEntity<PeriodicalScheduleMaintenanceResp> periodicalScheduleMaintenanceDelete(
            @RequestBody PeriodicalScheduleMaintenanceDeleteReq periodicalScheduleMaintenanceDeleteReq) {
        periodicalScheduleMaintenanceService
                .periodicalScheduleMaintenanceDelete(periodicalScheduleMaintenanceDeleteReq);

        PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq = new PeriodicalScheduleMaintenanceGetReq();
        periodicalScheduleMaintenanceGetReq.setSubid(periodicalScheduleMaintenanceDeleteReq.getSubid());
        periodicalScheduleMaintenanceGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PeriodicalScheduleMaintenanceResp resp = periodicalScheduleMaintenanceService
                .getPeriodicalScheduleMaintenance(periodicalScheduleMaintenanceGetReq);
        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<PeriodicalScheduleMaintenanceResp>(resp, HttpStatus.OK);
    }
    
    @GetMapping(value = RegisterURLConstants.PERIODICAL_SCHEDULE_MAINTENANCE_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> periodicalPlanDownloadFile(@RequestParam("periodicalRecordId") Long periodicalRecordId)
            throws IOException {
        PeriodicalScheduleMaintenanceDtlTO fileTo = periodicalScheduleMaintenanceService.planDocDownloadFile(periodicalRecordId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getPlanRecordDocumentFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getPlanRecordDocumentFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getPlanRecordDocument()));
    }
    
    @GetMapping(value = RegisterURLConstants.PERIODICAL_SCHEDULE_COMPLETION_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> periodicalActualDownloadFile(@RequestParam("periodicalRecordId") Long periodicalRecordId)
            throws IOException {
        PeriodicalScheduleMaintenanceDtlTO fileTo = periodicalScheduleMaintenanceService.actualDocDownloadFile(periodicalRecordId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getActualRecordsDocumentFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getActualRecordsDocumentFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getActualRecordDocument()));
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_PERIODICAL_DOCUMENTS, method = RequestMethod.POST)
    public ResponseEntity<DocumentsResp> getProjPeriodicalDocuemnts(
            @RequestBody PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq) {
        DocumentsResp resp = periodicalScheduleMaintenanceService
                .getProjPeriodicalDocuemnts(periodicalScheduleMaintenanceGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);
    }
}
