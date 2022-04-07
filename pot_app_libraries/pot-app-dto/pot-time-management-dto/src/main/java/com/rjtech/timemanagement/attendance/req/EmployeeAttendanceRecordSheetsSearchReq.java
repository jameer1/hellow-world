package com.rjtech.timemanagement.attendance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class EmployeeAttendanceRecordSheetsSearchReq extends ProjectTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Long> projIds;
	private String userType;
	private String fromDate;
	private String toDate;
	private Long userId;
	
	public List<Long> getProjIds() {
        return projIds;
    }
    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
