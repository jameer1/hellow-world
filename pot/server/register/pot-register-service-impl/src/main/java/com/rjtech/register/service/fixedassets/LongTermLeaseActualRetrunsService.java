package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.LongTermLeaseActualRetrunsDtlTO;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeleteReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.LongTermLeaseActualRetrunsResp;

public interface LongTermLeaseActualRetrunsService {

    LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetruns(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq);
    
    LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetrunsLastRecord(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq);

   void saveLongTermLeaseActualRetruns(MultipartFile file,LongTermLeaseActualRetrunsSaveReq longTermLeaseActualRetrunsSave);

    void longTermLeaseActualRetrunsDelete(LongTermLeaseActualRetrunsDeleteReq longTermLeaseActualRetrunsDeleteReq);
    
    void longTermLeaseActualRetrunsDeactivate(LongTermLeaseActualRetrunsDeactiveReq longTermLeaseActualRetrunsDeactiveReq);

    LongTermLeaseActualRetrunsDtlTO getlongTermLeaseActualRetrunsDocDownloadFile(Long fileId);
    
    DocumentsResp getProjLongTermActualRetrunsDocuemnts(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq);

}
