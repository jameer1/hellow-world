package com.rjtech.projschedule.service.handler;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.service.handler.MaterialClassHandler;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projschedule.dto.ProjScheduleMaterialTO;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;
import com.rjtech.projschedule.model.ProjScheduleMaterialEntity;
import com.rjtech.projschedule.repository.ProjScheduleBaseLineRepository;

public class ProjScheduleMaterialHandler {

    public static ProjScheduleMaterialTO convertEntityToPOJO(ProjScheduleMaterialEntity projScheduleMaterialEntity) {
        ProjScheduleMaterialTO projScheduleMaterialTO = new ProjScheduleMaterialTO();

        projScheduleMaterialTO.setId(projScheduleMaterialEntity.getId());
        MaterialClassMstrEntity materialClass = projScheduleMaterialEntity.getMaterialClassId();
        if (materialClass != null) {
            projScheduleMaterialTO.setMaterialClassId(materialClass.getId());
            projScheduleMaterialTO.setMaterialClassTO(MaterialClassHandler.populateMaterialItems(materialClass, false));
        }

        ProjScheduleBaseLineEntity baseLine = projScheduleMaterialEntity.getProjScheduleBaseLineEntity();
        if (baseLine != null)
            projScheduleMaterialTO.setBaseLineId(baseLine.getId());
        projScheduleMaterialTO.setOriginalQty(projScheduleMaterialEntity.getOriginalQty());
        projScheduleMaterialTO.setRevisedQty(projScheduleMaterialEntity.getRevisedQty());
        projScheduleMaterialTO.setActualQty(projScheduleMaterialEntity.getActualQty());
        projScheduleMaterialTO.setRemainingQty(projScheduleMaterialEntity.getRemainingQty());
        projScheduleMaterialTO.setEstimateComplete(projScheduleMaterialEntity.getEstimateComplete());
        projScheduleMaterialTO.setEstimateCompletion(projScheduleMaterialEntity.getEstimateCompletion());

        if (CommonUtil.isNotBlankDate(projScheduleMaterialEntity.getStartDate())) {
            projScheduleMaterialTO
                    .setStartDate(CommonUtil.convertDateToString(projScheduleMaterialEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projScheduleMaterialEntity.getFinishDate())) {
            projScheduleMaterialTO
                    .setFinishDate(CommonUtil.convertDateToString(projScheduleMaterialEntity.getFinishDate()));
        }
        ResourceCurveEntity resourceCurve = projScheduleMaterialEntity.getResourceCurveId();
        if (resourceCurve != null)
            projScheduleMaterialTO.setResourceCurveId(resourceCurve.getId());

        projScheduleMaterialTO.setStatus(projScheduleMaterialEntity.getStatus());
        return projScheduleMaterialTO;

    }

    public static ProjScheduleMaterialEntity convertPOJOToEntity(ProjScheduleMaterialTO projScheduleMaterialTO,
            MaterialClassRepository materialClassRepository,
            ProjScheduleBaseLineRepository projScheduleBaseLineRepository,
            ResourceCurveRepository resourceCurveRepository) {
        ProjScheduleMaterialEntity projScheduleMaterialEntity = new ProjScheduleMaterialEntity();
        if (CommonUtil.isNonBlankLong(projScheduleMaterialTO.getId())) {
            projScheduleMaterialEntity.setId(projScheduleMaterialTO.getId());
        }
        projScheduleMaterialEntity
                .setMaterialClassId(materialClassRepository.findOne(projScheduleMaterialTO.getMaterialClassId()));
        if (CommonUtil.isNonBlankLong(projScheduleMaterialTO.getBaseLineId()))
            projScheduleMaterialEntity.setProjScheduleBaseLineEntity(
                    projScheduleBaseLineRepository.findOne(projScheduleMaterialTO.getBaseLineId()));
        projScheduleMaterialEntity.setOriginalQty(projScheduleMaterialTO.getOriginalQty());
        projScheduleMaterialEntity.setRevisedQty(projScheduleMaterialTO.getRevisedQty());
        projScheduleMaterialEntity.setActualQty(projScheduleMaterialTO.getActualQty());
        projScheduleMaterialEntity.setRemainingQty(projScheduleMaterialTO.getRemainingQty());
        projScheduleMaterialEntity.setEstimateComplete(projScheduleMaterialTO.getEstimateComplete());
        projScheduleMaterialEntity.setEstimateCompletion(projScheduleMaterialTO.getEstimateCompletion());
        if (CommonUtil.isNotBlankStr(projScheduleMaterialTO.getStartDate())) {
            projScheduleMaterialEntity
                    .setStartDate(CommonUtil.convertStringToDate(projScheduleMaterialTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projScheduleMaterialTO.getFinishDate())) {
            projScheduleMaterialEntity
                    .setFinishDate(CommonUtil.convertStringToDate(projScheduleMaterialTO.getFinishDate()));
        }
        projScheduleMaterialEntity
                .setResourceCurveId(resourceCurveRepository.findOne(projScheduleMaterialTO.getResourceCurveId()));

        projScheduleMaterialEntity.setStatus(projScheduleMaterialTO.getStatus());
        return projScheduleMaterialEntity;
    }

}
