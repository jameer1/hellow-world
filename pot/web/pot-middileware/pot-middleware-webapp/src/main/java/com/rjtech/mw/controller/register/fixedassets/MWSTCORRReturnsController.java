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
import com.rjtech.register.fixedassets.req.STCORRReturnsDeactiveReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsFilterReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsGetReq;
import com.rjtech.register.fixedassets.resp.STCORRReturnsResp;
import com.rjtech.register.fixedassets.resp.SubSequentRentalRecepitsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWSTCORRReturnsController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_STCORR_RETURNS, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> getStcorrReturns(@RequestBody STCORRReturnsGetReq stcorrReturnsGetReq) {
        STCORRReturnsResp resp = mwFixedAssetsService.getStcorrReturns(stcorrReturnsGetReq);
        return new ResponseEntity<STCORRReturnsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_STCORR_RETURNS, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> saveStcorrReturns(MultipartFile file, String saveShortTermReq) {
        return new ResponseEntity<STCORRReturnsResp>(mwFixedAssetsService.saveStcorrReturns(file, saveShortTermReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.STCORR_RETURNS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> deactivateSTCORRReturns(
            @RequestBody STCORRReturnsDeactiveReq stcorrReturnsDeactiveReq) {
        return new ResponseEntity<STCORRReturnsResp>(
                mwFixedAssetsService.deactivateSTCORRReturns(stcorrReturnsDeactiveReq), HttpStatus.OK);
    }
    
    @GetMapping(value = RegisterURLConstants.STCORR_RETURNS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> getSTCORRReturnsFileInfo(@RequestParam("shortTermRecordId") Long shortTermRecordId) {
        return mwFixedAssetsService.getSTCORRReturnsFileInfo(shortTermRecordId);
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_STCORR_SUBSEQUENT_RENTAL_RECEIPTS, method = RequestMethod.POST)
    public ResponseEntity<SubSequentRentalRecepitsResp> getStcorrSubSequentRentalReceipts(@RequestBody STCORRReturnsGetReq stcorrReturnsGetReq) {        
        SubSequentRentalRecepitsResp resp = mwFixedAssetsService.getStcorrSubSequentRentalReceipts(stcorrReturnsGetReq);
        return new ResponseEntity<SubSequentRentalRecepitsResp>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.STCORR_RETURNS_SEARCH, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> getStcorrReturnsSearch( @RequestBody STCORRReturnsFilterReq stcorrReturnsFilterReq) {        
        STCORRReturnsResp resp = mwFixedAssetsService.getStcorrReturnsSearch(stcorrReturnsFilterReq);
        return new ResponseEntity<STCORRReturnsResp>(resp, HttpStatus.OK);
    }
}
