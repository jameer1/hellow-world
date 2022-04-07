package com.rjtech.projschedule.service.handler;

import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projschedule.dto.ProjSchedulePlantTO;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;
import com.rjtech.projschedule.model.ProjSchedulePlantEntity;
import com.rjtech.projschedule.repository.ProjScheduleBaseLineRepository;
import com.rjtech.projsettings.service.handler.ProjectPlantsDtlHandler;

public class ProjSchedulePlantHandler {

    public static ProjSchedulePlantTO convertEntityToPOJO(ProjSchedulePlantEntity projSchedulePlantEntity) {
        ProjSchedulePlantTO projSchedulePlantTO = new ProjSchedulePlantTO();

        projSchedulePlantTO.setId(projSchedulePlantEntity.getId());
        PlantMstrEntity plantMstr = projSchedulePlantEntity.getPlantClassId();
        if (plantMstr != null) {
            projSchedulePlantTO.setPlantId(plantMstr.getId());
            projSchedulePlantTO.setPlantClassTO(ProjectPlantsDtlHandler.converPlantClassEntityToPOJO(plantMstr, true));
        }
        ProjScheduleBaseLineEntity baseline = projSchedulePlantEntity.getProjScheduleBaseLineEntity();
        if (baseline != null)
            projSchedulePlantTO.setBaseLineId(baseline.getId());
        projSchedulePlantTO.setOriginalQty(projSchedulePlantEntity.getOriginalQty());
        projSchedulePlantTO.setRevisedQty(projSchedulePlantEntity.getRevisedQty());
        projSchedulePlantTO.setActualQty(projSchedulePlantEntity.getActualQty());
        projSchedulePlantTO.setRemainingQty(projSchedulePlantEntity.getRemainingQty());
        projSchedulePlantTO.setEstimateComplete(projSchedulePlantEntity.getEstimateComplete());
        projSchedulePlantTO.setEstimateCompletion(projSchedulePlantEntity.getEstimateCompletion());

        if (CommonUtil.isNotBlankDate(projSchedulePlantEntity.getStartDate())) {
            projSchedulePlantTO.setStartDate(CommonUtil.convertDateToString(projSchedulePlantEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projSchedulePlantEntity.getFinishDate())) {
            projSchedulePlantTO.setFinishDate(CommonUtil.convertDateToString(projSchedulePlantEntity.getFinishDate()));
        }
        ResourceCurveEntity resourceCurve = projSchedulePlantEntity.getResourceCurveId();
        if (resourceCurve != null)
            projSchedulePlantTO.setResourceCurveId(resourceCurve.getId());

        projSchedulePlantTO.setStatus(projSchedulePlantEntity.getStatus());
        return projSchedulePlantTO;

    }

    public static ProjSchedulePlantEntity convertPOJOToEntity(ProjSchedulePlantTO projSchedulePlantTO,
            PlantClassRepository plantClassRepository, ProjScheduleBaseLineRepository projScheduleBaseLineRepository,
            ResourceCurveRepository resourceCurveRepository) {
        ProjSchedulePlantEntity projSchedulePlantEntity = new ProjSchedulePlantEntity();
        if (CommonUtil.isNonBlankLong(projSchedulePlantTO.getId())) {
            projSchedulePlantEntity.setId(projSchedulePlantTO.getId());
        }
        projSchedulePlantEntity.setPlantClassId(plantClassRepository.findOne(projSchedulePlantTO.getPlantId()));
        if (CommonUtil.isNonBlankLong(projSchedulePlantTO.getBaseLineId())) {
            projSchedulePlantEntity.setProjScheduleBaseLineEntity(
                    projScheduleBaseLineRepository.findOne(projSchedulePlantTO.getBaseLineId()));
        }
        projSchedulePlantEntity.setOriginalQty(projSchedulePlantTO.getOriginalQty());
        projSchedulePlantEntity.setRevisedQty(projSchedulePlantTO.getRevisedQty());
        projSchedulePlantEntity.setActualQty(projSchedulePlantTO.getActualQty());
        projSchedulePlantEntity.setRemainingQty(projSchedulePlantTO.getRemainingQty());
        projSchedulePlantEntity.setEstimateComplete(projSchedulePlantTO.getEstimateComplete());
        projSchedulePlantEntity.setEstimateCompletion(projSchedulePlantTO.getEstimateCompletion());

        if (CommonUtil.isNotBlankStr(projSchedulePlantTO.getStartDate())) {
            projSchedulePlantEntity.setStartDate(CommonUtil.convertStringToDate(projSchedulePlantTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projSchedulePlantTO.getFinishDate())) {
            projSchedulePlantEntity.setFinishDate(CommonUtil.convertStringToDate(projSchedulePlantTO.getFinishDate()));
        }
        projSchedulePlantEntity
                .setResourceCurveId(resourceCurveRepository.findOne(projSchedulePlantTO.getResourceCurveId()));

        projSchedulePlantEntity.setStatus(projSchedulePlantTO.getStatus());
        return projSchedulePlantEntity;
    }

}
