package com.rjtech.notification.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.AttendenceNotificationsTO;

public class AttendenceNotificationsResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<AttendenceNotificationsTO> attendenceNotificationsTOs = new ArrayList<AttendenceNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public List<AttendenceNotificationsTO> getAttendenceNotificationsTOs() {
        return attendenceNotificationsTOs;
    }

    public void setAttendenceNotificationsTOs(List<AttendenceNotificationsTO> attendenceNotificationsTOs) {
        this.attendenceNotificationsTOs = attendenceNotificationsTOs;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

}
