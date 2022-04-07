package com.rjtech.register.plant.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantDeactivateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Long> ids = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

}
