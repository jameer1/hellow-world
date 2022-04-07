package com.rjtech.projschedule.service.handler;

import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projschedule.dto.ProjScheduleCostCodeTO;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;
import com.rjtech.projschedule.model.ProjScheduleCostCodeEntity;
import com.rjtech.projschedule.repository.ProjScheduleBaseLineRepository;

public class ProjScheduleCostCodeHandler {

    public static ProjScheduleCostCodeTO convertEntityToPOJO(ProjScheduleCostCodeEntity projScheduleCostCodeEntity) {
        ProjScheduleCostCodeTO projScheduleCostCodeTO = new ProjScheduleCostCodeTO();

        projScheduleCostCodeTO.setId(projScheduleCostCodeEntity.getId());
        ProjCostItemEntity costCode = projScheduleCostCodeEntity.getCostCodeId();
        if (costCode != null)
            projScheduleCostCodeTO.setCostCodeId(costCode.getId());
        ProjScheduleBaseLineEntity baseline = projScheduleCostCodeEntity.getProjScheduleBaseLineEntity();
        if (baseline != null)
            projScheduleCostCodeTO.setBaseLineId(baseline.getId());
        projScheduleCostCodeTO.setOriginalQty(projScheduleCostCodeEntity.getOriginalQty());
        projScheduleCostCodeTO.setRevisedQty(projScheduleCostCodeEntity.getRevisedQty());
        projScheduleCostCodeTO.setActualQty(projScheduleCostCodeEntity.getActualQty());
        projScheduleCostCodeTO.setRemainingQty(projScheduleCostCodeEntity.getRemainingQty());
        projScheduleCostCodeTO.setEstimateComplete(projScheduleCostCodeEntity.getEstimateComplete());
        projScheduleCostCodeTO.setEstimateCompletion(projScheduleCostCodeEntity.getEstimateCompletion());

        if (CommonUtil.isNotBlankDate(projScheduleCostCodeEntity.getStartDate())) {
            projScheduleCostCodeTO
                    .setStartDate(CommonUtil.convertDateToString(projScheduleCostCodeEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projScheduleCostCodeEntity.getFinishDate())) {
            projScheduleCostCodeTO
                    .setFinishDate(CommonUtil.convertDateToString(projScheduleCostCodeEntity.getFinishDate()));
        }
        ResourceCurveEntity resourceCurve = projScheduleCostCodeEntity.getResourceCurveId();
        if (resourceCurve != null)
            projScheduleCostCodeTO.setResourceCurveId(resourceCurve.getId());

        projScheduleCostCodeTO.setStatus(projScheduleCostCodeEntity.getStatus());
        return projScheduleCostCodeTO;

    }

    public static ProjScheduleCostCodeEntity convertPOJOToEntity(ProjScheduleCostCodeTO projScheduleCostCodeTO,
            ProjCostItemRepositoryCopy projCostItemRepository,
            ProjScheduleBaseLineRepository projScheduleBaseLineRepository,
            ResourceCurveRepository resourceCurveRepository) {
        ProjScheduleCostCodeEntity projScheduleCostCodeEntity = new ProjScheduleCostCodeEntity();
        if (CommonUtil.isNonBlankLong(projScheduleCostCodeTO.getId())) {
            projScheduleCostCodeEntity.setId(projScheduleCostCodeTO.getId());
        }
        if (CommonUtil.isNonBlankLong(projScheduleCostCodeTO.getCostCodeId())) {
            ProjCostItemEntity projCost = projCostItemRepository.findOne(projScheduleCostCodeTO.getCostCodeId());
            projScheduleCostCodeEntity.setCostCodeId(projCost);
        }
        Long baseLineId = projScheduleCostCodeTO.getBaseLineId();
        if (baseLineId != null)
            projScheduleCostCodeEntity
                    .setProjScheduleBaseLineEntity(projScheduleBaseLineRepository.findOne(baseLineId));
        projScheduleCostCodeEntity.setOriginalQty(projScheduleCostCodeTO.getOriginalQty());
        projScheduleCostCodeEntity.setRevisedQty(projScheduleCostCodeTO.getRevisedQty());
        projScheduleCostCodeEntity.setActualQty(projScheduleCostCodeTO.getActualQty());
        projScheduleCostCodeEntity.setRemainingQty(projScheduleCostCodeTO.getRemainingQty());
        projScheduleCostCodeEntity.setEstimateComplete(projScheduleCostCodeTO.getEstimateComplete());
        projScheduleCostCodeEntity.setEstimateCompletion(projScheduleCostCodeTO.getEstimateCompletion());

        if (CommonUtil.isNotBlankStr(projScheduleCostCodeTO.getStartDate())) {
            projScheduleCostCodeEntity
                    .setStartDate(CommonUtil.convertStringToDate(projScheduleCostCodeTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projScheduleCostCodeTO.getFinishDate())) {
            projScheduleCostCodeEntity
                    .setFinishDate(CommonUtil.convertStringToDate(projScheduleCostCodeTO.getFinishDate()));
        }
        if (CommonUtil.isNonBlankLong(projScheduleCostCodeTO.getResourceCurveId()))
            projScheduleCostCodeEntity
                    .setResourceCurveId(resourceCurveRepository.findOne(projScheduleCostCodeTO.getResourceCurveId()));

        projScheduleCostCodeEntity.setStatus(projScheduleCostCodeTO.getStatus());
        return projScheduleCostCodeEntity;
    }

}
