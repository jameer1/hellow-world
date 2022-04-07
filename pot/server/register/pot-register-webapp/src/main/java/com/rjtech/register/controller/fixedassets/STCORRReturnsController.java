package com.rjtech.register.controller.fixedassets;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rjtech.register.fixedassets.dto.STCORRReturnsDtlTO;
import com.rjtech.register.fixedassets.req.STCORRReturnsDeactiveReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsFilterReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsGetReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.STCORRReturnsResp;
import com.rjtech.register.fixedassets.resp.SubSequentRentalRecepitsResp;
import com.rjtech.register.service.fixedassets.STCORRReturnsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class STCORRReturnsController {

    @Autowired
    private STCORRReturnsService stcorrReturnsService;

    @RequestMapping(value = RegisterURLConstants.GET_STCORR_RETURNS, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> getStcorrReturns(@RequestBody STCORRReturnsGetReq stcorrReturnsGetReq) {        
        STCORRReturnsResp resp = stcorrReturnsService.getStcorrReturns(stcorrReturnsGetReq);
        return new ResponseEntity<STCORRReturnsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_STCORR_RETURNS, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> saveStcorrReturns(MultipartFile file, String clientRegReq)
            throws IOException {        
        STCORRReturnsSaveReq stcorrReturnsSave = AppUtils.fromJson(clientRegReq, STCORRReturnsSaveReq.class);
        stcorrReturnsService.saveSTCORRReturns(file, stcorrReturnsSave);        
        STCORRReturnsGetReq stcorrReturnsGetReq = new STCORRReturnsGetReq();
        stcorrReturnsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        stcorrReturnsGetReq.setFixedAssetid(stcorrReturnsGetReq.getFixedAssetid());
        STCORRReturnsResp resp = stcorrReturnsService.getStcorrReturns(stcorrReturnsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<STCORRReturnsResp>(resp, HttpStatus.OK);
       
    }

    @RequestMapping(value = RegisterURLConstants.STCORR_RETURNS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> deactivateSTCORRReturns(
            @RequestBody STCORRReturnsDeactiveReq stcorrReturnsDeactiveReq) {        
        stcorrReturnsService.deactivateSTCORRReturns(stcorrReturnsDeactiveReq);
        STCORRReturnsGetReq stcorrReturnsGetReq = new STCORRReturnsGetReq();
        stcorrReturnsGetReq.setId(stcorrReturnsDeactiveReq.getId());
        STCORRReturnsResp resp = stcorrReturnsService.getStcorrReturns(stcorrReturnsGetReq);
        resp.cloneAppResp(CommonUtil.getDeactiveAppResp()); 
        return new ResponseEntity<STCORRReturnsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.STCORR_RETURNS_SEARCH, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> getStcorrReturnsSearch(
            @RequestBody STCORRReturnsFilterReq stcorrReturnsFilterReq) {
        STCORRReturnsResp resp = stcorrReturnsService.getStcorrReturnsSearch(stcorrReturnsFilterReq);
        return new ResponseEntity<STCORRReturnsResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.STCORR_RETURNS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<STCORRReturnsResp> stcorrReturnsDelete(
            @RequestBody STCORRReturnsDeactiveReq stcorrReturnsDeleteReq) {
        stcorrReturnsService.stcorrReturnsDelete(stcorrReturnsDeleteReq);

        STCORRReturnsGetReq stcorrReturnsGetReq = new STCORRReturnsGetReq();
        stcorrReturnsGetReq.setSubid(stcorrReturnsDeleteReq.getSubid());
        stcorrReturnsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        STCORRReturnsResp resp = stcorrReturnsService.getStcorrReturns(stcorrReturnsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<STCORRReturnsResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.STCORR_RETURNS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<byte[]> stcorrReturnsDocDownloadFile(@RequestParam("shortTermRecordId") Long shortTermRecordId) {
        
        
        STCORRReturnsDtlTO fileTo = stcorrReturnsService
                .stcorrReturnsDocDownloadFile(shortTermRecordId);
        byte[] documentBody = fileTo.getTenantRecordDetails();
        HttpHeaders header = new HttpHeaders();
        if (fileTo.getTenantRecordDetailsFileType() == null) {
            header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        } else {
            header.set(HttpHeaders.CONTENT_TYPE, fileTo.getTenantRecordDetailsFileType());
        }
        header.set(HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileTo.getTenantRecordDetailsFileName());
        header.setContentLength(documentBody.length);
        return new ResponseEntity<byte[]>(documentBody, header, HttpStatus.OK);   
              
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_STCORR_SUBSEQUENT_RENTAL_RECEIPTS, method = RequestMethod.POST)
    public ResponseEntity<SubSequentRentalRecepitsResp> getStcorrSubSequentRentalReceipts(@RequestBody STCORRReturnsGetReq stcorrReturnsGetReq) {        
        SubSequentRentalRecepitsResp resp = stcorrReturnsService.getStcorrSubSequentRentalReceipts(stcorrReturnsGetReq);
        return new ResponseEntity<SubSequentRentalRecepitsResp>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_SHORT_TERMS_DOCUMENTS, method = RequestMethod.POST)
    public ResponseEntity<DocumentsResp> getProjPurchaseDocuemnts(
            @RequestBody STCORRReturnsGetReq stcorrReturnsGetReq) {
        DocumentsResp resp = stcorrReturnsService.getProjShortDocuemnts(stcorrReturnsGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);

    }
    
}
