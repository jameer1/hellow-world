package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantProjectOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -5740539835965433671L;

    private List<PlantRegProjTO> plantProjDtlTOs = null;

    public PlantProjectOnLoadResp() {

        plantProjDtlTOs = new ArrayList<PlantRegProjTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PlantRegProjTO> getPlantProjDtlTOs() {
        return plantProjDtlTOs;
    }

    public void setPlantProjDtlTOs(List<PlantRegProjTO> plantProjDtlTOs) {
        this.plantProjDtlTOs = plantProjDtlTOs;
    }

}
