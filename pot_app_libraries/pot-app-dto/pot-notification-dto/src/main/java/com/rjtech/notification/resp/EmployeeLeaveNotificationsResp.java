package com.rjtech.notification.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.EmployeeLeaveNotificationsTO;

public class EmployeeLeaveNotificationsResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<EmployeeLeaveNotificationsTO> employeeLeaveNotificationsTOs = new ArrayList<EmployeeLeaveNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private Map<Long, LabelKeyTO> empRegMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> companyMap = new HashMap<Long, LabelKeyTO>();

    public List<EmployeeLeaveNotificationsTO> getEmployeeLeaveNotificationsTOs() {
        return employeeLeaveNotificationsTOs;
    }

    public void setEmployeeLeaveNotificationsTOs(List<EmployeeLeaveNotificationsTO> employeeLeaveNotificationsTOs) {
        this.employeeLeaveNotificationsTOs = employeeLeaveNotificationsTOs;
    }

    public Map<Long, LabelKeyTO> getEmpRegMap() {
        return empRegMap;
    }

    public void setEmpRegMap(Map<Long, LabelKeyTO> empRegMap) {
        this.empRegMap = empRegMap;
    }

    public Map<Long, LabelKeyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, LabelKeyTO> companyMap) {
        this.companyMap = companyMap;
    }

}
