package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.model.AssetLifeStatusDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.constants.ApplicationConstants;

public class AssetLifeStatusDtlHandler {
    private AssetLifeStatusDtlHandler() {
    }

    public static AssetLifeStatusDtlTO convertEntityToPOJO(AssetLifeStatusDtlEntity entity, String awsS3Bucket,
            AmazonS3 awsS3Client) {
        AssetLifeStatusDtlTO assetLifeStatusDtlTO = new AssetLifeStatusDtlTO();
        if (entity != null) {
            assetLifeStatusDtlTO.setId(entity.getId());
            assetLifeStatusDtlTO.setEffectiveDate(CommonUtil.convertDateToString(entity.getEffectiveDate()));
            assetLifeStatusDtlTO.setBuildStructure(entity.getBuildStructure());
            assetLifeStatusDtlTO
                    .setPlantCommissioningDate(CommonUtil.convertDateToString(entity.getPlantCommissioningDate()));
            assetLifeStatusDtlTO.setStructureTotal(entity.getStructureTotal());
            assetLifeStatusDtlTO.setPlantEquipmentTotal(entity.getPlantEquipmentTotal());
            assetLifeStatusDtlTO.setAgeStructure(entity.getAgeStructure());
            assetLifeStatusDtlTO.setAgeEquipment(entity.getAgeEquipment());
            assetLifeStatusDtlTO.setRemainingStruture(entity.getRemainingStruture());
            assetLifeStatusDtlTO.setRemainingEquipment(entity.getRemainingEquipment());
            assetLifeStatusDtlTO
                    .setLifeAssignmentRecordsDocumentFileName(entity.getLifeAssignmentRecordsDocumentFileName());
            assetLifeStatusDtlTO
                    .setLifeAssignmentRecordsDocumentFileType(entity.getLifeAssignmentRecordsDocumentFileType());
            if (entity.getDocKey() != null) {
                assetLifeStatusDtlTO.setDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
                ClientRegEntity clientRegEntity = entity.getClientId();
                if (null != clientRegEntity) {
                    assetLifeStatusDtlTO.setClientId(clientRegEntity.getClientId());
                }
            }

            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

            if (null != fixedAssetsRegisterDtlEntity) {
                assetLifeStatusDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
            }
        }

        return assetLifeStatusDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(AssetLifeStatusDtlEntity entity, AmazonS3 awsS3Client,
            String awsS3Bucket) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getLifeAssignmentRecordsDocumentFileName());
        documentsDtlTO.setFileType(entity.getLifeAssignmentRecordsDocumentFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
        }
        else
        {
        	UploadUtil uploadUtil = new UploadUtil();
        	try
        	{
        		if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            	{
            		String file_path = "E://pavani/Downloads/Resources/Immovable Assets/Asset Life Status/121/418/ss.png";
            		uploadUtil.downloadFile( file_path );
            	}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}        	
        }
        return documentsDtlTO;
    }

    public static AssetLifeStatusDtlEntity convertPOJOToEntity(AssetLifeStatusDtlEntity dtlEntity, MultipartFile file,
            AssetLifeStatusDtlTO assetLifeStatusDtlTO, FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {
        if (CommonUtil.objectNotNull(assetLifeStatusDtlTO) && CommonUtil.isNonBlankLong(assetLifeStatusDtlTO.getId())) {
            dtlEntity.setId(assetLifeStatusDtlTO.getId());
        }

        dtlEntity.setEffectiveDate(CommonUtil.convertStringToDate(assetLifeStatusDtlTO.getEffectiveDate()));
        dtlEntity.setBuildStructure((assetLifeStatusDtlTO.getBuildStructure()));
        dtlEntity.setPlantCommissioningDate(
                CommonUtil.convertStringToDate(assetLifeStatusDtlTO.getPlantCommissioningDate()));
        dtlEntity.setStructureTotal((assetLifeStatusDtlTO.getStructureTotal()));
        dtlEntity.setPlantEquipmentTotal((assetLifeStatusDtlTO.getPlantEquipmentTotal()));
        dtlEntity.setAgeStructure(assetLifeStatusDtlTO.getAgeStructure());
        dtlEntity.setAgeEquipment(assetLifeStatusDtlTO.getAgeEquipment());
        dtlEntity.setRemainingStruture(assetLifeStatusDtlTO.getRemainingStruture());
        dtlEntity.setRemainingEquipment(assetLifeStatusDtlTO.getRemainingEquipment());
        dtlEntity.setStatus(StatusCodes.ACTIVE.getValue());

        if (CommonUtil.isNonBlankLong(assetLifeStatusDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(assetLifeStatusDtlTO.getFixedAssetid());
            dtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }

        if (CommonUtil.objectNotNull(file)) {
            dtlEntity.setLifeAssignmentRecordsDocumentFileName(file.getOriginalFilename());
            dtlEntity.setLifeAssignmentRecordsDocumentFileType(file.getContentType());
        }
        return dtlEntity;
    }

    public static ProjDocFileEntity convertAssetLifePOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long saleId ) {
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
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( saleId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
