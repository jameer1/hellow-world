package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRepairsTO;

public class PlantRepairOnLoadResp extends AppResp {

    private static final long serialVersionUID = -8794010198899684055L;

    private Map<Long, LabelKeyTO> materialMap = new HashMap<Long, LabelKeyTO>();

    private Map<Long, LabelKeyTO> plantServiceClassMap = new HashMap<Long, LabelKeyTO>();

    private List<PlantRepairsTO> plantRepairsTOs = new ArrayList<PlantRepairsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public Map<Long, LabelKeyTO> getMaterialMap() {
        return materialMap;
    }

    public void setMaterialMap(Map<Long, LabelKeyTO> materialMap) {
        this.materialMap = materialMap;
    }

    public Map<Long, LabelKeyTO> getPlantServiceClassMap() {
        return plantServiceClassMap;
    }

    public void setPlantServiceClassMap(Map<Long, LabelKeyTO> plantServiceClassMap) {
        this.plantServiceClassMap = plantServiceClassMap;
    }

    public List<PlantRepairsTO> getPlantRepairsTOs() {
        return plantRepairsTOs;
    }

    public void setPlantRepairsTOs(List<PlantRepairsTO> plantRepairsTOs) {
        this.plantRepairsTOs = plantRepairsTOs;
    }
}
