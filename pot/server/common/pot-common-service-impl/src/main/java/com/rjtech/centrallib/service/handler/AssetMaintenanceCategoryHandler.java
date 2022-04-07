package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AssetMaintenanceCategoryTO;
import com.rjtech.centrallib.model.AssetMaintenanceCategoryMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;

public class AssetMaintenanceCategoryHandler {
    public static AssetMaintenanceCategoryTO populateAssetMaintenanceItems(
            AssetMaintenanceCategoryMstrEntity assetMaintenanceCategoryMstrEntity, boolean addChild) {
        AssetMaintenanceCategoryTO assetMaintenanceCategoryTO = null;
        if (CommonUtil.objectNotNull(assetMaintenanceCategoryMstrEntity)) {
            assetMaintenanceCategoryTO = new AssetMaintenanceCategoryTO();
            assetMaintenanceCategoryTO.setId(assetMaintenanceCategoryMstrEntity.getId());
            assetMaintenanceCategoryTO.setCode(assetMaintenanceCategoryMstrEntity.getCode());
            assetMaintenanceCategoryTO.setName(assetMaintenanceCategoryMstrEntity.getName());
            ClientRegEntity client = assetMaintenanceCategoryMstrEntity.getClientId();
            if (client != null) {
                assetMaintenanceCategoryTO.setClientId(client.getClientId());
            }

            if (assetMaintenanceCategoryMstrEntity.isItem()) {
                assetMaintenanceCategoryTO.setItem(true);
            } else {
                assetMaintenanceCategoryTO.setItem(false);
            }
            AssetMaintenanceCategoryMstrEntity parent = assetMaintenanceCategoryMstrEntity
                    .getAssetMaintenanceCategoryMstrEntity();
            if (CommonUtil.objectNotNull(parent)) {
                assetMaintenanceCategoryTO.setParentName(parent.getName());
                assetMaintenanceCategoryTO.setParentId(parent.getId());
            }
            assetMaintenanceCategoryTO.setStatus(assetMaintenanceCategoryMstrEntity.getStatus());

            if (addChild) {
                if (CommonUtil.objectNotNull(assetMaintenanceCategoryTO.getChildAssetMaintenanceCategoryTOs())) {
                    assetMaintenanceCategoryTO.getChildAssetMaintenanceCategoryTOs().addAll(
                            addChilds(assetMaintenanceCategoryMstrEntity, assetMaintenanceCategoryTO, addChild));
                }
            }
        }
        return assetMaintenanceCategoryTO;
    }

    public static List<AssetMaintenanceCategoryTO> addChilds(
            AssetMaintenanceCategoryMstrEntity assetMaintenanceCategoryMstrEntity,
            AssetMaintenanceCategoryTO assetMaintenanceCategoryTO, boolean addChild) {
        List<AssetMaintenanceCategoryTO> assetMaintenanceCategoryTOs = new ArrayList<AssetMaintenanceCategoryTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (assetMaintenanceCategoryMstrEntity.getAssetMaintenanceCategoryMstrEntity() != null) {
            for (AssetMaintenanceCategoryMstrEntity childEntity : assetMaintenanceCategoryMstrEntity
                    .getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    assetMaintenanceCategoryTOs.add(populateAssetMaintenanceItems(childEntity, addChild));
                }
            }
        } else {
            for (AssetMaintenanceCategoryMstrEntity childEntity : assetMaintenanceCategoryMstrEntity
                    .getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    assetMaintenanceCategoryTOs.add(populateAssetMaintenanceItems(childEntity, addChild));
                }
            }
        }
        return assetMaintenanceCategoryTOs;
    }

    public static List<AssetMaintenanceCategoryMstrEntity> populateEntitiesFromPOJO(Long clientId,
            List<AssetMaintenanceCategoryTO> assetMaintenanceCategoryTOs) {
        List<AssetMaintenanceCategoryMstrEntity> assetMaintenanceCategoryMstrEntities = new ArrayList<AssetMaintenanceCategoryMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        AssetMaintenanceCategoryMstrEntity entity = null;

        for (AssetMaintenanceCategoryTO assetMaintenanceCategoryTO : assetMaintenanceCategoryTOs) {
            entity = new AssetMaintenanceCategoryMstrEntity();
            assetMaintenanceCategoryTO.setClientId(clientId);
            convertPOJOToEntity(entity, assetMaintenanceCategoryTO);
            assetMaintenanceCategoryMstrEntities.add(entity);
        }
        return assetMaintenanceCategoryMstrEntities;
    }

    public static AssetMaintenanceCategoryMstrEntity convertPOJOToEntity(
            AssetMaintenanceCategoryMstrEntity assetMaintenanceCategoryMstrEntity,
            AssetMaintenanceCategoryTO assetMaintenanceCategoryTO) {

        if (CommonUtil.isNonBlankLong(assetMaintenanceCategoryTO.getId())) {
            assetMaintenanceCategoryMstrEntity.setId(assetMaintenanceCategoryTO.getId());
        }
        assetMaintenanceCategoryMstrEntity.setCode(assetMaintenanceCategoryTO.getCode());
        // Client id will set from interceptor
        assetMaintenanceCategoryMstrEntity.setName(assetMaintenanceCategoryTO.getName());
        assetMaintenanceCategoryMstrEntity.setItem(assetMaintenanceCategoryTO.isItem());
        if (CommonUtil.isNonBlankLong(assetMaintenanceCategoryTO.getParentId())) {
            AssetMaintenanceCategoryMstrEntity parentEntity = new AssetMaintenanceCategoryMstrEntity();
            parentEntity.setId(assetMaintenanceCategoryTO.getParentId());
            assetMaintenanceCategoryMstrEntity.setAssetMaintenanceCategoryMstrEntity(parentEntity);
        }
        assetMaintenanceCategoryMstrEntity.setStatus(assetMaintenanceCategoryTO.getStatus());
        AssetMaintenanceCategoryMstrEntity childEntity = null;

        for (AssetMaintenanceCategoryTO childTO : assetMaintenanceCategoryTO.getChildAssetMaintenanceCategoryTOs()) {
            childEntity = new AssetMaintenanceCategoryMstrEntity();
            childEntity.setAssetMaintenanceCategoryMstrEntity(assetMaintenanceCategoryMstrEntity);
            assetMaintenanceCategoryMstrEntity.getChildEntities().add(convertPOJOToEntity(childEntity, childTO));
        }
        return assetMaintenanceCategoryMstrEntity;
    }

}
