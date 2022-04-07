package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.ProcurementNotificationsTO;

public class ProcureNotificationsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<ProcurementNotificationsTO> procurementNotificationsTOs = new ArrayList<ProcurementNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProcurementNotificationsTO> getProcurementNotificationsTOs() {
        return procurementNotificationsTOs;
    }

    public void setProcurementNotificationsTOs(List<ProcurementNotificationsTO> procurementNotificationsTOs) {
        this.procurementNotificationsTOs = procurementNotificationsTOs;
    }

}
