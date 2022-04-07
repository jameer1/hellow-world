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
import com.rjtech.register.fixedassets.dto.FixedAssetRepairsRecordsDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetRepairsDtlEntity;
import com.rjtech.register.fixedassets.req.AssetRepairsDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetRepairsResp;
import com.rjtech.register.repository.fixeassets.FixedAssetRepairsRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.service.fixedassets.FixedAssetsRepairsRecordsService;
import com.rjtech.register.service.handler.fixedassets.FixedAssetRepairsDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;

@Service(value = "fixedAssetsRepairsRecordsService")
@RJSService(modulecode = "fixedAssetsRepairsRecordsService")
@Transactional
public class FixedAssetsRepairsRecordsServiceImpl implements FixedAssetsRepairsRecordsService {

    private static final Logger log = LoggerFactory.getLogger(FixedAssetsRepairsRecordsServiceImpl.class);

    @Autowired
    private FixedAssetRepairsRepository fixedAssetRepairsRepository;

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

    @Override
    public FixedAssetRepairsResp getFixedAssetsRepairs(FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq) {
        FixedAssetRepairsResp resp = new FixedAssetRepairsResp();
        List<FixedAssetRepairsDtlEntity> fixedAssetRepairsDtlEntities = fixedAssetRepairsRepository.findAllAssetRepairs(
                fixedAssetRepairsRecordsGetReq.getFixedAssetid(), fixedAssetRepairsRecordsGetReq.getStatus());
        for (FixedAssetRepairsDtlEntity fixedAssetPurchaseRecordsDtlEntity : fixedAssetRepairsDtlEntities) {
            resp.getFixedAssetRepairsRecordsDtlTOs().add(FixedAssetRepairsDtlHandler
                    .convertEntityToPOJO(fixedAssetPurchaseRecordsDtlEntity, awsS3Bucket, awsS3Client));
        }
        return resp;
    }

