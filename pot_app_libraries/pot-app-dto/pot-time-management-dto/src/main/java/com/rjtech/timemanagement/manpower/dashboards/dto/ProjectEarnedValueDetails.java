package com.rjtech.timemanagement.manpower.dashboards.dto;

import java.io.Serializable;

public class ProjectEarnedValueDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long projId;

    private double earnedHrs;
    private double earnedAmount;

    private Long parentProjId;
    private String projName;
    private String parentProjName;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
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

    public String getParentProjName() {
        return parentProjName;
    }

    public void setParentProjName(String parentProjName) {
        this.parentProjName = parentProjName;
    }

    public double getEarnedHrs() {
        return earnedHrs;
    }

    public void setEarnedHrs(double earnedHrs) {
        this.earnedHrs = earnedHrs;
    }

    public double getEarnedAmount() {
        return earnedAmount;
    }

    public void setEarnedAmount(double earnedAmount) {
        this.earnedAmount = earnedAmount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        ProjectEarnedValueDetails other = (ProjectEarnedValueDetails) obj;
        if (projId == null) {
            if (other.projId != null)
                return false;
        } else if (!projId.equals(other.projId))
            return false;
        return true;
    }

}
