package com.rjtech.register.service.handler.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
import com.rjtech.register.fixedassets.dto.LongTermLeaseActualRetrunsDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.LongTermLeaseActualRetrunsDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;

public class LongTermLeaseActualRetrunsDtlHandler {
    private LongTermLeaseActualRetrunsDtlHandler() {
    }

    public static LongTermLeaseActualRetrunsDtlTO convertEntityToPOJO(LongTermLeaseActualRetrunsDtlEntity entity,
            AmazonS3 awsS3Client, String awsS3Bucket) {

        LongTermLeaseActualRetrunsDtlTO leaseActualRetrunsDtlTO = new LongTermLeaseActualRetrunsDtlTO();
        if (null != entity) {
            leaseActualRetrunsDtlTO.setId(entity.getId());
            leaseActualRetrunsDtlTO.setLease(entity.getLease());
            leaseActualRetrunsDtlTO.setLeaseAgreement(entity.getLeaseAgreement());
            leaseActualRetrunsDtlTO.setDate(CommonUtil.convertDateToString(entity.getDate()));
            leaseActualRetrunsDtlTO.setTenantId(entity.getTenantId());
            leaseActualRetrunsDtlTO.setTenantName(entity.getTenantName());
            leaseActualRetrunsDtlTO.setCumulativeNetRent(entity.getCumulativeNetRent());
            leaseActualRetrunsDtlTO.setCumulativeTaxAmount(entity.getCumulativeTaxAmount());
            leaseActualRetrunsDtlTO.setCumulativeNetRentTenant(entity.getCumulativeNetRentTenant());
            leaseActualRetrunsDtlTO.setCumulativeTaxAmountTenant(entity.getCumulativeTaxAmountTenant());
            leaseActualRetrunsDtlTO.setShortFallRent(entity.getShortFallRent());
            leaseActualRetrunsDtlTO.setShortFallTax(entity.getShortFallTax());
            leaseActualRetrunsDtlTO.setModeOfPayment(entity.getModeOfPayment());
            leaseActualRetrunsDtlTO.setReceiverBankName(entity.getReceiverBankName());
            leaseActualRetrunsDtlTO.setReceiverBankCode(entity.getReceiverBankCode());
            leaseActualRetrunsDtlTO.setReceiverBankAC(entity.getReceiverBankAC());
            leaseActualRetrunsDtlTO.setTransferReceiptNo(entity.getTransferReceiptNo());
            leaseActualRetrunsDtlTO.setRentalAmountReceived(entity.getRentalAmountReceived());
            leaseActualRetrunsDtlTO.setTaxAmountReceived(entity.getTaxAmountReceived());
            leaseActualRetrunsDtlTO.setUploadMoneyDocumentFileName(entity.getUploadMoneyDocumentFileName());
            leaseActualRetrunsDtlTO.setUploadMoneyDocumentFileSize(entity.getUploadMoneyDocumentFileSize());
            leaseActualRetrunsDtlTO.setUploadMoneyDocumentFileType(entity.getUploadMoneyDocumentFileType());
            if (null != entity.getDocKey()) {
                leaseActualRetrunsDtlTO.setDocUrl(awsS3Client
                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
            }

            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                leaseActualRetrunsDtlTO.setClientId(clientRegEntity.getClientId());
            }
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

            if (null != fixedAssetsRegisterDtlEntity) {
                leaseActualRetrunsDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
            }
        }
        return leaseActualRetrunsDtlTO;
    }

