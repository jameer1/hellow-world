package com.rjtech.timemanagement.workdairy.dto;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyCostCodeTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long costId;
    private Long crewId;
    private Long workDairyId;

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

}