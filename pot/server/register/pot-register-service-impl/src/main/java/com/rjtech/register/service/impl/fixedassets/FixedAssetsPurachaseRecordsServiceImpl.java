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
import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.req.AssetPurchaseDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetPurachseAcqulistionResp;
import com.rjtech.register.repository.fixeassets.FixedAssetPurchaseRecordsRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.service.fixedassets.FixedAssetsPurachaseRecordsService;
import com.rjtech.register.service.handler.fixedassets.FixedAssetAqulisitionRecordsDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;

@Service(value = "fixedAssetsPurachaseRecordsService")
@RJSService(modulecode = "fixedAssetsPurachaseRecordsService")
@Transactional
public class FixedAssetsPurachaseRecordsServiceImpl implements FixedAssetsPurachaseRecordsService {

    private static final Logger log = LoggerFactory.getLogger(FixedAssetsPurachaseRecordsServiceImpl.class);

    @Autowired
    private FixedAssetPurchaseRecordsRepository fixedAssetPurchaseRecordsRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    @Autowired
    private AswS3FileService aswS3FileService;

    @Autowired
    private AmazonS3 awsS3Client;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;

    public FixedAssetPurachseAcqulistionResp getFixedAssetsPurachaseAcqulisition(
            FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq) {
        FixedAssetPurachseAcqulistionResp resp = new FixedAssetPurachseAcqulistionResp();
        List<FixedAssetPurchaseRecordsDtlEntity> fixedAssetPurchaseRecordsDtlEntities = fixedAssetPurchaseRecordsRepository
                .findAllAssetPurchaase(fixedAssetPurachaseRecordsGetReq.getFixedAssetid(),
                        fixedAssetPurachaseRecordsGetReq.getStatus());
        for (FixedAssetPurchaseRecordsDtlEntity fixedAssetPurchaseRecordsDtlEntity : fixedAssetPurchaseRecordsDtlEntities) {
            resp.getFixedAssetAqulisitionRecordsDtlTOs().add(FixedAssetAqulisitionRecordsDtlHandler
                    .convertEntityToPOJO(fixedAssetPurchaseRecordsDtlEntity, awsS3Client, awsS3Bucket));
        }
        return resp;
    }

