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
import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;
import com.rjtech.register.fixedassets.model.LongTermLeasingDtlEntity;
import com.rjtech.register.fixedassets.req.LongTermLeasingDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.LongTermLeasingResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.LongTermLeasingRepository;
import com.rjtech.register.service.fixedassets.LongTermLeasingService;
import com.rjtech.register.service.handler.fixedassets.LongTermLeasingDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.constants.ApplicationConstants;

import com.rjtech.common.utils.StatusCodes;

@Service(value = "longTermLeasingService")
@RJSService(modulecode = "longTermLeasingService")
@Transactional
public class LongTermLeasingServiceImpl implements LongTermLeasingService {

    private static final Logger log = LoggerFactory.getLogger(LongTermLeasingServiceImpl.class);

    @Autowired
    private LongTermLeasingRepository longTermLeasingRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    @Autowired
    private AswS3FileService aswS3FileService;

    @Autowired
    private AmazonS3 awsS3Client;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public LongTermLeasingResp getLongTermLeasing(LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = new LongTermLeasingResp();
        List<LongTermLeasingDtlEntity> longTermLeasingDtlEntities = longTermLeasingRepository
                .findAllLongTermLeasing(longTermLeasingGetReq.getFixedAssetid(), longTermLeasingGetReq.getStatus());
        for (LongTermLeasingDtlEntity longTermLeasingDtlEntity : longTermLeasingDtlEntities) {
            resp.getLongTermLeasingDtlTOs().add(
                    LongTermLeasingDtlHandler.convertEntityToPOJO(longTermLeasingDtlEntity, awsS3Bucket, awsS3Client));
        }
        return resp;
    }
    
    public LongTermLeasingResp getLongtermleaselastrecord(LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = new LongTermLeasingResp();
        List<LongTermLeasingDtlEntity> longTermLeasingDtlEntities = longTermLeasingRepository
                .findAllLongTermLeasingLastRecord(longTermLeasingGetReq.getFixedAssetid(), longTermLeasingGetReq.getStatus());
        for (LongTermLeasingDtlEntity longTermLeasingDtlEntity : longTermLeasingDtlEntities) {
            resp.getLongTermLeasingDtlTOs().add(
                    LongTermLeasingDtlHandler.convertEntityToPOJO(longTermLeasingDtlEntity, awsS3Bucket, awsS3Client));
        }
        return resp;
    }
    
    public LongTermLeasingResp getLongtermleaseActiveAllRecord(LongTermLeasingGetReq longTermLeasingGetReq) {
        LongTermLeasingResp resp = new LongTermLeasingResp();
        List<LongTermLeasingDtlEntity> longTermLeasingDtlEntities = longTermLeasingRepository
                .findAllLongTermLeasingActiveAllRecord(longTermLeasingGetReq.getFixedAssetid(), longTermLeasingGetReq.getStatus(),longTermLeasingGetReq.getCurrentStatus());
        for (LongTermLeasingDtlEntity longTermLeasingDtlEntity : longTermLeasingDtlEntities) {
            resp.getLongTermLeasingDtlTOs().add(
                    LongTermLeasingDtlHandler.convertEntityToPOJO(longTermLeasingDtlEntity, awsS3Bucket, awsS3Client));
        }
        return resp;
    }

