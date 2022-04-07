package com.rjtech.timemanagement.attendance.dto;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class EmpAttendanceReasonTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2594360965057762968L;

    private Long projId;
    private Long crewId;
    private Long attendanceId;
    private Long empId;
    private String reason;
    private String attendanceMonth;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(String attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
