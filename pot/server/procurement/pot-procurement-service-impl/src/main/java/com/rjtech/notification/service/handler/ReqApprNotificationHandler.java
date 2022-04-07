package com.rjtech.notification.service.handler;

import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.notification.dto.ReqApprNotificationTO;
import com.rjtech.notification.model.ReqApprNotificationEntity;
//import com.rjtech.notification.model.ReqApprNotificationEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ReqApprNotificationHandler {

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
