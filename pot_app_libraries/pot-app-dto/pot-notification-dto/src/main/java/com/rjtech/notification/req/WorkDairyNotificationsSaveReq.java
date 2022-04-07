package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.WorkDairyNotificationsTO;

public class WorkDairyNotificationsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<WorkDairyNotificationsTO> workDairyNotificationsTOs = new ArrayList<WorkDairyNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<WorkDairyNotificationsTO> getWorkDairyNotificationsTOs() {
        return workDairyNotificationsTOs;
    }

    public void setWorkDairyNotificationsTOs(List<WorkDairyNotificationsTO> workDairyNotificationsTOs) {
        this.workDairyNotificationsTOs = workDairyNotificationsTOs;
    }
}
