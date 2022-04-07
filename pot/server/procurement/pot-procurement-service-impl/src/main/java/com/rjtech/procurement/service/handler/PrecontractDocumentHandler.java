package com.rjtech.procurement.service.handler;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

import com.rjtech.common.constants.AwsS3FileKeyConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.service.handler.ProjDocFileHandler;
import com.rjtech.procurement.dto.PreContractDocsTO;
import com.rjtech.procurement.model.PreContractDocEntity;
import com.rjtech.procurement.repository.PrecontractDocRepository;
import com.rjtech.procurement.repository.PrecontractRepository;
import com.rjtech.procurement.resp.PreContractDocResp;
import com.rjtech.common.utils.UploadUtil;

import com.rjtech.procurement.model.PreContractCmpDocEntity;
import com.rjtech.procurement.dto.PreContractCmpDocsTO;

@Component
public class PrecontractDocumentHandler {

    @Autowired
    private ProjDocFileHandler projDocFileHandler;
    
    @Autowired
    private UploadUtil uploadUtil;

    public PreContractDocResp getPreContractDocs(List<PreContractDocEntity> preContractDocEntities) {
        PreContractDocResp preContractDocResp = new PreContractDocResp();
        for (PreContractDocEntity preContractDocEntity : preContractDocEntities) {
            preContractDocResp.getPreContractDocsTOs().add(convertDocsEntityToPOJO(preContractDocEntity));
        }

        return preContractDocResp;
    }

    public PreContractDocsTO convertDocsEntityToPOJO(PreContractDocEntity preContractDocEntity) {
        PreContractDocsTO preContractDocsTO = new PreContractDocsTO();
        preContractDocsTO.setId(preContractDocEntity.getId());
        preContractDocsTO.setPreContractId(preContractDocEntity.getPreContractId().getId());
        ProjDocFileEntity projDocFile = preContractDocEntity.getProjDocFileEntity();
        if (projDocFile != null) {
            preContractDocsTO.setName(projDocFile.getName());
            preContractDocsTO.setFileType(projDocFile.getFileType());
            preContractDocsTO.setVersion(projDocFile.getVersion());
            preContractDocsTO.setCode(ProjDocFileHandler.generateCode(projDocFile));
            preContractDocsTO.setFileSize(projDocFile.getFileSize());
            preContractDocsTO.setDescription(projDocFile.getDescription());
            preContractDocsTO.setProjDocFileTOId(projDocFile.getId());
            preContractDocsTO.setUniqueKey(projDocFile.getUniqueKey());
        }
        preContractDocsTO.setDate(CommonUtil.convertDateToString(preContractDocEntity.getCreatedOn()));
        preContractDocsTO.setStatus(preContractDocEntity.getStatus());
        return preContractDocsTO;
    }

    public PreContractDocEntity convertDocsPOJOToEntity(PreContractDocsTO preContractDocsTO,
            PrecontractDocRepository precontractDocRepository, PrecontractRepository precontractRepository) {
        PreContractDocEntity contractDocEntity = null;
        if (CommonUtil.isNonBlankLong(preContractDocsTO.getId())) {
            contractDocEntity = precontractDocRepository.findOne(preContractDocsTO.getId());
        } else {
            contractDocEntity = new PreContractDocEntity();
        }
        if (preContractDocsTO.getProjDocFileTO() != null) {
            ProjDocFileEntity projDocFile = projDocFileHandler
                    .convertPOJOToEntity(preContractDocsTO.getProjDocFileTO());
            contractDocEntity.setProjDocFileEntity(projDocFile);
        }
        contractDocEntity.setStatus(preContractDocsTO.getStatus());
        contractDocEntity.setPreContractId(precontractRepository.findOne(preContractDocsTO.getPreContractId()));
        return contractDocEntity;
    }

    public void uploadPreconreactDocsToAwsS3(PreContractDocEntity preContractDocEntity,
            PreContractDocsTO preContractDocsTO) throws IOException {
        String uniqueKey = preContractDocsTO.getUniqueKey();
        ProjDocFileTO docFileTo = preContractDocsTO.getProjDocFileTO();
        if (docFileTo != null) {
            if (uniqueKey != null) {
                projDocFileHandler.updateExistingFileToAwsS3(uniqueKey, docFileTo.getMultipartFile());
            } else {
                projDocFileHandler.uploadFileToAwsS3(preContractDocEntity.getProjDocFileEntity(),
                        docFileTo.getMultipartFile(), AwsS3FileKeyConstants.PRECONTRACT_REFERENCE_DOCUMENT);
            }
        }
    }
    
    public void uploadPrecontractDocsToServer( String category, String base_directory, Long projId, PreContractDocsTO preContractDocsTO, PreContractCmpDocsTO preContractCmpDocsTO ) throws IOException
    {
    	//String uniqueKey = preContractCmpDocsTO.getUniqueKey();
    	ProjDocFileTO docFileTo = null;
        if( category.equals("From Vendor") )
        {
        	docFileTo = preContractCmpDocsTO.getProjDocFileTO();
        }
        else if( category.equals("Reference Documents") )
        {
        	docFileTo = preContractDocsTO.getProjDocFileTO();
        }
        String file_path = "/"+base_directory+"/"+String.valueOf(projId);
        System.out.println("directory:"+file_path);
        System.out.println(docFileTo.getName());
        /*File theDir = new File(file_path);
		if (!theDir.exists()){
			System.out.println("not exists block");
		    theDir.mkdirs();
		}
        if (docFileTo != null) {
        	System.out.println("if condition of uploadPrecontractCmpDocsToServer function");
        	uploadUtil.storeFile( docFileTo.getMultipartFile() , file_path );
        }*/        
    }
}
