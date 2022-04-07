package com.rjtech.timemanagement.manpower.reports.dto;

import java.util.Date;

public class ReportHoursTO {

    private Date date;
    private Double usedHrs;
    private Double idleHrs;

    public ReportHoursTO() {

    }

    public ReportHoursTO(Date workDate, Double usedHrs, Double idleHrs) {
        super();
        this.date = workDate;
        if (usedHrs == null)
            this.usedHrs = (double) 0;
        else
            this.usedHrs = usedHrs;

        if (idleHrs == null)
            this.idleHrs = (double) 0;
        else
            this.idleHrs = idleHrs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getUsedHrs() {
        return usedHrs;
    }

    public void setUsedHrs(Double usedHrs) {
        this.usedHrs = usedHrs;
    }

    public Double getIdleHrs() {
        return idleHrs;
    }

    public void setIdleHrs(Double idleHrs) {
        this.idleHrs = idleHrs;
    }

    public void add(ReportHoursTO hrsToAdd) {
        this.idleHrs += hrsToAdd.getIdleHrs();
        this.usedHrs += hrsToAdd.getUsedHrs();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        ReportHoursTO other = (ReportHoursTO) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

}
