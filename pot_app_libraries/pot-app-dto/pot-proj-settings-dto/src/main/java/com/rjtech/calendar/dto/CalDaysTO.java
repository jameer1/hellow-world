package com.rjtech.calendar.dto;

import com.rjtech.common.dto.ProjectTO;
import java.util.Date;  

public class CalDaysTO extends ProjectTO {

    private static final long serialVersionUID = 2625256943854586539L;

    private Long id;
    private Date date;
    private Integer dayType;
    private Integer indexValue;
    private boolean holiday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDayType() {
        return dayType;
    }

    public void setDayType(Integer dayType) {
        this.dayType = dayType;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

}
