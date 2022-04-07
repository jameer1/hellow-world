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
import com.rjtech.register.fixedassets.dto.CarParkingTollCollectionDtlTO;
import com.rjtech.register.fixedassets.model.CarParkingTollCollectionDtlEntity;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionDeleteReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionGetReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionSaveReq;
import com.rjtech.register.fixedassets.resp.CarParkingTollCollectionResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.repository.fixeassets.CarParkingTollCollectionRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.service.fixedassets.CarParkingTollCollectionService;
import com.rjtech.register.service.handler.fixedassets.CarParkingTollCollectionDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.common.utils.UploadUtil;

@Service(value = "carParkingTollCollectionService")
@RJSService(modulecode = "carParkingTollCollectionService")
@Transactional
public class CarParkingTollCollectionServiceImpl implements CarParkingTollCollectionService {

    private static final Logger log = LoggerFactory.getLogger(CarParkingTollCollectionServiceImpl.class);

    @Autowired
    private CarParkingTollCollectionRepository carParkingTollCollectionRepository;

    @Autowired
    private AswS3FileService aswS3FileService;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    @Autowired
    private AmazonS3 awsS3Client;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private UploadUtil uploadUtil;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public CarParkingTollCollectionResp getCarParkingTollCollection(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        CarParkingTollCollectionResp resp = new CarParkingTollCollectionResp();
        List<CarParkingTollCollectionDtlEntity> carParkingTollCollectionDtlEntities = carParkingTollCollectionRepository
                .findAllCarParkingTollCollection(carParkingTollCollectionGetReq.getFixedAssetid(),
                        carParkingTollCollectionGetReq.getStatus());
        for (CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity : carParkingTollCollectionDtlEntities) {
            resp.getCarParkingTollCollectionDtlTOs().add(CarParkingTollCollectionDtlHandler
                    .convertEntityToPOJO(carParkingTollCollectionDtlEntity, awsS3Bucket, awsS3Client));
        }
        return resp;
    }
    
    //last record in method
    public CarParkingTollCollectionResp getCarParkingTollCollectionLastRecord(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        CarParkingTollCollectionResp resp = new CarParkingTollCollectionResp();
        List<CarParkingTollCollectionDtlEntity> carParkingTollCollectionDtlEntities = carParkingTollCollectionRepository
                .findAllProjectCarParkingLastRecord(carParkingTollCollectionGetReq.getFixedAssetid(),
                        carParkingTollCollectionGetReq.getStatus());
       
        for (CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity : carParkingTollCollectionDtlEntities) { 	
            resp.getCarParkingTollCollectionDtlTOs().add(CarParkingTollCollectionDtlHandler
                    .convertEntityToPOJO(carParkingTollCollectionDtlEntity, awsS3Bucket, awsS3Client));
        }
        return resp;
    }
    
    //

