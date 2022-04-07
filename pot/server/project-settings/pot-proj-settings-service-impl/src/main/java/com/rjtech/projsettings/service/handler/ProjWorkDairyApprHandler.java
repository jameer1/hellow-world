package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
import com.rjtech.notification.repository.WorkDairyNotificationRepositoryCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projsettings.dto.ProjWorkDairyApprTO;
import com.rjtech.projsettings.model.WorkDairyAddtionalTimeApprEntity;
import com.rjtech.projsettings.repository.ProjWorkDairyRepository;

public class ProjWorkDairyApprHandler {

    public static List<WorkDairyAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjWorkDairyApprTO> projWorkDairyApprTOs, ProjCrewRepositoryCopy projCrewRepository,
            ProjWorkDairyRepository projWorkDairyRepository,
            WorkDairyNotificationRepositoryCopy workDairyNotificationRepository, LoginRepository loginRepository) {
        List<WorkDairyAddtionalTimeApprEntity> projWorkDairyApprEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        WorkDairyAddtionalTimeApprEntity entity = null;
        for (ProjWorkDairyApprTO projWorkDairyApprTO : projWorkDairyApprTOs) {
            entity = new WorkDairyAddtionalTimeApprEntity();

            if (CommonUtil.isNonBlankLong(projWorkDairyApprTO.getId())) {
                entity.setId(projWorkDairyApprTO.getId());
            }
            entity.setProjWorkDairyMstrEntity(projWorkDairyRepository.findOne(projWorkDairyApprTO.getWorkDairyId()));
            if (CommonUtil.isNotBlankStr(projWorkDairyApprTO.getFromDate())) {
                entity.setFromDate(CommonUtil.convertStringToDate(projWorkDairyApprTO.getFromDate()));
            }
            if (CommonUtil.isNotBlankStr(projWorkDairyApprTO.getToDate())) {
                entity.setToDate(CommonUtil.convertStringToDate(projWorkDairyApprTO.getToDate()));
            }
            if (CommonUtil.isNonBlankLong(projWorkDairyApprTO.getNotificationId())) {
                WorkDairyNotificationsEntity notificationsEntity = workDairyNotificationRepository
                        .findOne(projWorkDairyApprTO.getNotificationId());
                entity.setWorkDairyNotificationsEntity(notificationsEntity);
            }
            if (CommonUtil.isNonBlankLong(projWorkDairyApprTO.getApprUserId())) {
                UserMstrEntity apprWorkDairy = loginRepository.findOne(projWorkDairyApprTO.getApprUserId());
                entity.setApprUserMstrEntity(apprWorkDairy);
            }
            entity.setLatest(true);
            entity.setNotification(projWorkDairyApprTO.getNotification());
            entity.setStatus(projWorkDairyApprTO.getStatus());
            ProjCrewMstrEntity projCrewMstrEntity = projCrewRepository
                    .findCrewByCrewCodeAndProjId(projWorkDairyApprTO.getProjId(), projWorkDairyApprTO.getCrewName());
            entity.setProjCrewMasterEntity(projCrewRepository.findOne(projCrewMstrEntity.getId()));
            entity.setProjCrewMasterEntity(projCrewMstrEntity);

            projWorkDairyApprEntites.add(entity);
        }
        return projWorkDairyApprEntites;
    }

}
