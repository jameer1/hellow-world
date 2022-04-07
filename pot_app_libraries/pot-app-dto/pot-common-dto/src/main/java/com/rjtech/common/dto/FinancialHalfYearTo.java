package com.rjtech.common.dto;

import java.io.Serializable;

public class FinancialHalfYearTo implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String firstFromDate;
    private String firstToDate;

    private String secondFromDate;

    private String secondToDate;

    public String getFirstFromDate() {
        return firstFromDate;
    }

    public void setFirstFromDate(String firstFromDate) {
        this.firstFromDate = firstFromDate;
    }

    public String getFirstToDate() {
        return firstToDate;
    }

    public void setFirstToDate(String firstToDate) {
        this.firstToDate = firstToDate;
    }

    public String getSecondFromDate() {
        return secondFromDate;
    }

    public void setSecondFromDate(String secondFromDate) {
        this.secondFromDate = secondFromDate;
    }

    public String getSecondToDate() {
        return secondToDate;
    }

    public void setSecondToDate(String secondToDate) {
        this.secondToDate = secondToDate;
    }
    
    

}
