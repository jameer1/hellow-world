package com.rjtech.register.plant.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;
import java.util.ArrayList;
import java.util.List;

public class PlantProjPODeliveryResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private PlantProjPODtlTO plantProjPODtlTO = new PlantProjPODtlTO();
    private List<PlantProjPODtlTO> plantProjPODtlTOs = new ArrayList<PlantProjPODtlTO>();

    public PlantProjPODtlTO getPlantProjPODtlTO() {
        return plantProjPODtlTO;
    }

    public void setPlantProjPODtlTO(PlantProjPODtlTO plantProjPODtlTO) {
        this.plantProjPODtlTO = plantProjPODtlTO;
    }
    
    public List<PlantProjPODtlTO> getPlantProjPODtlTOs() {  	
        return plantProjPODtlTOs;
    }

    public void setPlantProjPODtlTOs(List<PlantProjPODtlTO> plantProjPODtlTOs) {
        this.plantProjPODtlTOs = plantProjPODtlTOs;
    }

}
