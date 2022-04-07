package com.rjtech.common.dto;

import java.io.Serializable;

public class CostActualHoursTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Double labourCost = Double.valueOf(0);
    private Double materialCost = Double.valueOf(0);
    private Double plantCost = Double.valueOf(0);
    private Double othersCost = Double.valueOf(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(Double labourCost) {
        this.labourCost = labourCost;
    }

    public Double getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(Double materialCost) {
        this.materialCost = materialCost;
    }

    public Double getPlantCost() {
        return plantCost;
    }

    public void setPlantCost(Double plantCost) {
        this.plantCost = plantCost;
    }

    public Double getOthersCost() {
        return othersCost;
    }

    public void setOthersCost(Double othersCost) {
        this.othersCost = othersCost;
    }
}
