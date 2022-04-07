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
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.AwsS3FileKeyConstants;
import com.rjtech.register.fixedassets.dto.AdvanceRentalRecepitsDtlTO;
import com.rjtech.register.fixedassets.dto.STCORRReturnsDtlTO;
import com.rjtech.register.fixedassets.dto.SubSequentRentalRecepitsDtlTO;
import com.rjtech.register.fixedassets.model.AdvanceRentalRecepitsDtlEntity;
import com.rjtech.register.fixedassets.model.STCORRReturnsDtlEntity;
import com.rjtech.register.fixedassets.model.SubSequentRentalRecepitsDtlEntity;
import com.rjtech.register.fixedassets.req.STCORRReturnsDeactiveReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsFilterReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsGetReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.STCORRReturnsResp;
import com.rjtech.register.fixedassets.resp.SubSequentRentalRecepitsResp;
import com.rjtech.register.repository.fixeassets.AdvanceRentalRecepitsRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.STCORRReturnsRepository;
import com.rjtech.register.repository.fixeassets.SubSequentRentalRecepitsRepository;
import com.rjtech.register.service.fixedassets.STCORRReturnsService;
import com.rjtech.register.service.handler.fixedassets.AdvanceRentalRecepitsDtlHandler;
import com.rjtech.register.service.handler.fixedassets.STCORRReturnsDtlHandler;
import com.rjtech.register.service.handler.fixedassets.SubSequentRentalRecepitsDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;

@Service(value = "stcorrReturnsService")
@RJSService(modulecode = "stcorrReturnsService")
@Transactional
public class STCORRReturnsServiceImpl implements STCORRReturnsService {

    private static final Logger log = LoggerFactory.getLogger(STCORRReturnsServiceImpl.class);

    @Autowired
    private STCORRReturnsRepository stcorrReturnsRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    @Autowired
    private AdvanceRentalRecepitsRepository advanceRentalRecepitsRepository;

    @Autowired
    private SubSequentRentalRecepitsRepository subSequentRentalRecepitsRepository;

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

    public STCORRReturnsResp getStcorrReturns(STCORRReturnsGetReq stcorrReturnsGetReq) {

        STCORRReturnsResp resp = new STCORRReturnsResp();
        List<STCORRReturnsDtlEntity> stcorrReturnsDtlEntits = stcorrReturnsRepository
                .findAllSTCORRReturns(stcorrReturnsGetReq.getFixedAssetid(), stcorrReturnsGetReq.getStatus());

        for (STCORRReturnsDtlEntity stcorrReturnsDtlEntity : stcorrReturnsDtlEntits) {
            resp.getStcorrReturnsDtlTOs()
                    .add(STCORRReturnsDtlHandler.convertEntityToPOJO(stcorrReturnsDtlEntity, awsS3Client, awsS3Bucket));
        }
        return resp;
    }

    public SubSequentRentalRecepitsResp getStcorrSubSequentRentalReceipts(STCORRReturnsGetReq stcorrReturnsGetReq) {
        SubSequentRentalRecepitsResp resp = new SubSequentRentalRecepitsResp();
        List<SubSequentRentalRecepitsDtlEntity> subSequentRentalRecepitsDtlEntity = stcorrReturnsRepository
                .getSubSequentRentalRecepits(stcorrReturnsGetReq.getId());
        for (SubSequentRentalRecepitsDtlEntity stcorrReturnsDtlEntity : subSequentRentalRecepitsDtlEntity) {
            resp.getSubSequentRentalRecepitsDtlTO()
                    .add(SubSequentRentalRecepitsDtlHandler.convertEntityToPOJO(stcorrReturnsDtlEntity));
        }
        return resp;
    }

