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

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeleteReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsSaveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsDeactiveReq;
import com.rjtech.register.fixedassets.resp.LongTermLeaseActualRetrunsResp;
import com.rjtech.register.fixedassets.resp.SalesRecordsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWLongTermLeaseActualRetrunsController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASE_ACTUAL_RETRUNS, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> getLongTermLeaseActualRetruns(
            @RequestBody LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        
        LongTermLeaseActualRetrunsResp resp = mwFixedAssetsService.getLongTermLeaseActualRetruns(longTermLeaseActualRetrunsGetReq);
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(resp, HttpStatus.OK);
       
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_LONG_TERM_LEASE_ACTUAL_RETRUNS_LAST_RECORD, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> getLongTermLeaseActualRetrunsLastRecord(
            @RequestBody LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        
        LongTermLeaseActualRetrunsResp resp = mwFixedAssetsService.getLongTermLeaseActualRetrunsLastRecord(longTermLeaseActualRetrunsGetReq);
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(resp, HttpStatus.OK);
       
    }
    

    @RequestMapping(value = RegisterURLConstants.SAVE_LONG_TERM_LEASE_ACTUAL_RETRUNS, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> saveLongTermLeaseActualRetruns(MultipartFile file,
            String longTermLeaseActualRetrunsSaveReq) {
        
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(mwFixedAssetsService.saveLongTermLeaseActualRetruns(file, longTermLeaseActualRetrunsSaveReq),
                HttpStatus.OK);
              

    }
    
    @RequestMapping(value = RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> deactivateLongTermLeaseActualRetruns(
            @RequestBody LongTermLeaseActualRetrunsDeactiveReq longTermLeaseActualRetrunsDeactiveReq) {

        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(
                mwFixedAssetsService.deactivateLongTermLeaseActualRetruns(longTermLeaseActualRetrunsDeactiveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<LongTermLeaseActualRetrunsResp> longTermLeaseActualRetrunsDelete(
            @RequestBody LongTermLeaseActualRetrunsDeleteReq longTermLeaseActualRetrunsDeleteReq) {
        
        return new ResponseEntity<LongTermLeaseActualRetrunsResp>(
                mwFixedAssetsService.longTermLeaseActualRetrunsDelete(longTermLeaseActualRetrunsDeleteReq), HttpStatus.OK);        
        
    }

    @GetMapping(value = RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> longTermLeaseActualRetrunsDocDownloadFile(@RequestParam("longTermLeaseActualId") Long longTermLeaseActualId) {
        return mwFixedAssetsService.getLongTermLeaseActualRetrunsFileInfo(longTermLeaseActualId);

    }
}
