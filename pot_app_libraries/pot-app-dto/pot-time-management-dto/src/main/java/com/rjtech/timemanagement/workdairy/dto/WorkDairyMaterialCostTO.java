package com.rjtech.timemanagement.workdairy.dto;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyMaterialCostTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long materialStatusId;
    private Long costId;
    private double quantity;
    private Long workDairyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getMaterialStatusId() {
        return materialStatusId;
    }

    public void setMaterialStatusId(Long materialStatusId) {
        this.materialStatusId = materialStatusId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}