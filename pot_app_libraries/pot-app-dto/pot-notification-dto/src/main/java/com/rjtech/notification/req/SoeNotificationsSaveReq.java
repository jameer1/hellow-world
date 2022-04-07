package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.SoeNotificationsTO;

public class SoeNotificationsSaveReq {

    private List<SoeNotificationsTO> soeNotificationsTOs = new ArrayList<SoeNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<SoeNotificationsTO> getSoeNotificationsTOs() {
        return soeNotificationsTOs;
    }

    public void setSoeNotificationsTOs(List<SoeNotificationsTO> soeNotificationsTOs) {
        this.soeNotificationsTOs = soeNotificationsTOs;
    }

}
