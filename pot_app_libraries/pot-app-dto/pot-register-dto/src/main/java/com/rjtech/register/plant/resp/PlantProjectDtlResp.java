package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;

public class PlantProjectDtlResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 7033246792005129317L;

    private List<PlantProjPODtlTO> plantProjDtlTOs = new ArrayList<PlantProjPODtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantProjPODtlTO> getPlantProjDtlTOs() {
        return plantProjDtlTOs;
    }

    public void setPlantProjDtlTOs(List<PlantProjPODtlTO> plantProjDtlTOs) {
        this.plantProjDtlTOs = plantProjDtlTOs;
    }

}
