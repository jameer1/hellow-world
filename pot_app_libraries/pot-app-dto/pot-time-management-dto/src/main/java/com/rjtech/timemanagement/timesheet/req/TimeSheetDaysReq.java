package com.rjtech.timemanagement.timesheet.req;

import com.rjtech.common.resp.AppResp;

public class TimeSheetDaysReq extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private String weekCommenceDay;
    private Integer weekStartNo;
    private Integer weekEndNo;

    public TimeSheetDaysReq() {
    }

    public String getWeekCommenceDay() {
        return weekCommenceDay;
    }

    public void setWeekCommenceDay(String weekCommenceDay) {
        this.weekCommenceDay = weekCommenceDay;
    }

    public Integer getWeekStartNo() {
        return weekStartNo;
    }

    public void setWeekStartNo(Integer weekStartNo) {
        this.weekStartNo = weekStartNo;
    }

    public Integer getWeekEndNo() {
        return weekEndNo;
    }

    public void setWeekEndNo(Integer weekEndNo) {
        this.weekEndNo = weekEndNo;
    }

}
