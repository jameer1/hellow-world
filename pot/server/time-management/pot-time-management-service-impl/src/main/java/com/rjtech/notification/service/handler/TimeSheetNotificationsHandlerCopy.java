package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.TimeSheetNotificationsTO;
import com.rjtech.notification.model.TimeSheetNotificationsEntity;
//import com.rjtech.notification.model.TimeSheetNotificationsEntityCopy;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetRepository;

public class TimeSheetNotificationsHandlerCopy {

    public static TimeSheetNotificationsTO convertEntityToPOJO(
            TimeSheetNotificationsEntity timeSheetNotificationsEntity) {
        TimeSheetNotificationsTO timeSheetNotificationsTO = new TimeSheetNotificationsTO();
        if (CommonUtil.isNotBlankDate(timeSheetNotificationsEntity.getDate())) {
            timeSheetNotificationsTO.setDate(CommonUtil.convertDateToString(timeSheetNotificationsEntity.getDate()));
        }
        if (null != timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity()) {
            timeSheetNotificationsTO
                    .setProjId(timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity().getProjectId());
        }
        timeSheetNotificationsTO
                .setCode(timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity().getCode());
        if (null != timeSheetNotificationsEntity.getTimeSheetEntity().getReqUserMstrEntity()) {
            timeSheetNotificationsTO.setFromUserId(
                    timeSheetNotificationsEntity.getTimeSheetEntity().getReqUserMstrEntity().getUserId());
        }
        if (null != timeSheetNotificationsEntity.getTimeSheetEntity().getApprUserMstrEntity()) {
            timeSheetNotificationsTO
                    .setToUserId(timeSheetNotificationsEntity.getTimeSheetEntity().getApprUserMstrEntity().getUserId());
        }
        timeSheetNotificationsTO.setReqComments(timeSheetNotificationsEntity.getTimeSheetEntity().getReqComments());
        timeSheetNotificationsTO.setApprComments(timeSheetNotificationsEntity.getTimeSheetEntity().getApprComments());
        timeSheetNotificationsTO
                .setTimeSheetNumber(TimeSheetNotificationsHandlerCopy.generateCode(timeSheetNotificationsEntity));
        if (CommonUtil.isNotBlankDate(timeSheetNotificationsEntity.getTimeSheetEntity().getWeekStartDate())) {
            timeSheetNotificationsTO.setWeeakCommencingDay(CommonUtil
                    .convertDateToString(timeSheetNotificationsEntity.getTimeSheetEntity().getWeekStartDate()));
        }
        timeSheetNotificationsTO.setNotificationStatus(timeSheetNotificationsEntity.getNotificationStatus());
        timeSheetNotificationsTO.setStatus(timeSheetNotificationsEntity.getStatus());

        return timeSheetNotificationsTO;
    }

    public static String generateCode(TimeSheetNotificationsEntity timeSheetNotificationsEntity) {
        return ModuleCodesPrefixes.TIME_SHEET_PREFIX.getDesc().concat("-")
                .concat(timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity().getCode()).concat("-")
                .concat(String.valueOf(timeSheetNotificationsEntity.getId()));
    }

    public static List<TimeSheetNotificationsEntity> convertPOJOToEntity(
            List<TimeSheetNotificationsTO> timeSheetNotificationsTOs, TimeSheetRepository timeSheetRepository) {
        List<TimeSheetNotificationsEntity> timeSheetNotificationsEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (TimeSheetNotificationsTO timeSheetNotificationsTO : timeSheetNotificationsTOs) {

            timeSheetNotificationsEntites.add(convertPOJOToEntity(timeSheetNotificationsTO, timeSheetRepository));
        }
        return timeSheetNotificationsEntites;
    }

    public static TimeSheetNotificationsEntity convertPOJOToEntity(
            TimeSheetNotificationsTO timeSheetNotificationsTO, TimeSheetRepository timeSheetRepository) {

        TimeSheetNotificationsEntity timeSheetNotificationsEntity = new TimeSheetNotificationsEntity();
        if (timeSheetNotificationsTO.getTimeSheetId() != null) {
            timeSheetNotificationsEntity
                    .setTimeSheetEntity(timeSheetRepository.findOne(timeSheetNotificationsTO.getTimeSheetId()));
        }

        //sending messages to the Notification Entity
        timeSheetNotificationsEntity.setDate(new Date());
        timeSheetNotificationsEntity.setNotificationMsg(timeSheetNotificationsTO.getNotificationMsg());
        timeSheetNotificationsEntity.setNotificationStatus(timeSheetNotificationsTO.getNotificationStatus());
        return timeSheetNotificationsEntity;
    }

}
