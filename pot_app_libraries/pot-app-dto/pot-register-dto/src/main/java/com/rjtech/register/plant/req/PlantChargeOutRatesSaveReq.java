package com.rjtech.register.plant.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.plant.dto.PlantChargeOutRatesTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantChargeOutRatesSaveReq extends ProjectTO {

    private static final long serialVersionUID = 7023285223685875655L;

    private PlantChargeOutRatesTO plantChargeOutRatesTO = new PlantChargeOutRatesTO();
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public PlantChargeOutRatesTO getPlantChargeOutRatesTO() {
        return plantChargeOutRatesTO;
    }

    public void setPlantChargeOutRatesTO(PlantChargeOutRatesTO plantChargeOutRatesTO) {
        this.plantChargeOutRatesTO = plantChargeOutRatesTO;
    }

}
