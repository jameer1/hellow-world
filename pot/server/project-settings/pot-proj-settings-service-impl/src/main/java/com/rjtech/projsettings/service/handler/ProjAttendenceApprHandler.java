package com.rjtech.projsettings.service.handler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projsettings.dto.ProjAttendceApprTO;
import com.rjtech.projsettings.model.AttendanceAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.AttendanceNormalTimeEntity;
import com.rjtech.projsettings.repository.ProjAttendenceRepository;

public class ProjAttendenceApprHandler {

    public static List<AttendanceAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjAttendceApprTO> projAttendceApprTOs, ProjCrewRepositoryCopy projCrewRepository,
            ProjAttendenceRepository projAttendenceRepository) {
        List<AttendanceAddtionalTimeApprEntity> projAttendceApprEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjAttendceApprTO projAttendceApprTO : projAttendceApprTOs) {
            AttendanceAddtionalTimeApprEntity entity = new AttendanceAddtionalTimeApprEntity();
            if (CommonUtil.isNonBlankLong(projAttendceApprTO.getId())) {
                entity.setId(projAttendceApprTO.getId());
            }
            entity.setNotification(projAttendceApprTO.getNotification());
            entity.setCutOffDays(projAttendceApprTO.getCutOffDays());
            entity.setCutOffHours(projAttendceApprTO.getCutOffHours());
            entity.setCutOffMinutes(projAttendceApprTO.getCutOffMinutes());
            ProjCrewMstrEntity projCrewMstrEntity = projCrewRepository
                    .findCrewByCrewCodeAndProjId(projAttendceApprTO.getProjId(), projAttendceApprTO.getCrewName());
            entity.setProjCrewMasterEntity(projCrewMstrEntity);

            if (CommonUtil.isNonBlankInteger(projAttendceApprTO.getProjAttenId())) {
                AttendanceNormalTimeEntity nrmlAttd = projAttendenceRepository
                        .findOne(projAttendceApprTO.getProjAttenId().longValue());
                entity.setAttendanceNormalTimeEntity(nrmlAttd);
            }

            Date now = new Date();
            Instant current = now.toInstant();
            LocalDateTime ldt = LocalDateTime.ofInstant(current, ZoneId.systemDefault())
                    .plusDays(projAttendceApprTO.getCutOffDays()).plusHours(projAttendceApprTO.getCutOffHours())
                    .plusMinutes(projAttendceApprTO.getCutOffMinutes());
            ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
            Date output = Date.from(zdt.toInstant());
            entity.setApproveReqEndDate(output);
            if (CommonUtil.isNotBlankStr(projAttendceApprTO.getFromDate())) {
                entity.setFromDate(CommonUtil.convertStringToDate(projAttendceApprTO.getFromDate()));
            }
            if (CommonUtil.isNotBlankStr(projAttendceApprTO.getToDate())) {
                entity.setToDate(CommonUtil.convertStringToDate(projAttendceApprTO.getToDate()));
            }
            entity.setType(projAttendceApprTO.getType());
            entity.setStatus(projAttendceApprTO.getStatus());
            projAttendceApprEntites.add(entity);
        }
        return projAttendceApprEntites;
    }

}
