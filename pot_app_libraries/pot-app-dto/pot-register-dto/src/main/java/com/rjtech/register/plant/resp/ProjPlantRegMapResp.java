package com.rjtech.register.plant.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.resp.AppResp;
import com.rjtech.register.plant.dto.PlantRegisterDtlTO;

public class ProjPlantRegMapResp extends AppResp {

    private static final long serialVersionUID = -9222635953451720346L;

    private Map<Long, PlantRegisterDtlTO> projPlantRegMap = new HashMap<Long, PlantRegisterDtlTO>();

    public Map<Long, PlantRegisterDtlTO> getProjPlantRegMap() {
        return projPlantRegMap;
    }

    public void setProjPlantRegMap(Map<Long, PlantRegisterDtlTO> projPlantRegMap) {
        this.projPlantRegMap = projPlantRegMap;
    }

}
