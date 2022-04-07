package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.CarParkingTollCollectionDtlTO;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.model.CarParkingTollCollectionDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;

public class CarParkingTollCollectionDtlHandler {
    private CarParkingTollCollectionDtlHandler() {
    }

    public static CarParkingTollCollectionDtlTO convertEntityToPOJO(CarParkingTollCollectionDtlEntity entity,
            String awsS3Bucket, AmazonS3 awsS3Client) {
        CarParkingTollCollectionDtlTO carParkingTollCollectionDtlTO = new CarParkingTollCollectionDtlTO();
        if (null != entity) {
            carParkingTollCollectionDtlTO.setId(entity.getId());
          //  carParkingTollCollectionDtlTO.setDate(CommonUtil.convertDateToDDMMYYYYString(entity.getDate()));
           // carParkingTollCollectionDtlTO.setDate(CommonUtil.convertStringToDate(entity.getDate()));
            carParkingTollCollectionDtlTO
            .setDate(CommonUtil.convertDateToString(entity.getDate()));
            carParkingTollCollectionDtlTO.setBankName(entity.getBankName());
            carParkingTollCollectionDtlTO.setBankCode(entity.getBankCode());
            carParkingTollCollectionDtlTO.setBankAc(entity.getBankAc());
            carParkingTollCollectionDtlTO.setTransferReceiptNo(entity.getTransferReceiptNo());
            carParkingTollCollectionDtlTO.setNetAmount(entity.getNetAmount());
            carParkingTollCollectionDtlTO.setTaxAmount(entity.getTaxAmount());
            carParkingTollCollectionDtlTO.setCumulativeNetAmount(entity.getCumulativeNetAmount());
            carParkingTollCollectionDtlTO.setCumulativeTaxAmount(entity.getCumulativeTaxAmount());
            carParkingTollCollectionDtlTO.setForecastNetAmt(entity.getForecastNetAmt());
            carParkingTollCollectionDtlTO.setForecastTaxAmt(entity.getForecastTaxAmt());
            carParkingTollCollectionDtlTO.setCumulativeNetRevenue(entity.getCumulativeNetRevenue());
            carParkingTollCollectionDtlTO.setCumulativeTaxAmount(entity.getCumulativeTaxAmount());
            carParkingTollCollectionDtlTO.setCarParkingTollFileSize(entity.getCarParkingTollFileSize());
            carParkingTollCollectionDtlTO.setCarParkingTollFileName(entity.getCarParkingTollFileName());
            carParkingTollCollectionDtlTO.setCarParkingTollFileType(entity.getCarParkingTollFileType());
            carParkingTollCollectionDtlTO.setModeOfPayment(entity.getModeOfPayment());
            carParkingTollCollectionDtlTO.setCumulativeTax(entity.getCumulativeTax());
            if (entity.getDocKey() != null) {
                carParkingTollCollectionDtlTO.setDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            }
        }
        return carParkingTollCollectionDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(CarParkingTollCollectionDtlEntity entity,
            AmazonS3 awsS3Client, String awsS3Bucket) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getCarParkingTollFileName());
        documentsDtlTO.setFileType(entity.getCarParkingTollFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
        }
        return documentsDtlTO;
    }

    public static CarParkingTollCollectionDtlEntity convertPOJOToEntity(
            CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity, MultipartFile file,
            CarParkingTollCollectionDtlTO carParkingTollCollectionDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {

        if (CommonUtil.objectNotNull(carParkingTollCollectionDtlTO)
                && CommonUtil.isNonBlankLong(carParkingTollCollectionDtlTO.getId())) {
            carParkingTollCollectionDtlEntity.setId(carParkingTollCollectionDtlTO.getId());
        }

        carParkingTollCollectionDtlEntity
                .setDate(CommonUtil.convertStringToDate(carParkingTollCollectionDtlTO.getDate()));
        carParkingTollCollectionDtlEntity.setBankName(carParkingTollCollectionDtlTO.getBankName());
        carParkingTollCollectionDtlEntity.setBankCode(carParkingTollCollectionDtlTO.getBankCode());
        carParkingTollCollectionDtlEntity.setBankAc(carParkingTollCollectionDtlTO.getBankAc());
        carParkingTollCollectionDtlEntity.setTransferReceiptNo(carParkingTollCollectionDtlTO.getTransferReceiptNo());
        carParkingTollCollectionDtlEntity.setNetAmount(carParkingTollCollectionDtlTO.getNetAmount());
        carParkingTollCollectionDtlEntity.setTaxAmount(carParkingTollCollectionDtlTO.getTaxAmount());
        carParkingTollCollectionDtlEntity
                .setCumulativeNetAmount(carParkingTollCollectionDtlTO.getCumulativeNetAmount());
        carParkingTollCollectionDtlEntity
                .setCumulativeTaxAmount(carParkingTollCollectionDtlTO.getCumulativeTaxAmount());
        carParkingTollCollectionDtlEntity.setForecastNetAmt(carParkingTollCollectionDtlTO.getForecastNetAmt());
        carParkingTollCollectionDtlEntity.setForecastTaxAmt(carParkingTollCollectionDtlTO.getForecastTaxAmt());
        carParkingTollCollectionDtlEntity
                .setCumulativeNetRevenue(carParkingTollCollectionDtlTO.getCumulativeNetRevenue());
        carParkingTollCollectionDtlEntity
                .setCumulativeTaxAmount(carParkingTollCollectionDtlTO.getCumulativeTaxAmount());
        carParkingTollCollectionDtlEntity.setCumulativeTax(carParkingTollCollectionDtlTO.getCumulativeTax());
        carParkingTollCollectionDtlEntity.setModeOfPayment(carParkingTollCollectionDtlTO.getModeOfPayment());

        if (CommonUtil.isNonBlankLong(carParkingTollCollectionDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(carParkingTollCollectionDtlTO.getFixedAssetid());
            carParkingTollCollectionDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }
        if (CommonUtil.objectNotNull(file)) {
            carParkingTollCollectionDtlEntity.setCarParkingTollFileName(file.getOriginalFilename());
            carParkingTollCollectionDtlEntity.setCarParkingTollFileType(file.getContentType());
        }

        carParkingTollCollectionDtlEntity.setStatus(carParkingTollCollectionDtlTO.getStatus());

        return carParkingTollCollectionDtlEntity;
    }

    public static ProjDocFileEntity convertCarParkingPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long fcpId ) {
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
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( fcpId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
