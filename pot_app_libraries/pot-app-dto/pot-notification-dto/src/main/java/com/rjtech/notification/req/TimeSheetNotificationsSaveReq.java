package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.TimeSheetNotificationsTO;

public class TimeSheetNotificationsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<TimeSheetNotificationsTO> timeSheetNotificationsTOs = new ArrayList<TimeSheetNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<TimeSheetNotificationsTO> getTimeSheetNotificationsTOs() {
        return timeSheetNotificationsTOs;
    }

    public void setTimeSheetNotificationsTOs(List<TimeSheetNotificationsTO> timeSheetNotificationsTOs) {
        this.timeSheetNotificationsTOs = timeSheetNotificationsTOs;
    }

}
