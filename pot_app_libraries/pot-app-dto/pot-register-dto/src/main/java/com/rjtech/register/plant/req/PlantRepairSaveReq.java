package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.dto.PlantRepairsTO;

public class PlantRepairSaveReq extends ProjectTO {

    private static final long serialVersionUID = 4746616701630060756L;

    private Long plantId;

    private List<PlantRepairsTO> plantRepairsTOs = new ArrayList<PlantRepairsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

    public List<PlantRepairsTO> getPlantRepairsTOs() {
        return plantRepairsTOs;
    }

    public void setPlantRepairsTOs(List<PlantRepairsTO> plantRepairsTOs) {
        this.plantRepairsTOs = plantRepairsTOs;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

}
