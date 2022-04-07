package com.rjtech.projectlib.dto;

import java.sql.Time;

import com.rjtech.common.dto.ClientTO;

public class ProjWorkShiftTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private Long id;
    private String code;
    private Time startTime;
    private Time finishTime;
    private Integer startHours;
    private Integer startMinutes;
    private Integer finishHours;
    private Integer finishMinutes;
    private Long projectId;

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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStartHours() {
        return startHours;
    }

    public void setStartHours(Integer startHours) {
        this.startHours = startHours;
    }

    public Integer getFinishHours() {
        return finishHours;
    }

    public void setFinishHours(Integer finishHours) {
        this.finishHours = finishHours;
    }

    public Integer getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(Integer startMinutes) {
        this.startMinutes = startMinutes;
    }

    public Integer getFinishMinutes() {
        return finishMinutes;
    }

    public void setFinishMinutes(Integer finishMinutes) {
        this.finishMinutes = finishMinutes;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjWorkShiftTO other = (ProjWorkShiftTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
