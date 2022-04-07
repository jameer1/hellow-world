package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.AttendenceNotificationsTO;
import com.rjtech.notification.model.AttendenceNotificationsEntity;
//import com.rjtech.notification.timemanagement.workdairy.repository.ProjCrewMstrRepositoryCopy;
import com.rjtech.notification.repository.ProjCrewMstrRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class AttendenceNotificationsHandler {

    private AttendenceNotificationsHandler() {
    }

    public static AttendenceNotificationsTO convertEntityToPOJO(
            AttendenceNotificationsEntity attendenceNotificationsEntity) {
        AttendenceNotificationsTO attendenceNotificationsTO = new AttendenceNotificationsTO();
        attendenceNotificationsTO.setId(attendenceNotificationsEntity.getId());
        if (CommonUtil.isNotBlankDate(attendenceNotificationsEntity.getDate())) {
            attendenceNotificationsTO.setDate(CommonUtil.convertDateToString(attendenceNotificationsEntity.getDate()));
        }
        attendenceNotificationsTO.setName(attendenceNotificationsEntity.getProjCrewMstrEntity().getDesc());
        attendenceNotificationsTO.setParentName(attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId()
                .getParentProjectMstrEntity().getProjName());
        attendenceNotificationsTO.setModuleCode(attendenceNotificationsEntity.getType().concat("-Notification"));
        attendenceNotificationsTO.setCode(generateNotificationNumber(attendenceNotificationsEntity));
        attendenceNotificationsTO.setCrewName(attendenceNotificationsEntity.getProjCrewMstrEntity().getDesc());
        attendenceNotificationsTO.setAttendenceMonth(CommonUtil.getMonth(attendenceNotificationsEntity.getCreatedOn()));
        attendenceNotificationsTO.setType(attendenceNotificationsEntity.getType());
        if (CommonUtil.isNotBlankDate(attendenceNotificationsEntity.getFromDate())) {
            attendenceNotificationsTO
                    .setFromDate(CommonUtil.convertDateToString(attendenceNotificationsEntity.getFromDate()));
        }
        if (CommonUtil.isNotBlankDate(attendenceNotificationsEntity.getToDate())) {
            attendenceNotificationsTO
                    .setToDate(CommonUtil.convertDateToString(attendenceNotificationsEntity.getToDate()));
        }
        if (null != attendenceNotificationsEntity.getFromUserId()) {
            attendenceNotificationsTO.setFromUserName(attendenceNotificationsEntity.getFromUserId().getUserName());
        }
        if (null != attendenceNotificationsEntity.getToUserId()) {
            attendenceNotificationsTO.setToUserName(attendenceNotificationsEntity.getToUserId().getUserName());
        }
        attendenceNotificationsTO.setNotificationStatus(attendenceNotificationsEntity.getNotificationStatus());
        attendenceNotificationsTO.setReqComments(attendenceNotificationsEntity.getReqComments());
        return attendenceNotificationsTO;
    }

    public static List<AttendenceNotificationsEntity> convertPOJOToEntity(
            List<AttendenceNotificationsTO> attendenceNotificationsTOs, LoginRepository loginRepository,
            ProjCrewMstrRepositoryCopy projCrewMstrRepository) {
        List<AttendenceNotificationsEntity> attendenceNotificationsEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (AttendenceNotificationsTO attendenceNotificationsTO : attendenceNotificationsTOs) {
            attendenceNotificationsEntites
                    .add(convertPOJOToEntity(attendenceNotificationsTO, loginRepository, projCrewMstrRepository));
        }
        return attendenceNotificationsEntites;
    }

    public static AttendenceNotificationsEntity convertPOJOToEntity(AttendenceNotificationsTO attendenceNotificationsTO,
            LoginRepository loginRepository, ProjCrewMstrRepositoryCopy projCrewMstrRepository) {

        AttendenceNotificationsEntity attendenceNotificationsEntity = new AttendenceNotificationsEntity();
        attendenceNotificationsEntity
                .setProjCrewMstrEntity(projCrewMstrRepository.findOne(attendenceNotificationsTO.getCrewId()));
        if (CommonUtil.isNotBlankStr(attendenceNotificationsTO.getFromDate())) {
            attendenceNotificationsEntity
                    .setFromDate(CommonUtil.convertStringToDate(attendenceNotificationsTO.getFromDate()));
        }
        if (CommonUtil.isNotBlankStr(attendenceNotificationsTO.getToDate())) {
            attendenceNotificationsEntity
                    .setToDate(CommonUtil.convertStringToDate(attendenceNotificationsTO.getToDate()));
        }

        attendenceNotificationsEntity.setFromUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        attendenceNotificationsEntity.setToUserId(loginRepository.findOne(attendenceNotificationsTO.getToUserId()));
        attendenceNotificationsEntity.setDate(new Date());
        attendenceNotificationsEntity.setType(attendenceNotificationsTO.getType());
        attendenceNotificationsEntity.setReqComments(attendenceNotificationsTO.getReqComments());
        attendenceNotificationsEntity.setNotificationStatus(attendenceNotificationsTO.getNotificationStatus());
        attendenceNotificationsEntity.setNotificationMsg(attendenceNotificationsTO.getNotificationMsg());
        attendenceNotificationsEntity.setStatus(attendenceNotificationsTO.getStatus());
        return attendenceNotificationsEntity;
    }

    public static String generateNotificationNumber(AttendenceNotificationsEntity entity) {
        String code = null;
        if (CommonConstants.EMP.equalsIgnoreCase(entity.getType())) {
            code = ModuleCodesPrefixes.EMP_ATTENDENCE_PREFIX.getDesc().concat("-")
                    .concat(entity.getProjCrewMstrEntity().getProjId().getCode()).concat("-")
                    .concat(String.valueOf(entity.getId()));
        } else if (CommonConstants.PLANT.equalsIgnoreCase(entity.getType())) {
            code = ModuleCodesPrefixes.PLANT_ATTENDENCE_PREFIX.getDesc().concat("-")
                    .concat(entity.getProjCrewMstrEntity().getProjId().getCode()).concat("-")
                    .concat(String.valueOf(entity.getId()));
        }
        return code;
    }

    public static String generateModuleCode(AttendenceNotificationsEntity attendenceNotificationsEntity) {
        return ModuleCodesPrefixes.EMP_ATTENDENCE_PREFIX.getDesc().concat("-")
                .concat(attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId().getCode()).concat("-")
                .concat(String.valueOf(attendenceNotificationsEntity.getId()));

    }
}
