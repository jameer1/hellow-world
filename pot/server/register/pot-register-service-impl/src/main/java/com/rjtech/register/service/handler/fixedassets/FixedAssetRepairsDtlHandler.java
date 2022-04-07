package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.dto.FixedAssetRepairsRecordsDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetRepairsDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.common.model.UserMstrEntity;

public class FixedAssetRepairsDtlHandler {
    private FixedAssetRepairsDtlHandler() {
    }

    public static FixedAssetRepairsRecordsDtlTO convertEntityToPOJO(FixedAssetRepairsDtlEntity entity,
            String awsS3Bucket, AmazonS3 awsS3Client) {
        FixedAssetRepairsRecordsDtlTO fixedAssetRepairsRecordsDtlTO = new FixedAssetRepairsRecordsDtlTO();
        if (entity.getDocKey() == null) {
            fixedAssetRepairsRecordsDtlTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
            fixedAssetRepairsRecordsDtlTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
            fixedAssetRepairsRecordsDtlTO.setResponsibleSupervisor(entity.getResponsibleSupervisor());
            fixedAssetRepairsRecordsDtlTO.setId(entity.getId());
            fixedAssetRepairsRecordsDtlTO.setMaintenanceCategory(entity.getMaintenanceCategory());
            fixedAssetRepairsRecordsDtlTO.setMaintenanceSubCategory(entity.getMaintenanceSubCategory());
            fixedAssetRepairsRecordsDtlTO.setId(entity.getId());
            fixedAssetRepairsRecordsDtlTO.setPurchaseOrderNumber(entity.getPurchaseOrderNumber());
            fixedAssetRepairsRecordsDtlTO.setStatus(entity.getStatus());
            fixedAssetRepairsRecordsDtlTO.setMaterialsConsumptionRecords(entity.getMaterialsConsumptionRecords());
            fixedAssetRepairsRecordsDtlTO.setRepairsRecordsDetailsFileName(entity.getRepairsRecordsDetailsFileName());
            fixedAssetRepairsRecordsDtlTO.setRepairsRecordsDetailsFileType(entity.getRepairsRecordsDetailsFileType());
            fixedAssetRepairsRecordsDtlTO
                    .setRepairsRecordsDetailsDocumentFileSize(entity.getRepairsRecordsDetailsDocumentFileSize());
            if (null != entity.getDocKey()) {
                fixedAssetRepairsRecordsDtlTO.setDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            }
            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                fixedAssetRepairsRecordsDtlTO.setClientId(clientRegEntity.getClientId());
            }
        }
        return fixedAssetRepairsRecordsDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(FixedAssetRepairsDtlEntity entity, String awsS3Bucket,
            AmazonS3 awsS3Client) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getRepairsRecordsDetailsFileName());
        documentsDtlTO.setFileType(entity.getRepairsRecordsDetailsFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
        }
        return documentsDtlTO;
    }

    public static FixedAssetRepairsDtlEntity convertRepairsPOJOEntity(
            FixedAssetRepairsDtlEntity fixedAssetRepairsDtlEntity, MultipartFile file,
            FixedAssetRepairsRecordsDtlTO fixedAssetRepairsRecordsDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {
        if (CommonUtil.objectNotNull(fixedAssetRepairsRecordsDtlTO)
                && CommonUtil.isNonBlankLong(fixedAssetRepairsRecordsDtlTO.getId())) {
            fixedAssetRepairsDtlEntity.setId(fixedAssetRepairsRecordsDtlTO.getId());
        }
        fixedAssetRepairsDtlEntity.setId(fixedAssetRepairsRecordsDtlTO.getId());
        fixedAssetRepairsDtlEntity.setStatus(fixedAssetRepairsRecordsDtlTO.getStatus());
        fixedAssetRepairsDtlEntity
                .setMaterialsConsumptionRecords(fixedAssetRepairsRecordsDtlTO.getMaterialsConsumptionRecords());
        fixedAssetRepairsDtlEntity
                .setStartDate(CommonUtil.convertStringToDate(fixedAssetRepairsRecordsDtlTO.getStartDate()));
        fixedAssetRepairsDtlEntity
                .setFinishDate(CommonUtil.convertStringToDate(fixedAssetRepairsRecordsDtlTO.getFinishDate()));
        fixedAssetRepairsDtlEntity.setPurchaseOrderNumber(fixedAssetRepairsRecordsDtlTO.getPurchaseOrderNumber());
        fixedAssetRepairsDtlEntity.setResponsibleSupervisor(fixedAssetRepairsRecordsDtlTO.getResponsibleSupervisor());
        fixedAssetRepairsDtlEntity.setMaintenanceCategory(fixedAssetRepairsRecordsDtlTO.getMaintenanceCategory());
        fixedAssetRepairsDtlEntity.setMaintenanceSubCategory(fixedAssetRepairsRecordsDtlTO.getMaintenanceSubCategory());
        fixedAssetRepairsDtlEntity.setDocKey(fixedAssetRepairsRecordsDtlTO.getDocKey());
        if (CommonUtil.isNonBlankLong(fixedAssetRepairsRecordsDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(fixedAssetRepairsRecordsDtlTO.getFixedAssetid());
            fixedAssetRepairsDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }
        if (CommonUtil.objectNotNull(file)) {
            fixedAssetRepairsDtlEntity.setRepairsRecordsDetailsFileName(file.getOriginalFilename());
            fixedAssetRepairsDtlEntity.setRepairsRecordsDetailsFileType(file.getContentType());
        }
        return fixedAssetRepairsDtlEntity;
    }
    
    public static ProjDocFileEntity convertRepairsPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long repairId ) {
    	ProjDocFolderEntity projDocFolderEntity = projDocFolderRepository.findByNameAndProjId( folder_category );
        UserMstrEntity userMstrEntity = new UserMstrEntity();
        UploadUtil uploadUtil = new UploadUtil();
        ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        ProjDocFileEntity resProjDocFileEntity = new ProjDocFileEntity();
        userMstrEntity.setUserId( userId );
    	FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                .findOne( fixedAssetId );
        projDocFileEntity.setName( file.getOriginalFilename() );
		projDocFileEntity.setFolderId( projDocFolderEntity );
		projDocFileEntity.setFileSize( uploadUtil.bytesIntoHumanReadable( file.getSize() ) );
		projDocFileEntity.setFileType( file.getContentType() );
		projDocFileEntity.setProjectId( fixedAssetsRegisterDtlEntity.getProjMstrEntity() );		
		projDocFileEntity.setCreatedBy( userMstrEntity );
		projDocFileEntity.setUpdatedBy( userMstrEntity );
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( repairId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
