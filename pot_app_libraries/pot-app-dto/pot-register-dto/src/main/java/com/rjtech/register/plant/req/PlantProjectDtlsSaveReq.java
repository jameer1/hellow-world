package com.rjtech.register.plant.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;

public class PlantProjectDtlsSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 8197627103061489618L;

    PlantProjPODtlTO plantProjDtlTO = new PlantProjPODtlTO();

    public PlantProjPODtlTO getPlantProjDtlTO() {
        return plantProjDtlTO;
    }

    public void setPlantProjDtlTO(PlantProjPODtlTO plantProjDtlTO) {
        this.plantProjDtlTO = plantProjDtlTO;
    }

}
