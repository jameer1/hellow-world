package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.LongTermLeasingDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;

public class LongTermLeasingDtlHandler {
    private LongTermLeasingDtlHandler() {
    }

    public static LongTermLeasingDtlTO convertEntityToPOJO(LongTermLeasingDtlEntity entity, String awsS3Bucket,
            AmazonS3 awsS3Client) {

        LongTermLeasingDtlTO longTermLeasingDtlTO = new LongTermLeasingDtlTO();
        if (null != entity) {
            longTermLeasingDtlTO.setId(entity.getId());
            longTermLeasingDtlTO.setLeaseAgreement(entity.getLeaseAgreement());
     //       longTermLeasingDtlTO.setLeaseAgreement(newAssitId);
            longTermLeasingDtlTO.setLeaseStart(CommonUtil.convertDateToString(entity.getLeaseStart()));
            longTermLeasingDtlTO.setLeaseFinish(CommonUtil.convertDateToString(entity.getLeaseFinish()));
            longTermLeasingDtlTO.setTenantid(entity.getTenantid());
            longTermLeasingDtlTO.setTenantName(entity.getTenantName());
            longTermLeasingDtlTO.setTenantAddress(entity.getTenantAddress());
            longTermLeasingDtlTO.setPaymentCycle(entity.getPaymentCycle());
            longTermLeasingDtlTO.setNetRentAmountPerCycle(entity.getNetRentAmountPerCycle());
            longTermLeasingDtlTO.setMaintenanceCharges(entity.getMaintenanceCharges());
            longTermLeasingDtlTO.setAssetMaintenanceCharges(entity.getAssetMaintenanceCharges());
            longTermLeasingDtlTO.setTaxableAmount(entity.getTaxableAmount());
            longTermLeasingDtlTO.setTax(entity.getTax());
            longTermLeasingDtlTO.setTaxAmount(entity.getTaxableAmount());
            longTermLeasingDtlTO.setGrossRent(entity.getGrossRent());
      //      longTermLeasingDtlTO.setPaymentCycleDueDate(CommonUtil.convertDateToString(entity.getPaymentCycleDueDate()));
            longTermLeasingDtlTO.setPaymentCycleDueDate(entity.getPaymentCycleDueDate());
            longTermLeasingDtlTO
                    .setLeaseExtendedFinshDate(CommonUtil.convertDateToString(entity.getLeaseExtendedFinshDate()));
            longTermLeasingDtlTO.setLeaseActualFinishFinshDate(
                    CommonUtil.convertDateToString(entity.getLeaseActualFinishFinshDate()));
            longTermLeasingDtlTO.setLeaseDocumentDetailsFileName(entity.getLeaseDocumentDetailsFileName());
            longTermLeasingDtlTO.setCurrentStatus(entity.getCurrentStatus());
            longTermLeasingDtlTO.setLeaseDocumentDetailsFileSize(entity.getLeaseDocumentDetailsFileSize());
            longTermLeasingDtlTO.setLeaseDocumentDetailsFileType(entity.getLeaseDocumentDetailsFileType());
            if (null != entity.getDocKey()) {
                longTermLeasingDtlTO.setDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            }
            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                longTermLeasingDtlTO.setClientId(clientRegEntity.getClientId());
            }

            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

            if (null != fixedAssetsRegisterDtlEntity) {
                longTermLeasingDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
            }
        }

