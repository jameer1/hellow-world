package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjStatusDatesTO;
import com.rjtech.projsettings.model.ProjStatusDatesEntity;

public class ProjStatusDatesHandler {

    public static ProjStatusDatesTO convertEntityToPOJO(ProjStatusDatesEntity projStatusDatesEntity) {

        ProjStatusDatesTO projStatusDatesTO = new ProjStatusDatesTO();
		System.out.println("Project Status > Project Status > Progress Status > FOR LOOP");
        if (CommonUtil.isNonBlankLong(projStatusDatesEntity.getId())) {
            projStatusDatesTO.setId(projStatusDatesEntity.getId());
        }
        if (CommonUtil.objectNotNull(projStatusDatesEntity.getProjMstrEntity())) {
            projStatusDatesTO.setProjId(projStatusDatesEntity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getStartDate())) {
            projStatusDatesTO.setStartDate(CommonUtil.convertDateToString(projStatusDatesEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getFinishDate())) {
            projStatusDatesTO.setFinishDate(CommonUtil.convertDateToString(projStatusDatesEntity.getFinishDate()));
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getScheduleStartDate())) {
            projStatusDatesTO
                    .setScheduleStartDate(CommonUtil.convertDateToString(projStatusDatesEntity.getScheduleStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getScheduleFinishDate())) {
            projStatusDatesTO.setScheduleFinishDate(
                    CommonUtil.convertDateToString(projStatusDatesEntity.getScheduleFinishDate()));
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getForecastStartDate())) {
            projStatusDatesTO
                    .setForecastStartDate(CommonUtil.convertDateToString(projStatusDatesEntity.getForecastStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getForecastFinishDate())) {
            projStatusDatesTO.setForecastFinishDate(
                    CommonUtil.convertDateToString(projStatusDatesEntity.getForecastFinishDate()));
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getResumeDate())) {
            projStatusDatesTO.setResumeDate(CommonUtil.convertDateToString(projStatusDatesEntity.getResumeDate()));
        }
        if (CommonUtil.isNotBlankDate(projStatusDatesEntity.getSuspendedDate())) {
            projStatusDatesTO
                    .setSuspendedDate(CommonUtil.convertDateToString(projStatusDatesEntity.getSuspendedDate()));
        }
        if (CommonUtil.isNotBlankStr(projStatusDatesEntity.getOriginalDuration())) {
            projStatusDatesTO.setOriginalDuration(projStatusDatesEntity.getOriginalDuration());
        }
        if (CommonUtil.isNotBlankStr(projStatusDatesEntity.getForeCastDuration())) {
            projStatusDatesTO.setForeCastDuration(projStatusDatesEntity.getForeCastDuration());
        }
        if (CommonUtil.isNotBlankStr(projStatusDatesEntity.getActualDuration())) {
            projStatusDatesTO.setActualDuration(projStatusDatesEntity.getActualDuration());
        }
        if (CommonUtil.isNotBlankStr(projStatusDatesEntity.getRemainingDuration())) {
            projStatusDatesTO.setRemainingDuration(projStatusDatesEntity.getRemainingDuration());
        }
        if (CommonUtil.isNotBlankStr(projStatusDatesEntity.getCurrentPhase())) {
        	projStatusDatesTO.setCurrentPhase(projStatusDatesEntity.getCurrentPhase());
        }

        return projStatusDatesTO;

    }

    public static List<ProjStatusDatesEntity> convertPOJOToEntity(ProjStatusDatesEntity statusDatesEntity,
            List<ProjStatusDatesTO> projStatusDatesTOs, EPSProjRepository epsProjRepository) {

        List<ProjStatusDatesEntity> projStatusDatesEntities = new ArrayList<ProjStatusDatesEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        for (ProjStatusDatesTO projStatusDatesTO : projStatusDatesTOs) {

            ProjMstrEntity proj = epsProjRepository.findOne(projStatusDatesTO.getProjId());
            statusDatesEntity.setProjMstrEntity(proj);

            statusDatesEntity.setStatus(projStatusDatesTO.getStatus());

            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getStartDate())) {
                statusDatesEntity.setStartDate(CommonUtil.convertStringToDate(projStatusDatesTO.getStartDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getFinishDate())) {
                statusDatesEntity.setFinishDate(CommonUtil.convertStringToDate(projStatusDatesTO.getFinishDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getScheduleStartDate())) {
                statusDatesEntity
                        .setScheduleStartDate(CommonUtil.convertStringToDate(projStatusDatesTO.getScheduleStartDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getScheduleFinishDate())) {
                statusDatesEntity.setScheduleFinishDate(
                        CommonUtil.convertStringToDate(projStatusDatesTO.getScheduleFinishDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getForecastStartDate())) {
                statusDatesEntity
                        .setForecastStartDate(CommonUtil.convertStringToDate(projStatusDatesTO.getForecastStartDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getForecastFinishDate())) {
                statusDatesEntity.setForecastFinishDate(
                        CommonUtil.convertStringToDate(projStatusDatesTO.getForecastFinishDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getResumeDate())) {
                statusDatesEntity.setResumeDate(CommonUtil.convertStringToDate(projStatusDatesTO.getResumeDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getSuspendedDate())) {
                statusDatesEntity
                        .setSuspendedDate(CommonUtil.convertStringToDate(projStatusDatesTO.getSuspendedDate()));
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getOriginalDuration())) {
                statusDatesEntity.setOriginalDuration(projStatusDatesTO.getOriginalDuration());
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getForeCastDuration())) {
                statusDatesEntity.setForeCastDuration(projStatusDatesTO.getForeCastDuration());
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getActualDuration())) {
                statusDatesEntity.setActualDuration(projStatusDatesTO.getActualDuration());
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getRemainingDuration())) {
                statusDatesEntity.setRemainingDuration(projStatusDatesTO.getRemainingDuration());
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getCompletionDuration())) {
                statusDatesEntity.setCompletionDuration(projStatusDatesTO.getCompletionDuration());
            }
            if (CommonUtil.isNotBlankStr(projStatusDatesTO.getCurrentPhase())) {
            	statusDatesEntity.setCurrentPhase(projStatusDatesTO.getCurrentPhase());
            }
            projStatusDatesEntities.add(statusDatesEntity);
        }

        return projStatusDatesEntities;

    }

}
