package com.rjtech.procurement.service.handler;

import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.procurement.dto.PreContractCmpDocsTO;
import com.rjtech.procurement.model.PreContractCmpDocEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.resp.PreContractCmpDocResp;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.procurement.model.PreContractDistributionDocEntity;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.DocumentTransmittalMessageEntity;
import com.rjtech.procurement.model.PreContractDocEntity;
import com.rjtech.document.service.handler.ProjDocFileHandler;

public class PrecontractCmpDocHandler {

    public static PreContractCmpDocResp getPreContractCmpDocs(List<PreContractCmpDocEntity> preContractCmpDocEntities) {
        PreContractCmpDocResp preContractCmpDocResp = new PreContractCmpDocResp();
        for (PreContractCmpDocEntity preContractCmpDocEntity : preContractCmpDocEntities) {
            preContractCmpDocResp.getPreContractCmpDocsTOs().add(convertCmpDocsEntityToPOJO(preContractCmpDocEntity));
        }

        return preContractCmpDocResp;
    }
    
    public static PreContractCmpDocResp getPreContractCmpDocsvendor(List<PreContractDocEntity> preContractEntites,
    		List<DocumentTransmittalMessageEntity> documentTransmittalMessageEntity,List<PreContractDistributionDocEntity> preContractDistributionDocEntity) {
    	PreContractCmpDocResp preContractCmpDocResp = new PreContractCmpDocResp();
    	PreContractCmpDocsTO preContractCmpDocsTO = new PreContractCmpDocsTO();
    	for(PreContractDocEntity preContractDistributionDoc: preContractEntites) {
    		preContractCmpDocsTO.setId(preContractDistributionDoc.getId());
          //  PreContractsCmpEntity preContractsCmpEntity = preContractCmpDocEntity.getPreContractsCmpEntity();
    	//	PreContractEntity preContractEntity = preContractDistributionDoc.getPreContractId();
            preContractCmpDocsTO.setPreContractCmpId(preContractDistributionDoc.getPreContractId().getId());
            ProjDocFileEntity projDocFile = preContractDistributionDoc.getProjDocFileEntity();
            if (projDocFile != null) {
            preContractCmpDocsTO.setCode(ProjDocFileHandler.generateCode(projDocFile));
            preContractCmpDocsTO.setName(projDocFile.getName());
            preContractCmpDocsTO.setModeType(preContractDistributionDocEntity.get(0).getModeType());
            preContractCmpDocsTO.setMimeType(projDocFile.getFileType());
            preContractCmpDocsTO.setVersion(projDocFile.getVersion());
            preContractCmpDocsTO.setFileSize(projDocFile.getFileSize());
            preContractCmpDocsTO.setDescription(projDocFile.getDescription());
            }
            preContractCmpDocsTO.setDate(CommonUtil.convertDateToString(preContractDistributionDoc.getCreatedOn()));
            preContractCmpDocsTO.setSender(documentTransmittalMessageEntity.get(0).getName());
            preContractCmpDocsTO.setReceiver(documentTransmittalMessageEntity.get(0).getVendorName());
          //  preContractCmpDocsTO.setFromVendor(preContractDistributionDoc.isFromVendor());
          //  preContractCmpDocsTO.setStatus(preContractDistributionDoc.getStatus());
            preContractCmpDocResp.getPreContractCmpDocsTOs().add(preContractCmpDocsTO);
    	}
    	return preContractCmpDocResp;
    }
    
