package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.CostMstrEntity;
import com.rjtech.centrallib.service.handler.CostCodeHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjCostItemTO;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

public class ProjCostItemHandlerCopy {

    public static ProjCostItemTO populateProjCostITems(ProjCostItemEntity projCostCodeItemEntity,
            boolean addChild) {
        ProjCostItemTO projCostCodeItemTO = null;
        if (CommonUtil.objectNotNull(projCostCodeItemEntity)) {
            projCostCodeItemTO = new ProjCostItemTO();
            projCostCodeItemTO.setId(projCostCodeItemEntity.getId());
            projCostCodeItemTO.setCode(projCostCodeItemEntity.getCode());
            projCostCodeItemTO.setName(projCostCodeItemEntity.getName());
            projCostCodeItemTO.setComments(projCostCodeItemEntity.getComments());

            projCostCodeItemTO.setItemParent(projCostCodeItemEntity.isItemParent());

            if (CommonConstants.ITEM_VALUE == projCostCodeItemEntity.getItem()) {
                projCostCodeItemTO.setItem(true);
            } else {
                projCostCodeItemTO.setItem(false);
            }
            if (CommonUtil.isNotBlankDate(projCostCodeItemEntity.getStartDate())) {
                projCostCodeItemTO.setStartDate(CommonUtil.convertDateToString(projCostCodeItemEntity.getStartDate()));
            }
            if (CommonUtil.isNotBlankDate(projCostCodeItemEntity.getFinishDate())) {
                projCostCodeItemTO
                        .setFinishDate(CommonUtil.convertDateToString(projCostCodeItemEntity.getFinishDate()));
            }
            projCostCodeItemTO.setWorkdairyEntry(projCostCodeItemEntity.isWorkDairyStatus());
            ProjMstrEntity project = projCostCodeItemEntity.getProjId();
            if (project != null)
                projCostCodeItemTO.setProjId(project.getProjectId());
            ProjCostItemEntity parent = projCostCodeItemEntity.getProjCostItemEntity();
            if (parent != null)
                projCostCodeItemTO.setParentId(parent.getId());
            CostMstrEntity costMstr = projCostCodeItemEntity.getCostMstrEntity();
            if (costMstr != null)
                projCostCodeItemTO.setCostId(costMstr.getId());
            if (CommonUtil.objectNotNull(projCostCodeItemEntity.getCostMstrEntity())) {
                projCostCodeItemTO
                        .setCostCodeTO(CostCodeHandler.convertEntityToPOJO(projCostCodeItemEntity.getCostMstrEntity()));
            }

            if (CommonUtil.objectNotNull(projCostCodeItemEntity.getProjCostItemEntity())) {
                projCostCodeItemTO.setParentName(projCostCodeItemEntity.getProjCostItemEntity().getName());
                projCostCodeItemTO.setParentCode(projCostCodeItemEntity.getProjCostItemEntity().getCode());
            }
            projCostCodeItemTO.setStatus(projCostCodeItemEntity.getStatus());

            if (addChild) {
                projCostCodeItemTO.getProjCostCodeItemTOs().addAll(addChilds(projCostCodeItemEntity, addChild));
            }
        }
        return projCostCodeItemTO;
    }

    public static List<ProjCostItemTO> addChilds(ProjCostItemEntity projCostCodeItemEntity, boolean addChild) {
        List<ProjCostItemTO> childSOETOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjCostItemEntity childEntity : projCostCodeItemEntity.getChildEntities()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                childSOETOs.add(populateProjCostITems(childEntity, addChild));
            }
        }
        return childSOETOs;
    }

}