        return longTermLeasingDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(LongTermLeasingDtlEntity entity, String awsS3Bucket,
            AmazonS3 awsS3Client) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getLeaseDocumentDetailsFileName());
        documentsDtlTO.setFileType(entity.getLeaseDocumentDetailsFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
        }
        return documentsDtlTO;
    }
    	//for adding new record in add action
    public static LongTermLeasingDtlEntity convertLongTermLeasingPOJOEntity(MultipartFile file,
            LongTermLeasingDtlTO longTermLeasingDtlTO, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, String newAssetId) { 

        LongTermLeasingDtlEntity longTermLeasingDtlEntity = new LongTermLeasingDtlEntity();

        if (CommonUtil.objectNotNull(longTermLeasingDtlTO) && CommonUtil.isNonBlankLong(longTermLeasingDtlTO.getId())) {
            longTermLeasingDtlEntity.setId(longTermLeasingDtlTO.getId());
        }
     //   longTermLeasingDtlEntity.setLeaseAgreement(longTermLeasingDtlTO.getLeaseAgreement());
        longTermLeasingDtlEntity.setLeaseAgreement(newAssetId); // setting new asset Id
        longTermLeasingDtlEntity.setLeaseStart(CommonUtil.convertStringToDate(longTermLeasingDtlTO.getLeaseStart()));
        longTermLeasingDtlEntity.setLeaseFinish(CommonUtil.convertStringToDate(longTermLeasingDtlTO.getLeaseFinish()));
        longTermLeasingDtlEntity.setTenantid(longTermLeasingDtlTO.getTenantid());
        longTermLeasingDtlEntity.setTenantName(longTermLeasingDtlTO.getTenantName());
        longTermLeasingDtlEntity.setTenantAddress(longTermLeasingDtlTO.getTenantAddress());
        longTermLeasingDtlEntity.setPaymentCycle(longTermLeasingDtlTO.getPaymentCycle());
        longTermLeasingDtlEntity.setNetRentAmountPerCycle(longTermLeasingDtlTO.getNetRentAmountPerCycle());
        longTermLeasingDtlEntity.setMaintenanceCharges(longTermLeasingDtlTO.getMaintenanceCharges());
        longTermLeasingDtlEntity.setAssetMaintenanceCharges(longTermLeasingDtlTO.getAssetMaintenanceCharges());
        longTermLeasingDtlEntity.setTaxableAmount(longTermLeasingDtlTO.getTaxableAmount());
        longTermLeasingDtlEntity.setTax(longTermLeasingDtlTO.getTax());
        longTermLeasingDtlEntity.setTaxAmount(longTermLeasingDtlTO.getTaxableAmount());
        longTermLeasingDtlEntity.setGrossRent(longTermLeasingDtlTO.getGrossRent());
    //    longTermLeasingDtlEntity.setPaymentCycleDueDate(CommonUtil.convertStringToDate(longTermLeasingDtlTO.getPaymentCycleDueDate()));
        longTermLeasingDtlEntity.setPaymentCycleDueDate(longTermLeasingDtlTO.getPaymentCycleDueDate());
        longTermLeasingDtlEntity.setLeaseExtendedFinshDate(
                CommonUtil.convertStringToDate(longTermLeasingDtlTO.getLeaseExtendedFinshDate()));
        longTermLeasingDtlEntity.setLeaseActualFinishFinshDate(
                CommonUtil.convertStringToDate(longTermLeasingDtlTO.getLeaseActualFinishFinshDate()));
        longTermLeasingDtlEntity.setCurrentStatus(longTermLeasingDtlTO.getCurrentStatus());
        longTermLeasingDtlEntity.setStatus(StatusCodes.ACTIVE.getValue());
        
        if (CommonUtil.isNonBlankLong(longTermLeasingDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(longTermLeasingDtlTO.getFixedAssetid());
            longTermLeasingDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);            
        }
        if (CommonUtil.objectNotNull(file)) {
            longTermLeasingDtlEntity.setLeaseDocumentDetailsFileName(file.getOriginalFilename());
            longTermLeasingDtlEntity.setLeaseDocumentDetailsFileType(file.getContentType());
        }
        return longTermLeasingDtlEntity;
    }

    public static ProjDocFileEntity convertLongTermLeasingPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long longTermId ) {
    	ProjDocFolderEntity projDocFolderEntity = projDocFolderRepository.findByNameAndProjId( folder_category );
        //ProjDocFolderEntity projDocFolderEntity = new ProjDocFolderEntity();
        UserMstrEntity userMstrEntity = new UserMstrEntity();
        userMstrEntity.setUserId( userId );
        UploadUtil uploadUtil = new UploadUtil();
        ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        ProjDocFileEntity resProjDocFileEntity = new ProjDocFileEntity();
        
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
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( longTermId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
