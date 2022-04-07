package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpNotificationsTO;

public class EmpNotificationsResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<EmpNotificationsTO> empNotificationsTOs = new ArrayList<EmpNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmpNotificationsTO> getEmpNotificationsTOs() {
        return empNotificationsTOs;
    }

    public void setEmpNotificationsTOs(List<EmpNotificationsTO> empNotificationsTOs) {
        this.empNotificationsTOs = empNotificationsTOs;
    }

}
