package com.rjtech.register.controller.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;
import com.rjtech.register.fixedassets.req.LongTermLeasingDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.LongTermLeasingResp;
import com.rjtech.register.service.fixedassets.LongTermLeasingService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class LongTermLeasingController {

    @Autowired
    private LongTermLeasingService longTermLeasingService;

    @PostMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASING)
    public ResponseEntity<LongTermLeasingResp> getLongTermLeasing(
            @RequestBody LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = longTermLeasingService.getLongTermLeasing(longTermLeasingGetReq);
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);

    }
    
    @PostMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASING_LAST_RECORD)
    public ResponseEntity<LongTermLeasingResp> getLongtermleaselastrecord(
            @RequestBody LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = longTermLeasingService.getLongtermleaselastrecord(longTermLeasingGetReq);
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);

    }
    
    @PostMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASING_ACTIVEALL_RECORD)
    public ResponseEntity<LongTermLeasingResp> getLongtermleaseActiveAllRecord(
            @RequestBody LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = longTermLeasingService.getLongtermleaseActiveAllRecord(longTermLeasingGetReq);
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);

    }

    @PostMapping(value = RegisterURLConstants.SAVE_LONG_TERM_LEASING)
    public ResponseEntity<LongTermLeasingResp> saveLongTermLeasing(MultipartFile file, String clientRegReq){

        LongTermLeasingSaveReq longTermLeasingSave = AppUtils.fromJson(clientRegReq, LongTermLeasingSaveReq.class);

        longTermLeasingService.saveLongTermLeasing(file, longTermLeasingSave);

        LongTermLeasingGetReq longTermLeasingGetReq = new LongTermLeasingGetReq();
        longTermLeasingGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        longTermLeasingGetReq.setFixedAssetid(longTermLeasingGetReq.getFixedAssetid());

        LongTermLeasingResp resp = longTermLeasingService.getLongTermLeasing(longTermLeasingGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);

    }

    @PostMapping(value = RegisterURLConstants.LONG_TERM_LEASING_DELETE)
    public ResponseEntity<LongTermLeasingResp> longTermLeasingDelete(
            @RequestBody LongTermLeasingDeactiveReq longTermLeasingDeleteReq) {
        longTermLeasingService.longTermLeasingDelete(longTermLeasingDeleteReq);

        LongTermLeasingGetReq longTermLeasingGetReq = new LongTermLeasingGetReq();
        longTermLeasingGetReq.setFixedAssetid(longTermLeasingDeleteReq.getFixedAssetid());
        longTermLeasingGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        LongTermLeasingResp resp = longTermLeasingService.getLongTermLeasing(longTermLeasingGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.LONG_TERM_LEASING_DEACTIVATES)
    public ResponseEntity<LongTermLeasingResp> deactivateLongTermLeasing(
            @RequestBody LongTermLeasingDeactiveReq longTermLeasingDeactiveReq) {
        longTermLeasingService.deactivateLongTermLeasing(longTermLeasingDeactiveReq);

        LongTermLeasingGetReq longTermLeasingGetReq = new LongTermLeasingGetReq();
        longTermLeasingGetReq.setId(longTermLeasingDeactiveReq.getId());

        LongTermLeasingResp resp = longTermLeasingService.getLongTermLeasing(longTermLeasingGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.LONG_TERM_LEASING_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> longTermLeasingDocDownloadFile(
            @RequestParam("rentalHistoryId") Long rentalHistoryId) {
        LongTermLeasingDtlTO fileTo = longTermLeasingService.getRentalHistoryFileInfo(rentalHistoryId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getLeaseDocumentDetailsFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getLeaseDocumentDetailsFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getLeaseDocumentDetails()));
    }
    
    @PostMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_RENTAL_HISTORY_DOCUMENTS)
    public ResponseEntity<DocumentsResp> getProjLongTermLeasingDocuemnts(
            @RequestBody LongTermLeasingGetReq longTermLeasingGetReq) {
        DocumentsResp resp = longTermLeasingService.getProjLongTermLeasingDocuemnts(longTermLeasingGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);

    }

}
