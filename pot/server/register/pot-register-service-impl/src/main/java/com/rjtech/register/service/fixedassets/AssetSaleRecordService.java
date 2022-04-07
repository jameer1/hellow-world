package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.SalesRecordsDtlTO;
import com.rjtech.register.fixedassets.req.SalesRecordsDeactiveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsGetReq;
import com.rjtech.register.fixedassets.req.SalesRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.SalesRecordsResp;

public interface AssetSaleRecordService {

    SalesRecordsResp getsalesRecords(SalesRecordsGetReq salesRecordsGetReq);

    void saveSalesRecords(MultipartFile file, SalesRecordsSaveReq salesRecordsSave);

    void deactivateSalesRecords(SalesRecordsDeactiveReq salesRecordsDeactiveReq);

    void salesRecordDelete(SalesRecordsDeactiveReq salesRecordsDeleteReq);

    SalesRecordsDtlTO getAssetRecordFileInfo(Long saleRecordId);
    
    DocumentsResp getSaleRecordsDocuments(SalesRecordsGetReq salesRecordsGetReq);

}
