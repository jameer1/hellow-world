package com.rjtech.register.plant.req;

import java.io.Serializable;

import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantLogRecordsSaveReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7707460366420097885L;

    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
    private PlantLogRecordsTO plantLogRecordsTO = new PlantLogRecordsTO();

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public PlantLogRecordsTO getPlantLogRecordsTO() {
        return plantLogRecordsTO;
    }

    public void setPlantLogRecordsTO(PlantLogRecordsTO plantLogRecordsTO) {
        this.plantLogRecordsTO = plantLogRecordsTO;
    }

}
