package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.dto.STCORRReturnsDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.STCORRReturnsDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;

public class STCORRReturnsDtlHandler {
    private STCORRReturnsDtlHandler() {
    }

    public static STCORRReturnsDtlTO convertEntityToPOJO(STCORRReturnsDtlEntity entity, AmazonS3 awsS3Client,
            String awsS3Bucket) {
        STCORRReturnsDtlTO stcorrReturnsDtlTO = new STCORRReturnsDtlTO();
        if (entity != null) {
            stcorrReturnsDtlTO.setId(entity.getId());
            stcorrReturnsDtlTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
            stcorrReturnsDtlTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
            stcorrReturnsDtlTO.setTenantId(entity.getTenantId());
            stcorrReturnsDtlTO.setTenantName(entity.getTenantName());
            stcorrReturnsDtlTO.setTenantRegisteredAddress(entity.getTenantRegisteredAddress());
            stcorrReturnsDtlTO.setEmailAddrees(entity.getEmailAddrees());
            stcorrReturnsDtlTO.setTenantPhoneNumber(entity.getTenantPhoneNumber());
            stcorrReturnsDtlTO.setCheckIn(CommonUtil.convertDateToString(entity.getCheckIn()));
            stcorrReturnsDtlTO.setCheckOut(CommonUtil.convertDateToString(entity.getCheckOut()));
            stcorrReturnsDtlTO.setNoOfDays(entity.getNoOfDays());
            stcorrReturnsDtlTO.setDailyNetRent(entity.getDailyNetRent());
            stcorrReturnsDtlTO.setTax(entity.getTax());
            stcorrReturnsDtlTO.setRentAmountReceivable(entity.getRentAmountReceivable());
            stcorrReturnsDtlTO.setGrossAmount(entity.getGrossAmount());
            stcorrReturnsDtlTO.setTaxAmount(entity.getTaxAmount());
            stcorrReturnsDtlTO.setAdvancePaid(entity.getAdvancePaid());
            stcorrReturnsDtlTO.setSubsequentRentalRecepits(entity.getSubsequentRentalReceipts());
            stcorrReturnsDtlTO.setRefundofRemainingAdvanceamount(entity.getRefundofRemainingAdvanceamount());
            stcorrReturnsDtlTO.setNetTaxAmountReceived(entity.getNetTaxAmountReceived());
            stcorrReturnsDtlTO.setNetAmountOfRentRecived(entity.getNetAmountOfRentRecived());
            stcorrReturnsDtlTO.setCurrentStatus(entity.getCurrentStatus());
            stcorrReturnsDtlTO.setTenantRecordDetailsFileName(entity.getTenantRecordDetailsFileName());
            stcorrReturnsDtlTO.setTenantRecordDetailsFileSize(entity.getTenantRecordDetailsFileSize());
            stcorrReturnsDtlTO.setTenantRecordDetailsFileType(entity.getTenantRecordDetailsFileType());
            if (entity.getDocKey() != null) {
                stcorrReturnsDtlTO.setDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            }
            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                stcorrReturnsDtlTO.setClientId(clientRegEntity.getClientId());
            }

            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

            if (null != fixedAssetsRegisterDtlEntity) {
                stcorrReturnsDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
            }

        }
        return stcorrReturnsDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(STCORRReturnsDtlEntity entity, String awsS3Bucket,
            AmazonS3 awsS3Client) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getTenantRecordDetailsFileName());
        documentsDtlTO.setFileType(entity.getTenantRecordDetailsFileType());
        if (null != entity.getDocKey()) {
            documentsDtlTO
                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
            documentsDtlTO.setDocUrl(
                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
        }
        return documentsDtlTO;
    }

    public static STCORRReturnsDtlEntity converSTCORRReturnsPOJOToEntity(STCORRReturnsDtlEntity stcorrReturnsDtlEntity,
            MultipartFile file, STCORRReturnsDtlTO stcorrReturnsDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {

        if (CommonUtil.isNonBlankLong(stcorrReturnsDtlTO.getId())) {
            stcorrReturnsDtlEntity.setId(stcorrReturnsDtlTO.getId());
        }
        stcorrReturnsDtlEntity.setStartDate(CommonUtil.convertStringToDate(stcorrReturnsDtlTO.getStartDate()));
        stcorrReturnsDtlEntity.setFinishDate(CommonUtil.convertStringToDate(stcorrReturnsDtlTO.getFinishDate()));
        stcorrReturnsDtlEntity.setTenantId(stcorrReturnsDtlTO.getTenantId());
        stcorrReturnsDtlEntity.setTenantName(stcorrReturnsDtlTO.getTenantName());
        stcorrReturnsDtlEntity.setTenantRegisteredAddress(stcorrReturnsDtlTO.getTenantRegisteredAddress());
        stcorrReturnsDtlEntity.setEmailAddrees(stcorrReturnsDtlTO.getEmailAddrees());
        stcorrReturnsDtlEntity.setTenantPhoneNumber(stcorrReturnsDtlTO.getTenantPhoneNumber());
        stcorrReturnsDtlEntity.setCheckIn(CommonUtil.convertStringToDate(stcorrReturnsDtlTO.getCheckIn()));
        stcorrReturnsDtlEntity.setCheckOut(CommonUtil.convertStringToDate(stcorrReturnsDtlTO.getCheckOut()));
        stcorrReturnsDtlEntity.setNoOfDays(stcorrReturnsDtlTO.getNoOfDays());
        stcorrReturnsDtlEntity.setDailyNetRent(stcorrReturnsDtlTO.getDailyNetRent());
        stcorrReturnsDtlEntity.setTax(stcorrReturnsDtlTO.getTax());
        stcorrReturnsDtlEntity.setRentAmountReceivable(stcorrReturnsDtlTO.getRentAmountReceivable());
        stcorrReturnsDtlEntity.setGrossAmount(stcorrReturnsDtlTO.getGrossAmount());
        stcorrReturnsDtlEntity.setTaxAmount(stcorrReturnsDtlTO.getTaxAmount());
        stcorrReturnsDtlEntity.setAdvancePaid(stcorrReturnsDtlTO.getAdvancePaid());
        stcorrReturnsDtlEntity.setSubsequentRentalReceipts(stcorrReturnsDtlTO.getSubsequentRentalRecepits());
        stcorrReturnsDtlEntity
                .setRefundofRemainingAdvanceamount(stcorrReturnsDtlTO.getRefundofRemainingAdvanceamount());
        stcorrReturnsDtlEntity.setNetTaxAmountReceived(stcorrReturnsDtlTO.getNetTaxAmountReceived());
        stcorrReturnsDtlEntity.setNetAmountOfRentRecived(stcorrReturnsDtlTO.getNetAmountOfRentRecived());
        stcorrReturnsDtlEntity.setCurrentStatus(stcorrReturnsDtlTO.getCurrentStatus());
        stcorrReturnsDtlEntity.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isNonBlankLong(stcorrReturnsDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(stcorrReturnsDtlTO.getFixedAssetid());
            stcorrReturnsDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }
        if (CommonUtil.objectNotNull(file)) {
            stcorrReturnsDtlEntity.setTenantRecordDetailsFileName(file.getOriginalFilename());
            stcorrReturnsDtlEntity.setTenantRecordDetailsFileType(file.getContentType());
        }
        return stcorrReturnsDtlEntity;
    }
    
    public static ProjDocFileEntity convertSTCORRRReturnsPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long shortTermId ) {
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
		projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( shortTermId ) );
		projDocFileEntity.setStatus( 1 );
		resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
		
		return resProjDocFileEntity;
    }
}
