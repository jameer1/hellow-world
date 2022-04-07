package com.rjtech.projschedule.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjScheduleBaseLineTO extends ProjectTO {

    private static final long serialVersionUID = -4929619665133609726L;
    private Long id;
    private String code;
    private String name;
    private String date;
    private String scheduleType;
    private String timeScale;
    private String scheduleItemType;

    public String getScheduleItemType() {
        return scheduleItemType;
    }

    public void setScheduleItemType(String scheduleItemType) {
        this.scheduleItemType = scheduleItemType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(String timeScale) {
        this.timeScale = timeScale;
    }

}
