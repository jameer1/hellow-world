package com.rjtech.notification.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.EmployeeNotificationsTO;

public class EmployeeNotificationsResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<EmployeeNotificationsTO> employeeNotificationsTOs = new ArrayList<EmployeeNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();

    public List<EmployeeNotificationsTO> getEmployeeNotificationsTOs() {
        return employeeNotificationsTOs;
    }

    public void setEmployeeNotificationsTOs(List<EmployeeNotificationsTO> employeeNotificationsTOs) {
        this.employeeNotificationsTOs = employeeNotificationsTOs;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

}
