package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.PlantServiceClassificationMstrTO;
import com.rjtech.common.dto.ClientTO;


public class PlantServiceClassSaveReq extends ClientTO {

    private static final long serialVersionUID = 1L;

    private List<PlantServiceClassificationMstrTO> plantServiceClassificationMstrTOs = new ArrayList<PlantServiceClassificationMstrTO>(
            5);

    public List<PlantServiceClassificationMstrTO> getPlantServiceClassificationMstrTOs() {
        return plantServiceClassificationMstrTOs;
    }

    public void setPlantServiceClassificationMstrTOs(
            List<PlantServiceClassificationMstrTO> plantServiceClassificationMstrTOs) {
        this.plantServiceClassificationMstrTOs = plantServiceClassificationMstrTOs;
    }
}
