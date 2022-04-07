package com.rjtech.timemanagement.timesheet.dto;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String timeSheetCode;
    private String weekCommenceDay;
    private Date weekStartDate;
    private Date weekEndDate;
    private String type;
    private Long empId;
    private Long crewId;
    private String crewName;
    private Integer maxHrs;
    private Integer additional = 0;
    private Long reqUserId;
    private String reqUserName;
    private String reqUserDisplayName;
    private String reqComments;
    private Long apprUserId;
    private String apprUserName;
    private String apprUserDisplayName;
    private String apprComments;
    private String apprStatus;
    private String notificationStatus;
    private String notificationMsg;
    private boolean disableFlag;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Integer getMaxHrs() {
        return maxHrs;
    }

    public void setMaxHrs(Integer maxHrs) {
        this.maxHrs = maxHrs;
    }

    public Integer getAdditional() {
        return additional;
    }

    public void setAdditional(Integer additional) {
        this.additional = additional;
    }

    public String getWeekCommenceDay() {
        return weekCommenceDay;
    }

    public void setWeekCommenceDay(String weekCommenceDay) {
        this.weekCommenceDay = weekCommenceDay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public Long getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId) {
        this.reqUserId = reqUserId;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public String getReqUserName() {
        return reqUserName;
    }

    public void setReqUserName(String reqUserName) {
        this.reqUserName = reqUserName;
    }

    public String getApprUserName() {
        return apprUserName;
    }

    public void setApprUserName(String apprUserName) {
        this.apprUserName = apprUserName;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getReqUserDisplayName() {
        return reqUserDisplayName;
    }

    public void setReqUserDisplayName(String reqUserDisplayName) {
        this.reqUserDisplayName = reqUserDisplayName;
    }

    public String getApprUserDisplayName() {
        return apprUserDisplayName;
    }

    public void setApprUserDisplayName(String apprUserDisplayName) {
        this.apprUserDisplayName = apprUserDisplayName;
    }

	public String getTimeSheetCode() {
		return timeSheetCode;
	}

	public void setTimeSheetCode(String timeSheetCode) {
		this.timeSheetCode = timeSheetCode;
	}

	public String getCrewName() {
		return crewName;
	}

	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}
	
	public boolean isDisableFlag() {
		return disableFlag;
	}
	
	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}

}