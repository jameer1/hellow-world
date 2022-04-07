package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantCurrentStatusResp extends AppResp {

    private static final long serialVersionUID = 1L;
    private List<PlantRegProjTO> plantRegProjTOs = new ArrayList<PlantRegProjTO>();
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();

    public PlantCurrentStatusResp() {

    }

    public List<PlantRegProjTO> getPlantRegProjTOs() {
        return plantRegProjTOs;
    }

    public void setPlantRegProjTOs(List<PlantRegProjTO> plantRegProjTOs) {
        this.plantRegProjTOs = plantRegProjTOs;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

}
