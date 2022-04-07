package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.EmployeeNotificationsTO;

public class EmployeeNotificationsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<EmployeeNotificationsTO> employeeNotificationsTOs = new ArrayList<EmployeeNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmployeeNotificationsTO> getEmployeeNotificationsTOs() {
        return employeeNotificationsTOs;
    }

    public void setEmployeeNotificationsTOs(List<EmployeeNotificationsTO> employeeNotificationsTOs) {
        this.employeeNotificationsTOs = employeeNotificationsTOs;
    }

}
