package com.rjtech.register.controller.fixedassets;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.rjtech.register.fixedassets.dto.LongTermLeaseActualRetrunsDtlTO;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeleteReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.LongTermLeaseActualRetrunsResp;
import com.rjtech.register.service.fixedassets.LongTermLeaseActualRetrunsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class LongTermLeaseActualRetrunsController {

    @Autowired
    private LongTermLeaseActualRetrunsService longTermLeaseActualRetrunsService;

    @PostMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASE_ACTUAL_RETRUNS)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> getLongTermLeaseActualRetruns(
            @RequestBody LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        LongTermLeaseActualRetrunsResp resp = longTermLeaseActualRetrunsService
                .getLongTermLeaseActualRetruns(longTermLeaseActualRetrunsGetReq);
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(resp, HttpStatus.OK);

    }
    
    @PostMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASE_ACTUAL_RETRUNS_LAST_RECORD)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> getLongTermLeaseActualRetrunsLastRecord(
            @RequestBody LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        LongTermLeaseActualRetrunsResp resp = longTermLeaseActualRetrunsService
                .getLongTermLeaseActualRetrunsLastRecord(longTermLeaseActualRetrunsGetReq);
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(resp, HttpStatus.OK);

    }

    @PostMapping(value = RegisterURLConstants.SAVE_LONG_TERM_LEASE_ACTUAL_RETRUNS)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> saveLongTermLeaseActualRetruns(MultipartFile file,
            String clientRegReq){        
        LongTermLeaseActualRetrunsSaveReq longTermLeaseActualRetrunsSaveReq = AppUtils.fromJson(clientRegReq, LongTermLeaseActualRetrunsSaveReq.class);
        longTermLeaseActualRetrunsService.saveLongTermLeaseActualRetruns(file,longTermLeaseActualRetrunsSaveReq);
       
        LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq = new LongTermLeaseActualRetrunsGetReq();
        longTermLeaseActualRetrunsGetReq.setFixedAssetid(longTermLeaseActualRetrunsGetReq.getFixedAssetid());
        longTermLeaseActualRetrunsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
               
        LongTermLeaseActualRetrunsResp resp = longTermLeaseActualRetrunsService
                .getLongTermLeaseActualRetruns(longTermLeaseActualRetrunsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DEACTIVATES)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> deactivateLongTermLeaseActualRetruns(
            @RequestBody LongTermLeaseActualRetrunsDeactiveReq longTermLeaseActualRetrunsDeactiveReq) {
        longTermLeaseActualRetrunsService.longTermLeaseActualRetrunsDeactivate(longTermLeaseActualRetrunsDeactiveReq);

        LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq = new LongTermLeaseActualRetrunsGetReq();
        longTermLeaseActualRetrunsGetReq.setId(longTermLeaseActualRetrunsDeactiveReq.getId());

        LongTermLeaseActualRetrunsResp resp = longTermLeaseActualRetrunsService
                .getLongTermLeaseActualRetruns(longTermLeaseActualRetrunsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(resp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DELETE)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> longTermLeaseActualRetrunsDelete(
            @RequestBody LongTermLeaseActualRetrunsDeleteReq longTermLeaseActualRetrunsDeleteReq) {
        longTermLeaseActualRetrunsService.longTermLeaseActualRetrunsDelete(longTermLeaseActualRetrunsDeleteReq);

        LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq = new LongTermLeaseActualRetrunsGetReq();
        longTermLeaseActualRetrunsGetReq.setFixedAssetid(longTermLeaseActualRetrunsDeleteReq.getFixedAssetid());
        longTermLeaseActualRetrunsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        LongTermLeaseActualRetrunsResp resp = longTermLeaseActualRetrunsService
                .getLongTermLeaseActualRetruns(longTermLeaseActualRetrunsGetReq);
        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<byte[]> longTermLeaseActualRetrunsDocDownloadFile(@RequestParam("longTermLeaseActualId") Long longTermLeaseActualId) {
        LongTermLeaseActualRetrunsDtlTO fileTo = longTermLeaseActualRetrunsService
                .getlongTermLeaseActualRetrunsDocDownloadFile(longTermLeaseActualId);
        byte[] documentBody = fileTo.getUploadMoneyDocument();
        HttpHeaders header = new HttpHeaders();
        if (fileTo.getUploadMoneyDocumentFileType() == null) {
            header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        } else {
            header.set(HttpHeaders.CONTENT_TYPE, fileTo.getUploadMoneyDocumentFileType());
        }
        header.set(HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileTo.getUploadMoneyDocumentFileName());
        header.setContentLength(documentBody.length);
        return new ResponseEntity<byte[]>(documentBody, header, HttpStatus.OK);
    }

    
    @PostMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_LONG_TERMS_DOCUMENTS)
    public ResponseEntity<DocumentsResp> getProjLongTermActualRetrunsDocuemnts(
            @RequestBody LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        DocumentsResp resp = longTermLeaseActualRetrunsService
                .getProjLongTermActualRetrunsDocuemnts(longTermLeaseActualRetrunsGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);

    }

}