    public static DocumentsDtlTO convertEntityToPOJODocuments(LongTermLeaseActualRetrunsDtlEntity entity,
            String awsS3Bucket, AmazonS3 awsS3Client) {
        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
        documentsDtlTO.setId(entity.getId());
        documentsDtlTO.setFileName(entity.getUploadMoneyDocumentFileName());
        documentsDtlTO.setFileType(entity.getUploadMoneyDocumentFileType());
        documentsDtlTO.setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
        documentsDtlTO.setDocUrl(
                awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
        return documentsDtlTO;
    }

    public static LongTermLeaseActualRetrunsDtlEntity convertPOJOToEntity(
            LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity, MultipartFile file,
            LongTermLeaseActualRetrunsDtlTO longTermLeaseActualRetrunsDtlTO,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {

        longTermLeaseActualRetrunsDtlEntity.setLease(longTermLeaseActualRetrunsDtlTO.getLease());
        longTermLeaseActualRetrunsDtlEntity.setLeaseAgreement(longTermLeaseActualRetrunsDtlTO.getLeaseAgreement());
        longTermLeaseActualRetrunsDtlEntity
                .setDate(CommonUtil.convertStringToDate(longTermLeaseActualRetrunsDtlTO.getDate()));
        longTermLeaseActualRetrunsDtlEntity.setTenantId(longTermLeaseActualRetrunsDtlTO.getTenantId());
        longTermLeaseActualRetrunsDtlEntity.setTenantName(longTermLeaseActualRetrunsDtlTO.getTenantName());
        longTermLeaseActualRetrunsDtlEntity
                .setCumulativeNetRent(longTermLeaseActualRetrunsDtlTO.getCumulativeNetRent());
        longTermLeaseActualRetrunsDtlEntity
                .setCumulativeTaxAmount(longTermLeaseActualRetrunsDtlTO.getCumulativeTaxAmount());
        longTermLeaseActualRetrunsDtlEntity
                .setCumulativeNetRentTenant(longTermLeaseActualRetrunsDtlTO.getCumulativeNetRentTenant());
        longTermLeaseActualRetrunsDtlEntity
                .setCumulativeTaxAmountTenant(longTermLeaseActualRetrunsDtlTO.getCumulativeTaxAmountTenant());
        longTermLeaseActualRetrunsDtlEntity.setShortFallRent(longTermLeaseActualRetrunsDtlTO.getShortFallRent());
        longTermLeaseActualRetrunsDtlEntity.setShortFallTax(longTermLeaseActualRetrunsDtlTO.getShortFallTax());
        longTermLeaseActualRetrunsDtlEntity.setModeOfPayment(longTermLeaseActualRetrunsDtlTO.getModeOfPayment());
        longTermLeaseActualRetrunsDtlEntity.setReceiverBankName(longTermLeaseActualRetrunsDtlTO.getReceiverBankName());
        longTermLeaseActualRetrunsDtlEntity.setReceiverBankCode(longTermLeaseActualRetrunsDtlTO.getReceiverBankCode());
        longTermLeaseActualRetrunsDtlEntity.setReceiverBankAC(longTermLeaseActualRetrunsDtlTO.getReceiverBankAC());
        longTermLeaseActualRetrunsDtlEntity
                .setTransferReceiptNo(longTermLeaseActualRetrunsDtlTO.getTransferReceiptNo());
        longTermLeaseActualRetrunsDtlEntity
                .setRentalAmountReceived(longTermLeaseActualRetrunsDtlTO.getRentalAmountReceived());
        longTermLeaseActualRetrunsDtlEntity
                .setTaxAmountReceived(longTermLeaseActualRetrunsDtlTO.getTaxAmountReceived());
        longTermLeaseActualRetrunsDtlEntity.setStatus(longTermLeaseActualRetrunsDtlTO.getStatus());
        longTermLeaseActualRetrunsDtlEntity.setDocKey(longTermLeaseActualRetrunsDtlTO.getDocKey());
        longTermLeaseActualRetrunsDtlEntity.setFixedAssetsRegisterDtlEntity(
                fixedAssetsRegisterRepository.findOne(longTermLeaseActualRetrunsDtlTO.getFixedAssetid()));
        if (CommonUtil.objectNotNull(file)) {
        longTermLeaseActualRetrunsDtlEntity.setUploadMoneyDocumentFileName(file.getOriginalFilename());
        longTermLeaseActualRetrunsDtlEntity.setUploadMoneyDocumentFileType(file.getContentType());
        }
        return longTermLeaseActualRetrunsDtlEntity;
    }

    public static ProjDocFileEntity convertLTLAReturnsPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
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
