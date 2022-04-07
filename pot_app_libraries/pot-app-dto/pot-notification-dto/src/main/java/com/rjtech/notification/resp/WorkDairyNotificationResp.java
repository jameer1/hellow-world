package com.rjtech.notification.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.WorkDairyNotificationsTO;

public class WorkDairyNotificationResp extends AppResp {

    private static final long serialVersionUID = 1L;
    private List<WorkDairyNotificationsTO> workDairyNotificationsTOs = new ArrayList<WorkDairyNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

    public List<WorkDairyNotificationsTO> getWorkDairyNotificationsTOs() {
        return workDairyNotificationsTOs;
    }

    public void setWorkDairyNotificationsTOs(List<WorkDairyNotificationsTO> workDairyNotificationsTOs) {
        this.workDairyNotificationsTOs = workDairyNotificationsTOs;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

}
