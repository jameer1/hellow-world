package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.EmployeeLeaveNotificationsTO;
import com.rjtech.notification.model.EmployeeNotificationsEntity;

public class EmployeeLeaveNotificationsHandler {

    public static EmployeeLeaveNotificationsTO convertEntityToPOJO(
            EmployeeNotificationsEntity employeeNotificationsEntity) {

        EmployeeLeaveNotificationsTO employeeLeaveNotificationsTO = new EmployeeLeaveNotificationsTO();
        employeeLeaveNotificationsTO.setId(employeeNotificationsEntity.getId());
        if (CommonUtil.isNotBlankDate(employeeNotificationsEntity.getDate())) {
            employeeLeaveNotificationsTO.setDate(CommonUtil.convertDateToString(employeeNotificationsEntity.getUpdatedOn()));
        }
        employeeLeaveNotificationsTO.setCode(generateCode(employeeNotificationsEntity));

        UserMstrEntity userMstrEntity = employeeNotificationsEntity.getApprUserId();
        UserMstrEntity userMstrReqEntity = employeeNotificationsEntity.getReqUserId();
        ProjMstrEntity projMstrEntity = employeeNotificationsEntity.getProjMstrEntity();
        if (null != userMstrEntity) {
            employeeLeaveNotificationsTO.setApprUserId(userMstrEntity.getUserId());
            employeeLeaveNotificationsTO.setToUserName(employeeNotificationsEntity.getApprUserId().getDisplayName());
        }
        if (null != userMstrReqEntity) {
            employeeLeaveNotificationsTO.setReqUserId(userMstrReqEntity.getUserId());
            employeeLeaveNotificationsTO.setFromUserName(employeeNotificationsEntity.getReqUserId().getDisplayName());
        }
        if (null != projMstrEntity) {
            employeeLeaveNotificationsTO.setProjId(projMstrEntity.getProjectId());
        }
        
        employeeLeaveNotificationsTO.setNotifyStatus(employeeNotificationsEntity.getNotifyStatus());
        employeeLeaveNotificationsTO.setType(employeeNotificationsEntity.getType());
        employeeLeaveNotificationsTO.setStatus(employeeNotificationsEntity.getStatus());
        employeeLeaveNotificationsTO.setRequistionCode(generateReqCode(employeeNotificationsEntity));
        return employeeLeaveNotificationsTO;
    }

    public static List<EmployeeNotificationsEntity> convertPOJOToEntity(
            List<EmployeeLeaveNotificationsTO> employeeLeaveNotificationsTOs) {
        List<EmployeeNotificationsEntity> employeeNotificationsEntities = new ArrayList<EmployeeNotificationsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (EmployeeLeaveNotificationsTO employeeLeaveNotificationsTO : employeeLeaveNotificationsTOs) {
            employeeNotificationsEntities.add(convertPOJOToEntity(employeeLeaveNotificationsTO));
        }
        return employeeNotificationsEntities;
    }

    public static EmployeeNotificationsEntity convertPOJOToEntity(
            EmployeeLeaveNotificationsTO employeeLeaveNotificationsTO) {
        EmployeeNotificationsEntity employeeNotificationsEntity = new EmployeeNotificationsEntity();
        if (CommonUtil.isNonBlankLong(employeeLeaveNotificationsTO.getId())) {
            employeeNotificationsEntity.setId(employeeLeaveNotificationsTO.getId());
        }

        employeeNotificationsEntity.setNotifyStatus(employeeLeaveNotificationsTO.getNotifyStatus());
        employeeNotificationsEntity.setStatus(employeeLeaveNotificationsTO.getStatus());
        return employeeNotificationsEntity;
    }

    public static String generateCode(EmployeeNotificationsEntity employeeNotificationsEntity) {
        return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                .concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()
                        .concat("-" + employeeNotificationsEntity.getProjMstrEntity().getCode())
                        .concat("-" + employeeNotificationsEntity.getId()));
    }

    public static String generateReqCode(EmployeeNotificationsEntity employeeNotificationsEntity) {
        return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                .concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()
                        .concat("-" + employeeNotificationsEntity.getProjMstrEntity().getCode())
                        .concat("-" + employeeNotificationsEntity.getId()));
    }

}
