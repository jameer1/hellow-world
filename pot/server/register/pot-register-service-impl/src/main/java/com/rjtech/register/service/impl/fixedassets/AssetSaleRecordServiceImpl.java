package com.rjtech.register.service.impl.fixedassets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.rjtech.aws.common.s3.file.service.AswS3FileService;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.AwsS3FileKeyConstants;
import com.rjtech.register.fixedassets.dto.SalesRecordsDtlTO;
import com.rjtech.register.fixedassets.model.SalesRecordDtlEntity;
import com.rjtech.register.fixedassets.req.SalesRecordsDeactiveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsGetReq;
import com.rjtech.register.fixedassets.req.SalesRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.SalesRecordsResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.SalesRecordsRepository;
import com.rjtech.register.service.fixedassets.AssetSaleRecordService;
import com.rjtech.register.service.handler.fixedassets.AssetSalesRecordsDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFileRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.net.MalformedURLException;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service(value = "assetSaleRecordService")
@RJSService(modulecode = "assetSaleRecordService")
@Transactional
public class AssetSaleRecordServiceImpl implements AssetSaleRecordService {

    private static final Logger log = LoggerFactory.getLogger(AssetSaleRecordServiceImpl.class);

    @Autowired
    private SalesRecordsRepository salesRecordsRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    @Autowired
    private AswS3FileService aswS3FileService;

    @Autowired
    private AmazonS3 awsS3Client;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;
    
    @Autowired
    private UploadUtil uploadUtil;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public SalesRecordsResp getsalesRecords(SalesRecordsGetReq salesRecordsGetReq) {
        SalesRecordsResp resp = new SalesRecordsResp();
        List<SalesRecordDtlEntity> salesRecordDtlEntities = salesRecordsRepository
                .findAllSalesRecords(salesRecordsGetReq.getFixedAssetid(), salesRecordsGetReq.getStatus());

        for (SalesRecordDtlEntity salesRecordDtlEntity : salesRecordDtlEntities) {
            resp.getSalesRecordsDtlTOs().add(
                    AssetSalesRecordsDtlHandler.convertEntityToPOJO(salesRecordDtlEntity, awsS3Client, awsS3Bucket));
        }
        return resp;
    }

