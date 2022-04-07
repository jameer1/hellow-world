package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class PlantInvoiceDocketItemTO extends ProjectTO {

    private static final long serialVersionUID = -2754078377987345435L;
    /**
     * 
     */

    private Long id;
    private Long purId;
    private Long plantId;
    private Long plantPOId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getPlantPOId() {
        return plantPOId;
    }

    public void setPlantPOId(Long plantPOId) {
        this.plantPOId = plantPOId;
    }

}
