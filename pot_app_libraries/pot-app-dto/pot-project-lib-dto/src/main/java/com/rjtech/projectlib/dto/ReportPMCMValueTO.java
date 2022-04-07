package com.rjtech.projectlib.dto;

import java.util.Date;

public class ReportPMCMValueTO  {

    private Date date;
    private Long value;

    public ReportPMCMValueTO() {

    }

    public ReportPMCMValueTO(Date date, Long value) {
        super();
        this.date = date;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void add(ReportPMCMValueTO hrsToAdd) {
        this.value += hrsToAdd.getValue();
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
        ReportPMCMValueTO other = (ReportPMCMValueTO) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }
}

