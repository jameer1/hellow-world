package com.rjtech.notification.service.handler;

import java.util.Date;

import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.notification.dto.WorkDairyNotificationsTO;
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
//import com.rjtech.notification.model.WorkDairyNotificationsEntityCopy;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyRepository;

public class WorkDairyNotificationsHandlerCopy {

    public static WorkDairyNotificationsEntity convertPOJOToEntity(
            WorkDairyNotificationsTO workDairyNotificationsTO, WorkDairyRepository workDairyRepository,
            WorkDairyTO workDairyTo) {
        WorkDairyNotificationsEntity workDairyNotificationsEntity = new WorkDairyNotificationsEntity();
        if (workDairyTo.getId() != null) {
            workDairyNotificationsEntity.setWorkDairyEntity(workDairyRepository.findOne(workDairyTo.getId()));
        }
        workDairyNotificationsEntity.setDate(new Date());
        workDairyNotificationsEntity.setNotificationMsg(workDairyNotificationsTO.getNotificationMsg());
        workDairyNotificationsEntity.setNotificationStatus((workDairyNotificationsTO.getNotificationStatus()));
        return workDairyNotificationsEntity;
    }

    public static String generateCode(WorkDairyNotificationsEntity workDairyNotificationsEntity) {
        WorkDairyEntity workDairyEntity = workDairyNotificationsEntity.getWorkDairyEntity();
        return ModuleCodesPrefixes.WORK_DIARY_PREFIX.getDesc().concat("-").concat(workDairyEntity.getProjId().getCode())
                .concat("-").concat(String.valueOf(workDairyNotificationsEntity.getId()));

    }
}
