package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjStatusMilestonesTO;
import com.rjtech.projsettings.model.ProjStatusMileStonesEntity;

public class ProjStatusMileStonesHandler {

    public static ProjStatusMilestonesTO convertEntityToPOJO(ProjStatusMileStonesEntity projStatusMileStonesEntity) {

        ProjStatusMilestonesTO projStatusMilestonesTO = new ProjStatusMilestonesTO();

        if (CommonUtil.isNonBlankLong(projStatusMileStonesEntity.getId())) {
            projStatusMilestonesTO.setId(projStatusMileStonesEntity.getId());
        }
        if (CommonUtil.objectNotNull(projStatusMileStonesEntity.getProjMstrEntity())) {
            projStatusMilestonesTO.setProjId(projStatusMileStonesEntity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.isNotBlankStr(projStatusMileStonesEntity.getMileStones())) {
            projStatusMilestonesTO.setMileStones(projStatusMileStonesEntity.getMileStones());
        }
        if (CommonUtil.isNotBlankDate(projStatusMileStonesEntity.getMileStoneOriginal())) {
            projStatusMilestonesTO.setMileStoneOriginal(
                    CommonUtil.convertDateToString(projStatusMileStonesEntity.getMileStoneOriginal()));
        }
        if (CommonUtil.isNotBlankDate(projStatusMileStonesEntity.getMileStoneForeCast())) {
            projStatusMilestonesTO.setMileStoneForeCast(
                    CommonUtil.convertDateToString(projStatusMileStonesEntity.getMileStoneForeCast()));
        }
        if (CommonUtil.isNotBlankDate(projStatusMileStonesEntity.getMileStoneActual())) {
            projStatusMilestonesTO.setMileStoneActual(
                    CommonUtil.convertDateToString(projStatusMileStonesEntity.getMileStoneActual()));
        }
        if (CommonUtil.isNotBlankStr(projStatusMileStonesEntity.getMileStoneVariance())) {
            projStatusMilestonesTO.setMileStoneVariance(projStatusMileStonesEntity.getMileStoneVariance());
        }

        return projStatusMilestonesTO;

    }

    public static List<ProjStatusMileStonesEntity> convertPOJOToEntity(
            ProjStatusMileStonesEntity statusMileStonesEntity, List<ProjStatusMilestonesTO> projStatusMilestonesTOs,
            EPSProjRepository epsProjRepository) {

        List<ProjStatusMileStonesEntity> projStatusDatesEntities = new ArrayList<ProjStatusMileStonesEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        for (ProjStatusMilestonesTO projStatusMilestonesTO : projStatusMilestonesTOs) {

            ProjMstrEntity proj = epsProjRepository.findOne(projStatusMilestonesTO.getProjId());
            statusMileStonesEntity.setProjMstrEntity(proj);

            statusMileStonesEntity.setStatus(projStatusMilestonesTO.getStatus());

            statusMileStonesEntity.setMileStones(projStatusMilestonesTO.getMileStones());
            statusMileStonesEntity.setMileStoneOriginal(
                    CommonUtil.convertStringToDate(projStatusMilestonesTO.getMileStoneOriginal()));
            statusMileStonesEntity.setMileStoneForeCast(
                    CommonUtil.convertStringToDate(projStatusMilestonesTO.getMileStoneForeCast()));
            statusMileStonesEntity
                    .setMileStoneActual(CommonUtil.convertStringToDate(projStatusMilestonesTO.getMileStoneActual()));
            statusMileStonesEntity.setMileStoneVariance(projStatusMilestonesTO.getMileStoneVariance());

            projStatusDatesEntities.add(statusMileStonesEntity);
        }

        return projStatusDatesEntities;

    }

}
