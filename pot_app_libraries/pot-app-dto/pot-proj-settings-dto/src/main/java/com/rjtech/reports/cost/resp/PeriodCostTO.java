package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PeriodCostTO implements Serializable {

    private static final long serialVersionUID = 1339633713208641387L;

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

    private String currencyCode;

    private Double totalPrevCost = Double.valueOf(0);
    private Double totalPrevEarned = Double.valueOf(0);
    private Double totalPrevPlanned = Double.valueOf(0);

    private Double totalReportCost = Double.valueOf(0);
    private Double totalReportEarned = Double.valueOf(0);
    private Double totalReportPlanned = Double.valueOf(0);

    private List<CostValuesTO> prevValues = new ArrayList<>();
    private List<CostValuesTO> reportValues = new ArrayList<>();
    private Double plannedValue = Double.valueOf(0);

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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getTotalPrevCost() {
        return Double.valueOf(new DecimalFormat("#.##").format(totalPrevCost));
    }

    public void setTotalPrevCost(Double totalPrevCost) {
        this.totalPrevCost = totalPrevCost;
    }

    public Double getTotalPrevEarned() {
        return Double.valueOf(new DecimalFormat("#.##").format(totalPrevEarned));
    }

    public void setTotalPrevEarned(Double totalPrevEarned) {
        this.totalPrevEarned = totalPrevEarned;
    }

    public Double getTotalPrevPlanned() {
        return totalPrevPlanned;
    }

    public void setTotalPrevPlanned(Double totalPrevPlanned) {
        this.totalPrevPlanned = totalPrevPlanned;
    }

    public Double getTotalReportCost() {
        return Double.valueOf(new DecimalFormat("#.##").format(totalReportCost));
    }

    public void setTotalReportCost(Double totalReportCost) {
        this.totalReportCost = totalReportCost;
    }

    public Double getTotalReportEarned() {
        return Double.valueOf(new DecimalFormat("#.##").format(totalReportEarned));
    }

    public void setTotalReportEarned(Double totalReportEarned) {
        this.totalReportEarned = totalReportEarned;
    }

    public Double getTotalReportPlanned() {
        return Double.valueOf(new DecimalFormat("#.##").format(totalReportPlanned));
    }

    public void setTotalReportPlanned(Double totalReportPlanned) {
        this.totalReportPlanned = totalReportPlanned;
    }

    public List<CostValuesTO> getPrevValues() {
        return prevValues;
    }

    public void setPrevValues(List<CostValuesTO> prevValues) {
        this.prevValues = prevValues;
    }

    public List<CostValuesTO> getReportValues() {
        return reportValues;
    }

    public void setReportValues(List<CostValuesTO> reportValues) {
        this.reportValues = reportValues;
    }

	public Double getPlannedValue() {
		return plannedValue;
	}

	public void setPlannedValue(Double plannedValue) {
		this.plannedValue = plannedValue;
	}

}
