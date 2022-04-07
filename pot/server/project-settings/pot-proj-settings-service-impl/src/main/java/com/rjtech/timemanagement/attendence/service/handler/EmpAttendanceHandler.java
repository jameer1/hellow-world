package com.rjtech.timemanagement.attendence.service.handler;

import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.notification.model.AttendenceNotificationsEntity;

public class EmpAttendanceHandler {

    public static String generateCode(AttendenceNotificationsEntity attendenceNotificationsEntity) {
        return ModuleCodesPrefixes.TIME_SHEET_PREFIX.getDesc().concat("-")
                .concat(attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId().getCode()).concat("-")
                .concat(String.valueOf(attendenceNotificationsEntity.getId()));
    }

}
