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
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;
import com.rjtech.register.fixedassets.model.AssetLifeStatusDtlEntity;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusSaveReq;
import com.rjtech.register.fixedassets.resp.AssetLifeStatusResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.repository.fixeassets.AssetLifeStatusRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.service.fixedassets.AssetLifeStatusService;
import com.rjtech.register.service.handler.fixedassets.AssetLifeStatusDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.common.model.UserMstrEntity;

@Service(value = "assetLifeStatusService")
@RJSService(modulecode = "assetLifeStatusService")
@Transactional
public class AssetLifeStatusServiceImpl implements AssetLifeStatusService {

    private static final Logger log = LoggerFactory.getLogger(AssetLifeStatusServiceImpl.class);

    @Autowired
    private AssetLifeStatusRepository assetLifeStatusRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    @Autowired
    private AswS3FileService aswS3FileService;

    @Autowired
    private AmazonS3 awsS3Client;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public AssetLifeStatusResp getAssetLifeStatus(AssetLifeStatusGetReq assetLifeStatusGetReq) {
        AssetLifeStatusResp resp = new AssetLifeStatusResp();
        List<AssetLifeStatusDtlEntity> assetLifeStatusDtlEntities = assetLifeStatusRepository
                .findAllAssetLifeStatus(assetLifeStatusGetReq.getFixedAssetid(), assetLifeStatusGetReq.getStatus());

        for (AssetLifeStatusDtlEntity assetLifeStatusDtlEntity : assetLifeStatusDtlEntities) {
            resp.getAssetLifeStatusDtlTOs().add(
                    AssetLifeStatusDtlHandler.convertEntityToPOJO(assetLifeStatusDtlEntity, awsS3Bucket, awsS3Client));
        }

        return resp;

    }

