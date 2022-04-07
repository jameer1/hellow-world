package com.rjtech.projschedule.service.handler;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projschedule.dto.ProjScheduleManPowerTO;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;
import com.rjtech.projschedule.model.ProjScheduleManPowerEntity;
import com.rjtech.projschedule.repository.ProjScheduleBaseLineRepository;
import com.rjtech.projsettings.service.handler.ProjManpowerHandler;

public class ProjScheduleManPowerHandler {

    public static ProjScheduleManPowerTO convertEntityToPOJO(ProjScheduleManPowerEntity projScheduleManPowerEntity) {
        ProjScheduleManPowerTO projScheduleManPowerTO = new ProjScheduleManPowerTO();

        projScheduleManPowerTO.setId(projScheduleManPowerEntity.getId());
        EmpClassMstrEntity empClassMstrEntity = projScheduleManPowerEntity.getEmpClassId();
        if (empClassMstrEntity != null) {
            projScheduleManPowerTO.setEmpClassId(empClassMstrEntity.getId());
            projScheduleManPowerTO.setEmpClassTO(ProjManpowerHandler.convertEmpClassEntityToPOJO(empClassMstrEntity));
        }

        ProjScheduleBaseLineEntity baseLine = projScheduleManPowerEntity.getProjScheduleBaseLineEntity();
        if (baseLine != null)
            projScheduleManPowerTO.setBaseLineId(baseLine.getId());
        projScheduleManPowerTO.setOriginalQty(projScheduleManPowerEntity.getOriginalQty());
        projScheduleManPowerTO.setRevisedQty(projScheduleManPowerEntity.getRevisedQty());
        projScheduleManPowerTO.setActualQty(projScheduleManPowerEntity.getActualQty());
        projScheduleManPowerTO.setRemainingQty(projScheduleManPowerEntity.getRemainingQty());
        projScheduleManPowerTO.setEstimateComplete(projScheduleManPowerEntity.getEstimateComplete());
        projScheduleManPowerTO.setEstimateCompletion(projScheduleManPowerEntity.getEstimateCompletion());

        if (CommonUtil.isNotBlankDate(projScheduleManPowerEntity.getStartDate())) {
            projScheduleManPowerTO
                    .setStartDate(CommonUtil.convertDateToString(projScheduleManPowerEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projScheduleManPowerEntity.getFinishDate())) {
            projScheduleManPowerTO
                    .setFinishDate(CommonUtil.convertDateToString(projScheduleManPowerEntity.getFinishDate()));
        }
        ResourceCurveEntity resourceCurve = projScheduleManPowerEntity.getResourceCurveId();
        if (resourceCurve != null)
            projScheduleManPowerTO.setResourceCurveId(resourceCurve.getId());

        projScheduleManPowerTO.setStatus(projScheduleManPowerEntity.getStatus());
        return projScheduleManPowerTO;

    }

    public static ProjScheduleManPowerEntity convertPOJOToEntity(ProjScheduleManPowerTO projScheduleManPowerTO,
            EmpClassRepository empClassRepository, ProjScheduleBaseLineRepository projScheduleBaseLineRepository,
            ResourceCurveRepository resourceCurveRepository) {
        ProjScheduleManPowerEntity projScheduleManPowerEntity = new ProjScheduleManPowerEntity();
        if (CommonUtil.isNonBlankLong(projScheduleManPowerTO.getId())) {
            projScheduleManPowerEntity.setId(projScheduleManPowerTO.getId());
        }
        projScheduleManPowerEntity.setEmpClassId(empClassRepository.findOne(projScheduleManPowerTO.getEmpClassId()));
        if (projScheduleManPowerTO.getBaseLineId() != null) {
            projScheduleManPowerEntity.setProjScheduleBaseLineEntity(
                    projScheduleBaseLineRepository.findOne(projScheduleManPowerTO.getBaseLineId()));
        }
        projScheduleManPowerEntity.setOriginalQty(projScheduleManPowerTO.getOriginalQty());
        projScheduleManPowerEntity.setRevisedQty(projScheduleManPowerTO.getRevisedQty());
        projScheduleManPowerEntity.setActualQty(projScheduleManPowerTO.getActualQty());
        projScheduleManPowerEntity.setRemainingQty(projScheduleManPowerTO.getRemainingQty());
        projScheduleManPowerEntity.setEstimateComplete(projScheduleManPowerTO.getEstimateComplete());
        projScheduleManPowerEntity.setEstimateCompletion(projScheduleManPowerTO.getEstimateCompletion());

        if (CommonUtil.isNotBlankStr(projScheduleManPowerTO.getStartDate())) {
            projScheduleManPowerEntity
                    .setStartDate(CommonUtil.convertStringToDate(projScheduleManPowerTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projScheduleManPowerTO.getFinishDate())) {
            projScheduleManPowerEntity
                    .setFinishDate(CommonUtil.convertStringToDate(projScheduleManPowerTO.getFinishDate()));
        }
        projScheduleManPowerEntity
                .setResourceCurveId(resourceCurveRepository.findOne(projScheduleManPowerTO.getResourceCurveId()));

        projScheduleManPowerEntity.setStatus(projScheduleManPowerTO.getStatus());
        return projScheduleManPowerEntity;
    }

}
