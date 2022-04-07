package com.rjtech.timemanagement.timesheet.dto;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetEmpRegTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long empId;
    private Long timeSheetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

}