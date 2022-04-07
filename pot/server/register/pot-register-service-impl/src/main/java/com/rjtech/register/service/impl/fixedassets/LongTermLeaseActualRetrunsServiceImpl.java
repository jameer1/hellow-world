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
import com.rjtech.register.fixedassets.dto.LongTermLeaseActualRetrunsDtlTO;
import com.rjtech.register.fixedassets.model.LongTermLeaseActualRetrunsDtlEntity;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeleteReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.LongTermLeaseActualRetrunsResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.LongTermLeaseActualRetrunsRepository;
import com.rjtech.register.service.fixedassets.LongTermLeaseActualRetrunsService;
import com.rjtech.register.service.handler.fixedassets.LongTermLeaseActualRetrunsDtlHandler;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;

@Service(value = "longTermLeaseActualRetrunsService")
@Transactional
public class LongTermLeaseActualRetrunsServiceImpl implements LongTermLeaseActualRetrunsService {
    
    private static final Logger log = LoggerFactory.getLogger(LongTermLeaseActualRetrunsServiceImpl.class);

    @Autowired
    private LongTermLeaseActualRetrunsRepository longTermLeaseActualRetrunsRepository;
    
    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;
    
    @Autowired
    private AswS3FileService aswS3FileService;
    
    @Autowired
    private AmazonS3 awsS3Client;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetruns(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        LongTermLeaseActualRetrunsResp resp = new LongTermLeaseActualRetrunsResp();
        List<LongTermLeaseActualRetrunsDtlEntity> longTermLeaseActualRetrunsDtlEntities = longTermLeaseActualRetrunsRepository
                .findAllLongTermLeaseActualRetruns(longTermLeaseActualRetrunsGetReq.getFixedAssetid(),
                        longTermLeaseActualRetrunsGetReq.getStatus());

        for (LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity : longTermLeaseActualRetrunsDtlEntities) {
            resp.getLongTermLeaseActualRetrunsDtlTOs().add(LongTermLeaseActualRetrunsDtlHandler
                    .convertEntityToPOJO(longTermLeaseActualRetrunsDtlEntity, awsS3Client, awsS3Bucket));
        }

        return resp;
    }
    
    public LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetrunsLastRecord(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        LongTermLeaseActualRetrunsResp resp = new LongTermLeaseActualRetrunsResp();
        List<LongTermLeaseActualRetrunsDtlEntity> longTermLeaseActualRetrunsDtlEntities = longTermLeaseActualRetrunsRepository
                .findAllLongTermLeaseActualRetrunsLastRecord(longTermLeaseActualRetrunsGetReq.getFixedAssetid(),
                        longTermLeaseActualRetrunsGetReq.getStatus());

        for (LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity : longTermLeaseActualRetrunsDtlEntities) {
            resp.getLongTermLeaseActualRetrunsDtlTOs().add(LongTermLeaseActualRetrunsDtlHandler
                    .convertEntityToPOJO(longTermLeaseActualRetrunsDtlEntity, awsS3Client, awsS3Bucket));
        }

        return resp;
    }
    

