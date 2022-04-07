package com.rjtech.calendar.dto;

import java.util.Date;

import com.rjtech.common.dto.ClientTO;

public class CalSpecialDaysTO extends ClientTO {

    private static final long serialVersionUID = 2625256943854586539L;
    private Long id;
    private Long calendarId;
    private String date;
    private String desc;
    private boolean holiday;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

}