    public void saveLongTermLeasing(MultipartFile file, LongTermLeasingSaveReq longTermLeasingSave) {

        List<LongTermLeasingDtlEntity> list = new ArrayList<>();
        List<LongTermLeasingDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        boolean isDocFileInserted = false;
        UploadUtil uploadUtil = new UploadUtil();
        Long userId = longTermLeasingSave.getUserId();
        String newAssitId = "";
        String AssetName = "";
        int temp = 0;
        
        System.out.println("Folder category:"+longTermLeasingSave.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( longTermLeasingSave.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_name = longTermLeasingSave.getFolderCategory();
        if (CommonUtil.isListHasData(longTermLeasingSave.getLongTermLeasingDtlTOs())) {
            for (LongTermLeasingDtlTO longTermLeasingDtlTO : longTermLeasingSave.getLongTermLeasingDtlTOs()) {
                LongTermLeasingDtlEntity entity = null;
                LongTermLeasingDtlEntity longTermLeasingDtlEntity;

                Integer count = longTermLeasingRepository.getCountOfLongTermLeasing(longTermLeasingDtlTO.getFixedAssetid());
                AssetName = fixedAssetsRegisterRepository.getAssetNameById(longTermLeasingDtlTO.getFixedAssetid());
                
                if (CommonUtil.isNonBlankLong(longTermLeasingDtlTO.getId())) {
                	System.out.println("if condition getId");
                    isUpdate = true;
                    entity = longTermLeasingRepository.findOne(longTermLeasingDtlTO.getId());
                    if (null != file) {
                    	isDocFileInserted = true;
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            				{   
                        		ProjDocFileEntity resProjDocFileEntity = LongTermLeasingDtlHandler.convertLongTermLeasingPOJOToProjDocFileEntity( file, longTermLeasingDtlTO.getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_name, longTermLeasingDtlTO.getId() );                        		
            					System.out.println("if block of upload condition update block");
            					String extras[] = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( entity.getId() ) };
            					// Upload purchase record docs to server				
            					uploadUtil.uploadFile( file, folder_name, folder_path, extras );
            					entity.setProjDocFileEntity( resProjDocFileEntity );
            				}
                        	else
                        	{
                        		aswS3FileService.updateExistingFile(file, entity.getDocKey());
                        	}
                        } catch (IOException e) {
                            log.error("Exception occurred while updating  the existing file to s3");
                        }
                    }
                    newAssitId = longTermLeasingDtlTO.getLeaseAgreement();
                } else {
                	System.out.println("else condition getId");
                    entity = new LongTermLeasingDtlEntity();
                    temp = count;
                    count = count+1;
                     newAssitId =  AssetName+ "-LS-" +count;
                     
                }
                
                longTermLeasingDtlEntity = LongTermLeasingDtlHandler.convertLongTermLeasingPOJOEntity(file,
                        longTermLeasingDtlTO, fixedAssetsRegisterRepository, newAssitId);  
                
                List<LongTermLeasingDtlEntity> list123 = longTermLeasingRepository.findAllLongTermLeasing(longTermLeasingDtlTO.getFixedAssetid(), 1);
                for(LongTermLeasingDtlEntity longTermLeasingDtlEntities : list123) {
                	for(int i=0; i<temp;i++) {
                		longTermLeasingDtlEntities.setCurrentStatus("Inactive");
     //           		longTermLeasingDtlEntity.setStatus(StatusCodes.DEACIVATE.getValue()); if bug id given only
               	 }
                }
                
                list.add(longTermLeasingDtlEntity);
            }
            listResp = longTermLeasingRepository.save(list);
        }
        if (null != file && !isUpdate) {
        	System.out.println("if block of isUpdate condition");
            for (LongTermLeasingDtlEntity longTermLeasingDtlEntity : listResp) {
                String uniqueKey = AwsS3FileKeyConstants.LONG_TERM_LEASING_DETAIL + longTermLeasingDtlEntity.getId();
                try {
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    				{
                		ProjDocFileEntity resProjDocFileEntity = LongTermLeasingDtlHandler.convertLongTermLeasingPOJOToProjDocFileEntity( file, longTermLeasingDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_name, longTermLeasingDtlEntity.getId() );
    					System.out.println("if block of upload condition update block");
    					String extras[] = { String.valueOf( longTermLeasingDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( longTermLeasingDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( longTermLeasingDtlEntity.getId() ) };
    					// Upload purchase record docs to server				
    					uploadUtil.uploadFile( file, folder_name, folder_path, extras );
    					longTermLeasingDtlEntity.setProjDocFileEntity( resProjDocFileEntity );
    				}
                	else
                	{
                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	}
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                longTermLeasingDtlEntity.setDocKey(keyString);                
                longTermLeasingRepository.save(longTermLeasingDtlEntity);
            }
        }
    }

    public void deactivateLongTermLeasing(LongTermLeasingDeactiveReq longTermLeasingDeactiveReq) {
        String docUniqueKey = null;
        for (int i = 0; i < longTermLeasingDeactiveReq.getLongTermLeasingIds().size(); i++) {
            LongTermLeasingDtlEntity longTermLeasingDtlEntity = longTermLeasingRepository
                    .findOne(longTermLeasingDeactiveReq.getLongTermLeasingIds().get(i));
            docUniqueKey = longTermLeasingDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
        longTermLeasingRepository.deactivateLongTermLeasing(longTermLeasingDeactiveReq.getLongTermLeasingIds(),
                longTermLeasingDeactiveReq.getStatus());
    }

    public void longTermLeasingDelete(LongTermLeasingDeactiveReq longTermLeasingDeleteReq) {
        longTermLeasingRepository.longTermLeasingDelete(longTermLeasingDeleteReq.getLongTermLeasingIds());

    }

    public LongTermLeasingDtlTO getRentalHistoryFileInfo(Long rentalHistoryId) {
        LongTermLeasingDtlTO longTermLeasingDtlTO = new LongTermLeasingDtlTO();
        LongTermLeasingDtlEntity longTermLeasingDtlEntity = longTermLeasingRepository.findOne(rentalHistoryId);
        longTermLeasingDtlTO.setDocKey(longTermLeasingDtlEntity.getDocKey());
        byte[] fileBytes = null;
        try {
            fileBytes = aswS3FileService.downloadFile(longTermLeasingDtlEntity.getDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        longTermLeasingDtlTO.setLeaseDocumentDetails(fileBytes);
        longTermLeasingDtlTO
                .setLeaseDocumentDetailsFileName(longTermLeasingDtlEntity.getLeaseDocumentDetailsFileName());
        longTermLeasingDtlTO
                .setLeaseDocumentDetailsFileType(longTermLeasingDtlEntity.getLeaseDocumentDetailsFileType());
        return longTermLeasingDtlTO;
    }

    public DocumentsResp getProjLongTermLeasingDocuemnts(LongTermLeasingGetReq longTermLeasingGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(longTermLeasingGetReq.getProjectId(), 1);
        if (!fixedAssetIds.isEmpty()) {
            List<LongTermLeasingDtlEntity> longTermLeasingDtlEntities = longTermLeasingRepository
                    .findAllProjectLongTermLeasing(fixedAssetIds, 1);
            for (LongTermLeasingDtlEntity longTermLeasingDtlEntity : longTermLeasingDtlEntities) {
                resp.getDocumentsDtlTOs().add(LongTermLeasingDtlHandler
                        .convertEntityToPOJODocuments(longTermLeasingDtlEntity, awsS3Bucket, awsS3Client));
            }
        }
        return resp;
    }

}
