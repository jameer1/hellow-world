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
import com.rjtech.register.fixedassets.dto.AssetCostValueStatusDtlTO;
import com.rjtech.register.fixedassets.model.AssetCostValueStatusDtlEntity;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusSaveReq;
import com.rjtech.register.fixedassets.resp.AssetCostValueStatusResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.repository.fixeassets.AssetCostValueStatusRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.service.fixedassets.AssetCostValueStatusService;
import com.rjtech.register.service.handler.fixedassets.AssetCostValueStatusDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.common.utils.UploadUtil;

@Service(value = "assetCostValueStatusService")
@RJSService(modulecode = "assetCostValueStatusService")
@Transactional
public class AssetCostValueStatusServiceImpl implements AssetCostValueStatusService {

    private static final Logger log = LoggerFactory.getLogger(AssetCostValueStatusServiceImpl.class);

    @Autowired
    private AssetCostValueStatusRepository assetCostValueStatusRepository;

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
    
    public AssetCostValueStatusResp getAssetCostValueStatus(AssetCostValueStatusGetReq assetCostValueStatusGetReq) {
        AssetCostValueStatusResp resp = new AssetCostValueStatusResp();
        List<AssetCostValueStatusDtlEntity> assetCostValueStatusDtlEntities = assetCostValueStatusRepository
                .findAllAssetCostValueStatus(assetCostValueStatusGetReq.getFixedAssetid(),
                        assetCostValueStatusGetReq.getStatus());

        for (AssetCostValueStatusDtlEntity assetCostValueStatusDtlEntity : assetCostValueStatusDtlEntities) {
            resp.getAssetCostValueStatusDtlTOs().add(AssetCostValueStatusDtlHandler
                    .convertEntityToPOJO(assetCostValueStatusDtlEntity, awsS3Bucket, awsS3Client));
        }

        return resp;
    }

