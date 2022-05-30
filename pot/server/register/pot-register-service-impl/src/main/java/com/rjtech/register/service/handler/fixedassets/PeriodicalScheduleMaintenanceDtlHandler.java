package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.dto.PeriodicalScheduleMaintenanceDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.PeriodicalScheduleMaintenanceDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.common.model.UserMstrEntity;

public class PeriodicalScheduleMaintenanceDtlHandler {

    private PeriodicalScheduleMaintenanceDtlHandler() {
    }

    public static PeriodicalScheduleMaintenanceDtlEntity convertPOJOToEntity(
            PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity, MultipartFile planFile,
            MultipartFile actualFile, PeriodicalScheduleMaintenanceDtlTO periodicalScheduleMaintenanceDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {
        if (CommonUtil.objectNotNull(periodicalScheduleMaintenanceDtlTO)
                && CommonUtil.isNonBlankLong(periodicalScheduleMaintenanceDtlTO.getId())) {
            periodicalScheduleMaintenanceDtlEntity.setId(periodicalScheduleMaintenanceDtlTO.getId());
        }
        periodicalScheduleMaintenanceDtlEntity
                .setDueDate(CommonUtil.convertStringToDate(periodicalScheduleMaintenanceDtlTO.getDueDate()));
        periodicalScheduleMaintenanceDtlEntity
                .setMaintanceCategory(periodicalScheduleMaintenanceDtlTO.getMaintenanceCategory());
        periodicalScheduleMaintenanceDtlEntity
                .setMaintanceSubCategory(periodicalScheduleMaintenanceDtlTO.getMaintenanceSubCategory());
        periodicalScheduleMaintenanceDtlEntity
                .setActualResponsibleSupervisor(periodicalScheduleMaintenanceDtlTO.getActualResponsibleSupervisor());

        periodicalScheduleMaintenanceDtlEntity.setCurrentStatus(periodicalScheduleMaintenanceDtlTO.getCurrentStatus());
        periodicalScheduleMaintenanceDtlEntity
                .setStartDate(CommonUtil.convertStringToDate(periodicalScheduleMaintenanceDtlTO.getStartDate()));
        periodicalScheduleMaintenanceDtlEntity
                .setFinishDate(CommonUtil.convertStringToDate(periodicalScheduleMaintenanceDtlTO.getFinishDate()));
        periodicalScheduleMaintenanceDtlEntity
                .setPurchaseOrderNumber(periodicalScheduleMaintenanceDtlTO.getPurchaseOrderNumber());
        periodicalScheduleMaintenanceDtlEntity
                .setMaterialConsumptionRecords(periodicalScheduleMaintenanceDtlTO.getMaterialsConsumptionRecords());
        periodicalScheduleMaintenanceDtlEntity.setStatus(periodicalScheduleMaintenanceDtlTO.getStatus());
        periodicalScheduleMaintenanceDtlEntity
                .setPlanResponsibleSupervisor(periodicalScheduleMaintenanceDtlTO.getPlanResponsibleSupervisor());

        if (CommonUtil.objectNotNull(actualFile)) {
            periodicalScheduleMaintenanceDtlEntity.setActualRecordsDetailsFileName(actualFile.getOriginalFilename());
            periodicalScheduleMaintenanceDtlEntity.setActualRecordsDetailsFileType(actualFile.getContentType());
        }
        if (CommonUtil.objectNotNull(planFile)) {
            periodicalScheduleMaintenanceDtlEntity.setPlanRecordsDetailsFileName(planFile.getOriginalFilename());
            periodicalScheduleMaintenanceDtlEntity.setPlanRecordsDetailsFileType(planFile.getContentType());
        }
        if (CommonUtil.isNonBlankLong(AppUserUtils.getClientId())) {
            ClientRegEntity clientRegMstrEntity = new ClientRegEntity();
            clientRegMstrEntity.setClientId(AppUserUtils.getClientId());
            periodicalScheduleMaintenanceDtlEntity.setClientRegEntity(clientRegMstrEntity);
        }

        if (CommonUtil.isNonBlankLong(periodicalScheduleMaintenanceDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(periodicalScheduleMaintenanceDtlTO.getFixedAssetid());
            periodicalScheduleMaintenanceDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }

        return periodicalScheduleMaintenanceDtlEntity;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(PeriodicalScheduleMaintenanceDtlEntity entity,
            String awsS3Bucket, AmazonS3 awsS3Client) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getActualRecordsDetailsFileName());
        documentsDtlTO.setFileType(entity.getActualRecordsDetailsFileType());
        if (null != entity.getActualDocKey()) {
            documentsDtlTO.setDocUrl(awsS3Client
                    .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getActualDocKey())));
            documentsDtlTO.setFileSize(
                    awsS3Client.getObjectMetadata(awsS3Bucket, entity.getActualDocKey()).getContentLength());
        }
        return documentsDtlTO;
    }

    public static PeriodicalScheduleMaintenanceDtlTO convertEntityToPOJO(PeriodicalScheduleMaintenanceDtlEntity entity,
            AmazonS3 awsS3Client, String awsS3Bucket) {
        PeriodicalScheduleMaintenanceDtlTO periodicalScheduleMaintenanceDtlTO = new PeriodicalScheduleMaintenanceDtlTO();
        if (entity.getActualDocKey() == null) {
            periodicalScheduleMaintenanceDtlTO.setId(entity.getId());
            periodicalScheduleMaintenanceDtlTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
            periodicalScheduleMaintenanceDtlTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
            periodicalScheduleMaintenanceDtlTO.setDueDate(CommonUtil.convertDateToString(entity.getDueDate()));
            periodicalScheduleMaintenanceDtlTO.setMaintenanceCategory(entity.getMaintanceCategory());
            periodicalScheduleMaintenanceDtlTO.setMaintenanceSubCategory(entity.getMaintanceSubCategory());
            periodicalScheduleMaintenanceDtlTO.setActualResponsibleSupervisor(entity.getActualResponsibleSupervisor());
            periodicalScheduleMaintenanceDtlTO.setCurrentStatus(entity.getCurrentStatus());
            periodicalScheduleMaintenanceDtlTO.setPurchaseOrderNumber(entity.getPurchaseOrderNumber());
            periodicalScheduleMaintenanceDtlTO.setMaterialsConsumptionRecords(entity.getMaterialConsumptionRecords());
            periodicalScheduleMaintenanceDtlTO.setStatus(entity.getStatus());
            periodicalScheduleMaintenanceDtlTO.setPlanResponsibleSupervisor(entity.getPlanResponsibleSupervisor());
            periodicalScheduleMaintenanceDtlTO
                    .setActualRecordsDocumentFileName(entity.getActualRecordsDetailsFileName());
            periodicalScheduleMaintenanceDtlTO
                    .setActualRecordsDocumentFileType(entity.getActualRecordsDetailsFileType());
            periodicalScheduleMaintenanceDtlTO.setPlanRecordDocumentFileName(entity.getPlanRecordsDetailsFileName());
            periodicalScheduleMaintenanceDtlTO.setPlanRecordDocumentFileType(entity.getPlanRecordsDetailsFileType());
            if (null != entity.getActualDocKey()) {
                periodicalScheduleMaintenanceDtlTO.setActualDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getActualDocKey())));
            }
        }
        return periodicalScheduleMaintenanceDtlTO;
    }

    public static ProjDocFileEntity convertPSMPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long periodId ) {
    	ProjDocFolderEntity projDocFolderEntity = projDocFolderRepository.findByNameAndProjId( folder_category );
        UserMstrEntity userMstrEntity = new UserMstrEntity();
        UploadUtil uploadUtil = new UploadUtil();
        ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        ProjDocFileEntity resProjDocFileEntity = new ProjDocFileEntity();
        userMstrEntity.setUserId( userId );
    	FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                .findOne( fixedAssetId );
    	//String folder_path = folder.getUploadFolder();
        projDocFileEntity.setName( file.getOriginalFilename() );
		projDocFileEntity.setFolderId( projDocFolderEntity );
		projDocFileEntity.setFileSize( uploadUtil.bytesIntoHumanReadable( file.getSize() ) );
		projDocFileEntity.setFileType( file.getContentType() );
		projDocFileEntity.setProjectId( fixedAssetsRegisterDtlEntity.getProjMstrEntity() );		
		projDocFileEntity.setCreatedBy( userMstrEntity );
		projDocFileEntity.setUpdatedBy( userMstrEntity );
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( periodId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
