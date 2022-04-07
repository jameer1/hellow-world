package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegisterDtlTO;

public class PlantRegisterDtlResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3644094755627044754L;
    private List<PlantRegisterDtlTO> plantRegisterDtlTOs = new ArrayList<PlantRegisterDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantRegisterDtlTO> getPlantRegisterDtlTOs() {
        return plantRegisterDtlTOs;
    }

    public void setPlantRegisterDtlTOs(List<PlantRegisterDtlTO> plantRegisterDtlTOs) {
        this.plantRegisterDtlTOs = plantRegisterDtlTOs;
    }

}
