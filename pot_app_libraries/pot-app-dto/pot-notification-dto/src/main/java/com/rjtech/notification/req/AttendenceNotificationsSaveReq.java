package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.AttendenceNotificationsTO;

public class AttendenceNotificationsSaveReq {

    private List<AttendenceNotificationsTO> attendenceNotificationsTOs = new ArrayList<AttendenceNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<AttendenceNotificationsTO> getAttendenceNotificationsTOs() {
        return attendenceNotificationsTOs;
    }

    public void setAttendenceNotificationsTOs(List<AttendenceNotificationsTO> attendenceNotificationsTOs) {
        this.attendenceNotificationsTOs = attendenceNotificationsTOs;
    }

}