    public void saveFixedAssetsPurachaseAcqulisition(MultipartFile file,
            FixedAssetPurachaseRecordsSaveReq fixedAssetPurachaseRecordsSave) {

        List<FixedAssetPurchaseRecordsDtlEntity> list = new ArrayList<>();
        List<FixedAssetPurchaseRecordsDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
                
        System.out.println("Folder category:"+fixedAssetPurachaseRecordsSave.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( fixedAssetPurachaseRecordsSave.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = fixedAssetPurachaseRecordsSave.getFolderCategory();
        Long userId = fixedAssetPurachaseRecordsSave.getUserId();
		
        if (CommonUtil.isListHasData(fixedAssetPurachaseRecordsSave.getFixedAssetAqulisitionRecordsDtlTO())) {
            for (FixedAssetAqulisitionRecordsDtlTO fixedAssetAqulisitionRecordsDtlTO : fixedAssetPurachaseRecordsSave
                    .getFixedAssetAqulisitionRecordsDtlTO()) {
                FixedAssetPurchaseRecordsDtlEntity entity = null;
                FixedAssetPurchaseRecordsDtlEntity fixedAssetPurchaseRecordsDtlEntity;

                if (CommonUtil.isNonBlankLong(fixedAssetAqulisitionRecordsDtlTO.getId())) {
                    isUpdate = true;
                    entity = fixedAssetPurchaseRecordsRepository.findOne(fixedAssetAqulisitionRecordsDtlTO.getId());
                    if (null != file && null != entity.getDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            				{
            					System.out.println("if block of upload condition update block");
            					String extras[] = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( entity.getId() ) };
            					// Upload purchase record docs to server				
            					uploadUtil.uploadFile( file, folder_category, folder_path, extras );            					
            					ProjDocFileEntity projDocFileEntity = FixedAssetAqulisitionRecordsDtlHandler.convertPurchasePOJOToProjDocFileEntity( file, fixedAssetAqulisitionRecordsDtlTO.getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, fixedAssetAqulisitionRecordsDtlTO.getId() );
            					entity.setProjDocFileEntity( projDocFileEntity );
            				}
                        	else
                        	{
                        		aswS3FileService.updateExistingFile(file, entity.getDocKey());
                        	}
                        } catch (IOException e) {
                            log.error("Exception occurred while updating  the existing file to s3");
                        }
                    } else {
                        isUpdate = false;
                    }
                } else {
                    entity = new FixedAssetPurchaseRecordsDtlEntity();
                }
                fixedAssetPurchaseRecordsDtlEntity = FixedAssetAqulisitionRecordsDtlHandler.convertPurchasePOJOEntity(
                        entity, file, fixedAssetAqulisitionRecordsDtlTO, fixedAssetsRegisterRepository );                
                list.add(fixedAssetPurchaseRecordsDtlEntity);
            }
            listResp = fixedAssetPurchaseRecordsRepository.save(list);
        }
        if (null != file && !isUpdate) {
            for (FixedAssetPurchaseRecordsDtlEntity fixedAssetPurchaseRecordsDtlEntity : listResp) {
                String uniqueKey = AwsS3FileKeyConstants.FIXED_ASSET_PURCHASE_RECORDS_DETAIL
                        + fixedAssetPurchaseRecordsDtlEntity.getId();
                try {
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    				{
    					System.out.println("if block of upload condition update block");
    					String extras[] = { String.valueOf( fixedAssetPurchaseRecordsDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( fixedAssetPurchaseRecordsDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( fixedAssetPurchaseRecordsDtlEntity.getId() ) };
    					// Upload purchase records docs to server				
    					uploadUtil.uploadFile( file, folder_category, folder_path, extras );    					
    					ProjDocFileEntity projDocFileEntity = FixedAssetAqulisitionRecordsDtlHandler.convertPurchasePOJOToProjDocFileEntity( file, fixedAssetPurchaseRecordsDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, fixedAssetPurchaseRecordsDtlEntity.getId() );
    					fixedAssetPurchaseRecordsDtlEntity.setProjDocFileEntity( projDocFileEntity );
    				}
                	else
                	{
                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	}                    
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                fixedAssetPurchaseRecordsDtlEntity.setDocKey(keyString);
                fixedAssetPurchaseRecordsRepository.save(fixedAssetPurchaseRecordsDtlEntity);
            }
        }
    }

    public void deactivateAssetsPurachase(AssetPurchaseDeactiveReq purchaseDeactiveReq) {
        String docUniqueKey = null;
        for (int i = 0; i < purchaseDeactiveReq.getPurchaseRecordsIds().size(); i++) {
            FixedAssetPurchaseRecordsDtlEntity fixedAssetPurchaseRecordsDtl = fixedAssetPurchaseRecordsRepository
                    .findOne(purchaseDeactiveReq.getPurchaseRecordsIds().get(i));
            docUniqueKey = fixedAssetPurchaseRecordsDtl.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
        fixedAssetPurchaseRecordsRepository.deactivatePurchaseRecord(purchaseDeactiveReq.getPurchaseRecordsIds(),
                purchaseDeactiveReq.getStatus());
    }

    public FixedAssetAqulisitionRecordsDtlTO getAssetRecordFileInfo(Long purchaseRecordId) {
        FixedAssetAqulisitionRecordsDtlTO fixedAssetAqulisitionRecordsDtlTO = new FixedAssetAqulisitionRecordsDtlTO();
        FixedAssetPurchaseRecordsDtlEntity fixedAssetPurchaseRecordsDtl = fixedAssetPurchaseRecordsRepository
                .findOne(purchaseRecordId);
        fixedAssetAqulisitionRecordsDtlTO.setDocKey(fixedAssetPurchaseRecordsDtl.getDocKey());
        byte[] fileBytes = null;
        try {
            fileBytes = aswS3FileService.downloadFile(fixedAssetPurchaseRecordsDtl.getDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        fixedAssetAqulisitionRecordsDtlTO.setPrachaseRecordDetails(fileBytes);
        fixedAssetAqulisitionRecordsDtlTO
                .setPurchaseRecordsDetailsFileName(fixedAssetPurchaseRecordsDtl.getPurchaseRecordsDetailsFileName());
        fixedAssetAqulisitionRecordsDtlTO
                .setPurchaseRecordsDetailsFileType(fixedAssetPurchaseRecordsDtl.getPurchaseRecordsDetailsFileType());
        return fixedAssetAqulisitionRecordsDtlTO;
    }

    public DocumentsResp getProjPurchaseDocuemnts(FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<FixedAssetPurchaseRecordsDtlEntity> fixedAssetPurchaseRecordsDtlEntities = fixedAssetPurchaseRecordsRepository
                .findAllProjectAssetPurchase(fixedAssetPurachaseRecordsGetReq.getProjectId(),
                        fixedAssetPurachaseRecordsGetReq.getStatus());
        if (CommonUtil.isListHasData(fixedAssetPurchaseRecordsDtlEntities)) {
            for (FixedAssetPurchaseRecordsDtlEntity fixedAssetPurchaseRecordsDtlEntity : fixedAssetPurchaseRecordsDtlEntities) {
                resp.getDocumentsDtlTOs().add(FixedAssetAqulisitionRecordsDtlHandler
                        .convertEntityToPOJODocuments(fixedAssetPurchaseRecordsDtlEntity, awsS3Client, awsS3Bucket));
            }
        }
        return resp;
    }

}
