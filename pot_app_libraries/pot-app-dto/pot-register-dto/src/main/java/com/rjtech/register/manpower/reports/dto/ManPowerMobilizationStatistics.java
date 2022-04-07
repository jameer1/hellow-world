package com.rjtech.register.manpower.reports.dto;

import java.util.Date;

public class ManPowerMobilizationStatistics extends ManPowerGenderStatistics {

    private static final long serialVersionUID = 1L;

    private Date mobilizationDate;
    private Date deMobilizationDate;
    private int prevCount;
    private int currentDeMobilCount;
    private int currentMobilCount;

    public int getPrevCount() {
        return prevCount;
    }

    public void setPrevCount(int prevCount) {
        this.prevCount = prevCount;
    }

    public int getCurrentDeMobilCount() {
        return currentDeMobilCount;
    }

    public void setCurrentDeMobilCount(int currentDeMobilCount) {
        this.currentDeMobilCount = currentDeMobilCount;
    }

    public int getCurrentMobilCount() {
        return currentMobilCount;
    }

    public void setCurrentMobilCount(int currentMobilCount) {
        this.currentMobilCount = currentMobilCount;
    }

    public int getTotalCount() {
        return (prevCount + currentMobilCount) - currentDeMobilCount;
    }

    public Date getMobilizationDate() {
        return mobilizationDate;
    }

    public void setMobilizationDate(Date mobilizationDate) {
        this.mobilizationDate = mobilizationDate;
    }

    public Date getDeMobilizationDate() {
        return deMobilizationDate;
    }

    public void setDeMobilizationDate(Date deMobilizationDate) {
        this.deMobilizationDate = deMobilizationDate;
    }

    public void incrementPrevCount() {
        this.prevCount++;
    }

    public void incrementCurrentMobilCount() {
        this.currentMobilCount++;
    }

    public void incrementCurrentDeMobilCount() {
        this.currentDeMobilCount++;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        return getClass() == obj.getClass();
    }

}
