package com.rjtech.timemanagement.workdairy.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkDairyPlantStatusTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long plantDtlId;
    private Long parentId;
    private double usedTotal;
    private double idleTotal;
    private String comments;
    private String apprStatus;
    private Long userId;
    private Integer status;

    private List<WorkDairyPlantCostDtlTO> workDairyPlantCostDtlTOs = new ArrayList<WorkDairyPlantCostDtlTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantDtlId() {
        return plantDtlId;
    }

    public void setPlantDtlId(Long plantDtlId) {
        this.plantDtlId = plantDtlId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public double getUsedTotal() {
        return usedTotal;
    }

    public void setUsedTotal(double usedTotal) {
        this.usedTotal = usedTotal;
    }

    public double getIdleTotal() {
        return idleTotal;
    }

    public void setIdleTotal(double idleTotal) {
        this.idleTotal = idleTotal;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<WorkDairyPlantCostDtlTO> getWorkDairyPlantCostDtlTOs() {
        return workDairyPlantCostDtlTOs;
    }

    public void setWorkDairyPlantCostDtlTOs(List<WorkDairyPlantCostDtlTO> workDairyPlantCostDtlTOs) {
        this.workDairyPlantCostDtlTOs = workDairyPlantCostDtlTOs;
    }

}