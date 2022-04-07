package com.rjtech.notification.service.handler;

import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity;

public class TimeSheetNotificationsHandlerCopy {

	private TimeSheetNotificationsHandlerCopy() {
    }

    public static String generateCode(TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity, String projCode) {
        return ModuleCodesPrefixes.TIME_SHEET_PREFIX.getDesc().concat("-")
                .concat(projCode).concat("-")
                .concat(String.valueOf(timeSheetAdditionalTimeEntity.getId()));
    }

}
