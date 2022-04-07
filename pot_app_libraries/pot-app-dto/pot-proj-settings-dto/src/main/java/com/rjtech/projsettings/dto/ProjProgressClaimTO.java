package com.rjtech.projsettings.dto;

import java.sql.Time;

import com.rjtech.common.dto.ProjectTO;

public class ProjProgressClaimTO extends ProjectTO {

    private static final long serialVersionUID = -7027297981919987287L;
    private Long id;
    private String claimType;
    private Long cutOffDays;
    private Time cutOffTime;
    private Integer cutOffHours;
    private Integer cutOffMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public Long getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Long cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
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