    public static PreContractCmpDocsTO convertCmpDocsEntityToPOJO(PreContractCmpDocEntity preContractCmpDocEntity) {
        PreContractCmpDocsTO preContractCmpDocsTO = new PreContractCmpDocsTO();
        preContractCmpDocsTO.setId(preContractCmpDocEntity.getId());
        PreContractsCmpEntity preContractsCmpEntity = preContractCmpDocEntity.getPreContractsCmpEntity();
        preContractCmpDocsTO.setPreContractCmpId(preContractsCmpEntity.getId());
        preContractCmpDocsTO.setCode(ModuleCodesPrefixes.PRE_CONTRACT_CMP_DOC_PREFIX.getDesc() + "-"
                + preContractsCmpEntity.getPreContractEntity().getProjId().getCode() + "-"
                + preContractCmpDocEntity.getId());
        preContractCmpDocsTO.setName(preContractCmpDocEntity.getName());
        if (CommonUtil.isNotBlankDate(preContractCmpDocEntity.getDate())) {
            preContractCmpDocsTO.setDate(CommonUtil.convertDateToString(preContractCmpDocEntity.getDate()));
        }
        preContractCmpDocsTO.setModeType(preContractCmpDocEntity.getModeType());
        preContractCmpDocsTO.setMimeType(preContractCmpDocEntity.getMimeType());
        preContractCmpDocsTO.setVersion(preContractCmpDocEntity.getVersion());
        preContractCmpDocsTO.setSender(preContractCmpDocEntity.getSender());
        preContractCmpDocsTO.setReceiver(preContractCmpDocEntity.getReceiver());
        preContractCmpDocsTO.setFromVendor(preContractCmpDocEntity.isFromVendor());
        preContractCmpDocsTO.setStatus(preContractCmpDocEntity.getStatus());

        return preContractCmpDocsTO;
    }

    public static PreContractCmpDocEntity convertCmpDocsPOJOToEntity(PreContractCmpDocsTO contractCmpDocsTO, ProjDocFileEntity projDocFileEntity) {
        PreContractCmpDocEntity contractCmpDocEntity = new PreContractCmpDocEntity();
        if (CommonUtil.isNonBlankLong(contractCmpDocsTO.getId())) {
            contractCmpDocEntity.setId(contractCmpDocsTO.getId());
        }
        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();
        preContractsCmpEntity.setId(contractCmpDocsTO.getPreContractCmpId());
        contractCmpDocEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        if (CommonUtil.isNotBlankStr(contractCmpDocsTO.getDate())) {
            contractCmpDocEntity.setDate(CommonUtil.convertStringToDate(contractCmpDocsTO.getDate()));
        }
        contractCmpDocEntity.setModeType(contractCmpDocsTO.getModeType());
        contractCmpDocEntity.setName(contractCmpDocsTO.getName());
        contractCmpDocEntity.setSender(contractCmpDocsTO.getSender());
        contractCmpDocEntity.setReceiver(contractCmpDocsTO.getReceiver());
        contractCmpDocEntity.setMimeType(contractCmpDocsTO.getMimeType());
        contractCmpDocEntity.setVersion(contractCmpDocsTO.getVersion());
        contractCmpDocEntity.setFromVendor(contractCmpDocsTO.isFromVendor());
        contractCmpDocEntity.setStatus(contractCmpDocsTO.getStatus());
        contractCmpDocEntity.setProjDocFileEntity( projDocFileEntity );
        return contractCmpDocEntity;
    }

    public static ProjDocFileEntity convertPOJOToProjDocFileEntity(ProjDocFileTO projDocFileTO) {
    	ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
    	
    	ProjDocFolderEntity projDocFolderEntity = new ProjDocFolderEntity();
    	projDocFolderEntity.setId( projDocFileTO.getFolderId() );
    	
    	ProjMstrEntity projMstrEntity = new ProjMstrEntity();
    	projMstrEntity.setProjectId( projDocFileTO.getProjId() );
    	
    	UserMstrEntity userMstrEntity = new UserMstrEntity();
    	userMstrEntity.setUserId( projDocFileTO.getUserId() );
    	
    	projDocFileEntity.setName( projDocFileTO.getName() );
		//projDocFileTO.setVersion(preContractCmpDocsTO.getVersion());
    	projDocFileEntity.setFileType( projDocFileTO.getFileType() );
    	projDocFileEntity.setFileSize( projDocFileTO.getFileSize() );
    	projDocFileEntity.setStatus( projDocFileTO.getStatus() );
    	projDocFileEntity.setProjectId( projMstrEntity );
    	projDocFileEntity.setFolderId( projDocFolderEntity );
    	projDocFileEntity.setDescription( projDocFileTO.getDescription() );
    	projDocFileEntity.setFolderPath( projDocFileTO.getFolderPath() );
    	projDocFileEntity.setCreatedBy( userMstrEntity );
    	projDocFileEntity.setUpdatedBy( userMstrEntity );
        return projDocFileEntity;
    }
}
