package com.rjtech.projsettings.service.handler;

import java.util.Date;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjTimeSheetWeekDtlTO;
import com.rjtech.projsettings.model.ProjTimeSheetWeekDtlEntity;

public class ProjTimeSheetWeekHandler {

    public static ProjTimeSheetWeekDtlTO convertEntityToPOJO(ProjTimeSheetWeekDtlEntity entity) {

        ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO = new ProjTimeSheetWeekDtlTO();

        projTimeSheetWeekDtlTO.setId(entity.getId());
        projTimeSheetWeekDtlTO.setWeekStartDay(
                ProjTimeSheetWeekMap.convertWeekDaysNumberTOWeeakDaysMap().get(entity.getWeekStartDay()));
        projTimeSheetWeekDtlTO
                .setWeekEndDay(ProjTimeSheetWeekMap.convertWeekDaysNumberTOWeeakDaysMap().get(entity.getWeekEndDay()));
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            projTimeSheetWeekDtlTO.setProjId(entity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.isNotBlankDate(entity.getEffectiveFrom())) {
            projTimeSheetWeekDtlTO.setEffectiveFrom(CommonUtil.convertDateToString(entity.getEffectiveFrom()));
        }
        if (CommonUtil.isNotBlankDate(entity.getEffectiveTo())) {
            projTimeSheetWeekDtlTO.setEffectiveTo(CommonUtil.convertDateToString(entity.getEffectiveTo()));
        }
        projTimeSheetWeekDtlTO.setIsLatest(entity.getIsLatest());
        projTimeSheetWeekDtlTO.setStatus(entity.getStatus());

        return projTimeSheetWeekDtlTO;
    }

    public static ProjTimeSheetWeekDtlEntity convertOnePojoToOneEntity(ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO,
            EPSProjRepository epsProjRepository) {

        ProjTimeSheetWeekDtlEntity entity = new ProjTimeSheetWeekDtlEntity();
        if (CommonUtil.isNonBlankLong(projTimeSheetWeekDtlTO.getId())) {
            entity.setId(projTimeSheetWeekDtlTO.getId());
        }
        entity.setWeekStartDay(ProjTimeSheetWeekMap.convertWeekDaysTOWeeakDaysNumberMap()
                .get(projTimeSheetWeekDtlTO.getWeekStartDay()));
        entity.setWeekEndDay(
                ProjTimeSheetWeekMap.convertWeekDaysTOWeeakDaysNumberMap().get(projTimeSheetWeekDtlTO.getWeekEndDay()));
        if (CommonUtil.isNonBlankLong(projTimeSheetWeekDtlTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projTimeSheetWeekDtlTO.getProjId());
            entity.setProjMstrEntity(projMstrEntity);
        }
        if (CommonUtil.isNotBlankStr(projTimeSheetWeekDtlTO.getEffectiveFrom())) {
            entity.setEffectiveFrom(CommonUtil.convertStringToDate(projTimeSheetWeekDtlTO.getEffectiveFrom()));
        }
        entity.setEffectiveTo(new Date());
        entity.setIsLatest("Y");
        entity.setStatus(projTimeSheetWeekDtlTO.getStatus());
        return entity;
    }
}
