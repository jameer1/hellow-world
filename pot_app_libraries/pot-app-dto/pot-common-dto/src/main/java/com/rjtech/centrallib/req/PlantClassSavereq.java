package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.common.dto.ClientTO;


public class PlantClassSavereq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<PlantClassTO> plantClassTOs = new ArrayList<PlantClassTO>(5);

    public List<PlantClassTO> getPlantClassTOs() {
        return plantClassTOs;
    }

    public void setPlantClassTOs(List<PlantClassTO> plantClassTOs) {
        this.plantClassTOs = plantClassTOs;
    }

}
