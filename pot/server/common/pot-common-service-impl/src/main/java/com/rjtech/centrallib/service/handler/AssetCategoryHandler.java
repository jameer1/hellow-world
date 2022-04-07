package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AssetCategoryTO;
import com.rjtech.centrallib.model.AssetCategoryMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;

public class AssetCategoryHandler {
    public static AssetCategoryTO populateAssetItems(AssetCategoryMstrEntity assetCategoryMstrEntity,
            boolean addChild) {
        AssetCategoryTO assetCategoryTO = null;
        if (CommonUtil.objectNotNull(assetCategoryMstrEntity)) {
            assetCategoryTO = new AssetCategoryTO();
            assetCategoryTO.setId(assetCategoryMstrEntity.getId());
            assetCategoryTO.setCode(assetCategoryMstrEntity.getCode());
            assetCategoryTO.setName(assetCategoryMstrEntity.getName());
            ClientRegEntity client = assetCategoryMstrEntity.getClientId();
            if (client != null) {
                assetCategoryTO.setClientId(client.getClientId());
            }

            if (assetCategoryMstrEntity.isItem()) {
                assetCategoryTO.setItem(true);
            } else {
                assetCategoryTO.setItem(false);
            }
            AssetCategoryMstrEntity parent = assetCategoryMstrEntity.getAssetCategoryMstrEntity();
            if (CommonUtil.objectNotNull(parent)) {
                assetCategoryTO.setParentName(parent.getName());
                assetCategoryTO.setParentId(parent.getId());
            }
            assetCategoryTO.setStatus(assetCategoryMstrEntity.getStatus());

            if (addChild) {
                if (CommonUtil.objectNotNull(assetCategoryTO.getChildAssetCategoryTOs())) {
                    assetCategoryTO.getChildAssetCategoryTOs()
                            .addAll(addChilds(assetCategoryMstrEntity, assetCategoryTO, addChild));
                }
            }
        }
        return assetCategoryTO;
    }

    public static List<AssetCategoryTO> addChilds(AssetCategoryMstrEntity assetCategoryMstrEntity,
            AssetCategoryTO assetCategoryTO, boolean addChild) {
        List<AssetCategoryTO> assetCategoryTOs = new ArrayList<AssetCategoryTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (assetCategoryMstrEntity.getAssetCategoryMstrEntity() != null) {
            for (AssetCategoryMstrEntity childEntity : assetCategoryMstrEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    assetCategoryTOs.add(populateAssetItems(childEntity, addChild));
                }
            }
        } else {
            for (AssetCategoryMstrEntity childEntity : assetCategoryMstrEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    assetCategoryTOs.add(populateAssetItems(childEntity, addChild));
                }
            }
        }
        return assetCategoryTOs;
    }

    public static List<AssetCategoryMstrEntity> populateEntitiesFromPOJO(List<AssetCategoryTO> assetCategoryTOs) {
        List<AssetCategoryMstrEntity> assetCategoryMstrEntities = new ArrayList<AssetCategoryMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        AssetCategoryMstrEntity entity = null;

        for (AssetCategoryTO assetCategoryTO : assetCategoryTOs) {
            entity = new AssetCategoryMstrEntity();
            convertPOJOToEntity(entity, assetCategoryTO);
            assetCategoryMstrEntities.add(entity);
        }
        return assetCategoryMstrEntities;
    }

    public static AssetCategoryMstrEntity convertPOJOToEntity(AssetCategoryMstrEntity assetCategoryMstrEntity,
            AssetCategoryTO assetCategoryTO) {

        if (CommonUtil.isNonBlankLong(assetCategoryTO.getId())) {
            assetCategoryMstrEntity.setId(assetCategoryTO.getId());
        }
        assetCategoryMstrEntity.setCode(assetCategoryTO.getCode());
        assetCategoryMstrEntity.setName(assetCategoryTO.getName());
        assetCategoryMstrEntity.setItem(assetCategoryTO.isItem());
        if (CommonUtil.isNonBlankLong(assetCategoryTO.getParentId())) {
            AssetCategoryMstrEntity parentEntity = new AssetCategoryMstrEntity();
            parentEntity.setId(assetCategoryTO.getParentId());
            assetCategoryMstrEntity.setAssetCategoryMstrEntity(parentEntity);
        }
        assetCategoryMstrEntity.setStatus(assetCategoryTO.getStatus());
        AssetCategoryMstrEntity childEntity = null;

        for (AssetCategoryTO childTO : assetCategoryTO.getChildAssetCategoryTOs()) {
            childEntity = new AssetCategoryMstrEntity();
            childEntity.setAssetCategoryMstrEntity(assetCategoryMstrEntity);
            assetCategoryMstrEntity.getChildEntities().add(convertPOJOToEntity(childEntity, childTO));
        }
        return assetCategoryMstrEntity;
    }

}
