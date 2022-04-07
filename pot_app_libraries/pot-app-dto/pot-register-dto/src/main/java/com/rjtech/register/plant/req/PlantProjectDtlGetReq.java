package com.rjtech.register.plant.req;

import java.io.Serializable;

public class PlantProjectDtlGetReq implements Serializable {

    private static final long serialVersionUID = 1164442685143086523L;
    private Long plantId;
    private Long plantPOId;
    private Integer apprStatus;
    private Long purchaseTypeId;
    private String fromDate;
    private String toDate;
    private Long plantProjId;
    private Long projId;
    private Integer status;
    private Long plantProjPODtlId;

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

    public Long getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(Long purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public Integer getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(Integer apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getPlantProjId() {
        return plantProjId;
    }

    public void setPlantProjId(Long plantProjId) {
        this.plantProjId = plantProjId;
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

    public Long getPlantProjPODtlId() {
        return plantProjPODtlId;
    }

    public void setPlantProjPODtlId(Long plantProjPODtlId) {
        this.plantProjPODtlId = plantProjPODtlId;
    }

}
