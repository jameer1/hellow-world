package com.rjtech.timemanagement.timesheet.dto;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetEmpTaskTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long empDtlId;
    private String date;
    private String taskName;
    private Long apprUsrId;
    private Long parentId;
    private String apprStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpDtlId() {
        return empDtlId;
    }

    public void setEmpDtlId(Long empDtlId) {
        this.empDtlId = empDtlId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getApprUsrId() {
        return apprUsrId;
    }

    public void setApprUsrId(Long apprUsrId) {
        this.apprUsrId = apprUsrId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}