package com.rjtech.notification.service.handler;

import java.util.Date;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.PlantNotificationsTO;
import com.rjtech.notification.model.PlantNotificationsEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class PlantNotificationsHandler {

    public static PlantNotificationsTO convertEntityToPOJO(PlantNotificationsEntity plantNotificationsEntity) {
    	
        PlantNotificationsTO plantNotificationsTO = new PlantNotificationsTO();
        plantNotificationsTO.setId(plantNotificationsEntity.getId());
        if (CommonUtil.isNotBlankDate(plantNotificationsEntity.getDate())) {
            plantNotificationsTO.setDate(CommonUtil.convertDateToString(plantNotificationsEntity.getUpdatedOn()));
        }
        plantNotificationsTO.setCode(generateNotifyCode(plantNotificationsEntity));
        
        UserMstrEntity userMstrEntity = plantNotificationsEntity.getApprUserId();
        UserMstrEntity userMstrReqEntity = plantNotificationsEntity.getReqUserId();
        ProjMstrEntity projMstrEntity = plantNotificationsEntity.getProjMstrEntity();
        Long forProjMstrEntity = plantNotificationsEntity.getForProject();
        if (null != userMstrEntity) {
            plantNotificationsTO.setApprUserId(userMstrEntity.getUserId());
            plantNotificationsTO.setToUserName(plantNotificationsEntity.getApprUserId().getDisplayName());
        }
        if (null != userMstrReqEntity) {
            plantNotificationsTO.setReqUserId(userMstrReqEntity.getUserId());
            plantNotificationsTO.setFromUserName(plantNotificationsEntity.getReqUserId().getDisplayName());
        }
        if (null != projMstrEntity) {
        	plantNotificationsTO.setProjId(projMstrEntity.getProjectId());
        }
        if (null != forProjMstrEntity) {
        	plantNotificationsTO.setForProject(forProjMstrEntity);
        }
        
        plantNotificationsTO.setNotificationStatus(plantNotificationsEntity.getNotificationStatus());
        plantNotificationsTO.setType(plantNotificationsEntity.getType());
        plantNotificationsTO.setReqComments(plantNotificationsEntity.getComments());
        plantNotificationsTO.setStatus(plantNotificationsEntity.getStatus());
        plantNotificationsTO.setRequistionCode(generateNotifyReqCode(plantNotificationsEntity));
        return plantNotificationsTO;
    }

    public static PlantNotificationsEntity convertPOJOToEntity(PlantNotificationsTO plantNotificationsTO,
            EPSProjRepository epsProjRepository, LoginRepository loginRepository) {
        PlantNotificationsEntity plantNotificationsEntity = new PlantNotificationsEntity();

        if (CommonUtil.isNotBlankStr(plantNotificationsTO.getDate())) {
            plantNotificationsEntity.setDate(CommonUtil.convertDDMMYYYYStringToDate(plantNotificationsTO.getDate()));
        } else {
            plantNotificationsEntity.setDate(new Date());
        }

        if (CommonUtil.isNonBlankLong(plantNotificationsTO.getProjId())) {
            ProjMstrEntity projMstr = epsProjRepository.findOne(plantNotificationsTO.getProjId());
            plantNotificationsEntity.setProjMstrEntity(projMstr);
        }
        if (CommonUtil.isNonBlankLong(plantNotificationsTO.getForProject())) {
            ProjMstrEntity forProject = epsProjRepository.findOne(plantNotificationsTO.getForProject());
            plantNotificationsEntity.setForProject(forProject.getProjectId());
        }
        if (CommonUtil.isNonBlankLong(plantNotificationsTO.getApprUserId())) {
            UserMstrEntity apprUserId = loginRepository.findOne(plantNotificationsTO.getApprUserId());
            plantNotificationsEntity.setApprUserId(apprUserId);
        }
        if (CommonUtil.isNonBlankLong(AppUserUtils.getUserId())) {
            UserMstrEntity reqUserId = loginRepository.findOne(AppUserUtils.getUserId());
            plantNotificationsEntity.setReqUserId(reqUserId);
        }
        plantNotificationsEntity.setNotificationStatus(plantNotificationsTO.getNotificationStatus());
        plantNotificationsEntity.setNotificationMsg("Additional Time for Request");
        plantNotificationsEntity.setStatus(plantNotificationsTO.getStatus());
        plantNotificationsEntity.setComments(plantNotificationsTO.getReqComments());
        return plantNotificationsEntity;
    }

    public static String generateNotifyCode(PlantNotificationsEntity entity) {
        return ModuleCodesPrefixes.PLANT_REG_PREFIX.getDesc().concat("-")
                .concat(ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()).concat("-")
                .concat(entity.getProjMstrEntity().getCode()).concat("-").concat(String.valueOf(entity.getId()));
    }

    public static String generateNotifyReqCode(PlantNotificationsEntity entity) {
        return ModuleCodesPrefixes.PLANT_REG_PREFIX.getDesc().concat("-")
                .concat(ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()).concat("-").concat(entity.getProjMstrEntity().getCode())
                .concat("-").concat(String.valueOf(entity.getId()));
    }
}
