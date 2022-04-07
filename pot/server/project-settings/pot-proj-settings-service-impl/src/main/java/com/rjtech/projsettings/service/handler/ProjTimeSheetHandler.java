package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjTimeSheetTO;
import com.rjtech.projsettings.model.TimesheetNormalTimeEntity;

public class ProjTimeSheetHandler {

    public static ProjTimeSheetTO convertEntityToPOJO(TimesheetNormalTimeEntity entity) {
        ProjTimeSheetTO projTimeSheetTO = new ProjTimeSheetTO();
        projTimeSheetTO.setId(entity.getId());
        projTimeSheetTO.setType(entity.getType());
        ProjMstrEntity projMstrEntity = entity.getProjId();
        if (!CommonUtil.isBlankInteger(entity.getCutOffDays())) {
            projTimeSheetTO.setCutOffDays(entity.getCutOffDays());
        }
        projTimeSheetTO.setCutOffTime(entity.getCutOffTime());
        if (null != projMstrEntity) {
            projTimeSheetTO.setProjId(projMstrEntity.getProjectId());
        }
        projTimeSheetTO.setTypeId(entity.getTypeId());
        projTimeSheetTO.setWeeakStartDay(entity.getWeeakStartDay());
        projTimeSheetTO.setWeeakEndDay(entity.getWeeakEndDay());
        projTimeSheetTO.setDefaultStatus(entity.getDefaultStatus());
        if (!CommonUtil.isBlankInteger(entity.getCutOffHours())) {
            projTimeSheetTO.setCutOffHours(entity.getCutOffHours());
        }
        if (!CommonUtil.isBlankInteger(entity.getCutOffMinutes())) {
            projTimeSheetTO.setCutOffMinutes(entity.getCutOffMinutes());
        }
        projTimeSheetTO.setStatus(entity.getStatus());
        return projTimeSheetTO;
    }

    public static List<TimesheetNormalTimeEntity> convertPOJOToEntity(List<ProjTimeSheetTO> ProjTimeSheetEntity,
            EPSProjRepository epsProjRepository) {
        List<TimesheetNormalTimeEntity> projTimeSheetEntites = new ArrayList<TimesheetNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjTimeSheetTO projTimeSheetTO : ProjTimeSheetEntity) {

            projTimeSheetEntites.add(convertOnePojoToOneEntity(projTimeSheetTO, epsProjRepository));
        }
        return projTimeSheetEntites;
    }

    public static TimesheetNormalTimeEntity convertOnePojoToOneEntity(ProjTimeSheetTO projTimeSheetTO,
            EPSProjRepository epsProjRepository) {
        TimesheetNormalTimeEntity entity = new TimesheetNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projTimeSheetTO.getId())) {
            entity.setId(projTimeSheetTO.getId());
        }
        entity.setType(projTimeSheetTO.getType());
        if (CommonUtil.isNonBlankInteger(projTimeSheetTO.getCutOffDays())) {
            entity.setCutOffDays(projTimeSheetTO.getCutOffDays());
        } else {
            entity.setCutOffDays(0);
        }
        entity.setCutOffTime(projTimeSheetTO.getCutOffTime());

        ProjMstrEntity projMstEntity = epsProjRepository.findOne(projTimeSheetTO.getProjId());

        if (null != projMstEntity) {
            entity.setProjId(projMstEntity);
        }
        entity.setTypeId(projTimeSheetTO.getTypeId());
        entity.setDefaultStatus(projTimeSheetTO.getDefaultStatus());
        if (CommonUtil.isNonBlankInteger(projTimeSheetTO.getCutOffHours())) {
            entity.setCutOffHours(projTimeSheetTO.getCutOffHours());
        } else {
            entity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projTimeSheetTO.getCutOffMinutes())) {
            entity.setCutOffMinutes(projTimeSheetTO.getCutOffMinutes());
        } else {
            entity.setCutOffMinutes(0);
        }
        entity.setWeeakStartDay(projTimeSheetTO.getWeeakStartDay());
        entity.setWeeakEndDay(projTimeSheetTO.getWeeakEndDay());
        entity.setStatus(projTimeSheetTO.getStatus());
        return entity;
    }
}
