package com.rjtech.projectlib.dto;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CostValuesTO implements Serializable {

    private static final long serialVersionUID = 4L;
    private String date;
    private Long costId;
    private Double costAmount = Double.valueOf(0);
    private Double earnedValue = Double.valueOf(0);
    private Double plannedValue = Double.valueOf(0);
    private Double estimateValue = Double.valueOf(0);

    public CostValuesTO(String date, Long costId, Double costAmount, Double earnedValue) {
        super();
        this.date = date;
        this.costId = costId;
        this.costAmount = costAmount;
        this.earnedValue = earnedValue;
    }
    public CostValuesTO()
    {

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Double getCostAmount() {
        return Double.valueOf(new DecimalFormat("#.##").format(costAmount));
    }

    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }

    public Double getEarnedValue() {
        return Double.valueOf(new DecimalFormat("#.##").format(earnedValue));
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

    public Double getEstimateValue() {
        return Double.valueOf(new DecimalFormat("#.##").format(estimateValue));
    }

    public void setEstimateValue(Double estimateValue) {
        this.estimateValue = estimateValue;
    }

}
