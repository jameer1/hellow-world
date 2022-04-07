package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.common.resp.AppResp;


public class PlantClassResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<PlantClassTO> plantClassTOs = null;

    public PlantClassResp() {
        plantClassTOs = new ArrayList<PlantClassTO>(5);
    }

    public List<PlantClassTO> getPlantClassTOs() {
        return plantClassTOs;
    }

}
