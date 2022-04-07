package com.rjtech.common.dto;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetWeekDtlTO extends ProjectTO {

    private static final long serialVersionUID = -1708644603945832282L;
    private Long id;
    private String weekEndDay;
    private String weekStartDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeekEndDay() {
        return weekEndDay;
    }

    public void setWeekEndDay(String weekEndDay) {
        this.weekEndDay = weekEndDay;
    }

    public String getWeekStartDay() {
        return weekStartDay;
    }

    public void setWeekStartDay(String weekStartDay) {
        this.weekStartDay = weekStartDay;
    }

}
