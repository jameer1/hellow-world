package com.rjtech.reports.cost.resp;

import java.io.Serializable;

public class CostItemReportTO implements Serializable {

    private static final long serialVersionUID = 2573669866672123611L;

    private Long epsId;
    private String epsName;

    private Long projId;
    private String projName;

    private Long costSubGroupId;
    private String costSubGroupCode;
    private String costSubGroupName;

    private Long costItemId;
    private String costItemCode;
    private String costItemName;

    private Double labourAmount = Double.valueOf(0);
    private Double plantAmount = Double.valueOf(0);
    private Double matAmount = Double.valueOf(0);
    private Double otherAmount = Double.valueOf(0);
    private String currencyCode;

    public Long getEpsId() {
        return epsId;
    }

    public void setEpsId(Long epsId) {
        this.epsId = epsId;
    }

    public String getEpsName() {
        return epsName;
    }

    public void setEpsName(String epsName) {
        this.epsName = epsName;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public Long getCostSubGroupId() {
        return costSubGroupId;
    }

    public void setCostSubGroupId(Long costSubGroupId) {
        this.costSubGroupId = costSubGroupId;
    }

    public String getCostSubGroupCode() {
        return costSubGroupCode;
    }

    public void setCostSubGroupCode(String costSubGroupCode) {
        this.costSubGroupCode = costSubGroupCode;
    }

    public String getCostSubGroupName() {
        return costSubGroupName;
    }

    public void setCostSubGroupName(String costSubGroupName) {
        this.costSubGroupName = costSubGroupName;
    }

    public Long getCostItemId() {
        return costItemId;
    }

    public void setCostItemId(Long costItemId) {
        this.costItemId = costItemId;
    }

    public String getCostItemCode() {
        return costItemCode;
    }

    public void setCostItemCode(String costItemCode) {
        this.costItemCode = costItemCode;
    }

    public String getCostItemName() {
        return costItemName;
    }

    public void setCostItemName(String costItemName) {
        this.costItemName = costItemName;
    }

    public Double getLabourAmount() {
        return labourAmount;
    }

    public void setLabourAmount(Double labourAmount) {
        this.labourAmount = labourAmount;
    }

    public Double getPlantAmount() {
        return plantAmount;
    }

    public void setPlantAmount(Double plantAmount) {
        this.plantAmount = plantAmount;
    }

    public Double getMatAmount() {
        return matAmount;
    }

    public void setMatAmount(Double matAmount) {
        this.matAmount = matAmount;
    }

    public Double getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(Double otherAmount) {
        this.otherAmount = otherAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
