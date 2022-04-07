package com.rjtech.timemanagement.plants.reports.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.timemanagement.manpower.reports.dto.ReportHoursTO;

public class PlantActualHrsTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long projId;
    private String projName;

    private Long parentProjId;
    private String parentProjName;

    private Long companyId;
    private String companyName;

    private Long plantId;
    private String plantAssertId;
    private String plantName;

    private Long plantClassId;
    private String plantTradeName;

    private Long costCodeId;

    private String costCodeName;
    private String costCodeDesc;
    private String parentCostCodeName;
    private String parentCostCode;

    private String unitOfMeasure;

    private double currentIdleHrs = 0;
    private double currentUsedHrs = 0;

    private double prevIdleHrs = 0;
    private double prevUsedHrs = 0;

    private List<ReportHoursTO> hrsList = new ArrayList<>();

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

    public Long getParentProjId() {
        return parentProjId;
    }

    public void setParentProjId(Long parentProjId) {
        this.parentProjId = parentProjId;
    }

    public String getParentProjName() {
        return parentProjName;
    }

    public void setParentProjName(String parentProjName) {
        this.parentProjName = parentProjName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getPlantAssertId() {
        return plantAssertId;
    }

    public void setPlantAssertId(String plantAssertId) {
        this.plantAssertId = plantAssertId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Long getPlantClassId() {
        return plantClassId;
    }

    public void setPlantClassId(Long plantClassId) {
        this.plantClassId = plantClassId;
    }

    public String getPlantTradeName() {
        return plantTradeName;
    }

    public void setPlantTradeName(String plantTradeName) {
        this.plantTradeName = plantTradeName;
    }

    public Long getCostCodeId() {
        return costCodeId;
    }

    public void setCostCodeId(Long costCodeId) {
        this.costCodeId = costCodeId;
    }

    public String getCostCodeName() {
        return costCodeName;
    }

    public void setCostCodeName(String costCodeName) {
        this.costCodeName = costCodeName;
    }

    public String getCostCodeDesc() {
        return costCodeDesc;
    }

    public void setCostCodeDesc(String costCodeDesc) {
        this.costCodeDesc = costCodeDesc;
    }

    public String getParentCostCodeName() {
        return parentCostCodeName;
    }

    public void setParentCostCodeName(String parentCostCodeName) {
        this.parentCostCodeName = parentCostCodeName;
    }

    public String getParentCostCode() {
        return parentCostCode;
    }

    public void setParentCostCode(String parentCostCode) {
        this.parentCostCode = parentCostCode;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getCurrentIdleHrs() {
        return currentIdleHrs;
    }

    public void setCurrentIdleHrs(double currentIdleHrs) {
        this.currentIdleHrs = currentIdleHrs;
    }

    public double getCurrentUsedHrs() {
        return currentUsedHrs;
    }

    public void setCurrentUsedHrs(double currentUsedHrs) {
        this.currentUsedHrs = currentUsedHrs;
    }

    public double getPrevIdleHrs() {
        return prevIdleHrs;
    }

    public void setPrevIdleHrs(double prevIdleHrs) {
        this.prevIdleHrs = prevIdleHrs;
    }

    public double getPrevUsedHrs() {
        return prevUsedHrs;
    }

    public void setPrevUsedHrs(double prevUsedHrs) {
        this.prevUsedHrs = prevUsedHrs;
    }

    public List<ReportHoursTO> getHrsList() {
        return hrsList;
    }

    public void setHrsList(List<ReportHoursTO> hrsList) {
        this.hrsList = hrsList;
    }

    public void addPrevIdleHrs(double prevIdleHrs) {
        this.prevIdleHrs = Double.sum(this.prevIdleHrs, prevIdleHrs);
    }

    public void addPrevUsedHrs(double prevUsedHrs) {
        this.prevUsedHrs = Double.sum(this.prevUsedHrs, prevUsedHrs);
    }

    public void addCurrentIdleHrs(double currentIdleHrs) {
        this.currentIdleHrs = Double.sum(this.currentIdleHrs, currentIdleHrs);
    }

    public void addCurrentUsedHrs(double currentUsedHrs) {
        this.currentUsedHrs = Double.sum(this.currentUsedHrs, currentUsedHrs);
    }

    public void addPrevHrs(ReportHoursTO prevHrs) {
        addPrevIdleHrs(prevHrs.getIdleHrs());
        addPrevUsedHrs(prevHrs.getUsedHrs());
    }

    public void addCurrentHrs(ReportHoursTO currentHrs) {
        addCurrentIdleHrs(currentHrs.getIdleHrs());
        addCurrentUsedHrs(currentHrs.getUsedHrs());
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result + ((costCodeId == null) ? 0 : costCodeId.hashCode());
        result = prime * result + ((projId == null) ? 0 : projId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof PlantActualHrsTO) {
    		PlantActualHrsTO other = (PlantActualHrsTO)obj;
    		return (other.plantId == this.plantId);
    	}else {
    		return false;
    	}
		/*
		 * if (this == obj) return true; if (obj == null) return false; if (getClass()
		 * != obj.getClass()) return false; PlantActualHrsTO other = (PlantActualHrsTO)
		 * obj; if (companyId == null) { if (other.companyId != null) return false; }
		 * else if (!companyId.equals(other.companyId)) return false; if (costCodeId ==
		 * null) { if (other.costCodeId != null) return false; } else if
		 * (!costCodeId.equals(other.costCodeId)) return false; if (projId == null) { if
		 * (other.projId != null) return false; } else if (!projId.equals(other.projId))
		 * return false; return true;
		 */
    }

}
