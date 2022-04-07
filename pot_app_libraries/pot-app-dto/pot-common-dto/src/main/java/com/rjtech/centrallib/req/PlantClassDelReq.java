package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class PlantClassDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -9179081553585340761L;

    private List<Long> plantClassIds = new ArrayList<Long>(5);

    public List<Long> getPlantClassIds() {
        return plantClassIds;
    }

    public void setPlantClassIds(List<Long> plantClassIds) {
        this.plantClassIds = plantClassIds;
    }

}
