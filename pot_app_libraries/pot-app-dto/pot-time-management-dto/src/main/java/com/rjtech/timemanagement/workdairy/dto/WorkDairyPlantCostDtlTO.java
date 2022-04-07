package com.rjtech.timemanagement.workdairy.dto;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyPlantCostDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long plantStatusId;
    private Long costId;
    private double usedTime;
    private double idleTime;
    private Long workDairyId;
    private double oldUsedTime;
    private double oldIdleTime;

    public double getOldUsedTime() {
        return oldUsedTime;
    }

    public void setOldUsedTime(double oldUsedTime) {
        this.oldUsedTime = oldUsedTime;
    }

    public double getOldIdleTime() {
        return oldIdleTime;
    }

    public void setOldIdleTime(double oldIdleTime) {
        this.oldIdleTime = oldIdleTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantStatusId() {
        return plantStatusId;
    }

    public void setPlantStatusId(Long plantStatusId) {
        this.plantStatusId = plantStatusId;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public double getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(double usedTime) {
        this.usedTime = usedTime;
    }

    public double getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(double idleTime) {
        this.idleTime = idleTime;
    }

}