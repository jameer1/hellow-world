package com.rjtech.register.controller.fixedassets;


	import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	import com.rjtech.register.fixedassets.dto.TollCollectionDtlTO;
	import com.rjtech.register.fixedassets.req.TollCollectionDeleteReq;
	import com.rjtech.register.fixedassets.req.TollCollectionGetReq;
	import com.rjtech.register.fixedassets.req.TollCollectionSaveReq;
	import com.rjtech.register.fixedassets.resp.TollCollectionResp;
	import com.rjtech.register.fixedassets.resp.DocumentsResp;
	import com.rjtech.register.service.fixedassets.TollCollectionService;

	@RestController
	@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
	public class TollCollectionController {

		private static final Logger log = LoggerFactory.getLogger(TollCollectionController.class);
		
	    @Autowired
	    private TollCollectionService tollCollectionService;

	    @RequestMapping(value = RegisterURLConstants.GET_TOLL_COLLECTION, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> getTollCollection(
	            @RequestBody TollCollectionGetReq tollCollectionGetReq) {
	        TollCollectionResp resp = tollCollectionService
	                .getTollCollection(tollCollectionGetReq);
	        return new ResponseEntity<TollCollectionResp>(resp, HttpStatus.OK);

	    }
	    
	    //last record in metod
	    
	    @RequestMapping(value = RegisterURLConstants.GET_TOLL_COLLECTION_LAST_RECORD, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> getTollCollectionLastRecord(
	            @RequestBody TollCollectionGetReq tollCollectionGetReq) {
	    	
	        TollCollectionResp resp = tollCollectionService
	                .getTollCollectionLastRecord(tollCollectionGetReq);
	        return new ResponseEntity<TollCollectionResp>(resp, HttpStatus.OK);

	    }
	    
	    //==========================

	    @RequestMapping(value = RegisterURLConstants.SAVE_TOLL_COLLECTION, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> saveTollCollection(MultipartFile file,
	            String clientRegReq) throws IOException {
	    	log.info(" clientRegReq"+ clientRegReq);
	    	System.out.println(" clientRegReq controller Toll "+ clientRegReq);
	        TollCollectionSaveReq TollCollectionSaveReq = AppUtils.fromJson(clientRegReq,
	                TollCollectionSaveReq.class);
	       
	        System.out.println(" tollCollectionSaveReq sdf "+ TollCollectionSaveReq.getTollCollectionDtlTO().size());
	        
	        tollCollectionService.saveTollCollection(file, TollCollectionSaveReq);
	        TollCollectionGetReq tollCollectionGetReq = new TollCollectionGetReq();
	        tollCollectionGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        TollCollectionResp resp = tollCollectionService
	                .getTollCollection(tollCollectionGetReq);
	        resp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<TollCollectionResp>(resp, HttpStatus.OK);
	    }

	    @RequestMapping(value = RegisterURLConstants.TOLL_COLLECTION_DELETE, method = RequestMethod.POST)
	    public ResponseEntity<TollCollectionResp> TollCollectionDelete(
	            @RequestBody TollCollectionDeleteReq tollCollectionDeleteReq) {
	        tollCollectionService.TollCollectionDelete(tollCollectionDeleteReq);

	        TollCollectionGetReq tollCollectionGetReq = new TollCollectionGetReq();
	        tollCollectionGetReq.setId(null);
	        tollCollectionGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        TollCollectionResp resp = tollCollectionService
	                .getTollCollection(tollCollectionGetReq);
	        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<TollCollectionResp>(resp, HttpStatus.OK);
	    }
	    

	    @GetMapping(value = RegisterURLConstants.TOLL_COLLECTION_DOC_DOWNLOAD_FILE)
	    public ResponseEntity<ByteArrayResource> TollCollectionDocDownloadFile(@RequestParam("fileId") Long fileId) {
	        TollCollectionDtlTO fileTo = tollCollectionService
	                .TollCollectionDocDownloadFile(fileId);
	        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getTollFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=\"" + fileTo.getTollFileName() + "\"")
	                .body(new ByteArrayResource(fileTo.getTollDocuments()));
	    }    
	    
	    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_DOCUMENTS, method = RequestMethod.POST)
	    public ResponseEntity<DocumentsResp> getProjTollDocuemnts(
	            @RequestBody TollCollectionGetReq TollCollectionGetReq) {
	        DocumentsResp resp = tollCollectionService
	                .getProjTollDocuemnts(TollCollectionGetReq);
	        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);
	    }
}
