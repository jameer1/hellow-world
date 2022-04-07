package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.register.emp.dto.EmpNotificationsTO;
import com.rjtech.register.emp.model.EmpNotificationsEntity;

public class EmpNotificationsHandler {

    public static EmpNotificationsTO convertEntityToPOJO(EmpNotificationsEntity empNotificationsEntity) {

        EmpNotificationsTO empNotificationsTO = new EmpNotificationsTO();
        empNotificationsTO.setId(empNotificationsEntity.getId());
        if (CommonUtil.isNotBlankDate(empNotificationsEntity.getDate())) {
            empNotificationsTO.setDate(CommonUtil.convertDateToString(empNotificationsEntity.getDate()));
        }
        empNotificationsTO.setCode(generateCode(empNotificationsEntity));
        if (CommonUtil.objectNotNull(empNotificationsEntity.getApprUserId()))
            empNotificationsTO.setApprUserId(empNotificationsEntity.getApprUserId().getUserId());
        empNotificationsTO.setReqUserId(empNotificationsEntity.getReqUserId().getUserId());
        empNotificationsTO.setForProject(empNotificationsEntity.getForProject());
        empNotificationsTO.setNotificationStatus(empNotificationsEntity.getNotificationStatus());

        empNotificationsTO.setStatus(empNotificationsEntity.getStatus());
        return empNotificationsTO;
    }

    private static String generateCode(EmpNotificationsEntity employeeNotificationsEntity) {
        return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                .concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()
                        .concat("-" + employeeNotificationsEntity.getProjMstrEntity().getCode())
                        .concat("-" + employeeNotificationsEntity.getId()));
    }
}
