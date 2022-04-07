package com.rjtech.timemanagement.workdairy.dto;

import java.io.Serializable;

public class WorkDairyEmpCostDtlTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long costId;
    private Long wageCostId;
    private double usedTime;
    private double idleTime;
    private double oldUsedTime;
    private double oldIdleTime;
    private Long workDairyId;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getWageCostId() {
        return wageCostId;
    }

    public void setWageCostId(Long wageCostId) {
        this.wageCostId = wageCostId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}