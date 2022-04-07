package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class PlantServiceClassDeactivateReq extends ClientTO {

    private static final long serialVersionUID = 1L;

    private List<Long> plantServiceIds = new ArrayList<Long>(5);

    public List<Long> getPlantServiceIds() {
        return plantServiceIds;
    }

    public void setPlantServiceIds(List<Long> plantServiceIds) {
        this.plantServiceIds = plantServiceIds;
    }

}
