package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.MaterialNotificationsTO;

public class MaterialNotificationsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<MaterialNotificationsTO> materialNotificationsTOs = new ArrayList<MaterialNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<MaterialNotificationsTO> getMaterialNotificationsTOs() {
        return materialNotificationsTOs;
    }

    public void setMaterialNotificationsTOs(List<MaterialNotificationsTO> materialNotificationsTOs) {
        this.materialNotificationsTOs = materialNotificationsTOs;
    }

}
