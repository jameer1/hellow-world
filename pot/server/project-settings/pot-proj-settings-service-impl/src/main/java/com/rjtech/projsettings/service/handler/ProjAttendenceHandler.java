package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjAttendenceTO;
import com.rjtech.projsettings.model.AttendanceNormalTimeEntity;

public class ProjAttendenceHandler {

    public static ProjAttendenceTO convertEntityToPOJO(AttendanceNormalTimeEntity entity) {
        ProjAttendenceTO projAttendenceTO = new ProjAttendenceTO();

        projAttendenceTO.setId(entity.getId());
        projAttendenceTO.setCutOffDays(entity.getCutOffDays());
        ProjMstrEntity projMstrEntity = entity.getProjId();
        if (null != projMstrEntity) {
            projAttendenceTO.setProjId(projMstrEntity.getProjectId());
        }
        projAttendenceTO.setCutOffHours(entity.getCutOffHours());
        projAttendenceTO.setCutOffMinutes(entity.getCutOffMinutes());
        projAttendenceTO.setType(entity.getType());

        projAttendenceTO.setStatus(entity.getStatus());

        return projAttendenceTO;

    }

    public static List<AttendanceNormalTimeEntity> convertPOJOToEntity(List<ProjAttendenceTO> projAttendenceTOs,
            EPSProjRepository epsProjRepository) {
        List<AttendanceNormalTimeEntity> projAttendenceEntites = new ArrayList<AttendanceNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjAttendenceTO projAttendenceTO : projAttendenceTOs) {

            projAttendenceEntites.add(convertOnePojpToOneEntity(projAttendenceTO, epsProjRepository));
        }
        return projAttendenceEntites;
    }

    public static AttendanceNormalTimeEntity convertOnePojpToOneEntity(ProjAttendenceTO projAttendenceTO,
            EPSProjRepository epsProjRepository) {
        AttendanceNormalTimeEntity entity = new AttendanceNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projAttendenceTO.getId())) {
            entity.setId(projAttendenceTO.getId());
        }

        if (CommonUtil.isNonBlankInteger(projAttendenceTO.getCutOffDays())) {
            entity.setCutOffDays(projAttendenceTO.getCutOffDays());
        } else {
            entity.setCutOffDays(0);
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projAttendenceTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        if (CommonUtil.isNonBlankInteger(projAttendenceTO.getCutOffHours())) {
            entity.setCutOffHours(projAttendenceTO.getCutOffHours());
        } else {
            entity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projAttendenceTO.getCutOffMinutes())) {
            entity.setCutOffMinutes(projAttendenceTO.getCutOffMinutes());
        } else {
            entity.setCutOffMinutes(0);
        }
        entity.setType(projAttendenceTO.getType());

        entity.setStatus(projAttendenceTO.getStatus());
        return entity;
    }

}
