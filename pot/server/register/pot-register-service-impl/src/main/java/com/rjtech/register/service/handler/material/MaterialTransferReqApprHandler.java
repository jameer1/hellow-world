package com.rjtech.register.service.handler.material;

import java.util.Date;

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.StockRepository;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
//import com.rjtech.register.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.material.dto.MaterialTransferReqApprTO;
import com.rjtech.register.material.model.MaterialNotificationsEntity;
import com.rjtech.register.material.model.MaterialTransferReqApprEntity;
import com.rjtech.register.service.handler.emp.EmpRegisterDtlHandler;

public class MaterialTransferReqApprHandler {

    public static MaterialTransferReqApprTO convertEntityToPOJO(MaterialTransferReqApprEntity entity) {

        MaterialTransferReqApprTO materialTransferReqTO = new MaterialTransferReqApprTO();
        materialTransferReqTO.setId(entity.getId());
        ProjMstrEntity projFromMstrEntity = entity.getFromProjId();
        if (null != projFromMstrEntity) {
            materialTransferReqTO.setFromProjId(projFromMstrEntity.getProjectId());
            materialTransferReqTO.setFromProjName(projFromMstrEntity.getProjName());
        }
        ProjMstrEntity projToMstrEntity = entity.getToProjId();
        if (null != projToMstrEntity) {
            materialTransferReqTO.setToProjId(projToMstrEntity.getProjectId());
            materialTransferReqTO.setToProjName(projToMstrEntity.getProjName());
        }

        StockMstrEntity stockFromMstrEntity = entity.getFromStoreId();
        ProjStoreStockMstrEntity projStoreStockFromMstrEntity = entity.getFromStoreProjectId();

        if ( stockFromMstrEntity != null ) {
            materialTransferReqTO.setFromStoreName(stockFromMstrEntity.getName());
            materialTransferReqTO.setFromStoreId(stockFromMstrEntity.getId());
        } else {
            materialTransferReqTO.setFromStoreName(projStoreStockFromMstrEntity.getCode());
            materialTransferReqTO.setFromStoreProjectId(projStoreStockFromMstrEntity.getId());
        }

        StockMstrEntity stockToMstrEntity = entity.getToStoreId();
        ProjStoreStockMstrEntity projStoreStockToMstrEntity = entity.getToStoreProjectId();

        if ( stockToMstrEntity != null ) {
            materialTransferReqTO.setToStoreName(stockToMstrEntity.getName());
            materialTransferReqTO.setToStoreId(stockToMstrEntity.getId());
        } else {
            materialTransferReqTO.setToStoreName(projStoreStockToMstrEntity.getCode());
            materialTransferReqTO.setToStoreProjectId(projStoreStockToMstrEntity.getId());
        }

        if (CommonUtil.objectNotNull(entity.getReqUserEntity())) {
            materialTransferReqTO
                    .setReqUserTO(EmpRegisterDtlHandler.convertUserEntityToPOJO(entity.getReqUserEntity()));
        }

        if (CommonUtil.objectNotNull(entity.getApprUserEntity())) {
            materialTransferReqTO
                    .setApprUserTO(EmpRegisterDtlHandler.convertUserEntityToPOJO(entity.getApprUserEntity()));
        }
        MaterialNotificationsEntity materialNotificationEntity = entity.getMaterialNotificationsEntity();
        if (CommonUtil.objectNotNull(materialNotificationEntity)) {
            materialTransferReqTO.setNotifyId(materialNotificationEntity.getId());
            materialTransferReqTO.setNotifyCode(ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                    + entity.getToProjId().getCode() + "-"
                    + materialNotificationEntity.getId());
        }
        materialTransferReqTO.setReqCode(ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" 
                + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                + entity.getToProjId().getCode() + "-"
                + entity.getId());
        if (entity.getApprStatus().equals(RegisterConstants.APPROVED))
            materialTransferReqTO.setApprCode(ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc() + "-" 
                    + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                    + entity.getToProjId().getCode() + "-"
                    + entity.getId());
        materialTransferReqTO.setReqComments(entity.getReqComments());
        materialTransferReqTO.setApprComments(entity.getApprComments());
        materialTransferReqTO.setApprStatus(entity.getApprStatus());
        if (CommonUtil.isNotBlankDate(entity.getReqDate())) {
            materialTransferReqTO.setReqDate(CommonUtil.convertDateToString(entity.getReqDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getReqExpiryDate())) {
            materialTransferReqTO.setReqExpiryDate(CommonUtil.convertDateToString(entity.getReqExpiryDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getApprDate())) {
            materialTransferReqTO.setApprDate(CommonUtil.convertDateToString(entity.getApprDate()));
        }

        return materialTransferReqTO;
    }

    public static MaterialTransferReqApprEntity convertPOJOToEntity(MaterialTransferReqApprTO materialTransferReqTO,
            EPSProjRepository epsProjRepository, StockRepository stockRepository,  ProjStoreStockRepositoryCopy projStoreStockRepository) {
        MaterialTransferReqApprEntity entity = new MaterialTransferReqApprEntity();

        if (CommonUtil.isNonBlankLong(materialTransferReqTO.getId())) {
            entity.setId(materialTransferReqTO.getId());
        }
        ProjMstrEntity projFromMstrEntity = epsProjRepository.findOne(materialTransferReqTO.getFromProjId());
        if (null != projFromMstrEntity) {
            entity.setFromProjId(projFromMstrEntity);
        }
        ProjMstrEntity projToMstrEntity = epsProjRepository.findOne(materialTransferReqTO.getToProjId());
        if (null != projToMstrEntity) {
            entity.setToProjId(projToMstrEntity);
        }
        
        if ( materialTransferReqTO.getFromStoreId() != null ) {
            StockMstrEntity stockFromMstrEntity = stockRepository.findOne(materialTransferReqTO.getFromStoreId());
            entity.setFromStoreId( stockFromMstrEntity );
        }else if( materialTransferReqTO.getFromStoreProjectId() != null ){
            ProjStoreStockMstrEntity projStoreStockFromMstrEntity = projStoreStockRepository.findOne(materialTransferReqTO.getFromStoreProjectId());
            entity.setFromStoreProjectId( projStoreStockFromMstrEntity );
        }
        if (materialTransferReqTO.getToStoreId() != null) {
            StockMstrEntity stockToMstrEntity = stockRepository.findOne(materialTransferReqTO.getToStoreId());
            entity.setToStoreId(stockToMstrEntity);
        }else if( materialTransferReqTO.getToStoreProjectId() != null ){
            ProjStoreStockMstrEntity projStoreStockToMstrEntity = projStoreStockRepository.findOne(materialTransferReqTO.getToStoreProjectId());
            entity.setToStoreProjectId( projStoreStockToMstrEntity );
        }

        if (CommonUtil.objectNotNull(materialTransferReqTO.getReqUserTO())) {
            UserMstrEntity reqUserEntity = new UserMstrEntity();
            reqUserEntity.setUserId(materialTransferReqTO.getReqUserTO().getUserId());
            entity.setReqUserEntity(reqUserEntity);
        }

        if (CommonUtil.objectNotNull(materialTransferReqTO.getApprUserTO())) {
            UserMstrEntity apprUserEntity = new UserMstrEntity();
            apprUserEntity.setUserId(materialTransferReqTO.getApprUserTO().getUserId());
            entity.setApprUserEntity(apprUserEntity);
        }
        if (CommonUtil.objectNotNull(materialTransferReqTO.getNotifyId())) {
            MaterialNotificationsEntity materialNotificationsEntity = new MaterialNotificationsEntity();
            materialNotificationsEntity.setId(materialTransferReqTO.getNotifyId());
            entity.setMaterialNotificationsEntity(materialNotificationsEntity);
        }

        entity.setReqCode(materialTransferReqTO.getReqCode());
        entity.setApprCode(materialTransferReqTO.getApprCode());

        entity.setReqComments(materialTransferReqTO.getReqComments());
        entity.setApprComments(materialTransferReqTO.getApprComments());
        entity.setApprStatus(materialTransferReqTO.getApprStatus());

        if (CommonUtil.isBlankLong(materialTransferReqTO.getId())) {
            entity.setReqDate(new Date());
        } else {
            if (CommonUtil.isNotBlankStr(materialTransferReqTO.getReqDate()))
                entity.setReqDate(CommonUtil.convertStringToDate(materialTransferReqTO.getReqDate()));
        }
        if (CommonUtil.isNotBlankStr(materialTransferReqTO.getReqExpiryDate())) {
            entity.setReqExpiryDate(CommonUtil.convertStringToDate(materialTransferReqTO.getReqExpiryDate()));
        }
        if (CommonConstants.APPR_STATUS_APPROVED.equalsIgnoreCase(materialTransferReqTO.getApprStatus())) {
            entity.setApprDate(CommonUtil.convertStringToDate(materialTransferReqTO.getApprDate()));
        }

        entity.setStatus(materialTransferReqTO.getStatus());

        return entity;
    }
}
