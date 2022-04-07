package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantServiceHistoryTO;

public class PlantServiceHistoryResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3771158186525295480L;

    private List<PlantServiceHistoryTO> plantServiceHistoryTOs = new ArrayList<PlantServiceHistoryTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantServiceHistoryTO> getPlantServiceHistoryTOs() {
        return plantServiceHistoryTOs;
    }

    public void setPlantServiceHistoryTOs(List<PlantServiceHistoryTO> plantServiceHistoryTOs) {
        this.plantServiceHistoryTOs = plantServiceHistoryTOs;
    }

}
