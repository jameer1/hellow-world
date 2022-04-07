package com.rjtech.projsettings.dto;

import java.sql.Time;

import com.rjtech.common.dto.ProjectTO;

public class ProjTimeSheetTO extends ProjectTO {

    private static final long serialVersionUID = 343075446349756974L;
    private Long id;
    private String type;
    private Integer cutOffDays;
    private Time cutOffTime;
    private String defaultStatus;
    private Integer cutOffHours;
    private Integer cutOffMinutes;
    private Long typeId;
    private String weeakStartDay;
    private String weeakEndDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Integer getCutOffHours() {
        return cutOffHours;
    }

    public void setCutOffHours(Integer cutOffHours) {
        this.cutOffHours = cutOffHours;
    }

    public Integer getCutOffMinutes() {
        return cutOffMinutes;
    }

    public void setCutOffMinutes(Integer cutOffMinutes) {
        this.cutOffMinutes = cutOffMinutes;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getWeeakStartDay() {
        return weeakStartDay;
    }

    public void setWeeakStartDay(String weeakStartDay) {
        this.weeakStartDay = weeakStartDay;
    }

    public String getWeeakEndDay() {
        return weeakEndDay;
    }

    public void setWeeakEndDay(String weeakEndDay) {
        this.weeakEndDay = weeakEndDay;
    }

}