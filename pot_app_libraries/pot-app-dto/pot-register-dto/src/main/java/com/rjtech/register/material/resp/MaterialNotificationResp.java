package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialNotificationsTO;

public class MaterialNotificationResp extends AppResp {

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
