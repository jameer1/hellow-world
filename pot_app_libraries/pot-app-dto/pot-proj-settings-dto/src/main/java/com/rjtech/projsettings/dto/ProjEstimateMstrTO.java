package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjEstimateMstrTO extends ProjectTO {

    private static final long serialVersionUID = 3445372443332587508L;
    private Long id;
    private String estimateType;
    private Long estimateCost;
    private Long manHrs;
    private Long plantHrs;
    private Long materialHrs;
    private Long otherHrs;
    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
    }

    public Long getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(Long estimateCost) {
        this.estimateCost = estimateCost;
    }

    public Long getManHrs() {
        return manHrs;
    }

    public void setManHrs(Long manHrs) {
        this.manHrs = manHrs;
    }

    public Long getMaterialHrs() {
        return materialHrs;
    }

    public void setMaterialHrs(Long materialHrs) {
        this.materialHrs = materialHrs;
    }

    public Long getOtherHrs() {
        return otherHrs;
    }

    public void setOtherHrs(Long otherHrs) {
        this.otherHrs = otherHrs;
    }

    public Long getPlantHrs() {
        return plantHrs;
    }

    public void setPlantHrs(Long plantHrs) {
        this.plantHrs = plantHrs;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

}
