package com.rjtech.mw.controller.register.fixedassets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//public class MWTollColletionController {

	import org.springframework.beans.factory.annotation.Autowired;
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
	import com.rjtech.register.fixedassets.req.TollCollectionDeleteReq;
	import com.rjtech.register.fixedassets.req.TollCollectionGetReq;
	import com.rjtech.register.fixedassets.resp.TollCollectionResp;


	@RestController
	@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
	public class MWTollColletionController {
		
		 private static final Logger log = LoggerFactory.getLogger(MWTollColletionController.class);

	    @Autowired
	    private MWFixedAssetsService mwFixedAssetsService;

	    @RequestMapping(value = RegisterURLConstants.GET_TOLL_COLLECTION, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> getTollCollection(
	            @RequestBody TollCollectionGetReq TollCollectionGetReq) {
	        TollCollectionResp resp = mwFixedAssetsService
	                .getTollCollection(TollCollectionGetReq);
	        return new ResponseEntity<TollCollectionResp>(resp, HttpStatus.OK);
	    }
	    
	    // last record in method
	    
	    @RequestMapping(value = RegisterURLConstants.GET_TOLL_COLLECTION_LAST_RECORD, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> getTollCollectionLastRecord(
	            @RequestBody TollCollectionGetReq TollCollectionGetReq) {
	        TollCollectionResp resp = mwFixedAssetsService
	                .getTollCollectionLastRecord(TollCollectionGetReq);
	        return new ResponseEntity<TollCollectionResp>(resp, HttpStatus.OK);
	    }
	    
	    //====================
	    
	    @RequestMapping(value = RegisterURLConstants.SAVE_TOLL_COLLECTION, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> saveTollCollection(
	            @RequestBody MultipartFile file, String TollCollectionSaveReq) {
	    	log.info("TollCollectionSaveReq"+TollCollectionSaveReq);
	    	System.out.println("tollCollectionSaveReq mw sdfewr"+TollCollectionSaveReq.length());
	        return new ResponseEntity<TollCollectionResp>(
	                mwFixedAssetsService.saveTollCollection(file, TollCollectionSaveReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = RegisterURLConstants.TOLL_COLLECTION_DELETE, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> TollCollectionDelete(
	            @RequestBody TollCollectionDeleteReq TollCollectionDeleteReq) {
	        return new ResponseEntity<TollCollectionResp>(
	                mwFixedAssetsService.TollCollectionDelete(TollCollectionDeleteReq), HttpStatus.OK);
	    }

	    @GetMapping(value = RegisterURLConstants.TOLL_COLLECTION_DOC_DOWNLOAD_FILE)
	    public ResponseEntity<Void> TollCollectionDocDownloadFile(@RequestParam("fileId") Long fileId) {
	        mwFixedAssetsService.TollCollectionDocDownloadFile(fileId);
	        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	    }
}
