package com.rjtech.register.plant.req;

import java.io.Serializable;

public class PlantProjRepairGetReq implements Serializable {

    private static final long serialVersionUID = 1164442685143086523L;
    private Long id;
    private Long plantId;
    private Long projId;
    private Integer status;
    private Long materialClassId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(Long materialClassId) {
        this.materialClassId = materialClassId;
    }

}
