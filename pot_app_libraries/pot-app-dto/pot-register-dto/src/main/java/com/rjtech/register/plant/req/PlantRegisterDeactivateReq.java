package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class PlantRegisterDeactivateReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -838923740507236370L;

    List<Long> plantRegIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getPlantRegIds() {
        return plantRegIds;
    }

    public void setPlantRegIds(List<Long> plantRegIds) {
        this.plantRegIds = plantRegIds;
    }

}
