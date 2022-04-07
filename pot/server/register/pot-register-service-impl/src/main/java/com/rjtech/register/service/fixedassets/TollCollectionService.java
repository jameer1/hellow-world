package com.rjtech.register.service.fixedassets;

//public class TollCollectionService {

	import org.springframework.web.multipart.MultipartFile;

	import com.rjtech.register.fixedassets.dto.TollCollectionDtlTO;
	import com.rjtech.register.fixedassets.req.TollCollectionDeleteReq;
	import com.rjtech.register.fixedassets.req.TollCollectionGetReq;
	import com.rjtech.register.fixedassets.req.TollCollectionSaveReq;
	import com.rjtech.register.fixedassets.resp.TollCollectionResp;
	import com.rjtech.register.fixedassets.resp.DocumentsResp;

	public interface TollCollectionService {

	    TollCollectionResp getTollCollection(
	            TollCollectionGetReq TollCollectionGetReq);
	    
	    
	    TollCollectionResp getTollCollectionLastRecord(
	            TollCollectionGetReq TollCollectionGetReq);

	    void saveTollCollection(MultipartFile file, TollCollectionSaveReq tollCollectionSaveReq);

	    void TollCollectionDelete(TollCollectionDeleteReq TollCollectionDeleteReq);

	    TollCollectionDtlTO TollCollectionDocDownloadFile(Long fileId);
	    
	    DocumentsResp getProjTollDocuemnts(
	            TollCollectionGetReq TollCollectionGetReq);
}
