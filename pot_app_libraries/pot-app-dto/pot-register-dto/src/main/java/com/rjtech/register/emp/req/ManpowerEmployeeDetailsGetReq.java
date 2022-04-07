package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

public class ManpowerEmployeeDetailsGetReq {
	
	private static final long serialVersionUID = 597086458869092353L;
	
	 private List<Long> projIds = new ArrayList<Long>();
	 private List<Long> cmpIds = new ArrayList<Long>();
	 private String employmentType;
	 private String gender;
	 private String currentStatus;
	 private String enrollFromDate;
	 private String enrollToDate;
	 private String expectedDeMobFromDate;
	 private String expectedDeMobToDate;
	 private String actualDeMobFromDate;
	 private String actualDeMobToDate;
	 private String subReportType;
	 private String empType;
	 
	 public List<Long> getProjIds() {
	        return projIds;
	    }

	 public void setProjIds(List<Long> projIds) {
	      this.projIds = projIds;
	 }
	 
	 public List<Long> getCmpIds() {
	       return cmpIds;
	  }

	 public void setCmpIds(List<Long> cmpIds) {
	     this.cmpIds = cmpIds;
	 }

	 public void setEmploymentType(String employmentType) {
		 this.employmentType = employmentType;
	 }
	 
	 public String getEmploymentType() {
		 return employmentType;
	 }
	 
	 public void setGender(String gender) {
		 this.gender = gender;
	 }
	 
	 public String getGender(){
		 return gender;
	 }
	 
	 public void setCurrentStatus(String currentStatus) {
		 this.currentStatus = currentStatus;
	 }
	 
	 public String getCurrentStatus(){
		 return currentStatus;
	 }
	 
	 public void setEnrollFrom(String enrollFromDate) {
		 this.enrollFromDate = enrollFromDate;
	 }
	 
	 public String getEnrollFromDate() {
		 return enrollFromDate;
	 }
	 
	 public void setEnrollToDate(String enrollToDate) {
		 this.enrollToDate = enrollToDate;
	 }
	 
	 public String getEnrollToDate() {
		 return enrollToDate;
	 }
	 
	 public void setExpectedDeMobFromDate(String expectedDeMobFromDate) {
		 this.expectedDeMobFromDate = expectedDeMobFromDate;
	 }
	 
	 public String getExpectedDeMobFromDate() {
		 return expectedDeMobFromDate;
	 }
	 
	 public void setExpectedDeMobToDate(String expectedDeMobToDate) {
		 this.expectedDeMobToDate = expectedDeMobToDate;
	 }
	 
	 public String getExpectedDeMobToDate() {
		 return expectedDeMobToDate;
	 }
	 
	 public void setActualDeMobFromDate(String actualDeMobFromDate) {
		 this.actualDeMobFromDate = actualDeMobFromDate;
	 }
	 
	 public String getActualDeMobFromDate() {
	 	return actualDeMobFromDate;
	 }
	 
	public void setActualDeMobToDate(String actualDeMobToDate) {
		this.actualDeMobToDate = actualDeMobToDate;
	}
	
	public String getActualDeMobToDate() {
		 return actualDeMobToDate;
	 }
	
	public void setSubReportType(String subReportType) {
		this.subReportType = subReportType;
	}
	
	public String getSubReportType() {
		return subReportType;
	}
	
	public String getEmpType() {
		return empType;
	}
	
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public String toString() {
		return "projIds: "+projIds+" cmpIds: "+cmpIds+" employmentType: "+employmentType+" gender: "+gender+" currentStatus: "+currentStatus+" enrollFromDate: "+enrollFromDate+" enrollToDate: "+enrollToDate+" gender: "+gender+" actualDeMobFromDate: "+
				actualDeMobFromDate+" actualDeMobToDate: "+actualDeMobToDate+" expectedDeMobFromDate: "+expectedDeMobFromDate+" expectedDeMobToDate: "+expectedDeMobToDate;
	}
	}