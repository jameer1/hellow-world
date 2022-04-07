package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantUtilisationRecordsTO;

public class PlantUtilisationRecordsSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2223620309153193411L;
    private List<PlantUtilisationRecordsTO> plantUtilisationRecordTOs = new ArrayList<PlantUtilisationRecordsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantUtilisationRecordsTO> getPlantUtilisationRecordTOs() {
        return plantUtilisationRecordTOs;
    }

    public void setPlantUtilisationRecordTOs(List<PlantUtilisationRecordsTO> plantUtilisationRecordTOs) {
        this.plantUtilisationRecordTOs = plantUtilisationRecordTOs;
    }

}
