package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.PlantServiceClassificationMstrTO;
import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;

public class PlantServiceClassificationMstrHandler {

    public static PlantServiceClassificationMstrTO convertEntityToPOJO(PlantServiceClassificationMstrEntity entity,
            Boolean addChild) {
        PlantServiceClassificationMstrTO plantServiceClassificationMstrTO = new PlantServiceClassificationMstrTO();

        if (CommonUtil.objectNotNull(entity)) {
            plantServiceClassificationMstrTO.setId(entity.getId());
            plantServiceClassificationMstrTO.setCode(entity.getCode());
            plantServiceClassificationMstrTO.setName(entity.getName());
            if (null != entity.getPlantServiceClassificationMstrEntity()) {
                plantServiceClassificationMstrTO.setParentId(entity.getPlantServiceClassificationMstrEntity().getId());
            }

            if (CommonConstants.ITEM_VALUE == entity.getIsItem()) {
                plantServiceClassificationMstrTO.setItem(true);
            } else {
                plantServiceClassificationMstrTO.setItem(false);
            }
            if (CommonUtil.objectNotNull(entity.getPlantServiceClassificationMstrEntity())) {
                plantServiceClassificationMstrTO
                        .setParentName(entity.getPlantServiceClassificationMstrEntity().getName());
            }

            if (CommonUtil.isListHasData(entity.getChildEntities()))

            {
                if (addChild) {
                    plantServiceClassificationMstrTO.getPlantServiceClassificationMstrTOs()
                            .addAll(addChilds(entity, plantServiceClassificationMstrTO, addChild));
                }
            }
            plantServiceClassificationMstrTO.setStatus(entity.getStatus());
        }
        return plantServiceClassificationMstrTO;
    }

    public static List<PlantServiceClassificationMstrTO> addChilds(
            PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity,
            PlantServiceClassificationMstrTO plantServiceClassificationMstrTO, boolean addChild) {

        List<PlantServiceClassificationMstrTO> childPlantServiceClassTOs = new ArrayList<PlantServiceClassificationMstrTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        if (CommonUtil.isNonBlankLong(plantServiceClassificationMstrTO.getParentId())) {

            for (PlantServiceClassificationMstrEntity childEntity : plantServiceClassificationMstrEntity
                    .getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childPlantServiceClassTOs.add(convertEntityToPOJO(childEntity, addChild));
                }
            }
        } else {
            if (null != plantServiceClassificationMstrEntity.getPlantServiceClassificationMstrEntity()) {
                plantServiceClassificationMstrTO.setParentId(
                        plantServiceClassificationMstrEntity.getPlantServiceClassificationMstrEntity().getId());
            }
            for (PlantServiceClassificationMstrEntity childEntity : plantServiceClassificationMstrEntity
                    .getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childPlantServiceClassTOs.add(convertEntityToPOJO(childEntity, addChild));
                }

            }
        }
        return childPlantServiceClassTOs;
    }

    public static List<PlantServiceClassificationMstrEntity> convertPOJOsToEntitys(
            List<PlantServiceClassificationMstrTO> plantServiceClassificationMstrTOs) {
        List<PlantServiceClassificationMstrEntity> plantServiceClassificationMstrEntites = new ArrayList<PlantServiceClassificationMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        PlantServiceClassificationMstrEntity entity = null;

        for (PlantServiceClassificationMstrTO plantServiceClassificationMstrTO : plantServiceClassificationMstrTOs) {
            entity = new PlantServiceClassificationMstrEntity();
            convertPOJOToEntity(entity, plantServiceClassificationMstrTO);
            plantServiceClassificationMstrEntites.add(entity);
        }
        return plantServiceClassificationMstrEntites;
    }

    public static PlantServiceClassificationMstrEntity convertPOJOToEntity(PlantServiceClassificationMstrEntity entity,
            PlantServiceClassificationMstrTO plantServiceClassificationMstrTO) {

        if (CommonUtil.isNonBlankLong(plantServiceClassificationMstrTO.getId())) {
            entity.setId(plantServiceClassificationMstrTO.getId());
        }
        entity.setCode(plantServiceClassificationMstrTO.getCode());
        entity.setName(plantServiceClassificationMstrTO.getName());

        if (plantServiceClassificationMstrTO.isItem()) {
            entity.setIsItem(CommonConstants.ITEM_VALUE);
        } else {
            entity.setIsItem(CommonConstants.GROUP_VALUE);
        }
        if (CommonUtil.isNonBlankLong(plantServiceClassificationMstrTO.getParentId())) {
            PlantServiceClassificationMstrEntity parentEntity = new PlantServiceClassificationMstrEntity();
            parentEntity.setId(plantServiceClassificationMstrTO.getParentId());
            entity.setPlantServiceClassificationMstrEntity(parentEntity);
        }
        entity.setStatus(plantServiceClassificationMstrTO.getStatus());
        PlantServiceClassificationMstrEntity childEntity = null;

        for (PlantServiceClassificationMstrTO childTO : plantServiceClassificationMstrTO
                .getPlantServiceClassificationMstrTOs()) {
            childEntity = new PlantServiceClassificationMstrEntity();
            childEntity.setPlantServiceClassificationMstrEntity(entity);
            entity.getChildEntities().add(convertPOJOToEntity(childEntity, childTO));
        }
        return entity;
    }

    public static LabelKeyTO convertEntityToLabelKeyTO(PlantServiceClassificationMstrEntity entity) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(entity.getId());
        labelKeyTO.setCode(entity.getCode());
        labelKeyTO.setName(entity.getName());
        if (CommonUtil.objectNotNull(entity.getPlantServiceClassificationMstrEntity())) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_NAME_KEY,
                    entity.getPlantServiceClassificationMstrEntity().getName());

        }

        return labelKeyTO;
    }
}
