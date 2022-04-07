package com.rjtech.calendar.dto;

import com.rjtech.common.dto.ProjectTO;

public class CalTO extends ProjectTO {

    private static final long serialVersionUID = 2625256943854586539L;

    private Long id;
    private String code;
    private String name;
    private boolean latest;
    private String fromDate;
    private Integer calDefaultValue;
    private String toDate;
    private boolean isProjectAssigned;
    private boolean isGlobal;
    private String calType;

    private String projectName;

    public String getCalType() {
        return calType;
    }

    public void setCalType(String calType) {
        this.calType = calType;
    }

    public boolean isProjectAssigned() {
        return isProjectAssigned;
    }

    public void setProjectAssigned(boolean isProjectAssigned) {
        this.isProjectAssigned = isProjectAssigned;
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

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public Integer getCalDefaultValue() {
        return calDefaultValue;
    }

    public void setCalDefaultValue(Integer calDefaultValue) {
        this.calDefaultValue = calDefaultValue;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
