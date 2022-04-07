package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjTimeSheetWeekDtlTO extends ProjectTO {

    private static final long serialVersionUID = -1708644603945832282L;
    private Long id;
    private String weekEndDay;
    private String weekStartDay;
    private String effectiveFrom;
    private String effectiveTo;
    private String isLatest;

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

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

}
