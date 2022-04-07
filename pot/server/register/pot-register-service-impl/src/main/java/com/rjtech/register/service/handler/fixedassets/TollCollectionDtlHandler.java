package com.rjtech.register.service.handler.fixedassets;


	
	import org.springframework.web.multipart.MultipartFile;

	import com.amazonaws.services.s3.AmazonS3;
	import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
	import com.rjtech.common.utils.CommonUtil;
	import com.rjtech.register.fixedassets.dto.TollCollectionDtlTO;
	import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
	import com.rjtech.register.fixedassets.model.TollCollectionDtlEntity;
	import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
	import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
	import com.rjtech.document.model.ProjDocFileEntity;
	import com.rjtech.document.model.ProjDocFolderEntity;
	import com.rjtech.common.utils.UploadUtil;
	import com.rjtech.document.repository.ProjDocFolderRepository;
	import com.rjtech.document.repository.ProjDocFileRepository;
	import com.rjtech.common.model.UserMstrEntity;

	public class TollCollectionDtlHandler {
	    private TollCollectionDtlHandler() {
	    }

	    public static TollCollectionDtlTO convertEntityToPOJO(TollCollectionDtlEntity entity,
	            String awsS3Bucket, AmazonS3 awsS3Client) {
	        TollCollectionDtlTO tollCollectionDtlTO = new TollCollectionDtlTO();
	        if (null != entity) {
	        	tollCollectionDtlTO.setId(entity.getId());
	         
	            tollCollectionDtlTO
	            .setDate(CommonUtil.convertDateToString(entity.getDate()));
	            tollCollectionDtlTO.setBankName(entity.getBankName());
	            tollCollectionDtlTO.setBankCode(entity.getBankCode());
	            tollCollectionDtlTO.setBankAc(entity.getBankAc());
	            tollCollectionDtlTO.setTransferReceiptNo(entity.getTransferReceiptNo());
	            tollCollectionDtlTO.setNetAmount(entity.getNetAmount());
	            tollCollectionDtlTO.setTaxAmount(entity.getTaxAmount());
	            tollCollectionDtlTO.setCumulativeNetAmount(entity.getCumulativeNetAmount());
	            tollCollectionDtlTO.setCumulativeTaxAmount(entity.getCumulativeTaxAmount());
	            tollCollectionDtlTO.setForecastNetAmt(entity.getForecastNetAmt());
	            tollCollectionDtlTO.setForecastTaxAmt(entity.getForecastTaxAmt());
	            tollCollectionDtlTO.setCumulativeNetRevenue(entity.getCumulativeNetRevenue());
	            tollCollectionDtlTO.setCumulativeTaxAmount(entity.getCumulativeTaxAmount());
	            tollCollectionDtlTO.setTollFileSize(entity.getTollFileSize());
	            tollCollectionDtlTO.setTollFileName(entity.getTollFileName());
	            tollCollectionDtlTO.setTollFileType(entity.getTollFileType());
	            tollCollectionDtlTO.setModeOfPayment(entity.getModeOfPayment());
	            tollCollectionDtlTO.setCumulativeTax(entity.getCumulativeTax());
	            if (entity.getDocKey() != null) {
	                tollCollectionDtlTO.setDocUrl(awsS3Client
	                        .generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
	            }
	        }
	        return tollCollectionDtlTO;
	    }

	    public static DocumentsDtlTO convertEntityToPOJODocuments(TollCollectionDtlEntity entity,
	            AmazonS3 awsS3Client, String awsS3Bucket) {
	        DocumentsDtlTO documentsDtlTO = new DocumentsDtlTO();
	        documentsDtlTO.setId(entity.getId());
	        documentsDtlTO.setFileName(entity.getTollFileName());
	        documentsDtlTO.setFileType(entity.getTollFileType());
	        if (null != entity.getDocKey()) {
	            documentsDtlTO.setDocUrl(
	                    awsS3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(awsS3Bucket, entity.getDocKey())));
	            documentsDtlTO
	                    .setFileSize(awsS3Client.getObjectMetadata(awsS3Bucket, entity.getDocKey()).getContentLength());
	        }
	        return documentsDtlTO;
	    }

	    public static TollCollectionDtlEntity convertPOJOToEntity(
	            TollCollectionDtlEntity tollCollectionDtlEntity, MultipartFile file,
	            TollCollectionDtlTO tollCollectionDtlTO,
	            FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {
	    	 System.out.println("tollCollectionDtlTO.getCumulativeTaxAmount()" +tollCollectionDtlTO.getCumulativeTaxAmount());

	        if (CommonUtil.objectNotNull(tollCollectionDtlTO)
	                && CommonUtil.isNonBlankLong(tollCollectionDtlTO.getId())) {
	            tollCollectionDtlEntity.setId(tollCollectionDtlTO.getId());
	        }

	        tollCollectionDtlEntity
	                .setDate(CommonUtil.convertStringToDate(tollCollectionDtlTO.getDate()));
	        tollCollectionDtlEntity.setBankName(tollCollectionDtlTO.getBankName());
	        tollCollectionDtlEntity.setBankCode(tollCollectionDtlTO.getBankCode());
	        tollCollectionDtlEntity.setBankAc(tollCollectionDtlTO.getBankAc());
	        tollCollectionDtlEntity.setTransferReceiptNo(tollCollectionDtlTO.getTransferReceiptNo());
	        tollCollectionDtlEntity.setNetAmount(tollCollectionDtlTO.getNetAmount());
	        tollCollectionDtlEntity.setTaxAmount(tollCollectionDtlTO.getTaxAmount());
	        tollCollectionDtlEntity
	                .setCumulativeNetAmount(tollCollectionDtlTO.getCumulativeNetAmount());
	        tollCollectionDtlEntity
	                .setCumulativeTaxAmount(tollCollectionDtlTO.getCumulativeTaxAmount());
	        tollCollectionDtlEntity.setForecastNetAmt(tollCollectionDtlTO.getForecastNetAmt());
	        tollCollectionDtlEntity.setForecastTaxAmt(tollCollectionDtlTO.getForecastTaxAmt());
	        tollCollectionDtlEntity
	                .setCumulativeNetRevenue(tollCollectionDtlTO.getCumulativeNetRevenue());
	        tollCollectionDtlEntity
	                .setCumulativeTaxAmount(tollCollectionDtlTO.getCumulativeTaxAmount());
	        tollCollectionDtlEntity.setCumulativeTax(tollCollectionDtlTO.getCumulativeTax());
	        tollCollectionDtlEntity.setModeOfPayment(tollCollectionDtlTO.getModeOfPayment());

	        if (CommonUtil.isNonBlankLong(tollCollectionDtlTO.getFixedAssetid())) {
	            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
	                    .findOne(tollCollectionDtlTO.getFixedAssetid());
	            tollCollectionDtlEntity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
	        }
	        if (CommonUtil.objectNotNull(file)) {
	            tollCollectionDtlEntity.setTollFileName(file.getOriginalFilename());
	            tollCollectionDtlEntity.setTollFileType(file.getContentType());
	        }
	        System.out.println("tollCollectionDtlTO.getCumulativeTaxAmount()" +tollCollectionDtlTO.getCumulativeTaxAmount());
	        tollCollectionDtlEntity.setStatus(tollCollectionDtlTO.getStatus());
	        

	        return tollCollectionDtlEntity;
	    }

	    public static ProjDocFileEntity convertTollCollectionPOJOToProjDocFileEntity( MultipartFile file, Long fixedAssetId, Long userId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
	    		ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long tollCollectionId ) {
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
			projDocFileEntity.setFolderPath( "/"+String.valueOf( fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( fixedAssetId )+"/"+String.valueOf( tollCollectionId ) );
			projDocFileEntity.setStatus( 1 );
			resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
			
			return resProjDocFileEntity;
	    }
}