    public void saveSTCORRReturns(MultipartFile file, STCORRReturnsSaveReq stcorrReturnsSave) {
        List<STCORRReturnsDtlEntity> list = new ArrayList<>();
        List<SubSequentRentalRecepitsDtlEntity> subSequentRentalRecepitslistResp = null;
        List<AdvanceRentalRecepitsDtlEntity> advanceRentalRecepitslistResp = null;
        List<STCORRReturnsDtlEntity> listResp = null;
        String keyString = null;
        boolean isUpdate = false;
        UploadUtil uploadUtil = new UploadUtil();
        
        System.out.println("Folder category:"+stcorrReturnsSave.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( stcorrReturnsSave.getFolderCategory() );
        String folder_path = folder.getUploadFolder();
        String folder_category = stcorrReturnsSave.getFolderCategory();
        Long userId = stcorrReturnsSave.getUserId();

        if (CommonUtil.isListHasData(stcorrReturnsSave.getStcorrReturnsDtlTOs())) {
            for (STCORRReturnsDtlTO stcorrReturnsDtlTO : stcorrReturnsSave.getStcorrReturnsDtlTOs()) {
                STCORRReturnsDtlEntity entity = null;
                STCORRReturnsDtlEntity stcorrReturnsDtlEntity;

                List<AdvanceRentalRecepitsDtlTO> advanceRentalRecepitsList = stcorrReturnsDtlTO.getAdvancePaidList();
                if (CommonUtil.isListHasData(advanceRentalRecepitsList)) {
                    List<AdvanceRentalRecepitsDtlEntity> advanceRentalRecepitslist = new ArrayList<>();
                    for (AdvanceRentalRecepitsDtlTO advancePaidTo : advanceRentalRecepitsList) {
                        AdvanceRentalRecepitsDtlEntity advancePaidDtlEntity;
                        if (CommonUtil.isBlankLong(advancePaidTo.getId())) {
                            advancePaidDtlEntity = new AdvanceRentalRecepitsDtlEntity();
                            advancePaidDtlEntity = AdvanceRentalRecepitsDtlHandler
                                    .convertPOJOTOEntity(advancePaidDtlEntity, advancePaidTo);
                            advanceRentalRecepitslist.add(advancePaidDtlEntity);
                            advanceRentalRecepitslistResp = advanceRentalRecepitsRepository
                                    .save(advanceRentalRecepitslist);
                        }
                    }
                }

                if (CommonUtil.isListHasData(stcorrReturnsDtlTO.getSubSequentRentalRecepitsList())) {
                    List<SubSequentRentalRecepitsDtlEntity> subSequentRentalRecepitslist = new ArrayList<>();
                    for (SubSequentRentalRecepitsDtlTO subSequentRentalRecepitsTo : stcorrReturnsDtlTO
                            .getSubSequentRentalRecepitsList()) {
                        SubSequentRentalRecepitsDtlEntity subSequentRentalRecepitsDtlEntity;
                        if (CommonUtil.isNonBlankLong(subSequentRentalRecepitsTo.getId())) {
                            subSequentRentalRecepitsDtlEntity = subSequentRentalRecepitsRepository
                                    .findOne(subSequentRentalRecepitsTo.getId());
                        } else {
                            subSequentRentalRecepitsDtlEntity = new SubSequentRentalRecepitsDtlEntity();
                        }
                        subSequentRentalRecepitsDtlEntity = SubSequentRentalRecepitsDtlHandler
                                .convertPOJOTOEntity(subSequentRentalRecepitsDtlEntity, subSequentRentalRecepitsTo);
                        subSequentRentalRecepitslist.add(subSequentRentalRecepitsDtlEntity);
                    }
                    subSequentRentalRecepitslistResp = subSequentRentalRecepitsRepository
                            .save(subSequentRentalRecepitslist);
                }

                Long stcorrId = stcorrReturnsDtlTO.getId();
                if (CommonUtil.isNonBlankLong(stcorrId)) {
                    isUpdate = true;
                    entity = stcorrReturnsRepository.findOne(stcorrId);
                    if (null != file) {
                        try {
                        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            				{
            					System.out.println("if block of upload condition update block");
            					String extras[] = { String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( stcorrId ) };
            					// Upload purchase record docs to server				
            					uploadUtil.uploadFile( file, folder_category, folder_path, extras );            					
            					ProjDocFileEntity projDocFileEntity = STCORRReturnsDtlHandler.convertSTCORRRReturnsPOJOToProjDocFileEntity( file, stcorrReturnsDtlTO.getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, stcorrReturnsDtlTO.getId() );
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
                    entity = new STCORRReturnsDtlEntity();
                }

                stcorrReturnsDtlEntity = STCORRReturnsDtlHandler.converSTCORRReturnsPOJOToEntity(entity, file,
                        stcorrReturnsDtlTO, fixedAssetsRegisterRepository);

                if (CommonUtil.isBlankLong(stcorrReturnsDtlTO.getId())) {
                    stcorrReturnsDtlEntity.setAdvanceRentalReceiptsDtlEntityList(advanceRentalRecepitslistResp);
                }
                stcorrReturnsDtlEntity.setSubSequentRentalReceiptsDtlEntityList(subSequentRentalRecepitslistResp);
                list.add(stcorrReturnsDtlEntity);
            }
            listResp = stcorrReturnsRepository.save(list);
        }

        if (null != file && !isUpdate) {
            for (STCORRReturnsDtlEntity stcorrReturnsDtlEntity : listResp) {
                String uniqueKey = AwsS3FileKeyConstants.GET_STCORR_RETURNS_DETAILS + stcorrReturnsDtlEntity.getId();
                try {
                	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    				{
    					System.out.println("if block of upload condition update block");
    					String extras[] = { String.valueOf( stcorrReturnsDtlEntity.getFixedAssetsRegisterDtlEntity().getProjMstrEntity().getProjectId() ), String.valueOf( stcorrReturnsDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid() ), String.valueOf( stcorrReturnsDtlEntity.getId() ) };
    					// Upload purchase records docs to server				
    					uploadUtil.uploadFile( file, folder_category, folder_path, extras );    					
    					ProjDocFileEntity projDocFileEntity = STCORRReturnsDtlHandler.convertSTCORRRReturnsPOJOToProjDocFileEntity( file, stcorrReturnsDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid(), userId, fixedAssetsRegisterRepository, projDocFolderRepository, projDocFileRepository, folder_category, stcorrReturnsDtlEntity.getId() );
    					stcorrReturnsDtlEntity.setProjDocFileEntity( projDocFileEntity );
    				}
                	else
                	{
                		keyString = aswS3FileService.uploadFile(file, uniqueKey);
                	}
                } catch (IOException e) {
                    log.error("Exception occurred while uploading the file to s3");
                }
                stcorrReturnsDtlEntity.setDocKey(keyString);
                stcorrReturnsRepository.save(stcorrReturnsDtlEntity);
            }
        }
    }

    public void deactivateSTCORRReturns(STCORRReturnsDeactiveReq stcorrReturnsDeactiveReq) {

        String docUniqueKey = null;
        for (int i = 0; i < stcorrReturnsDeactiveReq.getStcorrReturnsIds().size(); i++) {
            STCORRReturnsDtlEntity stcorrReturnsDtlEntity = stcorrReturnsRepository
                    .findOne(stcorrReturnsDeactiveReq.getStcorrReturnsIds().get(i));
            docUniqueKey = stcorrReturnsDtlEntity.getDocKey();
            if (null != docUniqueKey) {
                aswS3FileService.deleteFile(docUniqueKey);
            }
        }
        stcorrReturnsRepository.deactivateShortTermRecors(stcorrReturnsDeactiveReq.getStcorrReturnsIds(),
                StatusCodes.DEACIVATE.getValue());
    }

    public STCORRReturnsResp getStcorrReturnsSearch(STCORRReturnsFilterReq stcorrReturnsFilterReq) {

        STCORRReturnsResp resp = new STCORRReturnsResp();
        List<STCORRReturnsDtlEntity> salesRecordDtlEntities = stcorrReturnsRepository.findSTCORRRetrunsFilter(
                stcorrReturnsFilterReq.getFromDate(), stcorrReturnsFilterReq.getToDate(),
                stcorrReturnsFilterReq.getFixedAssetid(), stcorrReturnsFilterReq.getStatus());
        for (STCORRReturnsDtlEntity salesRecordDtlEntity : salesRecordDtlEntities) {
            resp.getStcorrReturnsDtlTOs()
                    .add(STCORRReturnsDtlHandler.convertEntityToPOJO(salesRecordDtlEntity, awsS3Client, awsS3Bucket));
        }
        return resp;
    }

    public void stcorrReturnsDelete(STCORRReturnsDeactiveReq stcorrReturnsDeleteReq) {
        stcorrReturnsRepository.stcorrReturnsDelete(stcorrReturnsDeleteReq.getStcorrReturnsIds());
    }

    public STCORRReturnsDtlTO stcorrReturnsDocDownloadFile(Long shortTermRecordId) {
        STCORRReturnsDtlTO stcorrReturnsDtlTO = new STCORRReturnsDtlTO();
        STCORRReturnsDtlEntity stcorrReturnsDtlEntity = stcorrReturnsRepository.findOne(shortTermRecordId);
        stcorrReturnsDtlTO.setDocKey(stcorrReturnsDtlEntity.getDocKey());
        byte[] fileBytes = null;
        try {
            fileBytes = aswS3FileService.downloadFile(stcorrReturnsDtlEntity.getDocKey());
        } catch (IOException e) {
            log.error("Exception occurred while downloading the file from s3");
        }
        stcorrReturnsDtlTO.setTenantRecordDetails(fileBytes);
        stcorrReturnsDtlTO.setTenantRecordDetailsFileName(stcorrReturnsDtlEntity.getTenantRecordDetailsFileName());
        stcorrReturnsDtlTO.setTenantRecordDetailsFileType(stcorrReturnsDtlEntity.getTenantRecordDetailsFileType());
        return stcorrReturnsDtlTO;
    }

    public DocumentsResp getProjShortDocuemnts(STCORRReturnsGetReq stcorrReturnsGetReq) {
        DocumentsResp resp = new DocumentsResp();
        List<Integer> fixedAssetIds = fixedAssetsRegisterRepository
                .findAllAssetByProjectId(stcorrReturnsGetReq.getProjectId(), 1);
        if (fixedAssetIds.size() > 0) {
            List<STCORRReturnsDtlEntity> stcorrReturnsDtlEntits = stcorrReturnsRepository
                    .findAllProjectShortTerm(fixedAssetIds, 1);
            for (STCORRReturnsDtlEntity sTCORRReturnsDtlEntity : stcorrReturnsDtlEntits) {
                resp.getDocumentsDtlTOs().add(STCORRReturnsDtlHandler
                        .convertEntityToPOJODocuments(sTCORRReturnsDtlEntity, awsS3Bucket, awsS3Client));
            }
        }
        return resp;
    }
}
