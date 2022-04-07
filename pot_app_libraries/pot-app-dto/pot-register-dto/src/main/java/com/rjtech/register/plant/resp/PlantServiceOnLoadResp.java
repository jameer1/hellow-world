package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantServiceHistoryTO;

public class PlantServiceOnLoadResp extends AppResp {

    private static final long serialVersionUID = 3146963976380953669L;

    private List<PlantServiceHistoryTO> plantServiceHistoryTOs = new ArrayList<PlantServiceHistoryTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private Map<Long, LabelKeyTO> plantServiceClassMap = new HashMap<Long, LabelKeyTO>();

    public List<PlantServiceHistoryTO> getPlantServiceHistoryTOs() {
        return plantServiceHistoryTOs;
    }

    public void setPlantServiceHistoryTOs(List<PlantServiceHistoryTO> plantServiceHistoryTOs) {
        this.plantServiceHistoryTOs = plantServiceHistoryTOs;
    }

    public Map<Long, LabelKeyTO> getPlantServiceClassMap() {
        return plantServiceClassMap;
    }

    public void setPlantServiceClassMap(Map<Long, LabelKeyTO> plantServiceClassMap) {
        this.plantServiceClassMap = plantServiceClassMap;
    }

}
