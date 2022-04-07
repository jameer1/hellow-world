package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.fixedassets.dto.AssetCostValueStatusDtlTO;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.model.AssetCostValueStatusDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;

public class AssetCostValueStatusDtlHandler {
    private AssetCostValueStatusDtlHandler() {
    }

    public static AssetCostValueStatusDtlTO convertEntityToPOJO(AssetCostValueStatusDtlEntity entity,
            String awsS3Bucket, AmazonS3 awsS3Client) {
        AssetCostValueStatusDtlTO assetCostValueStatusDtlTO = new AssetCostValueStatusDtlTO();
        if (null != entity) {
            assetCostValueStatusDtlTO.setId(entity.getId());
            assetCostValueStatusDtlTO.setEffectiveDate(CommonUtil.convertDateToString(entity.getEffectiveDate()));
            assetCostValueStatusDtlTO.setCurrency(entity.getCurrency());
            assetCostValueStatusDtlTO.setLandCost(entity.getLandCost());
            assetCostValueStatusDtlTO.setStructureCost(entity.getStructureCost());
            assetCostValueStatusDtlTO.setPlantEquipmentCost(entity.getPlantEquipmentCost());
            assetCostValueStatusDtlTO.setTotalCost(entity.getTotalCostAsset());
            assetCostValueStatusDtlTO.setAssetMarket(entity.getAssetMarketValue());
            assetCostValueStatusDtlTO.setStructureScrap(entity.getStructureScrapValue());
            assetCostValueStatusDtlTO.setEquipmentScrapValue(entity.getEquipmentScrapValue());
            assetCostValueStatusDtlTO.setCummStructure(entity.getCummStructure());
            assetCostValueStatusDtlTO.setCummPlant(entity.getCummPlant());
            assetCostValueStatusDtlTO.setCummAsset(entity.getCummAsset());
            assetCostValueStatusDtlTO.setYearlyStructure(entity.getYearlyStructure());
            assetCostValueStatusDtlTO.setYearlyPlant(entity.getYearlyPlant());
            assetCostValueStatusDtlTO.setYearlyTotalAmount(entity.getYearlyTotalAmount());
            assetCostValueStatusDtlTO.setRemainAssetCost(entity.getRemainAssetCost());
            assetCostValueStatusDtlTO.setStatus(entity.getStatus());
            assetCostValueStatusDtlTO.setAssetCostValueDocumentFileName(entity.getAssetCostValueDocumentFileName());
            assetCostValueStatusDtlTO.setAssetCostValueDocumentFileType(entity.getAssetCostValueDocumentFileType());
            assetCostValueStatusDtlTO.setDocKey(entity.getDocKey());
            if (null != entity.getDocKey()) {
                assetCostValueStatusDtlTO.setDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
                ClientRegEntity clientRegEntity = entity.getClientId();
                if (null != clientRegEntity) {
                    assetCostValueStatusDtlTO.setClientId(clientRegEntity.getClientId());
                }
            }

            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

            if (null != fixedAssetsRegisterDtlEntity) {
                assetCostValueStatusDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
            }
        }
        return assetCostValueStatusDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(AssetCostValueStatusDtlEntity entity,
            AmazonS3 awsS3Client, String awsS3Bucket) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getAssetCostValueDocumentFileName());
        documentsDtlTO.setFileType(entity.getAssetCostValueDocumentFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
        }
        return documentsDtlTO;
    }

    public static AssetCostValueStatusDtlEntity convertPOJOToEntity(AssetCostValueStatusDtlEntity entity,
            MultipartFile file, AssetCostValueStatusDtlTO assetCostValueStatusDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {
        if (CommonUtil.objectNotNull(assetCostValueStatusDtlTO)
                && CommonUtil.isNonBlankLong(assetCostValueStatusDtlTO.getId())) {
            entity.setId(assetCostValueStatusDtlTO.getId());
        }
        entity.setId(assetCostValueStatusDtlTO.getId());
        entity.setEffectiveDate(CommonUtil.convertStringToDate(assetCostValueStatusDtlTO.getEffectiveDate()));
        entity.setCurrency(assetCostValueStatusDtlTO.getCurrency());
        entity.setLandCost(assetCostValueStatusDtlTO.getLandCost());
        entity.setStructureCost(assetCostValueStatusDtlTO.getStructureCost());
        entity.setPlantEquipmentCost(assetCostValueStatusDtlTO.getPlantEquipmentCost());
        entity.setTotalCostAsset(assetCostValueStatusDtlTO.getTotalCost());
        entity.setAssetMarketValue(assetCostValueStatusDtlTO.getAssetMarket());
        entity.setStructureScrapValue(assetCostValueStatusDtlTO.getStructureScrap());
        entity.setEquipmentScrapValue(assetCostValueStatusDtlTO.getEquipmentScrapValue());
        entity.setCummStructure(assetCostValueStatusDtlTO.getCummStructure());
        entity.setCummPlant(assetCostValueStatusDtlTO.getCummPlant());
        entity.setCummAsset(assetCostValueStatusDtlTO.getCummAsset());
        entity.setYearlyStructure(assetCostValueStatusDtlTO.getYearlyStructure());
        entity.setYearlyPlant(assetCostValueStatusDtlTO.getYearlyPlant());
        entity.setYearlyTotalAmount(assetCostValueStatusDtlTO.getYearlyTotalAmount());
        entity.setRemainAssetCost(assetCostValueStatusDtlTO.getRemainAssetCost());
        entity.setStatus(StatusCodes.ACTIVE.getValue());

        if (CommonUtil.isNonBlankLong(assetCostValueStatusDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(assetCostValueStatusDtlTO.getFixedAssetid());
            entity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }
        if (CommonUtil.objectNotNull(file)) {
            entity.setAssetCostValueDocumentFileName(file.getOriginalFilename());
            entity.setAssetCostValueDocumentFileType(file.getContentType());
        }

        return entity;
    }
    
    public static ProjDocFileEntity convertAssetCostPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long costId ) {
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
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( costId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
