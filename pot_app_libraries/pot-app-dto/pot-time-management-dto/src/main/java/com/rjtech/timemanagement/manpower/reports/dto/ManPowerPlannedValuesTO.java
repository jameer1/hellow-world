package com.rjtech.timemanagement.manpower.reports.dto;

import java.io.Serializable;
import java.util.Date;

public class ManPowerPlannedValuesTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date date;
    private Long projId;
    private Long empClassId;

    private double actualHrs;
    private double earnedValue;
    private double plannedValue;
    private Long parentProjId;
    private String projName;
    private String parentProjName;
    private String empClassName;

    public ManPowerPlannedValuesTO() {

    }

    public ManPowerPlannedValuesTO(Date date, Long projId) {
        super();
        this.date = date;
        this.projId = projId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public double getActualHrs() {
        return actualHrs;
    }

    public void setActualHrs(double actualHrs) {
        this.actualHrs = actualHrs;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getParentProjName() {
        return parentProjName;
    }

    public void setParentProjName(String parentProjName) {
        this.parentProjName = parentProjName;
    }

    public Long getParentProjId() {
        return parentProjId;
    }

    public void setParentProjId(Long parentProjId) {
        this.parentProjId = parentProjId;
    }

    public double getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

    public String getEmpClassName() {
        return empClassName;
    }

    public void setEmpClassName(String empClassName) {
        this.empClassName = empClassName;
    }

    public void addActualHrs(double hrsToAdd) {
        this.actualHrs += hrsToAdd;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((empClassId == null) ? 0 : empClassId.hashCode());
        result = prime * result + ((projId == null) ? 0 : projId.hashCode());
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
        ManPowerPlannedValuesTO other = (ManPowerPlannedValuesTO) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (empClassId == null) {
            if (other.empClassId != null)
                return false;
        } else if (!empClassId.equals(other.empClassId))
            return false;
        if (projId == null) {
            if (other.projId != null)
                return false;
        } else if (!projId.equals(other.projId))
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
