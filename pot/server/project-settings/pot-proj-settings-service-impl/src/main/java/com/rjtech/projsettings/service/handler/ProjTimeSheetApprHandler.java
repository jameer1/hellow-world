package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.model.TimeSheetNotificationsEntity;
import com.rjtech.notification.repository.TimeSheetNotificationsRepositoryCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projsettings.dto.ProjTimeSheetApprTO;
import com.rjtech.projsettings.model.TimeSheetAddtionalTimeApprEntity;

public class ProjTimeSheetApprHandler {

    public static List<TimeSheetAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjTimeSheetApprTO> projTimeSheetApprEntity, ProjCrewRepositoryCopy projCrewRepository,
            TimeSheetNotificationsRepositoryCopy timeSheetNotificationsRepository, LoginRepository loginRepository) {
        List<TimeSheetAddtionalTimeApprEntity> projTimeSheetApprEntites = new ArrayList<TimeSheetAddtionalTimeApprEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        TimeSheetAddtionalTimeApprEntity entity = null;
        for (ProjTimeSheetApprTO projTimeSheetApprTO : projTimeSheetApprEntity) {
            entity = new TimeSheetAddtionalTimeApprEntity();

            if (CommonUtil.isNonBlankLong(projTimeSheetApprTO.getId())) {
                entity.setId(projTimeSheetApprTO.getId());
            }
            if (CommonUtil.isNonBlankLong(projTimeSheetApprTO.getNotificationId())) {
                TimeSheetNotificationsEntity timeSheetNotificationsEntity = timeSheetNotificationsRepository
                        .findOne(projTimeSheetApprTO.getNotificationId());
                entity.setTimeSheetNotificationsEntity(timeSheetNotificationsEntity);
            }

            //  entity.setProjTimeSheetEntity(projTimeSheetApprTO.getTimeSheetId());
            entity.setLatest(true);
            if (CommonUtil.isNotBlankStr(projTimeSheetApprTO.getTimeSheetDate())) {
                entity.setTimeSheetDate(CommonUtil.convertStringToDate(projTimeSheetApprTO.getTimeSheetDate()));
            }

            if (CommonUtil.isNotBlankStr(projTimeSheetApprTO.getTimeSheetEndDate())) {
                entity.setTimeSheetEndDate(CommonUtil.convertStringToDate(projTimeSheetApprTO.getTimeSheetEndDate()));
            }

            entity.setGrantHrs(projTimeSheetApprTO.getGrantHrs());
            entity.setTimeSheetNo(Long.valueOf(projTimeSheetApprTO.getTimeSheetNo()));
            if (CommonUtil.isNonBlankLong(projTimeSheetApprTO.getApprUserId())) {
                UserMstrEntity apprUserMstrEntity = loginRepository.findOne(projTimeSheetApprTO.getApprUserId());
                entity.setApprUserMstrEntity(apprUserMstrEntity);
            }
            entity.setNotification(projTimeSheetApprTO.getNotification());
            entity.setCrewType(projTimeSheetApprTO.getCrewType());
            entity.setStatus(projTimeSheetApprTO.getStatus());

            ProjCrewMstrEntity projCrewMstrEntity = projCrewRepository
                    .findCrewByCrewCodeAndProjId(projTimeSheetApprTO.getProjId(), projTimeSheetApprTO.getCrewName());
            entity.setProjCrewMasterEntity(projCrewMstrEntity);
            projTimeSheetApprEntites.add(entity);

            projTimeSheetApprEntites.add(entity);
        }
        return projTimeSheetApprEntites;
    }

}
