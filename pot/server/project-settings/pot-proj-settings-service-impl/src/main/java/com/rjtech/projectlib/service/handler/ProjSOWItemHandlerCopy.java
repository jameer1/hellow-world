package com.rjtech.projectlib.service.handler;

import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.projectlib.model.ProjSOWItemEntity;


public class ProjSOWItemHandlerCopy {

    public static ProjSOWItemTO convertEntityToPOJO(ProjSOWItemEntity projSOWItemEntity) {

        ProjSOWItemTO projSOWItemTO = new ProjSOWItemTO();
        projSOWItemTO.setItem(projSOWItemEntity.isItem());
        projSOWItemTO.setProjCostItemTO(
                ProjCostItemHandlerCopy.populateProjCostITems(projSOWItemEntity.getProjCostItemEntity(), false));
        projSOWItemTO.setProjSORItemTO(
                ProjSORItemHandlerCopy.populateSORITems(projSOWItemEntity.getProjSORItemEntity(), false));
        projSOWItemTO.setProjSOEItemTO(
                ProjSOEItemHandlerCopy.populateProjSOEITems(projSOWItemEntity.getProjSOEItemEntity(), false));

        projSOWItemTO.setId(projSOWItemEntity.getId());

        projSOWItemTO.setCode(projSOWItemEntity.getCode());
        projSOWItemTO.setName(projSOWItemEntity.getName());
        projSOWItemTO.setSowId(projSOWItemEntity.getId());

        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity()))
            projSOWItemTO.setProjCostId(projSOWItemEntity.getProjCostItemEntity().getId());
        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity()))
            projSOWItemTO.setSorId(projSOWItemEntity.getProjSORItemEntity().getId());
        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity()))
            projSOWItemTO.setSoeId(projSOWItemEntity.getProjSOEItemEntity().getId());

        if (CommonUtil.isNotBlankDate(projSOWItemEntity.getStartDate())) {
            projSOWItemTO.setStartDate(CommonUtil.convertDateToString(projSOWItemEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projSOWItemEntity.getFinishDate())) {
            projSOWItemTO.setFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getFinishDate()));
        }

        if (CommonUtil.isNotBlankDate(projSOWItemEntity.getActualFinishDate())) {
            projSOWItemTO.setActualFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getActualFinishDate()));
        }

        projSOWItemTO.setComments(projSOWItemEntity.getComments());

        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity())
                && CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity())) {
            projSOWItemTO.setMeasureId(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getId());
            projSOWItemTO.setMeasureUnitTO(MeasurementHandler
                    .convertMeasurePOJOFromEnity(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity()));
        }

        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity())) {
            projSOWItemTO.setOriginalQty(projSOWItemEntity.getProjSOEItemEntity().getQuantity());
            projSOWItemTO.setQuantity(projSOWItemEntity.getProjSOEItemEntity().getQuantity());
        }

        projSOWItemTO.setActualQty(projSOWItemEntity.getActualQty());
        projSOWItemTO.setRevisedQty(projSOWItemEntity.getRevisedQty());
        projSOWItemTO.setProjId(projSOWItemEntity.getProjMstrEntity().getProjectId());

        projSOWItemTO.setStatus(projSOWItemEntity.getStatus());
        return projSOWItemTO;
    }

}
