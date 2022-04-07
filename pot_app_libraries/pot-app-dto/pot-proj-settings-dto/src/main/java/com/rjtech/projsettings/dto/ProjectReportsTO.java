package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjectReportsTO extends ProjectTO {

    private static final long serialVersionUID = 7166123162420346156L;
    private Long id;
    private String isDefault;
    private String week;
    private String month;
    private String year;
    private String firstQuarter;
    private String firstHalf;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFirstQuarter() {
        return firstQuarter;
    }

    public void setFirstQuarter(String firstQuarter) {
        this.firstQuarter = firstQuarter;
    }

    public String getFirstHalf() {
        return firstHalf;
    }

    public void setFirstHalf(String firstHalf) {
        this.firstHalf = firstHalf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

}
