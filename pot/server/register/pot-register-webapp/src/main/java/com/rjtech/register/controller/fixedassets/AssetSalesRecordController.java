package com.rjtech.register.controller.fixedassets;

import java.io.IOException;
import java.util.List;

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
import com.rjtech.register.fixedassets.dto.SalesRecordsDtlTO;
import com.rjtech.register.fixedassets.req.SalesRecordsDeactiveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsGetReq;
import com.rjtech.register.fixedassets.req.SalesRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.SalesRecordsResp;
import com.rjtech.register.service.fixedassets.AssetSaleRecordService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class AssetSalesRecordController {

    @Autowired
    private AssetSaleRecordService assetSaleRecordService;

    @RequestMapping(value = RegisterURLConstants.GET_SALESRECORD, method = RequestMethod.POST)
    public ResponseEntity<SalesRecordsResp> getSalesRecords(@RequestBody SalesRecordsGetReq salesRecordsGetReq) {

        SalesRecordsResp resp = assetSaleRecordService.getsalesRecords(salesRecordsGetReq);
        return new ResponseEntity<SalesRecordsResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.SAVE_SALESRECORD, method = RequestMethod.POST)
    public ResponseEntity<SalesRecordsResp> saveClient(MultipartFile file, String clientRegReq) throws IOException {
        SalesRecordsSaveReq salesRecordsSave = AppUtils.fromJson(clientRegReq, SalesRecordsSaveReq.class);

        assetSaleRecordService.saveSalesRecords(file, salesRecordsSave);

        SalesRecordsGetReq salesRecordsGetReq = new SalesRecordsGetReq();
        salesRecordsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        salesRecordsGetReq.setFixedAssetid(salesRecordsGetReq.getFixedAssetid());

        SalesRecordsResp resp = assetSaleRecordService.getsalesRecords(salesRecordsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<SalesRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SALES_RECORD_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<SalesRecordsResp> deactivateSalesRecords(
            @RequestBody SalesRecordsDeactiveReq salesRecordsDeactiveReq) {
        assetSaleRecordService.deactivateSalesRecords(salesRecordsDeactiveReq);

        SalesRecordsGetReq salesRecordsGetReq = new SalesRecordsGetReq();
        salesRecordsGetReq.setId(salesRecordsDeactiveReq.getId());

        SalesRecordsResp resp = assetSaleRecordService.getsalesRecords(salesRecordsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<SalesRecordsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SALES_RECORD_DELETE, method = RequestMethod.POST)
    public ResponseEntity<SalesRecordsResp> salesRecordDelete(
            @RequestBody SalesRecordsDeactiveReq salesRecordsDeleteReq) {
        assetSaleRecordService.salesRecordDelete(salesRecordsDeleteReq);

        SalesRecordsGetReq salesRecordsGetReq = new SalesRecordsGetReq();
        salesRecordsGetReq.setId(salesRecordsDeleteReq.getId());

        SalesRecordsResp resp = assetSaleRecordService.getsalesRecords(salesRecordsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<SalesRecordsResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.SALES_RECORD_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> getAssetRecordFileInfo(@RequestParam("saleRecordId") Long saleRecordId) {
        SalesRecordsDtlTO fileTo = assetSaleRecordService.getAssetRecordFileInfo(saleRecordId);
        System.out.println("FIle name:"+fileTo.getSalesRecordsDetailsFileName());
        System.out.println("FIle type:"+fileTo.getSalesRecordsDetailsFileType());
        System.out.println("size:"+fileTo.getSalesRecordsDetails().length);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getSalesRecordsDetailsFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getSalesRecordsDetailsFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getSalesRecordsDetails()));
    }
    
    // code to download file
    /*@GetMapping(value = RegisterURLConstants.SALES_RECORD_DOC_DOWNLOAD_FILE)
    public ByteArrayResource getAssetRecordFileInfo(@RequestParam("saleRecordId") Long saleRecordId) {
        SalesRecordsDtlTO fileTo = assetSaleRecordService.getAssetRecordFileInfo(saleRecordId);
        //System.out.println("FIle name:"+fileTo.getSalesRecordsDetailsFileName());
        //System.out.println("FIle type:"+fileTo.getSalesRecordsDetailsFileType());
        //System.out.println("size:"+fileTo.getSalesRecordsDetails().length);
        return new ByteArrayResource(fileTo.getSalesRecordsDetails());
    }*/
    
    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_SALE_RECORDS_DOCUMENTS, method = RequestMethod.POST)
    public ResponseEntity<DocumentsResp> getSaleRecordsDocuments(@RequestBody SalesRecordsGetReq salesRecordsGetReq) {
        DocumentsResp resp = assetSaleRecordService.getSaleRecordsDocuments(salesRecordsGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);
    }

}
