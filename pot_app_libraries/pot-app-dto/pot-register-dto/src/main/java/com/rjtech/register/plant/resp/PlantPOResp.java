package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantPOResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -1955541747354597915L;
    private List<PlantRegProjTO> PlantProjectPOTO = new ArrayList<PlantRegProjTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantRegProjTO> getPlantProjectPOTO() {
        return PlantProjectPOTO;
    }

    public void setPlantProjectPOTO(List<PlantRegProjTO> plantProjectPOTO) {
        PlantProjectPOTO = plantProjectPOTO;
    }

}
