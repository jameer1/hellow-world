package com.rjtech.notification.service.handler;

import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.notification.model.PlantNotificationsEntityCopy;

public class PlantNotificationsHandlerCopy {
    private PlantNotificationsHandlerCopy() {
    }

    public static String generateNotifyCode(PlantNotificationsEntityCopy entity) {
        return ModuleCodesPrefixes.PLANT_REG_PREFIX.getDesc().concat("-")
                .concat(ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()).concat("-")
                .concat(entity.getProjId().getCode()).concat("-").concat(String.valueOf(entity.getId()));
    }

    public static String generateNotifyReqCode(PlantNotificationsEntityCopy entity) {
        return ModuleCodesPrefixes.PLANT_REG_PREFIX.getDesc().concat("-")
                .concat(ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()).concat("-").concat(entity.getProjId().getCode())
                .concat("-").concat(String.valueOf(entity.getId()));
    }
}
