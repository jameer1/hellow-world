package com.rjtech.register.service.impl.fixedassets;



	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.List;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;
	import org.springframework.web.multipart.MultipartFile;

	import com.amazonaws.services.s3.AmazonS3;
	import com.rjtech.aws.common.s3.file.service.AswS3FileService;
	import com.rjtech.common.utils.CommonUtil;
	import com.rjtech.register.constans.AwsS3FileKeyConstants;
	import com.rjtech.register.fixedassets.dto.TollCollectionDtlTO;
	import com.rjtech.register.fixedassets.model.TollCollectionDtlEntity;
	import com.rjtech.register.fixedassets.req.TollCollectionDeleteReq;
	import com.rjtech.register.fixedassets.req.TollCollectionGetReq;
	import com.rjtech.register.fixedassets.req.TollCollectionSaveReq;
	import com.rjtech.register.fixedassets.resp.TollCollectionResp;
	import com.rjtech.register.fixedassets.resp.DocumentsResp;
	import com.rjtech.register.repository.fixeassets.TollCollectionRepository;
	import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
	import com.rjtech.register.service.fixedassets.TollCollectionService;
	import com.rjtech.register.service.handler.fixedassets.TollCollectionDtlHandler;
	import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.common.utils.UploadUtil;

	@Service(value = "tollCollectionService")
	@RJSService(modulecode = "tollCollectionService")
	@Transactional
	public class TollCollectionServiceImpl implements TollCollectionService {

	    //private static final Logger log = LoggerFactory.getLogger(TollCollectionServiceImpl.class);
	    private static final Logger log = LoggerFactory.getLogger(TollCollectionServiceImpl.class);

	    @Autowired
	    private TollCollectionRepository tollCollectionRepository;

	    @Autowired
	    private AswS3FileService aswS3FileService;

	    @Autowired
	    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

	    @Autowired
	    private AmazonS3 awsS3Client;
	    
	    @Autowired
	    private ProjDocFolderRepository projDocFolderRepository;
	    
	    @Autowired
	    private ProjDocFileRepository projDocFileRepository;
	   
	    @Value("${aws.s3.bucket}")
	    private String awsS3Bucket;

	    public TollCollectionResp getTollCollection(
	            TollCollectionGetReq tollCollectionGetReq) {
	        TollCollectionResp resp = new TollCollectionResp();
	        List<TollCollectionDtlEntity> TollCollectionDtlEntities = tollCollectionRepository
	                .findAllTollCollection(tollCollectionGetReq.getFixedAssetid(),
	                		tollCollectionGetReq.getStatus());
	        for (TollCollectionDtlEntity tollCollectionDtlEntity : TollCollectionDtlEntities) {
	            resp.getTollCollectionDtlTOs().add(TollCollectionDtlHandler
	                    .convertEntityToPOJO(tollCollectionDtlEntity, awsS3Bucket, awsS3Client));
	        }
	        return resp;
	    }
	    
	    //last record in method
	    public TollCollectionResp getTollCollectionLastRecord(
	            TollCollectionGetReq tollCollectionGetReq) {
	        TollCollectionResp resp = new TollCollectionResp();
	        List<TollCollectionDtlEntity> tollCollectionDtlEntities = tollCollectionRepository
	                .findAllProjectTollLastRecord(tollCollectionGetReq.getFixedAssetid(),
	                        tollCollectionGetReq.getStatus());
	       
	        for (TollCollectionDtlEntity tollCollectionDtlEntity : tollCollectionDtlEntities) { 	
	            resp.getTollCollectionDtlTOs().add(TollCollectionDtlHandler
	                    .convertEntityToPOJO(tollCollectionDtlEntity, awsS3Bucket, awsS3Client));
	        }
	        return resp;
	    }
	    
	    //

	    public void saveTollCollection(MultipartFile file,
	            TollCollectionSaveReq tollCollectionSaveReq) {
	    	log.info("saveTollCollection" +tollCollectionSaveReq);
	    	System.out.println("save function tollCollectionSave" + tollCollectionSaveReq);
	        List<TollCollectionDtlEntity> list = new ArrayList<TollCollectionDtlEntity>();
	        System.out.println("save function1 list" +list );
	        List<TollCollectionDtlEntity> listResp = null;
	        System.out.println("save function2");
	        String keyString = null;
	        boolean isUpdate = false;
	        
	        UploadUtil uploadUtil = new UploadUtil();
	        
	        System.out.println("Folder category:"+tollCollectionSaveReq.getFolderCategory());
	        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( tollCollectionSaveReq.getFolderCategory() );
	        String folder_path = folder.getUploadFolder();
	        String folder_category = tollCollectionSaveReq.getFolderCategory();
	        Long userId = tollCollectionSaveReq.getUserId();
	        
	        if (!tollCollectionSaveReq.getTollCollectionDtlTO().isEmpty()) {
	        	System.out.println("save function3");
	            for (TollCollectionDtlTO tollCollectionDtlTO : tollCollectionSaveReq
	                    .getTollCollectionDtlTO()) {
	                TollCollectionDtlEntity entity = null;
	                TollCollectionDtlEntity TollCollectionDtlEntity;

	                if (CommonUtil.isNonBlankLong(tollCollectionDtlTO.getId())) {
	                    isUpdate = true;
	                    entity = tollCollectionRepository.findOne(tollCollectionDtlTO.getId());
	                    if (null != file && null != entity.getDocKey()) {
	                        try {
	                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
	    	                	{
	    	                		String[] extras = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( tollCollectionDtlTO.getId() ) };
	    	                		uploadUtil.uploadFile( file, folder_category, folder_path, extras );
	    	                		ProjDocFileEntity projDocFileEntity = TollCollectionDtlHandler.convertTollCollectionPOJOToProjDocFileEntity( file, tollCollectionDtlTO.getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, tollCollectionDtlTO.getId() );
	    	                		entity.setProjDocFileEntity( projDocFileEntity );
	    	                	}
	    	                	else
	    	                	{
	    	                		aswS3FileService.updateExistingFile(file, entity.getDocKey());
	    	                	}
	                        } catch (IOException e) {
	                            log.error("Exception occurred while updating  the existing file to s3");
	                        }
	                    } else {
	                        isUpdate = false;
	                    }
	                } else {
	                    entity = new TollCollectionDtlEntity();
	                }
	                TollCollectionDtlEntity = TollCollectionDtlHandler.convertPOJOToEntity(entity, file,
	                        tollCollectionDtlTO, fixedAssetsRegisterRepository);
	                list.add(TollCollectionDtlEntity);
	            }
	            System.out.println("save function list" +list);
	            listResp = tollCollectionRepository.save(list);
	        }
	        if (null != file && !isUpdate) {
	            for (TollCollectionDtlEntity tollCollectionDtlEntity : listResp) {
	                String uniqueKey = AwsS3FileKeyConstants.TOLL_COLLECTION
	                        + tollCollectionDtlEntity.getId();	                
	                try {
	                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
	                	{
	                		String[] extras = { String.valueOf( tollCollectionDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( tollCollectionDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( tollCollectionDtlEntity.getId() ) };
	                		uploadUtil.uploadFile( file, folder_category, folder_path, extras );
	                		ProjDocFileEntity projDocFileEntity = TollCollectionDtlHandler.convertTollCollectionPOJOToProjDocFileEntity( file, tollCollectionDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, tollCollectionDtlEntity.getId() );
	                		tollCollectionDtlEntity.setProjDocFileEntity( projDocFileEntity );
	                	}
	                	else
	                	{
	                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
	                	}	                    
	                } catch (IOException e) {
	                    log.error("Exception occurred while uploading the file to s3");
	                }
	                tollCollectionDtlEntity.setDocKey(keyString);
	                tollCollectionRepository.save(tollCollectionDtlEntity);
	            }
	        }
	    }

	    public void TollCollectionDelete(TollCollectionDeleteReq tollCollectionDeleteReq) {
	        String docUniqueKey = null;
	        for (int i = 0; i < tollCollectionDeleteReq.getTollCollectionIds().size(); i++) {
	            TollCollectionDtlEntity tollCollectionDtlEntity = tollCollectionRepository
	                    .findOne(tollCollectionDeleteReq.getTollCollectionIds().get(i));
	            docUniqueKey = tollCollectionDtlEntity.getDocKey();
	            if (null != docUniqueKey) {
	                aswS3FileService.deleteFile(docUniqueKey);
	            }
	        }
	        tollCollectionRepository
	                .TollCollectionDelete(tollCollectionDeleteReq.getTollCollectionIds());
	    }

	    public TollCollectionDtlTO TollCollectionDocDownloadFile(Long carParkingTollId) {
	        TollCollectionDtlTO fixedAssetAqulisitionRecordsDtlTO = new TollCollectionDtlTO();
	        TollCollectionDtlEntity fixedAssetPurchaseRecordsDtl = tollCollectionRepository
	                .findOne(carParkingTollId);
	        fixedAssetAqulisitionRecordsDtlTO.setDocKey(fixedAssetPurchaseRecordsDtl.getDocKey());
	        byte[] fileBytes = null;
	        try {
	            fileBytes = aswS3FileService.downloadFile(fixedAssetPurchaseRecordsDtl.getDocKey());
	        } catch (IOException e) {
	            log.error("Exception occurred while downloading the file from s3");
	        }
	        fixedAssetAqulisitionRecordsDtlTO.setTollDocuments(fileBytes);
	        fixedAssetAqulisitionRecordsDtlTO
	                .setTollFileName(fixedAssetPurchaseRecordsDtl.getTollFileName());
	        fixedAssetAqulisitionRecordsDtlTO
	                .setTollFileType(fixedAssetPurchaseRecordsDtl.getTollFileType());
	        return fixedAssetAqulisitionRecordsDtlTO;
	    }

	    public DocumentsResp getProjTollDocuemnts(TollCollectionGetReq tollCollectionGetReq) {
	        DocumentsResp resp = new DocumentsResp();
	        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
	                .findAllAssetByProjectId(tollCollectionGetReq.getProjectId(), 1);
	        if (!fixedAssetIds.isEmpty()) {
	            List<TollCollectionDtlEntity> tollCollectionDtlEntities = tollCollectionRepository
	                    .findAllProjectToll(fixedAssetIds, 1);
	            for (TollCollectionDtlEntity tollCollectionDtlEntity : tollCollectionDtlEntities) {
	                resp.getDocumentsDtlTOs().add(TollCollectionDtlHandler
	                        .convertEntityToPOJODocuments(tollCollectionDtlEntity, awsS3Client, awsS3Bucket));
	            }
	        }
	        return resp;
	    }

}
