package com.rjtech.notification.service.handler;

import java.util.Date;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.WorkDairyNotificationsTO;
//import com.rjtech.notification.model.ProjCrewMstrEntity;
//import com.rjtech.notification.model.ProjWorkShiftMstrEntity;
import com.rjtech.notification.model.WorkDairyAdditionalTimeEntity;
//import com.rjtech.notification.model.WorkDairyEntity;
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
//import com.rjtech.notification.timemanagement.workdairy.repository.WorkDairyRepository;
import com.rjtech.notification.repository.WorkDairyRepository;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

public class WorkDairyNotificationsHandler {

    public static WorkDairyNotificationsTO convertEntityToPOJO(
            WorkDairyNotificationsEntity workDairyNotificationsEntity) {
        WorkDairyNotificationsTO workDairyNotificationsTO = new WorkDairyNotificationsTO();
        workDairyNotificationsTO.setId(workDairyNotificationsEntity.getId());
        if (CommonUtil.isNotBlankDate(workDairyNotificationsEntity.getDate())) {
            workDairyNotificationsTO.setDate(CommonUtil.convertDateToString(workDairyNotificationsEntity.getDate()));
        }
        workDairyNotificationsTO.setCode(WorkDairyNotificationsHandler.generateCode(workDairyNotificationsEntity));
        WorkDairyEntity workDairy = workDairyNotificationsEntity.getWorkDairyEntity();
        if (null != workDairy) {
            workDairyNotificationsTO.setWorkDairyNumber(workDairy.getCode());
            if ("Request for External Approval".equalsIgnoreCase(workDairyNotificationsEntity.getNotificationMsg())
                    || "Approved By Client".equalsIgnoreCase(workDairyNotificationsEntity.getNotificationMsg())) {
                if (null != workDairy.getInternalApprUserId())
                    workDairyNotificationsTO.setFromUserId(workDairy.getInternalApprUserId().getUserId());
                if (null != workDairy.getClientApprUserId())
                    workDairyNotificationsTO.setToUserId(workDairy.getClientApprUserId().getUserId());
            } else {
                if (null != workDairy.getReqUserId())
                    workDairyNotificationsTO.setFromUserId(workDairy.getReqUserId().getUserId());
                if (null != workDairy.getInternalApprUserId())
                    workDairyNotificationsTO.setToUserId(workDairy.getInternalApprUserId().getUserId());
            }
            ProjMstrEntity projMstrEntity = workDairy.getProjId();
            if (null != projMstrEntity) {
                workDairyNotificationsTO.setProjId(projMstrEntity.getProjectId());
            }
            if (workDairy.getProjId() != null)
                workDairyNotificationsTO.setParentName(workDairy.getProjId().getProjName());
            com.rjtech.projectlib.model.ProjCrewMstrEntity crewMstrEntity = workDairy.getCrewId();
            if (null != crewMstrEntity)
                workDairyNotificationsTO.setCrewName(crewMstrEntity.getCode());
            ProjWorkShiftMstrEntity shift = workDairy.getShiftId();
            if (null != shift)
                workDairyNotificationsTO.setShift(shift.getCode());
        }
        workDairyNotificationsTO.setNotificationStatus(workDairyNotificationsEntity.getNotificationStatus());
        workDairyNotificationsTO.setNotificationMsg(workDairyNotificationsEntity.getNotificationMsg());
        workDairyNotificationsTO.setStatus(workDairyNotificationsEntity.getStatus());
        return workDairyNotificationsTO;
    }

    public static WorkDairyNotificationsEntity convertPOJOToEntity(WorkDairyNotificationsTO workDairyNotificationsTO,
            WorkDairyRepository workDairyRepository) {
        WorkDairyNotificationsEntity workDairyNotificationsEntity = new WorkDairyNotificationsEntity();
        workDairyNotificationsEntity.setDate(new Date());
        workDairyNotificationsEntity
                .setWorkDairyEntity(workDairyRepository.findOne(workDairyNotificationsTO.getWdmId()));
        workDairyNotificationsEntity.setStatus(workDairyNotificationsTO.getStatus());
        workDairyNotificationsEntity.setNotificationMsg("Request for Additional Time");
        workDairyNotificationsEntity.setNotificationStatus("Pending");
        return workDairyNotificationsEntity;
    }

    public static String generateCode(WorkDairyNotificationsEntity workDairyNotificationsEntity) {
        return ModuleCodesPrefixes.WORK_DIARY_PREFIX.getDesc().concat("-")
                .concat(workDairyNotificationsEntity.getWorkDairyEntity().getProjId().getCode()).concat("-")
                .concat(String.valueOf(workDairyNotificationsEntity.getId()));

    }

    public static String generateAdditionalTimeCode(WorkDairyAdditionalTimeEntity workDairyAdditionalTimeEntity) {
        return ModuleCodesPrefixes.WORK_DIARY_PREFIX.getDesc().concat("-")
                .concat(workDairyAdditionalTimeEntity.getCrewId().getProjId().getCode()).concat("-")
                .concat(String.valueOf(workDairyAdditionalTimeEntity.getId()));

    }

}
