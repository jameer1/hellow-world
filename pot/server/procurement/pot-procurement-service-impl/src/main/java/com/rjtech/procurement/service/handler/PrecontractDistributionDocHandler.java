package com.rjtech.procurement.service.handler;

import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.procurement.dto.PreContractCmpDistributionDocTO;
import com.rjtech.procurement.dto.PreContractDistributionDocTO;
import com.rjtech.procurement.model.PreContractCmpDistributionDocEntity;
import com.rjtech.procurement.model.PreContractDistributionDocEntity;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractDocEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.procurement.repository.PrecontractRepository;
import com.rjtech.procurement.repository.PrecontractDocRepository;
import com.rjtech.procurement.resp.PreContractDistributionDocResp;

public class PrecontractDistributionDocHandler {

    public static PreContractDistributionDocResp getPreContractDistributionDocTOs(
            List<PreContractDistributionDocEntity> preContractDocEntities,PrecontractDocRepository precontractDocRepository) {
        PreContractDistributionDocResp preContractDocResp = new PreContractDistributionDocResp();
        for (PreContractDistributionDocEntity preContractDocEntity : preContractDocEntities) {
        	String[] str = preContractDocEntity.getCode().split("-");
        	PreContractDocEntity preContractEntity = precontractDocRepository
    				.findDoc(Long.valueOf(str[(str.length)-1])); 
            preContractDocResp.getPreContractDistributionDocTOs().add(convertDocEntityToPOJO(preContractDocEntity,preContractEntity));
        }

        return preContractDocResp;
    }

    public static PreContractDistributionDocTO convertDocEntityToPOJO(
            PreContractDistributionDocEntity preContractDocEntity,PreContractDocEntity preContractEntity) {
        PreContractDistributionDocTO preContractDocsTO = new PreContractDistributionDocTO();
        preContractDocsTO.setId(preContractDocEntity.getId());
        PreContractEntity preContract = preContractDocEntity.getPreContractId();
        ProjDocFileEntity projDocFileEntity =  preContractEntity.getProjDocFileEntity();
        if (null != preContract) {
            preContractDocsTO.setPreContractId(preContract.getId());
        }
        preContractDocsTO.setDescription(projDocFileEntity.getDescription());
        preContractDocsTO.setFileSize(projDocFileEntity.getFileSize());
        preContractDocsTO.setName(preContractDocEntity.getName());
        preContractDocsTO.setMimeType(preContractDocEntity.getMimeType());
        preContractDocsTO.setModeType(preContractDocEntity.getModeType());
        preContractDocsTO.setVersion(preContractDocEntity.getVersion());
        preContractDocsTO.setDate(CommonUtil.convertDateToString(preContractDocEntity.getDate()));
        preContractDocsTO.setCode(preContractDocEntity.getCode());

        preContractDocsTO.setStatus(preContractDocEntity.getStatus());
        return preContractDocsTO;
    }

    public static PreContractDistributionDocEntity convertDocsPOJOToEntity(
            PreContractDistributionDocTO preContractDocsTO, PrecontractRepository precontractRepository) {
        PreContractDistributionDocEntity contractDocEntity = new PreContractDistributionDocEntity();
        if (CommonUtil.isNonBlankLong(preContractDocsTO.getId())) {
            contractDocEntity.setId(preContractDocsTO.getId());
        }
        contractDocEntity.setCode(preContractDocsTO.getCode());
        if (CommonUtil.isNotBlankStr(preContractDocsTO.getDate())) {
            contractDocEntity.setDate(CommonUtil.convertStringToDate(preContractDocsTO.getDate()));
        }
        contractDocEntity.setModeType(preContractDocsTO.getModeType());

        PreContractEntity preContractEntity = precontractRepository.findOne(preContractDocsTO.getPreContractId());
        if (null != preContractEntity) {
            contractDocEntity.setPreContractId(preContractEntity);
        }

        contractDocEntity.setName(preContractDocsTO.getName());
        contractDocEntity.setMimeType(preContractDocsTO.getMimeType());
        contractDocEntity.setVersion(preContractDocsTO.getVersion());

        contractDocEntity.setStatus(preContractDocsTO.getStatus());
        return contractDocEntity;
    }

    public static PreContractCmpDistributionDocTO convertCmpDistributionDocEntityToPOJO(
            PreContractCmpDistributionDocEntity preContractCmpDistributionDocEntity) {
        PreContractCmpDistributionDocTO preContractCmpDistributionDocStatusTO = new PreContractCmpDistributionDocTO();
        PreContractsCmpEntity preContractsCmpEntity = preContractCmpDistributionDocEntity.getPreContractCmpId();
        preContractCmpDistributionDocStatusTO.setId(preContractCmpDistributionDocEntity.getId());
        if (null != preContractsCmpEntity) {
            preContractCmpDistributionDocStatusTO.setPreContractCmpId(preContractsCmpEntity.getId());
        }
        if (CommonUtil.objectNotNull(preContractCmpDistributionDocEntity.getPreContractDistributionDocEntity())) {
            preContractCmpDistributionDocStatusTO.setDistributionStatusId(
                    preContractCmpDistributionDocEntity.getPreContractDistributionDocEntity().getId());
        }
        preContractCmpDistributionDocStatusTO.setTransmit(preContractCmpDistributionDocEntity.isTransmit());
        preContractCmpDistributionDocStatusTO.setStatus(preContractCmpDistributionDocEntity.getStatus());
        return preContractCmpDistributionDocStatusTO;
    }

}
