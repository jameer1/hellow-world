package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.ReqApprNotificationTO;
import com.rjtech.notification.model.ReqApprNotificationEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ReqApprNotificationHandler {

    public static ReqApprNotificationTO convertEntityToPOJO(ReqApprNotificationEntity notifictaionEntity) {
        ReqApprNotificationTO notificationTO = new ReqApprNotificationTO();
        notificationTO.setId(notifictaionEntity.getId());

        notificationTO.setReqCode(notifictaionEntity.getReqCode());
        notificationTO.setNotifyCode(notifictaionEntity.getNotifyCode());
        notificationTO.setCategory(notifictaionEntity.getCategory());
        notificationTO.setStage(notifictaionEntity.getStage());
        ProjMstrEntity project = notifictaionEntity.getProjId();
        if (project != null)
            notificationTO.setProjId(project.getProjectId());
        UserMstrEntity reqUser = notifictaionEntity.getReqUserId();
        if (reqUser != null)
            notificationTO.setReqUserId(reqUser.getUserId());
        UserMstrEntity apprUser = notifictaionEntity.getApprUserId();
        if (apprUser != null)
            notificationTO.setApprUserId(apprUser.getUserId());
        ClientRegEntity client = notifictaionEntity.getClientId();
        if (client != null)
            notificationTO.setClientId(client.getClientId());
        notificationTO.setStatus(notifictaionEntity.getStatus());

        return notificationTO;
    }

    public static List<ReqApprNotificationEntity> convertPOJOToEntity(List<ReqApprNotificationTO> notificationTOs,
            LoginRepository loginRepository, EPSProjRepository epsProjRepository) {
        List<ReqApprNotificationEntity> notifictationEntites = new ArrayList<ReqApprNotificationEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ReqApprNotificationTO notificationTO : notificationTOs) {

            notifictationEntites.add(convertPOJOToEntity(notificationTO, loginRepository, epsProjRepository));
        }
        return notifictationEntites;
    }

    public static ReqApprNotificationEntity convertPOJOToEntity(ReqApprNotificationTO notificationTO,
            LoginRepository loginRepository, EPSProjRepository epsProjRepository) {

        ReqApprNotificationEntity notifictaionEntity = new ReqApprNotificationEntity();
        if (CommonUtil.isNonBlankLong(notificationTO.getId())) {
            notifictaionEntity.setId(notificationTO.getId());
        }
        notifictaionEntity.setReqCode(notificationTO.getCode());
        notifictaionEntity.setNotifyCode(notificationTO.getNotifyCode());
        notifictaionEntity.setCategory(notificationTO.getCategory());
        notifictaionEntity.setStage(notificationTO.getStage());
        notifictaionEntity.setReqUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        notifictaionEntity.setApprUserId(loginRepository.findOne(notificationTO.getApprUserId()));
        notifictaionEntity.setProjId(epsProjRepository.findOne(notificationTO.getProjId()));
        notifictaionEntity.setStatus(notificationTO.getStatus());

        return notifictaionEntity;
    }
}
