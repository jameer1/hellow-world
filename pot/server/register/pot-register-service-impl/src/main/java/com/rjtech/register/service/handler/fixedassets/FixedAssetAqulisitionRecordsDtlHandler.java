package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;

public class FixedAssetAqulisitionRecordsDtlHandler {
    private FixedAssetAqulisitionRecordsDtlHandler() {
    }

    public static FixedAssetAqulisitionRecordsDtlTO convertEntityToPOJO(FixedAssetPurchaseRecordsDtlEntity entity,
            AmazonS3 awsS3Client, String awsS3Bucket) {

        FixedAssetAqulisitionRecordsDtlTO fixedAssetAqulisitionRecordsDtlTO = new FixedAssetAqulisitionRecordsDtlTO();
        if (null != entity) {
            fixedAssetAqulisitionRecordsDtlTO.setId(entity.getId());
            fixedAssetAqulisitionRecordsDtlTO
                    .setDateOfPurchase(CommonUtil.convertDateToString(entity.getDateOfPurchase()));
            fixedAssetAqulisitionRecordsDtlTO.setPurchaseType(entity.getPurchaseType());
            fixedAssetAqulisitionRecordsDtlTO.setVendorName(entity.getVendorName());
            fixedAssetAqulisitionRecordsDtlTO.setVendorAddress(entity.getVendorAddress());
            fixedAssetAqulisitionRecordsDtlTO.setLandSurveyLotDetails(entity.getLandSurveyLotDetails());
            fixedAssetAqulisitionRecordsDtlTO.setLotIdentificationDetails(entity.getLotIdentificationDetails());
            fixedAssetAqulisitionRecordsDtlTO.setLandSizeAndDimesions(entity.getLandSizeAndDimensions());
            fixedAssetAqulisitionRecordsDtlTO.setStructureDetails(entity.getStructureDetails());
            fixedAssetAqulisitionRecordsDtlTO.setPlantAndEquipmentDetails(entity.getPlantAndEquipmentDetails());
            fixedAssetAqulisitionRecordsDtlTO.setLandRegisterOfficeDetails(entity.getLandRegisterOfficeDetails());
            fixedAssetAqulisitionRecordsDtlTO.setLandValuation(entity.getLandValuation());
            fixedAssetAqulisitionRecordsDtlTO.setStructureValuation(entity.getStructureValuation());
            fixedAssetAqulisitionRecordsDtlTO.setPlantAndEquipmentValuation(entity.getPlantAndEquipmentValuation());
            fixedAssetAqulisitionRecordsDtlTO.setTotalValuation(entity.getTotalValuation());
            fixedAssetAqulisitionRecordsDtlTO.setTotalPurchaseAmount(entity.getTotalPurachaseAmount());
            fixedAssetAqulisitionRecordsDtlTO.setBuyerSolicitor(entity.getBuyerSolicitor());
            fixedAssetAqulisitionRecordsDtlTO.setVendorSolicitor(entity.getVendorSolicitor());
            fixedAssetAqulisitionRecordsDtlTO
                    .setFixedAssetid(entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid());
            fixedAssetAqulisitionRecordsDtlTO.setInitialMarginAmountPaid(entity.getInitialMarginAmountPaid());
			fixedAssetAqulisitionRecordsDtlTO.setTaxAmountWithHeld(entity.getTaxAmountWithHeld());
            fixedAssetAqulisitionRecordsDtlTO.setLoanAmount(entity.getLoanAmount());
            fixedAssetAqulisitionRecordsDtlTO.setLoanPeriod(entity.getLoanPeriod());
            fixedAssetAqulisitionRecordsDtlTO.setLoanAvailedFrom(entity.getLoanAvailedFrom());
            fixedAssetAqulisitionRecordsDtlTO.setFixedRateOfInterest(entity.getFixedRateOfInterest());
            fixedAssetAqulisitionRecordsDtlTO.setAnnualRateOfInterest(entity.getAnnualRateOfInterest());
            fixedAssetAqulisitionRecordsDtlTO
                    .setPurchaseRecordsDetailsFileName(entity.getPurchaseRecordsDetailsFileName());
            fixedAssetAqulisitionRecordsDtlTO
                    .setPurchaseRecordsDetailsFileType(entity.getPurchaseRecordsDetailsFileType());
            fixedAssetAqulisitionRecordsDtlTO
                    .setPurchaseRecordsDetailsDocumentFileSize(entity.getPurchaseRecordsDetailsFileSize());
            fixedAssetAqulisitionRecordsDtlTO.setDocKey(entity.getDocKey());
            fixedAssetAqulisitionRecordsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                fixedAssetAqulisitionRecordsDtlTO.setClientId(clientRegEntity.getClientId());
            }
        }

        return fixedAssetAqulisitionRecordsDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(FixedAssetPurchaseRecordsDtlEntity entity,
            AmazonS3 awsS3Client, String awsS3Bucket) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getPurchaseRecordsDetailsFileName());
        documentsDtlTO.setFileType(entity.getPurchaseRecordsDetailsFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
        }
        return documentsDtlTO;
    }

    public static FixedAssetPurchaseRecordsDtlEntity convertPurchasePOJOEntity(
            FixedAssetPurchaseRecordsDtlEntity assetPurchaseRecordsDtlEntity, MultipartFile file,
            FixedAssetAqulisitionRecordsDtlTO fixedAssetAqulisitionRecordsDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {

        if (CommonUtil.objectNotNull(fixedAssetAqulisitionRecordsDtlTO)
                && CommonUtil.isNonBlankLong(fixedAssetAqulisitionRecordsDtlTO.getId())) {
            assetPurchaseRecordsDtlEntity.setId(fixedAssetAqulisitionRecordsDtlTO.getId());
        }
        assetPurchaseRecordsDtlEntity.setDateOfPurchase(
                CommonUtil.convertStringToDate(fixedAssetAqulisitionRecordsDtlTO.getDateOfPurchase()));

        assetPurchaseRecordsDtlEntity.setPurchaseType(fixedAssetAqulisitionRecordsDtlTO.getPurchaseType());
        assetPurchaseRecordsDtlEntity.setVendorName(fixedAssetAqulisitionRecordsDtlTO.getVendorName());
        assetPurchaseRecordsDtlEntity.setVendorAddress(fixedAssetAqulisitionRecordsDtlTO.getVendorAddress());
        assetPurchaseRecordsDtlEntity
                .setLandSurveyLotDetails(fixedAssetAqulisitionRecordsDtlTO.getLandSurveyLotDetails());
        assetPurchaseRecordsDtlEntity
                .setLotIdentificationDetails(fixedAssetAqulisitionRecordsDtlTO.getLotIdentificationDetails());
        assetPurchaseRecordsDtlEntity
                .setLandSizeAndDimensions(fixedAssetAqulisitionRecordsDtlTO.getLandSizeAndDimesions());
        assetPurchaseRecordsDtlEntity.setStructureDetails(fixedAssetAqulisitionRecordsDtlTO.getStructureDetails());
        assetPurchaseRecordsDtlEntity
                .setPlantAndEquipmentDetails(fixedAssetAqulisitionRecordsDtlTO.getPlantAndEquipmentDetails());
        assetPurchaseRecordsDtlEntity
                .setLandRegisterOfficeDetails(fixedAssetAqulisitionRecordsDtlTO.getLandRegisterOfficeDetails());
        assetPurchaseRecordsDtlEntity.setLandValuation(fixedAssetAqulisitionRecordsDtlTO.getLandValuation());
        assetPurchaseRecordsDtlEntity.setStructureValuation(fixedAssetAqulisitionRecordsDtlTO.getStructureValuation());
        assetPurchaseRecordsDtlEntity
                .setPlantAndEquipmentValuation(fixedAssetAqulisitionRecordsDtlTO.getPlantAndEquipmentValuation());
        assetPurchaseRecordsDtlEntity.setTotalValuation(fixedAssetAqulisitionRecordsDtlTO.getTotalValuation());
        assetPurchaseRecordsDtlEntity
                .setTotalPurachaseAmount(fixedAssetAqulisitionRecordsDtlTO.getTotalPurchaseAmount());
        assetPurchaseRecordsDtlEntity.setBuyerSolicitor(fixedAssetAqulisitionRecordsDtlTO.getBuyerSolicitor());
        assetPurchaseRecordsDtlEntity.setVendorSolicitor(fixedAssetAqulisitionRecordsDtlTO.getVendorSolicitor());
        assetPurchaseRecordsDtlEntity.setLoanAmount(fixedAssetAqulisitionRecordsDtlTO.getLoanAmount());
        assetPurchaseRecordsDtlEntity.setLoanAvailedFrom(fixedAssetAqulisitionRecordsDtlTO.getLoanAvailedFrom());
        assetPurchaseRecordsDtlEntity.setLoanPeriod(fixedAssetAqulisitionRecordsDtlTO.getLoanPeriod());
        assetPurchaseRecordsDtlEntity
                .setAnnualRateOfInterest(fixedAssetAqulisitionRecordsDtlTO.getAnnualRateOfInterest());
        assetPurchaseRecordsDtlEntity
                .setFixedRateOfInterest(fixedAssetAqulisitionRecordsDtlTO.getFixedRateOfInterest());
        assetPurchaseRecordsDtlEntity
                .setInitialMarginAmountPaid(fixedAssetAqulisitionRecordsDtlTO.getInitialMarginAmountPaid());
		assetPurchaseRecordsDtlEntity.setTaxAmountWithHeld(fixedAssetAqulisitionRecordsDtlTO.getTaxAmountWithHeld());
        assetPurchaseRecordsDtlEntity.setStatus(fixedAssetAqulisitionRecordsDtlTO.getStatus());
        assetPurchaseRecordsDtlEntity.setDocKey(fixedAssetAqulisitionRecordsDtlTO.getDocKey());
        
        		
        if (CommonUtil.isNonBlankLong(fixedAssetAqulisitionRecordsDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(fixedAssetAqulisitionRecordsDtlTO.getFixedAssetid());
            assetPurchaseRecordsDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }
        if (CommonUtil.objectNotNull(file)) {
            assetPurchaseRecordsDtlEntity.setPurchaseRecordsDetailsFileName(file.getOriginalFilename());
            assetPurchaseRecordsDtlEntity.setPurchaseRecordsDetailsFileType(file.getContentType());
        }
        return assetPurchaseRecordsDtlEntity;
    }

    public static ProjDocFileEntity convertPurchasePOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long longTermId ) {
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
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( longTermId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
