package com.rjtech.projectlib.service.handler;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjStoreStockTO;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;

public class ProjStoreStockHandler {

    public static ProjStoreStockTO convertEntityToPOJO(ProjStoreStockMstrEntity entity) {
        ProjStoreStockTO projStoreStockTO = null;
        projStoreStockTO = new ProjStoreStockTO();
        projStoreStockTO.setId(entity.getId());
        projStoreStockTO.setCode(entity.getCode());
        projStoreStockTO.setDesc(entity.getDesc());
        projStoreStockTO.setCategory(entity.getCategory());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity()))
            projStoreStockTO.setProjId(entity.getProjMstrEntity().getProjectId());
        projStoreStockTO.setStatus(entity.getStatus());
        return projStoreStockTO;
    }

    public static ProjStoreStockMstrEntity convertPOJOToEntity(ProjStoreStockTO projStoreStockTO,
            EPSProjRepository projRepository) {
        ProjStoreStockMstrEntity entity = null;
        entity = new ProjStoreStockMstrEntity();
        if (CommonUtil.isNonBlankLong(projStoreStockTO.getId())) {
            entity.setId(projStoreStockTO.getId());
        }

        ProjMstrEntity projMstrEntity = projRepository.findOne(projStoreStockTO.getProjId());
        entity.setProjMstrEntity(projMstrEntity);

        entity.setCode(projStoreStockTO.getCode());
        entity.setDesc(projStoreStockTO.getDesc());
        entity.setCategory(projStoreStockTO.getCategory());
        entity.setStatus(projStoreStockTO.getStatus());
        return entity;
    }

}
