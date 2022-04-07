package com.rjtech.projschedule.service.handler;

import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projschedule.dto.ProjScheduleSOWTO;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;
import com.rjtech.projschedule.model.ProjScheduleSOWEntity;
import com.rjtech.projschedule.repository.ProjScheduleBaseLineRepository;

public class ProjScheduleSOWHandler {

    public static ProjScheduleSOWTO convertEntityToPOJO(ProjScheduleSOWEntity projScheduleSOWEntity) {
        ProjScheduleSOWTO projScheduleSOWTO = new ProjScheduleSOWTO();

        projScheduleSOWTO.setId(projScheduleSOWEntity.getId());
        ProjSOWItemEntity sow = projScheduleSOWEntity.getSowId();
        if (sow != null)
            projScheduleSOWTO.setSowId(sow.getId());
        ProjScheduleBaseLineEntity baseline = projScheduleSOWEntity.getProjScheduleBaseLineEntity();
        if (baseline != null)
            projScheduleSOWTO.setBaseLineId(baseline.getId());
        projScheduleSOWTO.setOriginalQty(projScheduleSOWEntity.getOriginalQty());
        projScheduleSOWTO.setRevisedQty(projScheduleSOWEntity.getRevisedQty());
        projScheduleSOWTO.setActualQty(projScheduleSOWEntity.getActualQty());
        projScheduleSOWTO.setRemainingQty(projScheduleSOWEntity.getRemainingQty());
        projScheduleSOWTO.setEstimateComplete(projScheduleSOWEntity.getEstimateComplete());
        projScheduleSOWTO.setEstimateCompletion(projScheduleSOWEntity.getEstimateCompletion());

        if (CommonUtil.isNotBlankDate(projScheduleSOWEntity.getStartDate())) {
            projScheduleSOWTO.setStartDate(CommonUtil.convertDateToString(projScheduleSOWEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projScheduleSOWEntity.getFinishDate())) {
            projScheduleSOWTO.setFinishDate(CommonUtil.convertDateToString(projScheduleSOWEntity.getFinishDate()));
        }
        ResourceCurveEntity resourceCurve = projScheduleSOWEntity.getResourceCurveId();
        if (resourceCurve != null)
            projScheduleSOWTO.setResourceCurveId(resourceCurve.getId());

        projScheduleSOWTO.setStatus(projScheduleSOWEntity.getStatus());
        return projScheduleSOWTO;

    }

    public static ProjScheduleSOWEntity convertPOJOToEntity(ProjScheduleSOWTO projScheduleSOWTO,
            ProjSOWItemRepositoryCopy projSOWItemRepository,
            ProjScheduleBaseLineRepository projScheduleBaseLineRepository,
            ResourceCurveRepository resourceCurveRepository) {
        ProjScheduleSOWEntity projScheduleSOWEntity = new ProjScheduleSOWEntity();
        if (CommonUtil.isNonBlankLong(projScheduleSOWTO.getId())) {
            projScheduleSOWEntity.setId(projScheduleSOWTO.getId());
        }

        if (CommonUtil.isNonBlankLong(projScheduleSOWTO.getSowId())) {
            ProjSOWItemEntity projSOWItemEntity = projSOWItemRepository.findOne(projScheduleSOWTO.getSowId());
            projScheduleSOWEntity.setSowId(projSOWItemEntity);
        }
        Long baseLineId = projScheduleSOWTO.getBaseLineId();
        if (baseLineId != null)
            projScheduleSOWEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineRepository.findOne(baseLineId));
        projScheduleSOWEntity.setOriginalQty(projScheduleSOWTO.getOriginalQty());
        projScheduleSOWEntity.setRevisedQty(projScheduleSOWTO.getRevisedQty());
        projScheduleSOWEntity.setActualQty(projScheduleSOWTO.getActualQty());
        projScheduleSOWEntity.setRemainingQty(projScheduleSOWTO.getRemainingQty());
        projScheduleSOWEntity.setEstimateComplete(projScheduleSOWTO.getEstimateComplete());
        projScheduleSOWEntity.setEstimateCompletion(projScheduleSOWTO.getEstimateCompletion());

        if (CommonUtil.isNotBlankStr(projScheduleSOWTO.getStartDate())) {
            projScheduleSOWEntity.setStartDate(CommonUtil.convertStringToDate(projScheduleSOWTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projScheduleSOWTO.getFinishDate())) {
            projScheduleSOWEntity.setFinishDate(CommonUtil.convertStringToDate(projScheduleSOWTO.getFinishDate()));
        }
        projScheduleSOWEntity
                .setResourceCurveId(resourceCurveRepository.findOne(projScheduleSOWTO.getResourceCurveId()));

        projScheduleSOWEntity.setStatus(projScheduleSOWTO.getStatus());
        return projScheduleSOWEntity;
    }

}
