package com.rjtech.document.service.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.aws.common.s3.file.service.AswS3FileService;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.eps.model.ProjMstrEntity;

@Component
public class ProjDocFileHandler {

    @Autowired
    private AswS3FileService awsAswS3FileService;

    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    @Autowired
    private ProjDocFolderRepository projDocFolderRepo;

    public ProjDocFileTO convertEntityToPOJO(ProjDocFileEntity projDocFileEntity, boolean includeFileContent)
            throws IOException {
        ProjDocFileTO projDocFileTO = new ProjDocFileTO();
        projDocFileTO.setId(projDocFileEntity.getId());
        projDocFileTO.setCode(generateCode(projDocFileEntity));
        projDocFileTO.setName(projDocFileEntity.getName());
        projDocFileTO.setFileType(projDocFileEntity.getFileType());
        projDocFileTO.setFileSize(projDocFileEntity.getFileSize());
        projDocFileTO.setVersion(projDocFileEntity.getVersion());
        projDocFileTO.setUpdatedOn(projDocFileEntity.getUpdatedOn());
        projDocFileTO.setCreatedOn(projDocFileEntity.getCreatedOn());
        projDocFileTO.setUpdatedBy(projDocFileEntity.getUpdatedBy().getUserName());
        projDocFileTO.setFileStatus(projDocFileEntity.getFileStatus());
        projDocFileTO.setStatus(projDocFileEntity.getStatus());
        projDocFileTO.setFolderId(projDocFileEntity.getFolderId().getId());
        projDocFileTO.setCreatedBy(projDocFileEntity.getCreatedBy().getUserName());
        projDocFileTO.setCategory(projDocFileEntity.getCategory());
        projDocFileTO.setDescription(projDocFileEntity.getDescription());
        projDocFileTO.setUniqueKey(projDocFileEntity.getUniqueKey());
        if (includeFileContent) {
            projDocFileTO.setFileContent(awsAswS3FileService.downloadFile(projDocFileEntity.getUniqueKey()));
        }

        return projDocFileTO;
    }

    public ProjDocFileEntity convertPOJOToEntity(ProjDocFileTO projDocFileTO) {

        ProjDocFileEntity projDocFileEntity = null;
        
        if (CommonUtil.isNonBlankLong(projDocFileTO.getId())) {
        	System.out.println("if condition of ProjDocFIleHandler convertPOJOToEntity");
            projDocFileEntity = projDocFileRepository.findOne(projDocFileTO.getId());
        } else {
        	System.out.println("else condition of ProjDocFIleHandler convertPOJOToEntity");
            projDocFileEntity = new ProjDocFileEntity();
        }
        Long folderId = projDocFileTO.getFolderId();
        System.out.println("Folder id:"+folderId);
        if (CommonUtil.isNonBlankLong(folderId)) {
        	System.out.println("if block:"+folderId);
        	projDocFileEntity.setFolderId(projDocFolderRepo.findOne(folderId));    	
        }
        projDocFileEntity.setVersion(projDocFileTO.getVersion());
        projDocFileEntity.setFileStatus(projDocFileTO.getFileStatus());
        projDocFileEntity.setStatus(projDocFileTO.getStatus());
        projDocFileEntity.setCategory(projDocFileTO.getCategory());
        projDocFileEntity.setDescription(projDocFileTO.getDescription());
        projDocFileEntity.setUniqueKey(projDocFileTO.getUniqueKey());
        if( ( projDocFileTO.getFromSource().equals("Procurement") || projDocFileTO.getFromSource().equals("Other Documents") ) && projDocFileTO.getFromSource() != null )
        {
        	ProjMstrEntity projMstrEntity = new ProjMstrEntity();
    		projMstrEntity.setProjectId( projDocFileTO.getProjId() );
    		projDocFileEntity.setProjectId( projMstrEntity );
        }
        projDocFileEntity.setFolderPath( projDocFileTO.getFolderPath() );
        MultipartFile multipartFile = projDocFileTO.getMultipartFile();
     //   if (multipartFile != null && multipartFile.getSize() > 0) {
            projDocFileEntity.setName(projDocFileTO.getName());
            projDocFileEntity.setFileType(projDocFileTO.getFileType());
            projDocFileEntity.setFileSize(projDocFileTO.getFileSize());
    //    }
        return projDocFileEntity;
    }

    public void uploadFileToAwsS3(ProjDocFileEntity projDocFileEntity, MultipartFile multipartFile,
            String uniqueKeyPrefix) throws IOException {
        // Upload file to AWS S3
        String fileKey = awsAswS3FileService.uploadFile(multipartFile,
                uniqueKeyPrefix + "_" + projDocFileEntity.getId());
        projDocFileEntity.setUniqueKey(fileKey);
    }

    public void checkAndUpdateFileTOAwsS3(ProjDocFileEntity projDocFileEntity, MultipartFile multipartFile,
            String uniqueKeyPrefix) throws IOException {
        String uniqueKey = projDocFileEntity.getUniqueKey();
        if (uniqueKey != null) {
            updateExistingFileToAwsS3(uniqueKey, multipartFile);
        } else {
            uploadFileToAwsS3(projDocFileEntity, multipartFile, uniqueKeyPrefix);
        }

    }

    public void updateExistingFileToAwsS3(String uniqueKey, MultipartFile multipartFile) throws IOException {
        awsAswS3FileService.updateExistingFile(multipartFile, uniqueKey);
    }

    public static String generateCode(ProjDocFileEntity projDocFileEntity) {
        ProjDocFolderEntity folder = projDocFileEntity.getFolderId();
        if (folder != null)
            /*return (ModuleCodesPrefixes.PROJ_DOC_PREFIX.getDesc() + "-" + folder.getProjId().getCode() + "-"
                    + AppUtils.formatNumberToString(projDocFileEntity.getId()));*/
        	return (ModuleCodesPrefixes.PROJ_DOC_PREFIX.getDesc() + "-" + AppUtils.formatNumberToString(projDocFileEntity.getId()));
        else
            return null;
    }

}
