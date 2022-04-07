package com.rjtech.register.plant.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;

public class PlantProcureDeliveryResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private PlantProjPODtlTO plantProjPODtlTO = new PlantProjPODtlTO();

    public PlantProjPODtlTO getPlantProjPODtlTO() {
        return plantProjPODtlTO;
    }

    public void setPlantProjPODtlTO(PlantProjPODtlTO plantProjPODtlTO) {
        this.plantProjPODtlTO = plantProjPODtlTO;
    }

}