    public void saveCarParkingTollCollection(MultipartFile file,
            CarParkingTollCollectionSaveReq carParkingTollCollectionSave) {
        List<CarParkingTollCollectionDtlEntity> list = new ArrayList<CarParkingTollCollectionDtlEntity>();
        List<CarParkingTollCollectionDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
        
        System.out.println("Folder category:"+carParkingTollCollectionSave.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( carParkingTollCollectionSave.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = carParkingTollCollectionSave.getFolderCategory();
        Long userId = carParkingTollCollectionSave.getUserId();
        
        if (!carParkingTollCollectionSave.getCarParkingTollCollectionDtlTO().isEmpty()) {
            for (CarParkingTollCollectionDtlTO carParkingTollCollectionDtlTO : carParkingTollCollectionSave
                    .getCarParkingTollCollectionDtlTO()) {
            	Long asset_id_wrap = carParkingTollCollectionSave.getFixedAssetid();
                CarParkingTollCollectionDtlEntity entity = null;
                CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity;

                if (CommonUtil.isNonBlankLong(carParkingTollCollectionDtlTO.getId())) {
                    isUpdate = true;
                    entity = carParkingTollCollectionRepository.findOne(carParkingTollCollectionDtlTO.getId());
                    if (null != file && null != entity.getDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
                        	{
                        		String[] extras = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( entity.getId() ) };
                        		ProjDocFileEntity projDocFileEntity = CarParkingTollCollectionDtlHandler.convertCarParkingPOJOToProjDocFileEntity( file, entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, entity.getId() );
                        		uploadUtil.uploadFile( file, folder_category, folder_path, extras );
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
                    entity = new CarParkingTollCollectionDtlEntity();
                }
                carParkingTollCollectionDtlEntity = CarParkingTollCollectionDtlHandler.convertPOJOToEntity(entity, file,
                        carParkingTollCollectionDtlTO, fixedAssetsRegisterRepository);
                list.add(carParkingTollCollectionDtlEntity);
            }
            listResp = carParkingTollCollectionRepository.save(list);
        }
        
        if (null != file && !isUpdate) {
            for( CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity : listResp ) {
                String uniqueKey = AwsS3FileKeyConstants.CAR_PARKING_TOLL_COLLECTION
                        + carParkingTollCollectionDtlEntity.getId();
                /*ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( folder_name );		
        		String dir_path = folder.getUploadFolder();*/
                try {
                    //keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
                	{
                		String[] extras = { String.valueOf( carParkingTollCollectionDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( carParkingTollCollectionDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( carParkingTollCollectionDtlEntity.getId() ) };
                		ProjDocFileEntity projDocFileEntity = CarParkingTollCollectionDtlHandler.convertCarParkingPOJOToProjDocFileEntity( file, carParkingTollCollectionDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, carParkingTollCollectionDtlEntity.getId() );
                		uploadUtil.uploadFile( file, folder_category, folder_path, extras );
                		carParkingTollCollectionDtlEntity.setProjDocFileEntity( projDocFileEntity );
                	}
                	else
                	{
                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	}
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                carParkingTollCollectionDtlEntity.setDocKey(keyString);
                carParkingTollCollectionRepository.save(carParkingTollCollectionDtlEntity);
            }
        }
    }

    public void carParkingTollCollectionDelete(CarParkingTollCollectionDeleteReq carParkingTollCollectionDeleteReq) {
        String docUniqueKey = null;
        for (int i = 0; i < carParkingTollCollectionDeleteReq.getCarParkingTollCollectionIds().size(); i++) {
            CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity = carParkingTollCollectionRepository
                    .findOne(carParkingTollCollectionDeleteReq.getCarParkingTollCollectionIds().get(i));
            docUniqueKey = carParkingTollCollectionDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
        carParkingTollCollectionRepository
                .carParkingTollCollectionDelete(carParkingTollCollectionDeleteReq.getCarParkingTollCollectionIds());
    }

    public CarParkingTollCollectionDtlTO carParkingTollCollectionDocDownloadFile(Long carParkingTollId) {
        CarParkingTollCollectionDtlTO fixedAssetAqulisitionRecordsDtlTO = new CarParkingTollCollectionDtlTO();
        CarParkingTollCollectionDtlEntity fixedAssetPurchaseRecordsDtl = carParkingTollCollectionRepository
                .findOne(carParkingTollId);
        fixedAssetAqulisitionRecordsDtlTO.setDocKey(fixedAssetPurchaseRecordsDtl.getDocKey());
        byte[] fileBytes = null;
        try {
            fileBytes = aswS3FileService.downloadFile(fixedAssetPurchaseRecordsDtl.getDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        fixedAssetAqulisitionRecordsDtlTO.setCarParkingTollDocuments(fileBytes);
        fixedAssetAqulisitionRecordsDtlTO
                .setCarParkingTollFileName(fixedAssetPurchaseRecordsDtl.getCarParkingTollFileName());
        fixedAssetAqulisitionRecordsDtlTO
                .setCarParkingTollFileType(fixedAssetPurchaseRecordsDtl.getCarParkingTollFileType());
        return fixedAssetAqulisitionRecordsDtlTO;
    }

    public DocumentsResp getProjCarParkingDocuemnts(CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(carParkingTollCollectionGetReq.getProjectId(), 1);
        if (!fixedAssetIds.isEmpty()) {
            List<CarParkingTollCollectionDtlEntity> carParkingTollCollectionDtlEntities = carParkingTollCollectionRepository
                    .findAllProjectCarParking(fixedAssetIds, 1);
            for (CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity : carParkingTollCollectionDtlEntities) {
                resp.getDocumentsDtlTOs().add(CarParkingTollCollectionDtlHandler
                        .convertEntityToPOJODocuments(carParkingTollCollectionDtlEntity, awsS3Client, awsS3Bucket));
            }
        }
        return resp;
    }

}
