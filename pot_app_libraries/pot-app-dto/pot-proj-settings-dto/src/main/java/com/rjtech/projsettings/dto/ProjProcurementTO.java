package com.rjtech.projsettings.dto;

import java.sql.Time;

import com.rjtech.common.dto.ProjectTO;

public class ProjProcurementTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String procureType;
    private Time cutOffTime;
    private Integer cutOffDays;
    private String defaultStatus;
    private Integer cutOffHours;
    private Integer cutOffMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcureType() {
        return procureType;
    }

    public void setProcureType(String procureType) {
        this.procureType = procureType;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
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

}