    @Override
    public void saveFixedAssetsRepairs(MultipartFile file,
            FixedAssetRepairsRecordsSaveReq fixedAssetRepairsRecordsSaveReq) {
        List<FixedAssetRepairsDtlEntity> list = new ArrayList<FixedAssetRepairsDtlEntity>();
        List<FixedAssetRepairsDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
        
        System.out.println("Folder category:"+fixedAssetRepairsRecordsSaveReq.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( fixedAssetRepairsRecordsSaveReq.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = fixedAssetRepairsRecordsSaveReq.getFolderCategory();
        Long userId = fixedAssetRepairsRecordsSaveReq.getUserId();
        
        List<FixedAssetRepairsRecordsDtlTO> fixedAssetRepairsRecordsList = fixedAssetRepairsRecordsSaveReq
                .getAssetsRepaisSchedulesTO();
        if (CommonUtil.isListHasData(fixedAssetRepairsRecordsList)) {
            for (FixedAssetRepairsRecordsDtlTO fixedAssetRepairsRecordsDtlTO : fixedAssetRepairsRecordsList) {
                FixedAssetRepairsDtlEntity entity = null;
                FixedAssetRepairsDtlEntity fixedAssetRepairsDtlEntity;
                Long repairId = fixedAssetRepairsRecordsDtlTO.getId();
                if (CommonUtil.isNonBlankLong(repairId)) {
                    isUpdate = true;
                    entity = fixedAssetRepairsRepository.findOne(repairId);
                    if (null != file && null != entity.getDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    	                	{
    	                		Long asset_id = entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid();
    	                		String[] extras = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( asset_id ), String.valueOf( repairId ) };
    	                		uploadUtil.uploadFile( file, folder_category, folder_path, extras );
    	                		ProjDocFileEntity planProjDocFileEntity = FixedAssetRepairsDtlHandler.convertRepairsPOJOToProjDocFileEntity( file, asset_id, userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, repairId );
            					entity.setProjDocFileEntity( planProjDocFileEntity );
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
                    entity = new FixedAssetRepairsDtlEntity();
                }
                fixedAssetRepairsDtlEntity = FixedAssetRepairsDtlHandler.convertRepairsPOJOEntity(entity, file,
                        fixedAssetRepairsRecordsDtlTO, fixedAssetsRegisterRepository);
                list.add(fixedAssetRepairsDtlEntity);
            }
            listResp = fixedAssetRepairsRepository.save(list);
        }
        if (null != file && !isUpdate) {
            for (FixedAssetRepairsDtlEntity fixedAssetRepairsDtlEntity : listResp) {
                String uniqueKey = AwsS3FileKeyConstants.FIXED_ASSET_REPAIR_RECORDS_DETAIL
                        + fixedAssetRepairsDtlEntity.getId();
                try {
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
                	{
                		Long asset_id = fixedAssetRepairsDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid();
                		String[] extras = { String.valueOf( fixedAssetRepairsDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( asset_id ), String.valueOf( fixedAssetRepairsDtlEntity.getId() ) };
                		uploadUtil.uploadFile( file, folder_category, folder_path, extras );
                		ProjDocFileEntity planProjDocFileEntity = FixedAssetRepairsDtlHandler.convertRepairsPOJOToProjDocFileEntity( file, asset_id, userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, fixedAssetRepairsDtlEntity.getId() );
                		fixedAssetRepairsDtlEntity.setProjDocFileEntity( planProjDocFileEntity );
                	}
                    else
                    {
                    	keyString = aswS3FileService.uploadFile(file, uniqueKey);
                    }
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                fixedAssetRepairsDtlEntity.setDocKey(keyString);
                fixedAssetRepairsRepository.save(fixedAssetRepairsDtlEntity);
            }
        }
    }

    @Override
    public void deactivateAssetsRepairs(AssetRepairsDeactiveReq assetRepairsDeactiveReq) {
        String docUniqueKey = null;
        List<Long> deactivateIds = assetRepairsDeactiveReq.getRepairRecordsIds();
        for (int i = 0; i < deactivateIds.size(); i++) {
            FixedAssetRepairsDtlEntity fixedAssetRepairsDtlEntity = fixedAssetRepairsRepository
                    .findOne(deactivateIds.get(i));
            docUniqueKey = fixedAssetRepairsDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
        fixedAssetRepairsRepository.deactivateRepairsRecord(assetRepairsDeactiveReq.getRepairRecordsIds(),
                assetRepairsDeactiveReq.getStatus());

    }

    @Override
    public FixedAssetRepairsRecordsDtlTO getAssetRecordFileInfo(Long repairRecordId) {
        FixedAssetRepairsRecordsDtlTO fixedAssetRepairsRecordsDtlTO = new FixedAssetRepairsRecordsDtlTO();
        FixedAssetRepairsDtlEntity fixedAssetRepairsDtlEntity = fixedAssetRepairsRepository.findOne(repairRecordId);
        fixedAssetRepairsRecordsDtlTO.setDocKey(fixedAssetRepairsDtlEntity.getDocKey());
        byte[] fileBytes = null;
        try {
            fileBytes = aswS3FileService.downloadFile(fixedAssetRepairsDtlEntity.getDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        fixedAssetRepairsRecordsDtlTO.setRepairsRecordDetails(fileBytes);
        fixedAssetRepairsRecordsDtlTO
                .setRepairsRecordsDetailsFileName((fixedAssetRepairsDtlEntity.getRepairsRecordsDetailsFileName()));
        fixedAssetRepairsRecordsDtlTO
                .setRepairsRecordsDetailsFileType(fixedAssetRepairsDtlEntity.getRepairsRecordsDetailsFileType());
        return fixedAssetRepairsRecordsDtlTO;
    }

    @Override
    public DocumentsResp getProjRepairsDocuemnts(FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(fixedAssetRepairsRecordsGetReq.getProjectId(), 1);
        if (!fixedAssetIds.isEmpty()) {
            List<FixedAssetRepairsDtlEntity> fixedAssetRepairsDtlEntities = fixedAssetRepairsRepository
                    .findAllProjectRepairs(fixedAssetIds, 1);
            for (FixedAssetRepairsDtlEntity fixedAssetRepairsDtlEntity : fixedAssetRepairsDtlEntities) {
                resp.getDocumentsDtlTOs().add(FixedAssetRepairsDtlHandler
                        .convertEntityToPOJODocuments(fixedAssetRepairsDtlEntity, awsS3Bucket, awsS3Client));
            }
        }
        return resp;
    }
}