package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRepairsTO;

public class PlantRepairsResp extends AppResp {

    private static final long serialVersionUID = -5597802034283220581L;

    private List<PlantRepairsTO> plantRepairsTOs = new ArrayList<PlantRepairsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantRepairsTO> getPlantRepairsTOs() {
        return plantRepairsTOs;
    }

    public void setPlantRepairsTOs(List<PlantRepairsTO> plantRepairsTO) {
        this.plantRepairsTOs = plantRepairsTO;
    }

}
