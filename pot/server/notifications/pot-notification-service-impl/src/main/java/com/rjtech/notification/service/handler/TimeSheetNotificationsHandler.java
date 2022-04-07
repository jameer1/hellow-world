package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.TimeSheetNotificationsTO;
//import com.rjtech.notification.model.ProjCrewMstrEntity;
import com.rjtech.notification.model.TimeSheetAdditionalTimeEntity;
import com.rjtech.notification.model.TimeSheetNotificationsEntity;
import com.rjtech.notification.repository.TimeSheetRepositoryCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.notification.timemanagement.timesheet.model.TimeSheetEntityCopy;
import com.rjtech.register.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;

public class TimeSheetNotificationsHandler {
    private TimeSheetNotificationsHandler() {
    }

    public static TimeSheetNotificationsTO convertEntityToPOJO(
            TimeSheetNotificationsEntity timeSheetNotificationsEntity) {
        TimeSheetNotificationsTO timeSheetNotificationsTO = new TimeSheetNotificationsTO();
        if (null != timeSheetNotificationsEntity) {
            TimeSheetEntity timeSheetEntity = timeSheetNotificationsEntity.getTimeSheetEntity();
            if (null != timeSheetEntity) {
                if (null != timeSheetEntity.getProjMstrEntity()) {
                    timeSheetNotificationsTO.setProjId(timeSheetEntity.getProjMstrEntity().getProjectId());
                    timeSheetNotificationsTO.setProjName(timeSheetEntity.getProjMstrEntity().getProjName());
                    timeSheetNotificationsTO
                            .setEpsName(timeSheetEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
                }
                if (CommonUtil.isNotBlankDate(timeSheetNotificationsEntity.getDate()))
                    timeSheetNotificationsTO
                            .setDate(CommonUtil.convertDateToString(timeSheetNotificationsEntity.getDate()));
                ClientRegEntity clientRegEntity = timeSheetNotificationsEntity.getClientId();
                if (null != clientRegEntity) {
                    timeSheetNotificationsTO.setClientId(clientRegEntity.getClientId());
                }
                if (null != timeSheetEntity.getReqUserMstrEntity()) {
                    timeSheetNotificationsTO.setFromUserName(timeSheetEntity.getReqUserMstrEntity().getUserName());
                }
                if (null != timeSheetEntity.getApprUserMstrEntity()) {
                    timeSheetNotificationsTO.setToUserName(timeSheetEntity.getApprUserMstrEntity().getUserName());
                }
                timeSheetNotificationsTO
                        .setWeeakCommencingDay(CommonUtil.convertDateToString(timeSheetEntity.getWeekStartDate()));
                timeSheetNotificationsTO.setTimeSheetNumber(generateTimeSheetCode(timeSheetEntity));
                timeSheetNotificationsTO.setReqComments(timeSheetEntity.getReqComments());
                timeSheetNotificationsTO.setApprComments(timeSheetEntity.getReqComments());
            }
            timeSheetNotificationsTO.setId(timeSheetNotificationsEntity.getId());
            timeSheetNotificationsTO
                    .setNotificationNumber(TimeSheetNotificationsHandler.generateCode(timeSheetNotificationsEntity));
            timeSheetNotificationsTO.setNotificationStatus(timeSheetNotificationsEntity.getNotificationStatus());
            timeSheetNotificationsTO.setNotificationMsg(timeSheetNotificationsEntity.getNotificationMsg());
            timeSheetNotificationsTO.setStatus(timeSheetNotificationsEntity.getStatus());
        }

        return timeSheetNotificationsTO;
    }

    public static List<TimeSheetNotificationsEntity> convertPOJOToEntity(
            List<TimeSheetNotificationsTO> timeSheetNotificationsTOs, TimeSheetRepositoryCopy timeSheetRepository) {
        List<TimeSheetNotificationsEntity> timeSheetNotificationsEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (TimeSheetNotificationsTO timeSheetNotificationsTO : timeSheetNotificationsTOs) {

            timeSheetNotificationsEntites.add(convertPOJOToEntity(timeSheetNotificationsTO, timeSheetRepository));
        }
        return timeSheetNotificationsEntites;
    }

    public static TimeSheetNotificationsEntity convertPOJOToEntity(TimeSheetNotificationsTO timeSheetNotificationsTO,
            TimeSheetRepositoryCopy timeSheetRepository) {

        TimeSheetNotificationsEntity timeSheetNotificationsEntity = new TimeSheetNotificationsEntity();
        if (null != (timeSheetNotificationsTO.getTimeSheetId())) {
            TimeSheetEntity timeSheetEntity = timeSheetRepository
                    .findOne(timeSheetNotificationsTO.getTimeSheetId());
            if (null != timeSheetEntity) {
                timeSheetNotificationsEntity.setTimeSheetEntity(timeSheetEntity);
                if (null != timeSheetNotificationsTO.getReqComments()) {
                    timeSheetEntity.setReqComments(timeSheetNotificationsTO.getReqComments());
                }
                if (null != timeSheetNotificationsTO.getApprComments()) {
                    timeSheetEntity.setApprComments(timeSheetNotificationsTO.getApprComments());
                }
            }
            timeSheetNotificationsEntity.setDate(new Date());
            timeSheetNotificationsEntity.setNotificationStatus(timeSheetNotificationsTO.getNotificationStatus());
            timeSheetNotificationsEntity.setNotificationStatus(timeSheetNotificationsTO.getNotificationStatus());
            timeSheetNotificationsEntity.setStatus(timeSheetNotificationsTO.getStatus());
            timeSheetNotificationsEntity.setNotificationMsg(timeSheetNotificationsTO.getNotificationMsg());
        }
        return timeSheetNotificationsEntity;

    }

    public static String generateCode(TimeSheetNotificationsEntity timeSheetNotificationsEntity) {
        return ModuleCodesPrefixes.TIME_SHEET_PREFIX.getDesc().concat("-")
                .concat(timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity().getCode()).concat("-")
                .concat(String.valueOf(timeSheetNotificationsEntity.getId()));
    }

    public static String generateTimeSheetCode(TimeSheetEntity timeSheetEntity) {
        Integer additional = timeSheetEntity.getAdditional();
        EmpRegisterDtlEntity emp = timeSheetEntity.getEmpRegisterDtlEntity();
        StringBuilder code = new StringBuilder();
        if (emp == null && additional != null && additional > 0) {
            code.append("A");
        }
        code.append("TS-");
        code.append(timeSheetEntity.getProjMstrEntity().getCode());
        code.append("-");
        if (emp == null) {
            ProjCrewMstrEntity crew = timeSheetEntity.getProjCrewMstrEntity();
            if (crew != null)
                code.append(crew.getCode());
        } else {
            code.append(emp.getCode());
        }
        code.append("-");
        code.append(AppUtils.formatNumberToString(timeSheetEntity.getId()));
        return code.toString();
    }

    public static String generateCode(TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity, String projCode) {
        return ModuleCodesPrefixes.TIME_SHEET_PREFIX.getDesc().concat("-")
                .concat(projCode).concat("-")
                .concat(String.valueOf(timeSheetAdditionalTimeEntity.getId()));
    }
}
