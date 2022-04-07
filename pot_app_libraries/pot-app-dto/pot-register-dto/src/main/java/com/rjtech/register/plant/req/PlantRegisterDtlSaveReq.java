package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegisterDtlTO;

public class PlantRegisterDtlSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4563600866595219206L;

    private List<PlantRegisterDtlTO> plantRegisterDtlTOs = new ArrayList<PlantRegisterDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantRegisterDtlTO> getPlantRegisterDtlTOs() {
        return plantRegisterDtlTOs;
    }

    public void setPlantRegisterDtlTOs(List<PlantRegisterDtlTO> plantRegisterDtlTOs) {
        this.plantRegisterDtlTOs = plantRegisterDtlTOs;
    }

}
