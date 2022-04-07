package com.rjtech.register.plant.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PlantRepairsTO extends ProjectTO {

    private static final long serialVersionUID = -4353287679482367703L;

    private Long id;
    private Long plantId;
    private String date;
    private BigDecimal odoMeter;
    private Long serviceId;
    private String serviceName;
    private String serviceParentName;
    private Long materialId;
    private String materialName;
    private String materialUnit;
    private BigDecimal quantity;
    private Long projDockSchId;
    private String projDocket;
    private String comments;
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceParentName() {
        return serviceParentName;
    }

    public void setServiceParentName(String serviceParentName) {
        this.serviceParentName = serviceParentName;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public BigDecimal getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(BigDecimal odoMeter) {
        this.odoMeter = odoMeter;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getProjDockSchId() {
        return projDockSchId;
    }

    public void setProjDockSchId(Long projDockSchId) {
        this.projDockSchId = projDockSchId;
    }

    public String getProjDocket() {
        return projDocket;
    }

    public void setProjDocket(String projDocket) {
        this.projDocket = projDocket;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

}
