package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;
import java.sql.Time;

public class SchofEstimatesApprTO extends ProjectTO{
	
	private Long id;
	private Long schEstiId;
	private Long appUserId;
	private String fromDate; 
	private String toDate;
	private Long notificationId;
	private String notification;
	private Long projCrewId;
	private String crewName;
	
	private Integer cutOffDays;
	private Time cutOffTime;
	private String apprType;
	private Long apprTypeId;
	private String defaultStatus;
	private Integer cutOffHours;
	private Integer cutOffMinutes;
	
	public String getCrewName() {
		return crewName;
	}
	
	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSchEstiId() {
		return schEstiId;
	}
	
	public void setSchEstiId(Long schEstiId) {
		this.schEstiId = schEstiId;
	}
	
	public Long getAppUserId() {
		return appUserId;
	}
	
	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}
	
	public Long getProjCrewId() {
		return projCrewId;
	}
	
	public void setProjCrewId(Long projCrewId) {
		this.projCrewId = projCrewId;
	}
	
	public Long getNotificationId() {
		return notificationId;
	}
	
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
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
	
	public String getNotification() {
		return notification;
	}
	
	public void setNotification(String notification) {
		this.notification = notification;
	}
	
	public void setCutOffDays(Integer cutOffDays) {
		this.cutOffDays = cutOffDays;
	}
	
	public Integer getCutOffDays() {
		return cutOffDays;
	}
	

	 public void setCutOffTime(Time cutOffTime) { 
		 this.cutOffTime = cutOffTime;
	 }
	  
	  public Time getCutOffTime() { 
		  return cutOffTime;
	 }
	 
	
	public void setApprType(String apprType) {
		this.apprType = apprType;
	}
	
	public String getApprType() {
		return apprType;
	}
	
	public void setApprTypeId(Long apprTypeId) {
		this.apprTypeId = apprTypeId;
	}
	
	public Long getApprTypeId() {
		return apprTypeId;
	}
	
	public void setDefaultStatus(String defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
	
	public String getDefaultStatus() {
		return defaultStatus;
	}
	
	public void setCutOffHours(Integer cutOffHours) {
		this.cutOffHours = cutOffHours;
	}
	
	public Integer getCutOffHours() {
		return cutOffHours;
	}
	
	public void setCutOffMinutes(Integer cutOffMinutes) {
		this.cutOffMinutes = cutOffMinutes;
	}
	
	public Integer getCutOffMinutes() {
		return cutOffMinutes;
	}
}