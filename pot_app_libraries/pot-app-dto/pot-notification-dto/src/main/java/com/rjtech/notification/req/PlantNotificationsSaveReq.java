package com.rjtech.notification.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.PlantNotificationsTO;

public class PlantNotificationsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<PlantNotificationsTO> plantNotificationsTOs = new ArrayList<PlantNotificationsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantNotificationsTO> getPlantNotificationsTOs() {
        return plantNotificationsTOs;
    }

    public void setPlantNotificationsTOs(List<PlantNotificationsTO> plantNotificationsTOs) {
        this.plantNotificationsTOs = plantNotificationsTOs;
    }

}
