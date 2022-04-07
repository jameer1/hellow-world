package com.rjtech.register.plant.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantDeploymentSaveReq extends ProjectTO {

    private static final long serialVersionUID = 8426653730950721390L;

    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public PlantDeploymentSaveReq() {

    }

}
