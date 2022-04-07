package com.rjtech.timemanagement.progress.reports.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProgressReportTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workDairyDate;
    private Long projId;
    private String projCode;
    private String projName;

    private Long epsId;
    private String epsCode;
    private String epsName;

    private Long costId;
    private String costCode;
    private String costName;

    private Long parentCostId;
    private String parentCostCode;
    private String parentCostName;

    private String unitOfMeasure;

    private Long sowId;
    private String sowDesc;

    private Long soeId;
    private String soeCode;
    private String soeDesc;

    private Long parentSoeId;
    private String parentSoeCode;
    private String parentSoeDesc;

    private Long sorId;
    private String sorCode;
    private String sorDesc;
    private BigDecimal sorTotal;
    private BigDecimal sorQuantity;

    private Long parentSorId;
    private String parentSorCode;
    private String parentSorDesc;

    private double currentValue = 0;
    private double prevValue = 0;
    private double plannedValue = 0;
    private List<ReportValueTO> hrsList = new ArrayList<>();

    private String currency;

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getEpsCode() {
        return epsCode;
    }

    public void setEpsCode(String epsCode) {
        this.epsCode = epsCode;
    }

    public String getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(String workDairyDate) {
        this.workDairyDate = workDairyDate;
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

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public Long getParentCostId() {
        return parentCostId;
    }

    public void setParentCostId(Long parentCostId) {
        this.parentCostId = parentCostId;
    }

    public String getParentCostCode() {
        return parentCostCode;
    }

    public void setParentCostCode(String parentCostCode) {
        this.parentCostCode = parentCostCode;
    }

    public String getParentCostName() {
        return parentCostName;
    }

    public void setParentCostName(String parentCostName) {
        this.parentCostName = parentCostName;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public String getSowDesc() {
        return sowDesc;
    }

    public void setSowDesc(String sowDesc) {
        this.sowDesc = sowDesc;
    }

    public Long getSoeId() {
        return soeId;
    }

    public void setSoeId(Long soeId) {
        this.soeId = soeId;
    }

    public String getSoeCode() {
        return soeCode;
    }

    public void setSoeCode(String soeCode) {
        this.soeCode = soeCode;
    }

    public String getSoeDesc() {
        return soeDesc;
    }

    public void setSoeDesc(String soeDesc) {
        this.soeDesc = soeDesc;
    }

    public Long getParentSoeId() {
        return parentSoeId;
    }

    public void setParentSoeId(Long parentSoeId) {
        this.parentSoeId = parentSoeId;
    }

    public String getParentSoeCode() {
        return parentSoeCode;
    }

    public void setParentSoeCode(String parentSoeCode) {
        this.parentSoeCode = parentSoeCode;
    }

    public String getParentSoeDesc() {
        return parentSoeDesc;
    }

    public void setParentSoeDesc(String parentSoeDesc) {
        this.parentSoeDesc = parentSoeDesc;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public String getSorCode() {
        return sorCode;
    }

    public void setSorCode(String sorCode) {
        this.sorCode = sorCode;
    }

    public String getSorDesc() {
        return sorDesc;
    }

    public void setSorDesc(String sorDesc) {
        this.sorDesc = sorDesc;
    }

    public BigDecimal getSorTotal() {
        return sorTotal;
    }

    public void setSorTotal(BigDecimal sorTotal) {
        this.sorTotal = sorTotal;
    }

    public BigDecimal getSorQuantity() {
        return sorQuantity;
    }

    public void setSorQuantity(BigDecimal sorQuantity) {
        this.sorQuantity = sorQuantity;
    }

    public Long getParentSorId() {
        return parentSorId;
    }

    public void setParentSorId(Long parentSorId) {
        this.parentSorId = parentSorId;
    }

    public String getParentSorCode() {
        return parentSorCode;
    }

    public void setParentSorCode(String parentSorCode) {
        this.parentSorCode = parentSorCode;
    }

    public String getParentSorDesc() {
        return parentSorDesc;
    }

    public void setParentSorDesc(String parentSorDesc) {
        this.parentSorDesc = parentSorDesc;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(double prevValue) {
        this.prevValue = prevValue;
    }

    public List<ReportValueTO> getHrsList() {
        return hrsList;
    }

    public void setHrsList(List<ReportValueTO> hrsList) {
        this.hrsList = hrsList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void addPrevValue(double prevValue) {
        this.prevValue = Double.sum(this.prevValue, prevValue);
    }

    public void addCurrentValue(double currentValue) {
        this.currentValue = Double.sum(this.currentValue, currentValue);
    }

    public void addPrevHrs(ReportValueTO prevHrs) {
        addPrevValue(prevHrs.getValue());
    }

    public void addCurrentHrs(ReportValueTO currentHrs) {
        addCurrentValue(currentHrs.getValue());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projId == null) ? 0 : projId.hashCode());
        result = prime * result + ((sowId == null) ? 0 : sowId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProgressReportTO other = (ProgressReportTO) obj;
        if (projId == null) {
            if (other.projId != null)
                return false;
        } else if (!projId.equals(other.projId))
            return false;
        if (sowId == null) {
            if (other.sowId != null)
                return false;
        } else if (!sowId.equals(other.sowId))
            return false;
        return true;
    }

	public double getPlannedValue() {
		return plannedValue;
	}

	public void setPlannedValue(double plannedValue) {
		this.plannedValue = plannedValue;
	}

}
