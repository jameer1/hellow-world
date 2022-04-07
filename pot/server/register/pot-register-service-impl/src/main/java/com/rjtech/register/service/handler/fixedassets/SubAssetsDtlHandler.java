package com.rjtech.register.service.handler.fixedassets;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.AssetCategoryMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.model.ProfitCentreEntity;
//import com.rjtech.finance.model.ProfitCentreEntityCopy;
import com.rjtech.register.fixedassets.dto.SubAssetDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.SubAssetDtlEntity;
import com.rjtech.register.fixedassets.req.SubAssetsSaveReq;

public class SubAssetsDtlHandler {

    public static SubAssetDtlTO convertEntityToPOJO(SubAssetDtlEntity entity) {

        SubAssetDtlTO subAssetDtlTO = new SubAssetDtlTO();
        subAssetDtlTO.setSubid(entity.getSubid());
        subAssetDtlTO.setSubAssetId(entity.getSubAssetId());
        subAssetDtlTO.setSubAssetDescription(entity.getSubAssetDescription());
        subAssetDtlTO.setSubAssetCategory(entity.getSubAssetCategory());
        subAssetDtlTO.setYearBuild(CommonUtil.convertDateToString(entity.getYearBuild()));
        subAssetDtlTO.setDateCommissioned(CommonUtil.convertDateToString(entity.getDateCommissioned()));
        subAssetDtlTO.setProfitCenterId(entity.getProfitCentreEntity().getId());
        subAssetDtlTO.setAssetCategoryId(entity.getAssetCategoryMstrEntity().getId());
        subAssetDtlTO.setFixedAssetId(entity.getFixedAssetsRegisterDtlEntity().getFixedAssetid());

        ClientRegEntity clientRegEntity = entity.getClientRegEntity();
        if (null != clientRegEntity) {
            entity.setClientRegEntity(clientRegEntity);
        }

        FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();
        if (null != fixedAssetsRegisterDtlEntity) {
            entity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }
        ProfitCentreEntity profitCentreEntity = entity.getProfitCentreEntity();
        if (null != profitCentreEntity) {
            entity.setProfitCentreEntity(profitCentreEntity);
        }
        AssetCategoryMstrEntity assetCategoryMstrEntity = entity.getAssetCategoryMstrEntity();
        if (null != assetCategoryMstrEntity) {
            entity.setAssetCategoryMstrEntity(assetCategoryMstrEntity);
        }

        return subAssetDtlTO;
    }

    public static List<SubAssetDtlEntity> populateEntitisFromPOJO(SubAssetsSaveReq subAssetsSaveReq) {

        List<SubAssetDtlEntity> entities = new ArrayList<>();
        SubAssetDtlEntity entity = null;

        for (SubAssetDtlTO subAssetDtlTO : subAssetsSaveReq.getSubAssetDtlTO()) {
            entity = new SubAssetDtlEntity();
            converSubAssetPOJOToEntity(entity, subAssetDtlTO);
            entities.add(entity);

        }
        return entities;
    }

    private static SubAssetDtlEntity converSubAssetPOJOToEntity(SubAssetDtlEntity entity, SubAssetDtlTO subAssetDtlTO) {

        if (CommonUtil.isNonBlankLong(subAssetDtlTO.getSubid())) {
            entity.setSubid(subAssetDtlTO.getSubid());
        }
        entity.setSubAssetId(subAssetDtlTO.getSubAssetId());
        entity.setSubAssetDescription(subAssetDtlTO.getSubAssetDescription());
        entity.setSubAssetCategory(subAssetDtlTO.getSubAssetCategory());
        entity.setYearBuild(CommonUtil.convertDDMMYYYYStringToDate(subAssetDtlTO.getYearBuild()));
        entity.setDateCommissioned(CommonUtil.convertDDMMYYYYStringToDate(subAssetDtlTO.getDateCommissioned()));

        return entity;

    }

    public static SubAssetDtlEntity convertPOJOToEntity(SubAssetDtlEntity entity, SubAssetDtlTO subAssetDtlTO) {

        if (CommonUtil.isNonBlankLong(subAssetDtlTO.getSubid())) {
            entity.setSubid(subAssetDtlTO.getSubid());
        }
        entity.setSubAssetId(subAssetDtlTO.getSubAssetId());
        entity.setSubAssetDescription(subAssetDtlTO.getSubAssetDescription());
        entity.setSubAssetCategory(subAssetDtlTO.getSubAssetCategory());
        entity.setYearBuild(CommonUtil.convertDDMMYYYYStringToDate(subAssetDtlTO.getYearBuild()));
        entity.setDateCommissioned(CommonUtil.convertDDMMYYYYStringToDate(subAssetDtlTO.getDateCommissioned()));
        entity.setStatus(subAssetDtlTO.getStatus());

        if (CommonUtil.isNonBlankLong(subAssetDtlTO.getAssetCategoryId())) {
            AssetCategoryMstrEntity assetCategoryMstrEntity = new AssetCategoryMstrEntity();
            assetCategoryMstrEntity.setId(subAssetDtlTO.getAssetCategoryId());
            entity.setAssetCategoryMstrEntity(assetCategoryMstrEntity);
        }

        if (CommonUtil.isNonBlankLong(subAssetDtlTO.getProfitCenterId())) {
            ProfitCentreEntity profitCentreEntity = new ProfitCentreEntity();
            profitCentreEntity.setId(subAssetDtlTO.getProfitCenterId());
            entity.setProfitCentreEntity(profitCentreEntity);
        }

        if (CommonUtil.isNonBlankLong(subAssetDtlTO.getFixedAssetId())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = new FixedAssetsRegisterDtlEntity();
            fixedAssetsRegisterDtlEntity.setFixedAssetid(subAssetDtlTO.getFixedAssetId());
            entity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }

        return entity;
    }

}
