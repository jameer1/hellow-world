package com.rjtech.common.dto;

import java.io.Serializable;

public class FinancialYearTo implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String fromDate;
    private String toDate;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
