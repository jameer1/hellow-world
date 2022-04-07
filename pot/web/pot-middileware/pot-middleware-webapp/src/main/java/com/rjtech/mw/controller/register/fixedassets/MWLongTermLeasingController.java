package com.rjtech.mw.controller.register.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.LongTermLeasingDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingGetReq;
import com.rjtech.register.fixedassets.resp.LongTermLeasingResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWLongTermLeasingController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASING, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeasingResp> getLongTermLeasing(
            @RequestBody LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = mwFixedAssetsService.getLongTermLeasing(longTermLeasingGetReq);
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASING_LAST_RECORD, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeasingResp> getLongtermleaselastrecord(
            @RequestBody LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = mwFixedAssetsService.getLongtermleaselastrecord(longTermLeasingGetReq);
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASING_ACTIVEALL_RECORD, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeasingResp> getLongtermleaseActiveAllRecord(
            @RequestBody LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = mwFixedAssetsService.getLongtermleaseActiveAllRecord(longTermLeasingGetReq);
        return new ResponseEntity<LongTermLeasingResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_LONG_TERM_LEASING, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeasingResp> saveLongTermLeasing(MultipartFile file, String longTermLeasingSaveReq) {
        return new ResponseEntity<LongTermLeasingResp>(
                mwFixedAssetsService.saveLongTermLeasing(file, longTermLeasingSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.LONG_TERM_LEASING_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeasingResp> deactivateLongTermLeasing(
            @RequestBody LongTermLeasingDeactiveReq longTermLeasingDeactiveReq) {
        return new ResponseEntity<LongTermLeasingResp>(
                mwFixedAssetsService.deactivateLongTermLeasing(longTermLeasingDeactiveReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.LONG_TERM_LEASING_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> longTermLeasingDocDownloadFile(
            @RequestParam("rentalHistoryId") Long rentalHistoryId) {
        return mwFixedAssetsService.getAssetRecordFileInfo(rentalHistoryId);
    }

}
