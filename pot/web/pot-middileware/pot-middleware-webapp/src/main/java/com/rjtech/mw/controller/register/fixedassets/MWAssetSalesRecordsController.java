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
import com.rjtech.register.fixedassets.req.SalesRecordsDeactiveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsGetReq;
import com.rjtech.register.fixedassets.resp.SalesRecordsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWAssetSalesRecordsController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_SALESRECORD, method = RequestMethod.POST)
    public ResponseEntity<SalesRecordsResp> getSalesRecords(@RequestBody SalesRecordsGetReq salesRecordsGetReq) {
        SalesRecordsResp resp = mwFixedAssetsService.getSalesRecords(salesRecordsGetReq);
        return new ResponseEntity<SalesRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_SALESRECORD, method = RequestMethod.POST)
    public ResponseEntity<SalesRecordsResp> saveSalesRecords(@RequestBody MultipartFile file,
            String salesRecordsSaveReq) {
        return new ResponseEntity<SalesRecordsResp>(mwFixedAssetsService.saveSalesRecords(file, salesRecordsSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SALES_RECORD_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<SalesRecordsResp> deactivateSalesRecords(
            @RequestBody SalesRecordsDeactiveReq salesRecordsDeactiveReq) {

        return new ResponseEntity<SalesRecordsResp>(
                mwFixedAssetsService.deactivateSalesRecords(salesRecordsDeactiveReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.SALES_RECORD_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> getAssetRecordFileInfo(@RequestParam("saleRecordId") Long saleRecordId) {
        return mwFixedAssetsService.getAssetRecordFileInfo(saleRecordId);
    }

}
