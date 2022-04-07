package com.rjtech.timemanagement.manpower.reports.dto;

import java.util.Date;

public class ManPowerCostCodeDailyReportTO extends ManPowerActualHrsTO {

    private static final long serialVersionUID = 1L;

    private Date mobilizationDate;
    private String gender;
    private double normalRate;
    private double idleRate;

    public Date getMobilizationDate() {
        return mobilizationDate;
    }

    public void setMobilizationDate(Date mobilizationDate) {
        this.mobilizationDate = mobilizationDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(double normalRate) {
        this.normalRate = normalRate;
    }

    public double getIdleRate() {
        return idleRate;
    }

    public void setIdleRate(double idleRate) {
        this.idleRate = idleRate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((mobilizationDate == null) ? 0 : mobilizationDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ManPowerCostCodeDailyReportTO other = (ManPowerCostCodeDailyReportTO) obj;
        if (mobilizationDate == null) {
            if (other.mobilizationDate != null)
                return false;
        } else if (!mobilizationDate.equals(other.mobilizationDate))
            return false;
        return true;
    }

}
