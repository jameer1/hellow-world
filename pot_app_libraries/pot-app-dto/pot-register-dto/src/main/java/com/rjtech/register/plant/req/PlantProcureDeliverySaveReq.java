package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantPODocketDtlTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantProcureDeliverySaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PlantRegProjTO plantProjectPODtlTO = new PlantRegProjTO();

    private List<PlantPODocketDtlTO> plantProjDtlTOs = new ArrayList<PlantPODocketDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public PlantRegProjTO getPlantProjectPODtlTO() {
        return plantProjectPODtlTO;
    }

    public List<PlantPODocketDtlTO> getPlantProjDtlTOs() {
        return plantProjDtlTOs;
    }

    public void setPlantProjectPODtlTO(PlantRegProjTO plantProjectPODtlTO) {
        this.plantProjectPODtlTO = plantProjectPODtlTO;
    }

    public void setPlantProjDtlTOs(List<PlantPODocketDtlTO> plantProjDtlTOs) {
        this.plantProjDtlTOs = plantProjDtlTOs;
    }

}