    public void saveAssetCostValueStatus(MultipartFile file, AssetCostValueStatusSaveReq assetCostValueStatusSave) {
        List<AssetCostValueStatusDtlEntity> list = new ArrayList<>();
        List<AssetCostValueStatusDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
        
        System.out.println("Folder category:"+assetCostValueStatusSave.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( assetCostValueStatusSave.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = assetCostValueStatusSave.getFolderCategory();
        Long userId = assetCostValueStatusSave.getUserId();
        
        String folder_name = assetCostValueStatusSave.getFolderCategory();
        if (CommonUtil.isListHasData(assetCostValueStatusSave.getAssetCostValueStatusDtlTOs())) {
            for (AssetCostValueStatusDtlTO assetCostValueStatusDtlTO : assetCostValueStatusSave
                    .getAssetCostValueStatusDtlTOs()) {
                AssetCostValueStatusDtlEntity entity = null;
                AssetCostValueStatusDtlEntity assetCostValueStatusDtlEntity;

                if (CommonUtil.isNonBlankLong(assetCostValueStatusDtlTO.getId())) {
                    isUpdate = true;
                    entity = assetCostValueStatusRepository.findOne(assetCostValueStatusDtlTO.getId());
                    if (null != file && null != entity.getDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            				{
            					System.out.println("if block of upload condition update block");
            					String extras[] = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( entity.getId() ) };
            					// Upload asset life status docs to server			
            					uploadUtil.uploadFile( file, folder_category, folder_path, extras );            					
            					ProjDocFileEntity projDocFileEntity = AssetCostValueStatusDtlHandler.convertAssetCostPOJOToProjDocFileEntity( file, assetCostValueStatusDtlTO.getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, assetCostValueStatusDtlTO.getId() );
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
                    entity = new AssetCostValueStatusDtlEntity();
                }
                assetCostValueStatusDtlEntity = AssetCostValueStatusDtlHandler.convertPOJOToEntity(entity, file,
                        assetCostValueStatusDtlTO, fixedAssetsRegisterRepository);
                list.add(assetCostValueStatusDtlEntity);
            }
            listResp = assetCostValueStatusRepository.save(list);
        }
        if (null != file && !isUpdate) {
            for (AssetCostValueStatusDtlEntity assetCostValueStatusDtlEntity : listResp) {
                String uniqueKey = AwsS3FileKeyConstants.FIXED_ASSET_COST_VALUE_STATUS_RECORDS_DETAIL
                        + assetCostValueStatusDtlEntity.getId();
                try {
                	
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
                	{
                		System.out.println("if block of upload condition update block");
    					String extras[] = { String.valueOf( assetCostValueStatusDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( assetCostValueStatusDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( assetCostValueStatusDtlEntity.getId() ) };
    					// Upload asset life status docs to server			
    					uploadUtil.uploadFile( file, folder_category, folder_path, extras );            					
    					ProjDocFileEntity projDocFileEntity = AssetCostValueStatusDtlHandler.convertAssetCostPOJOToProjDocFileEntity( file, assetCostValueStatusDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, assetCostValueStatusDtlEntity.getId() );
    					assetCostValueStatusDtlEntity.setProjDocFileEntity( projDocFileEntity );
                	}
                	else
                	{
                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	}
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                assetCostValueStatusDtlEntity.setDocKey(keyString);
                assetCostValueStatusRepository.save(assetCostValueStatusDtlEntity);
            }
        }

    }

    public void deactivateAssetCostValueStatus(AssetCostValueStatusDeactiveReq assetCostValueStatusDeactiveReq) {
        String docUniqueKey = null;
        for (int i = 0; i < assetCostValueStatusDeactiveReq.getAssetCostValueStatusIds().size(); i++) {
            AssetCostValueStatusDtlEntity assetCostValueStatusDtlEntity = assetCostValueStatusRepository
                    .findOne(assetCostValueStatusDeactiveReq.getAssetCostValueStatusIds().get(i));
            docUniqueKey = assetCostValueStatusDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
        assetCostValueStatusRepository.deactivateAssetCostValueStatus(
                assetCostValueStatusDeactiveReq.getAssetCostValueStatusIds(),
                assetCostValueStatusDeactiveReq.getStatus());
    }

    public AssetCostValueStatusDtlTO assetCostValueStatusDocDownloadFile(Long purchaseRecordId) {
    	System.out.println("purchaseRecordId:"+purchaseRecordId);
        AssetCostValueStatusDtlTO assetCostValueStatusDtlTO = new AssetCostValueStatusDtlTO();
        AssetCostValueStatusDtlEntity assetCostValueStatusDtlEntity = assetCostValueStatusRepository
                .findOne(purchaseRecordId);
        assetCostValueStatusDtlTO.setDocKey(assetCostValueStatusDtlEntity.getDocKey());
        byte[] fileBytes = null;
        UploadUtil uploadUtil = new UploadUtil();
        try {
        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
        	{
        		String file_path = "E://pavani/Downloads/Resources/Immovable Assets/Asset Cost and Value Status/121/418/commands_to_execute_v3.txt";
        		uploadUtil.downloadFile( file_path );
        	}
        	else
        	{
        		fileBytes = aswS3FileService.downloadFile(assetCostValueStatusDtlEntity.getDocKey());
        	}
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        assetCostValueStatusDtlTO.setAssetCostValueRecords(fileBytes);
        assetCostValueStatusDtlTO
                .setAssetCostValueDocumentFileName(assetCostValueStatusDtlEntity.getAssetCostValueDocumentFileName());
        assetCostValueStatusDtlTO
                .setAssetCostValueDocumentFileType(assetCostValueStatusDtlEntity.getAssetCostValueDocumentFileType());
        return assetCostValueStatusDtlTO;
    }

    public DocumentsResp getProjAssetCostValueDocuemnts(AssetCostValueStatusGetReq assetCostValueStatusGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(assetCostValueStatusGetReq.getProjectId(), 1);
        if (!fixedAssetIds.isEmpty()) {
            List<AssetCostValueStatusDtlEntity> assetCostValueStatusDtlEntities = assetCostValueStatusRepository
                    .findAllProjectAssetCostValue(fixedAssetIds, 1);
            for (AssetCostValueStatusDtlEntity assetCostValueStatusDtlEntity : assetCostValueStatusDtlEntities) {
                resp.getDocumentsDtlTOs().add(AssetCostValueStatusDtlHandler
                        .convertEntityToPOJODocuments(assetCostValueStatusDtlEntity, awsS3Client, awsS3Bucket));
            }
        }
        return resp;
    }
}
