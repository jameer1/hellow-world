package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantPODocketDtlTO;

public class PlantDocketDtlsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6185083616549903555L;

    private List<PlantPODocketDtlTO> plantDocketDtlTOs = new ArrayList<PlantPODocketDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantPODocketDtlTO> getPlantDocketDtlTOs() {
        return plantDocketDtlTOs;
    }

    public void setPlantDocketDtlTOs(List<PlantPODocketDtlTO> plantDocketDtlTOs) {
        this.plantDocketDtlTOs = plantDocketDtlTOs;
    }

}
