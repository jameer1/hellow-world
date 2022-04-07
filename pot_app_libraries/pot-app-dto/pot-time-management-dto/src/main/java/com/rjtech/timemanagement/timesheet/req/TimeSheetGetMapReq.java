package com.rjtech.timemanagement.timesheet.req;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetGetMapReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long timeSheetId;
    private Long crewId;
    private Date weekStartDate;
    private Integer status;

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Date getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(Date weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
