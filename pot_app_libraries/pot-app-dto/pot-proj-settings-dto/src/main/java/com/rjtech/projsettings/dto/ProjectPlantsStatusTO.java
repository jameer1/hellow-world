package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjectPlantsStatusTO extends ProjectTO {

    private static final long serialVersionUID = 839138937792720456L;

    private Long plantId;
    private String plantsName;
    private Object originalQty;
    private Object revisedQty;
    private Object actualQty;
    private Object remainingQty;
    private Object estimate;
    private Object completion;
    private String mesureName;
    private String plantsCode;
    private Object percentageSpent;
    private Object compVariance;
    
    public String getMesureName() {
        return mesureName;
    }

    public void setMesureName(String mesureName) {
        this.mesureName = mesureName;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getPlantsName() {
        return plantsName;
    }

    public Object getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(Object originalQty) {
        this.originalQty = originalQty;
    }

    public Object getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(Object revisedQty) {
        this.revisedQty = revisedQty;
    }

    public Object getActualQty() {
        return actualQty;
    }

    public void setActualQty(Object actualQty) {
        this.actualQty = actualQty;
    }

    public Object getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Object remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Object getEstimate() {
        return estimate;
    }

    public void setEstimate(Object estimate) {
        this.estimate = estimate;
    }

    public Object getCompletion() {
        return completion;
    }

    public void setCompletion(Object completion) {
        this.completion = completion;
    }

    public void setPlantsName(String plantsName) {
        this.plantsName = plantsName;
    }

    public String getPlantsCode() {
        return plantsCode;
    }

    public void setPlantsCode(String plantsCode) {
        this.plantsCode = plantsCode;
    }

	public Object getPercentageSpent() {
		return percentageSpent;
	}

	public void setPercentageSpent(Object percentageSpent) {
		this.percentageSpent = percentageSpent;
	}

	public Object getCompVariance() {
		return compVariance;
	}

	public void setCompVariance(Object compVariance) {
		this.compVariance = compVariance;
	}

}
