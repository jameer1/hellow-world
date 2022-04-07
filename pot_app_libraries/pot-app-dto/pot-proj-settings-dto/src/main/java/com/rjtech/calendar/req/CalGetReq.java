package com.rjtech.calendar.req;

import com.rjtech.common.dto.ProjectTO;

public class CalGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1165124423124218730L;

    private Long id;
    private Long calendarId;
    private String month;
    private Integer monthStartNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getMonthStartNo() {
        return monthStartNo;
    }

    public void setMonthStartNo(Integer monthStartNo) {
        this.monthStartNo = monthStartNo;
    }

}
