package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantServiceHistoryTO;

public class PlantServiceHistorySaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6902726799006195479L;

    private Long plantId;

    private List<PlantServiceHistoryTO> plantServiceHistoryTOs = new ArrayList<PlantServiceHistoryTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantServiceHistoryTO> getPlantServiceHistoryTOs() {
        return plantServiceHistoryTOs;
    }

    public void setPlantServiceHistoryTOs(List<PlantServiceHistoryTO> plantServiceHistoryTOs) {
        this.plantServiceHistoryTOs = plantServiceHistoryTOs;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

}
