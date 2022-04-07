package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantNotificationsTO;

public class PlantNotificationResp extends AppResp {

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
