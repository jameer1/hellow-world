package com.rjtech.register.service.handler.material;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.material.dto.MaterialNotificationsTO;
import com.rjtech.register.material.model.MaterialNotificationsEntity;

public class MaterialNotificationsHandler {

    public static MaterialNotificationsTO convertEntityToPOJO(MaterialNotificationsEntity materialNotificationsEntity) {

        MaterialNotificationsTO materialNotificationsTO = new MaterialNotificationsTO();
        materialNotificationsTO.setId(materialNotificationsEntity.getId());
        ProjMstrEntity proj = materialNotificationsEntity.getProjId();
        materialNotificationsTO.setProjId(proj.getProjectId());
        if (CommonUtil.isNotBlankDate(materialNotificationsEntity.getDate())) {
            materialNotificationsTO.setDate(CommonUtil.convertDateToString(materialNotificationsEntity.getDate()));
        }
        materialNotificationsTO.setCode(ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                + proj.getCode() + "-"
                + materialNotificationsEntity.getId());
        materialNotificationsTO.setApprUserId(materialNotificationsEntity.getApprUserId().getUserId());
        materialNotificationsTO.setReqUserId(materialNotificationsEntity.getReqUserId().getUserId());
        materialNotificationsTO.setNotifyStatus(materialNotificationsEntity.getNotificationStatus());

        materialNotificationsTO.setStatus(materialNotificationsEntity.getStatus());
        return materialNotificationsTO;
    }
}
