package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;
import com.rjtech.register.fixedassets.req.LongTermLeasingDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.LongTermLeasingResp;

public interface LongTermLeasingService {

    LongTermLeasingResp getLongTermLeasing(LongTermLeasingGetReq longTermLeasingGetReq);
    
    LongTermLeasingResp getLongtermleaselastrecord(LongTermLeasingGetReq longTermLeasingGetReq);
    
    LongTermLeasingResp getLongtermleaseActiveAllRecord(LongTermLeasingGetReq longTermLeasingGetReq);

    void saveLongTermLeasing(MultipartFile file, LongTermLeasingSaveReq longTermLeasingSave);

    void deactivateLongTermLeasing(LongTermLeasingDeactiveReq longTermLeasingDeactiveReq);

    void longTermLeasingDelete(LongTermLeasingDeactiveReq longTermLeasingDeleteReq);

    LongTermLeasingDtlTO getRentalHistoryFileInfo(Long rentalHistoryId);
    
    DocumentsResp getProjLongTermLeasingDocuemnts(
            LongTermLeasingGetReq longTermLeasingGetReq);
}
