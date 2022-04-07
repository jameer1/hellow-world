package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOEItemTO;
import com.rjtech.projectlib.model.ProjSOEItemEntity;


public class ProjSOEItemHandlerCopy {

    public static ProjSOEItemTO populateProjSOEITems(ProjSOEItemEntity projSOEItemEntity, boolean addChild) {
        ProjSOEItemTO projSOEItemTO = null;
        if (CommonUtil.objectNotNull(projSOEItemEntity)) {
            projSOEItemTO = new ProjSOEItemTO();
            projSOEItemTO.setId(projSOEItemEntity.getId());
            projSOEItemTO.setCode(projSOEItemEntity.getCode());
            projSOEItemTO.setName(projSOEItemEntity.getName());
            projSOEItemTO.setProjId(projSOEItemEntity.getProjMstrEntity().getProjectId());

            if (CommonUtil.objectNotNull(projSOEItemEntity.getMeasurmentMstrEntity())) {
                projSOEItemTO.setMeasureId(projSOEItemEntity.getMeasurmentMstrEntity().getId());
                projSOEItemTO.setMeasureUnitTO(
                        MeasurementHandler.convertMeasurePOJOFromEnity(projSOEItemEntity.getMeasurmentMstrEntity()));
            }
            projSOEItemTO.setQuantity(projSOEItemEntity.getQuantity());
            projSOEItemTO.setComments(projSOEItemEntity.getComments());

            projSOEItemTO.setItem(projSOEItemEntity.isItem());
            if (CommonUtil.objectNotNull(projSOEItemEntity.getProjSOEItemEntity())) {
                projSOEItemTO.setParentId(projSOEItemEntity.getProjSOEItemEntity().getId());
                projSOEItemTO.setParentName(projSOEItemEntity.getProjSOEItemEntity().getName());
            }
            projSOEItemTO.setStatus(projSOEItemEntity.getStatus());

            if (addChild) {
                projSOEItemTO.getChildSOEItemTOs().addAll(addChilds(projSOEItemEntity, projSOEItemTO, addChild));
            }
        }
        return projSOEItemTO;
    }

    public static List<ProjSOEItemTO> addChilds(ProjSOEItemEntity projSOEItemEntity, ProjSOEItemTO projSOEItemTO,
            boolean addChild) {
        List<ProjSOEItemTO> childSOETOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (CommonUtil.objectNotNull(projSOEItemEntity.getChildEntities())) {
            for (ProjSOEItemEntity childEntity : projSOEItemEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childSOETOs.add(populateProjSOEITems(childEntity, addChild));
                }
            }
        }
        return childSOETOs;
    }

}
