package com.rjtech.centrallib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class PlantClassMapResp {

    Map<Long, LabelKeyTO> plantClassMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getPlantClassMap() {
        return plantClassMap;
    }

    public void setPlantClassMap(Map<Long, LabelKeyTO> plantClassMap) {
        this.plantClassMap = plantClassMap;
    }

}
