package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSORItemTO;
import com.rjtech.projectlib.model.ProjSORItemEntity;


public class ProjSORItemHandlerCopy {

    public static ProjSORItemTO populateSORITems(ProjSORItemEntity projSORItemEntity, boolean addChild) {
        ProjSORItemTO projSORItemTO = null;
        if (CommonUtil.objectNotNull(projSORItemEntity)) {
            projSORItemTO = new ProjSORItemTO();
            projSORItemTO.setId(projSORItemEntity.getId());
            projSORItemTO.setCode(projSORItemEntity.getCode());
            projSORItemTO.setName(projSORItemEntity.getName());
            projSORItemTO.setProjId(projSORItemEntity.getProjMstrEntity().getProjectId());

            if (CommonUtil.objectNotNull(projSORItemEntity.getMeasurmentMstrEntity())) {
                projSORItemTO.setMeasureId(projSORItemEntity.getMeasurmentMstrEntity().getId());
                projSORItemTO.setMeasureUnitTO(
                        MeasurementHandler.convertMeasurePOJOFromEnity(projSORItemEntity.getMeasurmentMstrEntity()));
            }

            projSORItemTO.setQuantity(projSORItemEntity.getQuantity());
            projSORItemTO.setManPowerHrs(projSORItemEntity.getManPowerHrs());

            projSORItemTO.setComments(projSORItemEntity.getComments());

            projSORItemTO.setLabourRate(projSORItemEntity.getLabourRate());
            projSORItemTO.setPlantRate(projSORItemEntity.getPlantRate());
            projSORItemTO.setMaterialRate(projSORItemEntity.getMaterialRate());
            projSORItemTO.setOthersRate(projSORItemEntity.getOthersRate());
            projSORItemTO.setItem(projSORItemEntity.isItem());
            if (CommonUtil.objectNotNull(projSORItemEntity.getProjSORItemEntity())) {
                projSORItemTO.setParentId(projSORItemEntity.getProjSORItemEntity().getId());
                projSORItemTO.setParentName(projSORItemEntity.getProjSORItemEntity().getName());
            }
            projSORItemTO.setStatus(projSORItemEntity.getStatus());

            if (addChild) {
                projSORItemTO.getChildSORItemTOs().addAll(addChilds(projSORItemEntity, projSORItemTO, addChild));
            }
        }
        return projSORItemTO;
    }

    public static List<ProjSORItemTO> addChilds(ProjSORItemEntity projSORItemEntity, ProjSORItemTO projSORItemTO,
            boolean addChild) {
        List<ProjSORItemTO> childSORTOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        if (CommonUtil.objectNotNull(projSORItemEntity.getChildEntities())) {
            for (ProjSORItemEntity childEntity : projSORItemEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childSORTOs.add(populateSORITems(childEntity, addChild));
                }
            }
        }
        return childSORTOs;
    }

}