    public void saveSalesRecords(MultipartFile file, SalesRecordsSaveReq salesRecordsSave) {

        List<SalesRecordDtlEntity> list = new ArrayList<>();
        List<SalesRecordDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( salesRecordsSave.getFolderCategory() );		
		String folder_path = folder.getUploadFolder();
		String folder_name = salesRecordsSave.getFolderCategory();
		Long userId = salesRecordsSave.getUserId();
		
        if (CommonUtil.isListHasData(salesRecordsSave.getSalesRecordsDtlTOs())) {
            for (SalesRecordsDtlTO salesRecordsDtlTO : salesRecordsSave.getSalesRecordsDtlTOs()) {
                SalesRecordDtlEntity entity = null;
                SalesRecordDtlEntity salesRecordDtlEntity;

                if (CommonUtil.isNonBlankLong(salesRecordsDtlTO.getId())) {
                    isUpdate = true;
                    entity = salesRecordsRepository.findOne(salesRecordsDtlTO.getId());
                    if (null != file) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
                        	{
                        		//public static ProjDocFileEntity convertSalesRecordPOJOToProjDocEntity( MultipartFile file, Long assetFixedAssetId, FixedAssetsRegisterRepository fixedAssetsRegisterRepository, 
                        		//ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_category, Long saleRecordId, Long userId )
                        		ProjDocFileEntity projDocFileEntity = AssetSalesRecordsDtlHandler.convertSalesRecordPOJOToProjDocEntity( file, salesRecordsDtlTO.getFixedAssetid(), fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_name, salesRecordsDtlTO.getId(), userId );
                        		String[] extras = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ) , String.valueOf( entity.getId() ) };
                        		uploadUtil.uploadFile( file, folder_name, folder_path, extras );
                        		entity.setProjDocFileEntity( projDocFileEntity );
                        	}
                        	else
                        	{
                        		aswS3FileService.updateExistingFile(file, entity.getDocKey());
                        	}                            
                        } catch (IOException e) {
                            log.error("Exception occurred while updating  the existing file to s3");
                        }
                    }
                } else {
                    entity = new SalesRecordDtlEntity();
                }
                salesRecordDtlEntity = AssetSalesRecordsDtlHandler.convertSalesPOJOEntity(entity, file,
                        salesRecordsDtlTO, fixedAssetsRegisterRepository);
                list.add(salesRecordDtlEntity);
            }
            listResp = salesRecordsRepository.save(list);
        }
        if (null != file && !isUpdate) {
            for (SalesRecordDtlEntity salesRecordDtlEntity : listResp) {
                String uniqueKey = AwsS3FileKeyConstants.FIXED_ASSET_SALES_RECORDS_DETAIL
                        + salesRecordDtlEntity.getId();                
                try {
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
                	{
                		ProjDocFileEntity projDocFileEntity = AssetSalesRecordsDtlHandler.convertSalesRecordPOJOToProjDocEntity( file, salesRecordDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_name, salesRecordDtlEntity.getId(), userId );
                		String[] extras = { String.valueOf( salesRecordDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( salesRecordDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ) , String.valueOf( salesRecordDtlEntity.getId() ) };
                		uploadUtil.uploadFile( file, folder_name, folder_path, extras );
                		salesRecordDtlEntity.setProjDocFileEntity( projDocFileEntity );
                	}
                	else
                	{
                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	}                    
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                salesRecordDtlEntity.setDocKey(keyString);
                salesRecordsRepository.save(salesRecordDtlEntity);
            }
        }

    }

    public void deactivateSalesRecords(SalesRecordsDeactiveReq salesRecordsDeactiveReq) {
        String docUniqueKey = null;
        for (int i = 0; i < salesRecordsDeactiveReq.getSalesRecordsIds().size(); i++) {
            SalesRecordDtlEntity salesRecordDtlEntity = salesRecordsRepository
                    .findOne(salesRecordsDeactiveReq.getSalesRecordsIds().get(i));
            docUniqueKey = salesRecordDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
        salesRecordsRepository.deactivateSalesRecord(salesRecordsDeactiveReq.getSalesRecordsIds(),
                salesRecordsDeactiveReq.getStatus());

    }

    public void salesRecordDelete(SalesRecordsDeactiveReq salesRecordsDeleteReq) {

        salesRecordsRepository.salesRecordDelete(salesRecordsDeleteReq.getSalesRecordsIds());

    }

    public SalesRecordsDtlTO getAssetRecordFileInfo(Long saleRecordId) {

        SalesRecordsDtlTO salesRecordsDtlTO = new SalesRecordsDtlTO();
        SalesRecordDtlEntity salesRecordDtlEntity = salesRecordsRepository.findOne(saleRecordId);
        salesRecordsDtlTO.setDocKey(salesRecordDtlEntity.getDocKey());
        byte[] fileBytes = null;
        try {
        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
        	{
        		System.out.println("getAssetRecordFileInfo function if block");        		
        		Resource resource;        		
        		String fileBasePath = "E://pavani/Downloads/Resources/Immovable Assets/Asset Life Status/121/418/ss.png";
        	    //Path path = Paths.get(fileBasePath);
        	    /*try {
        	        resource = new UrlResource(path.toUri());
        	        fileBytes = Files.readAllBytes(path);
        	        System.out.println("File size in bytes");
        	        System.out.println(fileBytes);
        	    } catch(MalformedURLException e) {
        	        e.printStackTrace();
        	    }*/
        	    /*Path path = Paths.get(fileBasePath);
        	    fileBytes = Files.readAllBytes(path);
        	    System.out.println("File size in bytes");
        	    System.out.println(fileBytes);*/
        		File file = new File(fileBasePath);
                FileInputStream fin = null;
                try {
                    // create FileInputStream object
                    fin = new FileInputStream(file);
         
                    fileBytes = new byte[(int)file.length()];
                    System.out.println("file length:"+file.length());
                }
                catch (FileNotFoundException e) {
                    System.out.println("File not found" + e);
                }
                catch (IOException ioe) {
                    System.out.println("Exception while reading file " + ioe);
                }
        	}
        	else
        	{
        		fileBytes = aswS3FileService.downloadFile(salesRecordsDtlTO.getDocKey());
        	}
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        salesRecordsDtlTO.setSalesRecordsDetails(fileBytes);
        salesRecordsDtlTO.setSalesRecordsDetailsFileName(salesRecordDtlEntity.getSalesRecordsDetailsFileName());
        salesRecordsDtlTO.setSalesRecordsDetailsFileType(salesRecordDtlEntity.getSalesRecordsDetailsFileType());
        return salesRecordsDtlTO;
    }

    public DocumentsResp getSaleRecordsDocuments(SalesRecordsGetReq salesRecordsGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(salesRecordsGetReq.getProjectId(), 1);
        if (!fixedAssetIds.isEmpty()) {
            List<SalesRecordDtlEntity> salesRecordDtlEntities = salesRecordsRepository
                    .findAllProjectSaleRecords(fixedAssetIds, 1);
            for (SalesRecordDtlEntity salesRecordDtlEntity : salesRecordDtlEntities) {
                resp.getDocumentsDtlTOs().add(AssetSalesRecordsDtlHandler
                        .convertEntityToPOJODocuments(salesRecordDtlEntity, awsS3Bucket, awsS3Client));
            }
        }
        return resp;
    }

}