    public void saveLongTermLeaseActualRetruns(MultipartFile file,LongTermLeaseActualRetrunsSaveReq longTermLeaseActualRetrunsSave) {
        List<LongTermLeaseActualRetrunsDtlEntity> list = new ArrayList<>();
        List<LongTermLeaseActualRetrunsDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
        
        System.out.println("Folder category:"+longTermLeaseActualRetrunsSave.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( longTermLeaseActualRetrunsSave.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = longTermLeaseActualRetrunsSave.getFolderCategory();
        Long userId = longTermLeaseActualRetrunsSave.getUserId();
        
        if (CommonUtil.isListHasData(longTermLeaseActualRetrunsSave.getLongTermLeaseActualRetrunsDtlTOs())) {
            for (LongTermLeaseActualRetrunsDtlTO longTermLeaseActualRetrunsDtlTO :longTermLeaseActualRetrunsSave.getLongTermLeaseActualRetrunsDtlTOs()) {
                LongTermLeaseActualRetrunsDtlEntity entity = null;
                LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity;
                if (CommonUtil.isNonBlankLong(longTermLeaseActualRetrunsDtlTO.getId())) {
                    isUpdate = true;
                    entity = longTermLeaseActualRetrunsRepository.findOne(longTermLeaseActualRetrunsDtlTO.getId());
                    if (null != file && null !=entity.getDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            				{
            					System.out.println("if block of upload condition update block");
            					String extras[] = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( longTermLeaseActualRetrunsDtlTO.getId() ) };
            					// Upload purchase record docs to server				
            					uploadUtil.uploadFile( file, folder_category, folder_path, extras );            					
            					ProjDocFileEntity projDocFileEntity = LongTermLeaseActualRetrunsDtlHandler.convertLTLAReturnsPOJOToProjDocFileEntity( file, longTermLeaseActualRetrunsDtlTO.getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, longTermLeaseActualRetrunsDtlTO.getId() );
            					entity.setProjDocFileEntity( projDocFileEntity );
            				}
                        	else
                        	{
                        		aswS3FileService.updateExistingFile(file, entity.getDocKey());
                        	}
                        } catch (IOException e) {
                            log.error("Exception occurred while updating  the existing file to s3");
                        }
                    }else
                    {
                        isUpdate = false;
                    }
                } else {
                    entity = new LongTermLeaseActualRetrunsDtlEntity();
                }
                longTermLeaseActualRetrunsDtlEntity = LongTermLeaseActualRetrunsDtlHandler.convertPOJOToEntity(entity, file,  longTermLeaseActualRetrunsDtlTO, fixedAssetsRegisterRepository);
                list.add(longTermLeaseActualRetrunsDtlEntity);
            }
            listResp = longTermLeaseActualRetrunsRepository.save(list);
        }
        if (null != file && !isUpdate) {
            for (LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity : listResp) {
                String uniqueKey = AwsS3FileKeyConstants.FIXED_ASSET_Long_Term_Lease_Actual_Returns_DETAIL
                        + longTermLeaseActualRetrunsDtlEntity.getId();
                try {
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    				{
    					System.out.println("if block of upload condition save block");
    					String extras[] = { String.valueOf( longTermLeaseActualRetrunsDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( longTermLeaseActualRetrunsDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( longTermLeaseActualRetrunsDtlEntity.getId() ) };
    					// Upload purchase records docs to server				
    					uploadUtil.uploadFile( file, folder_category, folder_path, extras );    					
    					ProjDocFileEntity projDocFileEntity = LongTermLeaseActualRetrunsDtlHandler.convertLTLAReturnsPOJOToProjDocFileEntity( file, longTermLeaseActualRetrunsDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, longTermLeaseActualRetrunsDtlEntity.getId() );
    					longTermLeaseActualRetrunsDtlEntity.setProjDocFileEntity( projDocFileEntity );
    				}
                	else
                	{
                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	}
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                longTermLeaseActualRetrunsDtlEntity.setDocKey(keyString);
                longTermLeaseActualRetrunsRepository.save(longTermLeaseActualRetrunsDtlEntity);
            }
        }
       
    }

       

    public void longTermLeaseActualRetrunsDelete(
            LongTermLeaseActualRetrunsDeleteReq longTermLeaseActualRetrunsDeleteReq) {
        longTermLeaseActualRetrunsRepository
                .delateLongTermLeaseActualRetruns(longTermLeaseActualRetrunsDeleteReq.getLongTermLeaseAcutalIds());

    }
    
    public void longTermLeaseActualRetrunsDeactivate(
            LongTermLeaseActualRetrunsDeactiveReq longTermLeaseActualRetrunsDeactiveReq) {
        
        String docUniqueKey = null;
        for (int i = 0; i < longTermLeaseActualRetrunsDeactiveReq.getLongTermLeaseAcutalIds().size(); i++) {
            LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity = longTermLeaseActualRetrunsRepository
                    .findOne(longTermLeaseActualRetrunsDeactiveReq.getLongTermLeaseAcutalIds().get(i));
            docUniqueKey = longTermLeaseActualRetrunsDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
               
        longTermLeaseActualRetrunsRepository
                .longTermLeaseActualRetrunsDeactivate(longTermLeaseActualRetrunsDeactiveReq.getLongTermLeaseAcutalIds(),longTermLeaseActualRetrunsDeactiveReq.getStatus());

    }

    public LongTermLeaseActualRetrunsDtlTO getlongTermLeaseActualRetrunsDocDownloadFile(Long longTermLeaseActualId) {
        
        LongTermLeaseActualRetrunsDtlTO longTermLeaseActualRetrunsDtlTO = new LongTermLeaseActualRetrunsDtlTO();
        LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity = longTermLeaseActualRetrunsRepository.findOne(longTermLeaseActualId);
        longTermLeaseActualRetrunsDtlTO.setDocKey(longTermLeaseActualRetrunsDtlEntity.getDocKey());
        byte[] fileBytes = null;
        try {
            fileBytes = aswS3FileService.downloadFile(longTermLeaseActualRetrunsDtlTO.getDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        longTermLeaseActualRetrunsDtlTO.setUploadMoneyDocument(fileBytes);
        longTermLeaseActualRetrunsDtlTO.setUploadMoneyDocumentFileName(longTermLeaseActualRetrunsDtlEntity.getUploadMoneyDocumentFileName());
        longTermLeaseActualRetrunsDtlTO.setUploadMoneyDocumentFileType(longTermLeaseActualRetrunsDtlEntity.getUploadMoneyDocumentFileType());
        return longTermLeaseActualRetrunsDtlTO;
      
    }
    
    public DocumentsResp getProjLongTermActualRetrunsDocuemnts(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository.findAllAssetByProjectId(longTermLeaseActualRetrunsGetReq.getProjectId(),1);
        if(!fixedAssetIds.isEmpty()) {
            List<LongTermLeaseActualRetrunsDtlEntity> entities = longTermLeaseActualRetrunsRepository
                    .findAllProjectLongTermActualRetruns(fixedAssetIds,1);       
            for (LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity : entities) {
                resp.getDocumentsDtlTOs().add(LongTermLeaseActualRetrunsDtlHandler
                        .convertEntityToPOJODocuments(longTermLeaseActualRetrunsDtlEntity, awsS3Bucket, awsS3Client));
            }
        }
        return resp;
    }

  

}