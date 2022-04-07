package com.rjtech.timemanagement.workdairy.dto;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyProgressCostTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long progressId;
    private Long costId;
    private double value;

    private Long parentId;
    private String apprStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

}