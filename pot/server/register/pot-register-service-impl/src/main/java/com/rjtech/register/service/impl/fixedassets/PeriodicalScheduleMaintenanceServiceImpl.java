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
import com.rjtech.register.fixedassets.dto.PeriodicalScheduleMaintenanceDtlTO;
import com.rjtech.register.fixedassets.model.PeriodicalScheduleMaintenanceDtlEntity;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceDeleteReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceGetReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.PeriodicalScheduleMaintenanceResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.PeriodicalScheduleMaintenanceRepository;
import com.rjtech.register.service.fixedassets.PeriodicalScheduleMaintenanceService;
import com.rjtech.register.service.handler.fixedassets.PeriodicalScheduleMaintenanceDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.common.utils.UploadUtil;

@Service(value = "periodicalScheduleMaintenanceService")
@RJSService(modulecode = "periodicalScheduleMaintenanceService")
@Transactional
public class PeriodicalScheduleMaintenanceServiceImpl implements PeriodicalScheduleMaintenanceService {

    private static final Logger log = LoggerFactory.getLogger(PeriodicalScheduleMaintenanceServiceImpl.class);

    @Autowired
    private PeriodicalScheduleMaintenanceRepository periodicalScheduleMaintenanceRepository;

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
    private ProjDocFileRepository projDocFileRepository;

    @Override
    public void saveFixedAssetsPeriodicals(MultipartFile filePlan, MultipartFile fileActual,
            PeriodicalScheduleMaintenanceSaveReq periodicalScheduleMaintenanceSaveReq) {
        List<PeriodicalScheduleMaintenanceDtlEntity> list = new ArrayList<PeriodicalScheduleMaintenanceDtlEntity>();
        List<PeriodicalScheduleMaintenanceDtlEntity> listResp = null;
        String keyPlanString = null;
        String keyActualString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
        
        System.out.println("Folder category:"+periodicalScheduleMaintenanceSaveReq.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( periodicalScheduleMaintenanceSaveReq.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = periodicalScheduleMaintenanceSaveReq.getFolderCategory();
        Long userId = periodicalScheduleMaintenanceSaveReq.getUserId();
        
        List<PeriodicalScheduleMaintenanceDtlTO> fixedAssetperiodicalsRecordsList = periodicalScheduleMaintenanceSaveReq
                .getPeriodicalScheduleMaintenanceDtlTO();
        if (CommonUtil.isListHasData(fixedAssetperiodicalsRecordsList)) {
            for (PeriodicalScheduleMaintenanceDtlTO periodicalScheduleMaintenanceDtlTO : fixedAssetperiodicalsRecordsList) {
                PeriodicalScheduleMaintenanceDtlEntity entity = null;
                PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity;
                Long periodicalId = periodicalScheduleMaintenanceDtlTO.getId();
                if (CommonUtil.isNonBlankLong(periodicalId)) {
                    isUpdate = true;
                    entity = periodicalScheduleMaintenanceRepository.findOne(periodicalId);
                    if (null != filePlan && null != entity.getPlanDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    	                	{
    	                		Long asset_id = entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid();
    	                		String[] extras = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( asset_id ), String.valueOf( periodicalId ), "PLAN" };
    	                		uploadUtil.uploadFile( filePlan, folder_category, folder_path, extras );
    	                		ProjDocFileEntity planProjDocFileEntity = PeriodicalScheduleMaintenanceDtlHandler.convertPSMPOJOToProjDocFileEntity( filePlan, asset_id, userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, periodicalId );
            					entity.setProjDocFileEntity( planProjDocFileEntity );
    	                	}
                            else
                            {
                            	aswS3FileService.updateExistingFile(filePlan, entity.getPlanDocKey());
                            }
                        } catch (IOException e) {
                            log.error("Exception occurred while updating  the existing file to s3", e);
                        }

                    } else if (null != fileActual && null != entity.getActualDocKey()) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    	                	{
    	                		Long asset_id = entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid();
    	                		String[] extras = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( asset_id ), String.valueOf( periodicalId ), "ACTUAL" };
    	                		uploadUtil.uploadFile( fileActual, folder_category, folder_path, extras );
    	                		ProjDocFileEntity actualProjDocFileEntity = PeriodicalScheduleMaintenanceDtlHandler.convertPSMPOJOToProjDocFileEntity( fileActual, asset_id, userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, periodicalId );
            					entity.setProjDocFileEntity( actualProjDocFileEntity );
    	                	}
                            else
                            {
                            	aswS3FileService.updateExistingFile(fileActual, entity.getActualDocKey());
                            }
                        } catch (IOException e) {
                            log.error("Exception occurred while updating  the existing file to s3", e);
                        }
                    } else {
                        isUpdate = false;
                    }
                } else {
                    entity = new PeriodicalScheduleMaintenanceDtlEntity();
                }