    public void saveAssetLifeStatus(MultipartFile file, AssetLifeStatusSaveReq assetLifeStatusSave) {

        List<AssetLifeStatusDtlEntity> list = new ArrayList<AssetLifeStatusDtlEntity>();
        String keyString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
        
        System.out.println("Folder category:"+assetLifeStatusSave.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( assetLifeStatusSave.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = assetLifeStatusSave.getFolderCategory();
        Long userId = assetLifeStatusSave.getUserId();
        
        if (CommonUtil.isListHasData(assetLifeStatusSave.getAssetLifeStatusDtlTOs())) {
            for (AssetLifeStatusDtlTO assetLifeStatusDtlTO : assetLifeStatusSave.getAssetLifeStatusDtlTOs()) {
                AssetLifeStatusDtlEntity entity = null;
                AssetLifeStatusDtlEntity assetLifeStatusDtlEntity;
                if (CommonUtil.isNonBlankLong(assetLifeStatusDtlTO.getId())) {
                    isUpdate = true;
                    entity = assetLifeStatusRepository.findOne(assetLifeStatusDtlTO.getId());
                    if (null != file && null != entity.getDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            				{
            					System.out.println("if block of upload condition update block");
            					String extras[] = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( entity.getId() ) };
            					// Upload asset life status docs to server			
            					uploadUtil.uploadFile( file, folder_category, folder_path, extras );            					
            					ProjDocFileEntity projDocFileEntity = AssetLifeStatusDtlHandler.convertAssetLifePOJOToProjDocFileEntity( file, assetLifeStatusDtlTO.getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, assetLifeStatusDtlTO.getId() );
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
                    entity = new AssetLifeStatusDtlEntity();
                }
                assetLifeStatusDtlEntity = AssetLifeStatusDtlHandler.convertPOJOToEntity(entity, file,
                        assetLifeStatusDtlTO, fixedAssetsRegisterRepository);
                list.add(assetLifeStatusDtlEntity);
            }
            assetLifeStatusRepository.save(list);
        }

        if (null != file && !isUpdate) {
            for (AssetLifeStatusDtlEntity assetLifeStatusDtlEntity : list) {
                String uniqueKey = AwsS3FileKeyConstants.FIXED_ASSET_LIFE_STATUS_RECORDS_DETAIL
                        + assetLifeStatusDtlEntity.getId();
                try {                	
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
                	{
                		System.out.println("if block of upload condition of save");
                		Long asset_id = assetLifeStatusDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid();
    					String extras[] = { String.valueOf( assetLifeStatusDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( asset_id ), String.valueOf( assetLifeStatusDtlEntity.getId() ) };
    					// Upload purchase record docs to server				
    					uploadUtil.uploadFile( file, folder_category, folder_path, extras );            					
    					ProjDocFileEntity projDocFileEntity = AssetLifeStatusDtlHandler.convertAssetLifePOJOToProjDocFileEntity( file, asset_id, userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, assetLifeStatusDtlEntity.getId() );
    					assetLifeStatusDtlEntity.setProjDocFileEntity( projDocFileEntity );
                	}
                    else
                    {
                    	keyString = aswS3FileService.uploadFile(file, uniqueKey);
                    }
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                assetLifeStatusDtlEntity.setDocKey(keyString);
                assetLifeStatusRepository.save(assetLifeStatusDtlEntity);
            }
        }

    }

    public void deactivateAssetLifeStatus(AssetLifeStatusDeactiveReq assetLifeStatusDeactiveReq) {
        String docUniqueKey = null;
        for (int i = 0; i < assetLifeStatusDeactiveReq.getAssetLifeStatusIds().size(); i++) {
            AssetLifeStatusDtlEntity assetLifeStatusDtlEntity = assetLifeStatusRepository
                    .findOne(assetLifeStatusDeactiveReq.getAssetLifeStatusIds().get(i));
            docUniqueKey = assetLifeStatusDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }

        assetLifeStatusRepository.deactivateAssetLifeStatus(assetLifeStatusDeactiveReq.getAssetLifeStatusIds(),
                assetLifeStatusDeactiveReq.getStatus());
    }

    public void assetLifeStatusDelete(AssetLifeStatusDeleteReq assetLifeStatusDeleteReq) {
        assetLifeStatusRepository.assetLifeStatusDelete(assetLifeStatusDeleteReq.getAssetLifeStatusIds());

    }

    public AssetLifeStatusDtlTO assetLifeStatusDocDownloadFile(Long fileId) {
        AssetLifeStatusDtlTO assetLifeStatusDtlTO = new AssetLifeStatusDtlTO();
        AssetLifeStatusDtlEntity assetLifeStatusDtlEntity = assetLifeStatusRepository.findOne(fileId);
        assetLifeStatusDtlTO.setDocKey(assetLifeStatusDtlEntity.getDocKey());
        byte[] fileBytes = null;
        try {        	
            fileBytes = aswS3FileService.downloadFile(assetLifeStatusDtlTO.getDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        assetLifeStatusDtlTO.setLifeAssignmentRecordsDocumentFileName(
                assetLifeStatusDtlEntity.getLifeAssignmentRecordsDocumentFileName());
        assetLifeStatusDtlTO.setLifeAssignmentRecords(fileBytes);
        assetLifeStatusDtlTO.setLifeAssignmentRecordsDocumentFileType(
                assetLifeStatusDtlEntity.getLifeAssignmentRecordsDocumentFileType());
        return assetLifeStatusDtlTO;
    }

    public DocumentsResp getProjAssetLifeStatusDocuemnts(AssetLifeStatusGetReq assetLifeStatusGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(assetLifeStatusGetReq.getProjectId(), 1);
        if (!fixedAssetIds.isEmpty()) {
            List<AssetLifeStatusDtlEntity> assetLifeStatusDtlEntities = assetLifeStatusRepository
                    .findAllProjectAssetLifeStatus(fixedAssetIds, 1);
            for (AssetLifeStatusDtlEntity assetLifeStatusDtlEntity : assetLifeStatusDtlEntities) {
                resp.getDocumentsDtlTOs().add(AssetLifeStatusDtlHandler
                        .convertEntityToPOJODocuments(assetLifeStatusDtlEntity, awsS3Client, awsS3Bucket));
            }
        }
        return resp;
    }

}
