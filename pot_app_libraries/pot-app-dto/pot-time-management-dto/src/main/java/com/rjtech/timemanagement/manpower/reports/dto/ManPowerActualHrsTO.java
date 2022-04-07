package com.rjtech.timemanagement.manpower.reports.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManPowerActualHrsTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date workDate;
    private Long projId;
    private Long companyId;
    private Long costCodeId;
    private Long userId;
    private Long wageId;

    private Long parentProjId;
    private Long crewId;
    private Long empClassId;
    private Long unitOfMeasureId;

    private String projName;
    private String companyName;
    private String parentProjName;
    private String costCodeName;
    private String costCodeDesc;
    private String parentCostCodeName;
    private String parentCostCode;
    private String empClassName;
    private String empCode;
    private String empFirstname;
    private String empLastname;
    private String unitOfMeasure;
    private String wageCode;
    private String crewName;
    private String empCategoryName;

    private float wageFactorValue;

    private double currentIdleHrs = 0;
    private double currentUsedHrs = 0;

    private double prevIdleHrs = 0;
    private double prevUsedHrs = 0;

    private double totalIdleHrs;
    private double totalUsedHrs;

    List<ReportHoursTO> hrsList = new ArrayList<>();

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCostCodeId() {
        return costCodeId;
    }

    public void setCostCodeId(Long costCodeId) {
        this.costCodeId = costCodeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWageId() {
        return wageId;
    }

    public void setWageId(Long wageId) {
        this.wageId = wageId;
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

    public double getTotalIdleHrs() {
        this.totalIdleHrs = Double.sum(prevIdleHrs, currentIdleHrs);
        return totalIdleHrs;
    }

    public double getTotalUsedHrs() {
        this.totalUsedHrs = Double.sum(prevUsedHrs, currentUsedHrs);
        return totalUsedHrs;
    }

    public Long getParentProjId() {
        return parentProjId;
    }

    public void setParentProjId(Long parentProjId) {
        this.parentProjId = parentProjId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentProjName() {
        return parentProjName;
    }

    public void setParentProjName(String parentProjName) {
        this.parentProjName = parentProjName;
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

    public String getEmpClassName() {
        return empClassName;
    }

    public void setEmpClassName(String empClassName) {
        this.empClassName = empClassName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpFirstname() {
        return empFirstname;
    }

    public void setEmpFirstname(String empFirstname) {
        this.empFirstname = empFirstname;
    }

    public String getEmpLastname() {
        return empLastname;
    }

    public void setEmpLastname(String empLastname) {
        this.empLastname = empLastname;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getWageCode() {
        return wageCode;
    }

    public void setWageCode(String wageCode) {
        this.wageCode = wageCode;
    }

    public List<ReportHoursTO> getHrsList() {
        return hrsList;
    }

    public void setHrsList(List<ReportHoursTO> hrsList) {
        this.hrsList = hrsList;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public String getEmpCategoryName() {
        return empCategoryName;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

    public Long getUnitOfMeasureId() {
        return unitOfMeasureId;
    }

    public void setUnitOfMeasureId(Long unitOfMeasureId) {
        this.unitOfMeasureId = unitOfMeasureId;
    }

    public float getWageFactorValue() {
        return wageFactorValue;
    }

    public void setWageFactorValue(float wageFactorValue) {
        this.wageFactorValue = wageFactorValue;
    }

    public void setEmpCategoryName(String empCategoryName) {
        this.empCategoryName = empCategoryName;
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
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((wageId == null) ? 0 : wageId.hashCode());
        result = prime * result + ((workDate == null) ? 0 : workDate.hashCode());
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
        ManPowerActualHrsTO other = (ManPowerActualHrsTO) obj;
        if (companyId == null) {
            if (other.companyId != null)
                return false;
        } else if (!companyId.equals(other.companyId))
            return false;
        if (costCodeId == null) {
            if (other.costCodeId != null)
                return false;
        } else if (!costCodeId.equals(other.costCodeId))
            return false;
        if (projId == null) {
            if (other.projId != null)
                return false;
        } else if (!projId.equals(other.projId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (wageId == null) {
            if (other.wageId != null)
                return false;
        } else if (!wageId.equals(other.wageId))
            return false;
        if (workDate == null) {
            if (other.workDate != null)
                return false;
        } else if (!workDate.equals(other.workDate))
            return false;
        return true;
    }

}