                periodicalScheduleMaintenanceDtlEntity = PeriodicalScheduleMaintenanceDtlHandler.convertPOJOToEntity(
                        entity, filePlan, fileActual, periodicalScheduleMaintenanceDtlTO,
                        fixedAssetsRegisterRepository);
                list.add(periodicalScheduleMaintenanceDtlEntity);
            }
            listResp = periodicalScheduleMaintenanceRepository.save(list);
        }
        if (!isUpdate) {
            for (PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity : listResp) {
                String uniquePlanKey = AwsS3FileKeyConstants.PERIODICAL_SCHEDULE_MAINTENANCE
                        + AwsS3FileKeyConstants.PLAN_DOC + +periodicalScheduleMaintenanceDtlEntity.getId();
                String uniqueActualKey = AwsS3FileKeyConstants.PERIODICAL_SCHEDULE_MAINTENANCE
                        + AwsS3FileKeyConstants.ACTUAL_DOC + +periodicalScheduleMaintenanceDtlEntity.getId();
                try {
                	 
                    if (null != filePlan) {
                        periodicalScheduleMaintenanceDtlEntity.setPlanDocKey(keyPlanString);
                        if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
	                	{
	                		Long asset_id = periodicalScheduleMaintenanceDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid();
	                		String[] extras = { String.valueOf( periodicalScheduleMaintenanceDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( asset_id ), String.valueOf( periodicalScheduleMaintenanceDtlEntity.getId() ), "PLAN" };
	                		uploadUtil.uploadFile( filePlan, folder_category, folder_path, extras );
	                		ProjDocFileEntity planProjDocFileEntity = PeriodicalScheduleMaintenanceDtlHandler.convertPSMPOJOToProjDocFileEntity( filePlan, asset_id, userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, periodicalScheduleMaintenanceDtlEntity.getId() );
	                		periodicalScheduleMaintenanceDtlEntity.setProjDocFileEntity( planProjDocFileEntity );
	                	}
                        else
                        {
                        	keyPlanString = aswS3FileService.uploadFile(filePlan, uniquePlanKey);
                        }
                    }
                    if (null != fileActual) {
                        periodicalScheduleMaintenanceDtlEntity.setActualDocKey(keyActualString);
                        if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
	                	{
	                		Long asset_id = periodicalScheduleMaintenanceDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid();
	                		String[] extras = { String.valueOf( periodicalScheduleMaintenanceDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( asset_id ), String.valueOf( periodicalScheduleMaintenanceDtlEntity.getId() ), "ACTUAL" };
	                		uploadUtil.uploadFile( fileActual, folder_category, folder_path, extras );
	                		ProjDocFileEntity actualProjDocFileEntity = PeriodicalScheduleMaintenanceDtlHandler.convertPSMPOJOToProjDocFileEntity( fileActual, asset_id, userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, periodicalScheduleMaintenanceDtlEntity.getId() );
	                		periodicalScheduleMaintenanceDtlEntity.setProjDocFileEntity( actualProjDocFileEntity );
	                	}
                        else
                        {
                        	keyActualString = aswS3FileService.uploadFile(fileActual, uniqueActualKey);
                        }
                    }
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3", e);
                }

                periodicalScheduleMaintenanceRepository.save(periodicalScheduleMaintenanceDtlEntity);
            }
        }
    }

    public void periodicalScheduleMaintenanceDelete(
            PeriodicalScheduleMaintenanceDeleteReq periodicalScheduleMaintenanceDeleteReq) {
        String docPlanUniqueKey = null;
        String docActualUniqueKey = null;
        List<Long> periodicalIds = periodicalScheduleMaintenanceDeleteReq.getPeriodicalScheduleMaintenanceIds();
        for (int i = 0; i < periodicalIds.size(); i++) {
            PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity = periodicalScheduleMaintenanceRepository
                    .findOne(periodicalIds.get(i));
            docPlanUniqueKey = periodicalScheduleMaintenanceDtlEntity.getPlanDocKey();
            docActualUniqueKey = periodicalScheduleMaintenanceDtlEntity.getActualDocKey();
            if (null != docPlanUniqueKey) {
                aswS3FileService.deleteFile(docPlanUniqueKey);
            }
            if (null != docActualUniqueKey) {
                aswS3FileService.deleteFile(docActualUniqueKey);
            }
        }
        periodicalScheduleMaintenanceRepository.deactivateperiodicalScheduleMaintenanceRecord(
                periodicalScheduleMaintenanceDeleteReq.getPeriodicalScheduleMaintenanceIds(),
                periodicalScheduleMaintenanceDeleteReq.getStatus());

    }

    @Override
    public PeriodicalScheduleMaintenanceResp getPeriodicalScheduleMaintenance(
            PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq) {
        PeriodicalScheduleMaintenanceResp resp = new PeriodicalScheduleMaintenanceResp();
        List<PeriodicalScheduleMaintenanceDtlEntity> periodicalScheduleMaintenanceDtlEntities = periodicalScheduleMaintenanceRepository
                .findAllPeriodicalScheduleMaintenance(periodicalScheduleMaintenanceGetReq.getFixedAssetid(),
                        periodicalScheduleMaintenanceGetReq.getStatus());
        for (PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity : periodicalScheduleMaintenanceDtlEntities) {
            resp.getPeriodicalScheduleMaintenanceDtlTOs().add(PeriodicalScheduleMaintenanceDtlHandler
                    .convertEntityToPOJO(periodicalScheduleMaintenanceDtlEntity, awsS3Client, awsS3Bucket));
        }
        return resp;
    }

    @Override
    public PeriodicalScheduleMaintenanceDtlTO planDocDownloadFile(Long fileId) {
        PeriodicalScheduleMaintenanceDtlTO periodicalScheduleMaintenanceDtlTO = new PeriodicalScheduleMaintenanceDtlTO();
        PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity = periodicalScheduleMaintenanceRepository
                .findOne(fileId);
        periodicalScheduleMaintenanceDtlTO.setPlanDocKey(periodicalScheduleMaintenanceDtlEntity.getPlanDocKey());
        periodicalScheduleMaintenanceDtlTO.setActualDocKey(periodicalScheduleMaintenanceDtlEntity.getActualDocKey());
        byte[] filePlanBytes = null;
        try {
            filePlanBytes = aswS3FileService.downloadFile(periodicalScheduleMaintenanceDtlEntity.getPlanDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3", e);
        }
        periodicalScheduleMaintenanceDtlTO.setPlanRecordDocument(filePlanBytes);
        periodicalScheduleMaintenanceDtlTO
                .setPlanRecordDocumentFileName(periodicalScheduleMaintenanceDtlEntity.getPlanRecordsDetailsFileName());
        periodicalScheduleMaintenanceDtlTO
                .setPlanRecordDocumentFileType(periodicalScheduleMaintenanceDtlEntity.getPlanRecordsDetailsFileType());
        return periodicalScheduleMaintenanceDtlTO;
    }

    @Override
    public PeriodicalScheduleMaintenanceDtlTO actualDocDownloadFile(Long fileId) {
        PeriodicalScheduleMaintenanceDtlTO periodicalScheduleMaintenanceDtlTO = new PeriodicalScheduleMaintenanceDtlTO();
        PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity = periodicalScheduleMaintenanceRepository
                .findOne(fileId);
        periodicalScheduleMaintenanceDtlTO.setPlanDocKey(periodicalScheduleMaintenanceDtlEntity.getPlanDocKey());
        periodicalScheduleMaintenanceDtlTO.setActualDocKey(periodicalScheduleMaintenanceDtlEntity.getActualDocKey());
        byte[] fileActualBytes = null;
        try {
            fileActualBytes = aswS3FileService.downloadFile(periodicalScheduleMaintenanceDtlEntity.getActualDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3", e);
        }
        periodicalScheduleMaintenanceDtlTO.setActualRecordDocument(fileActualBytes);
        periodicalScheduleMaintenanceDtlTO.setActualRecordsDocumentFileName(
                periodicalScheduleMaintenanceDtlEntity.getActualRecordsDetailsFileName());
        periodicalScheduleMaintenanceDtlTO.setActualRecordsDocumentFileType(
                periodicalScheduleMaintenanceDtlEntity.getActualRecordsDetailsFileType());
        return periodicalScheduleMaintenanceDtlTO;
    }

    public DocumentsResp getProjPeriodicalDocuemnts(
            PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(periodicalScheduleMaintenanceGetReq.getProjectId(), 1);
        if (!fixedAssetIds.isEmpty()) {
            List<PeriodicalScheduleMaintenanceDtlEntity> periodicalScheduleMaintenanceDtlEntities = periodicalScheduleMaintenanceRepository
                    .findAllProjectPeriodical(fixedAssetIds, 1);
            for (PeriodicalScheduleMaintenanceDtlEntity periodicalScheduleMaintenanceDtlEntity : periodicalScheduleMaintenanceDtlEntities) {
                resp.getDocumentsDtlTOs().add(PeriodicalScheduleMaintenanceDtlHandler.convertEntityToPOJODocuments(
                        periodicalScheduleMaintenanceDtlEntity, awsS3Bucket, awsS3Client));
            }
        }
        return resp;
    }

}
