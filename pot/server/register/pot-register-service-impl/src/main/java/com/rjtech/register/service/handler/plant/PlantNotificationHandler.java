package com.rjtech.register.service.handler.plant;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.register.plant.dto.PlantNotificationsTO;
import com.rjtech.register.plant.model.PlantNotificationsEntity;

public class PlantNotificationHandler {

    public static PlantNotificationsTO convertEntityToPOJO(PlantNotificationsEntity plantNotificationsEntity) {
        PlantNotificationsTO plantNotificationsTO = new PlantNotificationsTO();

        plantNotificationsTO.setId(plantNotificationsEntity.getId());
        if (CommonUtil.isNotBlankDate(plantNotificationsEntity.getDate())) {
            plantNotificationsTO.setDate(CommonUtil.convertDateToString(plantNotificationsEntity.getDate()));
        }
        plantNotificationsTO.setCode(generateNotifyCode(plantNotificationsEntity));
        plantNotificationsTO.setProjId(
                (plantNotificationsEntity.getProjMstrEntity() != null) ? plantNotificationsEntity.getProjMstrEntity().getProjectId()
                        : null);
        plantNotificationsTO.setApprUserId((plantNotificationsEntity.getApprUserId() != null)
                ? plantNotificationsEntity.getApprUserId().getUserId()
                : null);
        plantNotificationsTO.setReqUserId(
                (plantNotificationsEntity.getReqUserId() != null) ? plantNotificationsEntity.getReqUserId().getUserId()
                        : null);
        plantNotificationsTO.setNotificationStatus(plantNotificationsEntity.getNotificationStatus());

        plantNotificationsTO.setStatus(plantNotificationsEntity.getStatus());
        return plantNotificationsTO;
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
