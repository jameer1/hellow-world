package com.rjtech.register.plant.req;

import com.rjtech.common.dto.ProjectTO;

public class AssignPlantToPlantPoDocketsReq extends ProjectTO {
    /**
     * 
     */
    private static final long serialVersionUID = 6615592749602480898L;
    private Long plantId;
    private Long docketId;

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getDocketId() {
        return docketId;
    }

    public void setDocketId(Long docketId) {
        this.docketId = docketId;
    }

}
