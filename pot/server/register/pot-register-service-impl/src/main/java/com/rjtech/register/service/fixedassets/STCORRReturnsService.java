package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.STCORRReturnsDtlTO;
import com.rjtech.register.fixedassets.req.STCORRReturnsDeactiveReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsFilterReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsGetReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsSaveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsGetReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.STCORRReturnsResp;
import com.rjtech.register.fixedassets.resp.SalesRecordsResp;
import com.rjtech.register.fixedassets.resp.SubSequentRentalRecepitsResp;

public interface STCORRReturnsService {

    STCORRReturnsResp getStcorrReturns(STCORRReturnsGetReq stcorrReturnsGetReq);

    void saveSTCORRReturns(MultipartFile file,STCORRReturnsSaveReq stcorrReturnsSave);

    void deactivateSTCORRReturns(STCORRReturnsDeactiveReq stcorrReturnsDeactiveReq);

    STCORRReturnsResp getStcorrReturnsSearch(STCORRReturnsFilterReq stcorrReturnsFilterReq);

    void stcorrReturnsDelete(STCORRReturnsDeactiveReq stcorrReturnsDeleteReq);

    STCORRReturnsDtlTO stcorrReturnsDocDownloadFile(Long fileId);
    
    SubSequentRentalRecepitsResp getStcorrSubSequentRentalReceipts(STCORRReturnsGetReq stcorrReturnsGetReq); 
    
    DocumentsResp getProjShortDocuemnts(STCORRReturnsGetReq stcorrReturnsGetReq);

}
