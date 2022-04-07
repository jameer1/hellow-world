package com.rjtech.timemanagement.timesheet.req;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetGetReq extends ProjectTO {

    private static final long serialVersionUID = 3754852758992336728L;

    private Long timeSheetId;
    private Long timeSheetEmpId;
    private Long crewId;
    private Long empId;
    private Integer additional = 0;
    private String currentDate;
    private String weekCommenceDay;
    private Date weekStartDate;
    private Date weekEndDate;
    private String apprStatus;
    private String apprComments;
    private String reqComments;
    private Long apprUserId;
    private Long fromTimeSheetId;
    private Long fromCrewId;
    private Date fromWeekStartDate;
    private String weekStatingDate;
    private String weekEndingDate;

    public String getWeekStatingDate() {
        return weekStatingDate;
    }

    public void setWeekStatingDate(String weekStatingDate) {
        this.weekStatingDate = weekStatingDate;
    }

    public String getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(String weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Date getFromWeekStartDate() {
        return fromWeekStartDate;
    }

    public void setFromWeekStartDate(Date fromWeekStartDate) {
        this.fromWeekStartDate = fromWeekStartDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Date getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(Date weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public Date getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(Date weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public String getWeekCommenceDay() {
        return weekCommenceDay;
    }

    public void setWeekCommenceDay(String weekCommenceDay) {
        this.weekCommenceDay = weekCommenceDay;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Long getFromTimeSheetId() {
        return fromTimeSheetId;
    }

    public void setFromTimeSheetId(Long fromTimeSheetId) {
        this.fromTimeSheetId = fromTimeSheetId;
    }

    public Long getFromCrewId() {
        return fromCrewId;
    }

    public void setFromCrewId(Long fromCrewId) {
        this.fromCrewId = fromCrewId;
    }

    public Long getTimeSheetEmpId() {
        return timeSheetEmpId;
    }

    public void setTimeSheetEmpId(Long timeSheetEmpId) {
        this.timeSheetEmpId = timeSheetEmpId;
    }

    public Integer getAdditional() {
        return additional;
    }

    public void setAdditional(Integer additional) {
        this.additional = additional;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

}
