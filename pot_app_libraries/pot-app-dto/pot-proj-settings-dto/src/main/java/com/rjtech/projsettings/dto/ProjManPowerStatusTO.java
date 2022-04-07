package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjManPowerStatusTO extends ProjectTO {

    private static final long serialVersionUID = -7662362351456630826L;

    private String empCatgName;
    private Double originalQty;
    private Double revisedQty;
    private Double actualQty;
    private Double remainingQty;
    private Double estimateComplete;
    private Double estimateCompletion;
    private String estimateType;
    private Double earnedValue;
    private Double plannedValue;
    private Double compVariance;
    private Double percentageSpent;

    public String getEmpCatgName() {
        return empCatgName;
    }

    public void setEmpCatgName(String empCatgName) {
        this.empCatgName = empCatgName;
    }

    public Double getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(Double originalQty) {
        this.originalQty = originalQty;
    }

    public Double getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(Double revisedQty) {
        this.revisedQty = revisedQty;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

    public Double getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Double remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Double getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(Double estimateComplete) {
        this.estimateComplete = estimateComplete;
    }

    public Double getEstimateCompletion() {
        return estimateCompletion;
    }

    public void setEstimateCompletion(Double estimateCompletion) {
        this.estimateCompletion = estimateCompletion;
    }
    
    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
    }
    
    public Double getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(Double earnedValue) {
        this.earnedValue = earnedValue;
    }
    
    public Double getPlannedValue() {
        return plannedValue;
    }

    public void setPlannedValue(Double plannedValue) {
        this.plannedValue = plannedValue;
    }

	public Double getCompVariance() {
		return compVariance;
	}

	public void setCompVariance(Double compVariance) {
		this.compVariance = compVariance;
	}

	public Double getPercentageSpent() {
		return percentageSpent;
	}

	public void setPercentageSpent(Double percentageSpent) {
		this.percentageSpent = percentageSpent;
	}
}
