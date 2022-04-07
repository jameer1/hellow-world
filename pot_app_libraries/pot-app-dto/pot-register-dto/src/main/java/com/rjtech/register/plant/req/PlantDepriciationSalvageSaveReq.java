package com.rjtech.register.plant.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.plant.dto.PlantDepriciationSalvageTO;

public class PlantDepriciationSalvageSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2360750510616978973L;

    private PlantDepriciationSalvageTO plantDepriciationSalvageTO = new PlantDepriciationSalvageTO();

    public PlantDepriciationSalvageTO getPlantDepriciationSalvageTO() {
        return plantDepriciationSalvageTO;
    }

    public void setPlantDepriciationSalvageTO(PlantDepriciationSalvageTO plantDepriciationSalvageTO) {
        this.plantDepriciationSalvageTO = plantDepriciationSalvageTO;
    }

}
