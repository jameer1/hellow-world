package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.dto.SalesRecordsDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.SalesRecordDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;

public class AssetSalesRecordsDtlHandler {

    private AssetSalesRecordsDtlHandler() {
    }

    public static SalesRecordsDtlTO convertEntityToPOJO(SalesRecordDtlEntity entity, AmazonS3 awsS3Client, String awsS3Bucket) {

        SalesRecordsDtlTO salesRecordsDtlTO = new SalesRecordsDtlTO();
        if(null != entity){
            salesRecordsDtlTO.setId(entity.getId());
            salesRecordsDtlTO.setDateOfSale(CommonUtil.convertDateToString(entity.getDateOfSale()));
            salesRecordsDtlTO.setSaleType(entity.getSaleType());
            salesRecordsDtlTO.setBuyerName(entity.getBuyerName());
            salesRecordsDtlTO.setBuyerAddress(entity.getBuyerAddress());
            salesRecordsDtlTO.setLandRegisterOfficeDetails(entity.getLandRegisterOfficeDetails());
            salesRecordsDtlTO.setLandValudation(entity.getLandValudation());
            salesRecordsDtlTO.setStructureValuation(entity.getStructureValuation());
            salesRecordsDtlTO.setPlantEquipmentValutaion(entity.getPlantEquipmentValutaion());
            salesRecordsDtlTO.setTotalValuation(entity.getTotalValuation());
            salesRecordsDtlTO.setTotalSalesAmount(entity.getTotalSalesAmount());
            salesRecordsDtlTO.setBuyerSolicitor(entity.getBuyerSolicitor());
            salesRecordsDtlTO.setVendorSolicitor(entity.getVendorSolicitor());
            salesRecordsDtlTO.setSalesRecordsDetailsDocumentFileSize(entity.getSalesRecordsDetailsDocumentFileSize());
            salesRecordsDtlTO.setSalesRecordsDetailsFileType(entity.getSalesRecordsDetailsFileType());
            salesRecordsDtlTO.setSalesRecordsDetailsFileName(entity.getSalesRecordsDetailsFileName());
            salesRecordsDtlTO.setDocKey(entity.getDocKey());
            salesRecordsDtlTO.setDocUrl(awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                salesRecordsDtlTO.setClientId(clientRegEntity.getClientId());
            }

            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

            if (null != fixedAssetsRegisterDtlEntity) {
                salesRecordsDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
            }
        }

        return salesRecordsDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(SalesRecordDtlEntity entity, String awsS3Bucket,
            AmazonS3 awsS3Client) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getSalesRecordsDetailsFileName());
        documentsDtlTO.setFileType(entity.getSalesRecordsDetailsFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
        }
        return documentsDtlTO;
    }

    public static SalesRecordDtlEntity convertSalesPOJOEntity(SalesRecordDtlEntity salesRecordDtlEntity,
            MultipartFile file, SalesRecordsDtlTO salesRecordsDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {

        if (CommonUtil.objectNotNull(salesRecordsDtlTO) && CommonUtil.isNonBlankLong(salesRecordsDtlTO.getId())) {
            salesRecordDtlEntity.setId(salesRecordsDtlTO.getId());
        }
        salesRecordDtlEntity.setDateOfSale(CommonUtil.convertStringToDate(salesRecordsDtlTO.getDateOfSale()));
        salesRecordDtlEntity.setSaleType(salesRecordsDtlTO.getSaleType());
        salesRecordDtlEntity.setBuyerName(salesRecordsDtlTO.getBuyerName());
        salesRecordDtlEntity.setBuyerAddress(salesRecordsDtlTO.getBuyerAddress());
        salesRecordDtlEntity.setLandRegisterOfficeDetails(salesRecordsDtlTO.getLandRegisterOfficeDetails());
        salesRecordDtlEntity.setLandValudation(salesRecordsDtlTO.getLandValudation());
        salesRecordDtlEntity.setStructureValuation(salesRecordsDtlTO.getStructureValuation());
        salesRecordDtlEntity.setPlantEquipmentValutaion(salesRecordsDtlTO.getPlantEquipmentValutaion());
        salesRecordDtlEntity.setTotalValuation(salesRecordsDtlTO.getTotalValuation());
        salesRecordDtlEntity.setTotalSalesAmount(salesRecordsDtlTO.getTotalSalesAmount());
        salesRecordDtlEntity.setBuyerSolicitor(salesRecordsDtlTO.getBuyerSolicitor());
        salesRecordDtlEntity.setVendorSolicitor(salesRecordsDtlTO.getVendorSolicitor());
        salesRecordDtlEntity.setStatus(salesRecordsDtlTO.getStatus());
        salesRecordDtlEntity.setDocKey(salesRecordsDtlTO.getDocKey());
        if (CommonUtil.isNonBlankLong(salesRecordsDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(salesRecordsDtlTO.getFixedAssetid());
            salesRecordDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }
        if (CommonUtil.objectNotNull(file)) {
            salesRecordDtlEntity.setSalesRecordsDetailsFileName(file.getOriginalFilename());
            salesRecordDtlEntity.setSalesRecordsDetailsFileType(file.getContentType());
        }
        salesRecordDtlEntity.setDocKey(salesRecordsDtlTO.getDocKey());
        return salesRecordDtlEntity;
    }

    public static ProjDocFileEntity convertSalesRecordPOJOToProjDocEntity( MultipartFile file, Long assetFixedAssetId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long saleRecordId, Long userId ) {
    	ProjDocFolderEntity projDocFolderEntity = projDocFolderRepository.findByNameAndProjId( folder_category );
    	
        UserMstrEntity userMstrEntity = new UserMstrEntity();
        userMstrEntity.setUserId( userId );
        ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        ProjDocFileEntity resProjDocFileEntity = new ProjDocFileEntity();
        UploadUtil uploadUtil = new UploadUtil();        
    	FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository.findOne( assetFixedAssetId );
    	
        projDocFileEntity.setName( file.getOriginalFilename() );
		projDocFileEntity.setFolderId( projDocFolderEntity );
		projDocFileEntity.setFileSize( uploadUtil.bytesIntoHumanReadable( file.getSize() ) );
		projDocFileEntity.setFileType( file.getContentType() );
		projDocFileEntity.setProjectId( fixedAssetsRegisterDtlEntity.getProjMstrEntity() );		
		projDocFileEntity.setCreatedBy( userMstrEntity );
		projDocFileEntity.setUpdatedBy( userMstrEntity );
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( assetFixedAssetId )+"/"+String.valueOf( saleRecordId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
