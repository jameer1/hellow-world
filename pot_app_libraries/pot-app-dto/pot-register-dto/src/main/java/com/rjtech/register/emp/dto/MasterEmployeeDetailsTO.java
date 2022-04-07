package com.rjtech.register.emp.dto;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class MasterEmployeeDetailsTO extends EmpRegisterDtlTO {
	
	private static final long serialVersionUID = 1L;
	
	private Date DOBirth;
	private String localNonLocal;
	private String employmentType;
	private Date  empEnrollment;
	private String currentStatus;
	private Date expectedDeMobDate;
	private Date actualDeMobDate;
	private Date enrollmentDate;
	private String empType;
	private String location;
	private Date mobilaizationDate;
	private String empClassCode;
	private Long projEmpClassId;
	private String projEmpClassCatg;
	private String projEmpClassTrade;
	private String projEmpClassUnion;
	private List<EmpRegisterDtlTO> empRegisterDtlTOs = new ArrayList<EmpRegisterDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	
	public Date getDOBirth() {
		return DOBirth;
	}
	
	public void setDOBirth(Date DOBirth) {
		this.DOBirth = DOBirth;
	}
	
	public String getLocalNonLocal() {
		return localNonLocal;
	}
	
	public void setLocalNonLocal(String localNonLocal) {
		this.localNonLocal = localNonLocal;
	}
	
	public String getEmploymentTypeType() {
		return employmentType;
	}
	
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	
	public Date getEmpEnrollment() {
		return empEnrollment;
	}
	
	public void setEmpEnrollment(Date empEnrollment) {
		this.empEnrollment = empEnrollment;
	}
	
	public String getCurrentStatus() {
		return currentStatus;
	}
	
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	public void setExpectedDeMobDate(Date expectedDeMobDate) {
		this.expectedDeMobDate = expectedDeMobDate;
	}
	
	public Date getExpectedDeMobDate() {
		return expectedDeMobDate;
	}
	
	public void setActualDeMobDate(Date actualDeMobDate) {
		this.actualDeMobDate = actualDeMobDate;
	}
	
	public Date getActualDeMobDate() {
		return actualDeMobDate;
	}
	
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
	
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	
	public String getEmpType() {
		return empType;
	}
	
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	 public List<EmpRegisterDtlTO> getEmpRegisterDtlTOs() {
	        return empRegisterDtlTOs;
	    }

	    public void setEmpRegisterDtlTOs(List<EmpRegisterDtlTO> empRegisterDtlTOs) {
	        this.empRegisterDtlTOs = empRegisterDtlTOs;
	    }
	
	 public void setMobilaizationDate(Date mobilaizationDate) {
		 this.mobilaizationDate = mobilaizationDate;
	 }
	 
	 public Date getMobilaizationDate() {
		 return mobilaizationDate;
	 }
	 
	 public void setEmpClassCode(String empClassCode) {
		 this.empClassCode = empClassCode;
	 }
	 
	 public String getEmpClassCode() {
		 return empClassCode;
	 }
	 
	public void setProjEmpClassId(Long projEmpClassId) {
		this.projEmpClassId = projEmpClassId;
	}
	
	public Long getProjEmpClassId() {
		return projEmpClassId;
	}
	
	public void setProjEmpClassCatg(String projEmpClassCatg) {
		this.projEmpClassCatg = projEmpClassCatg;
	}
	
	public String getProjEmpClassCatg() {
		return projEmpClassCatg;
	}
	
	public void setProjEmpClassTrade(String projEmpClassTrade) {
		this.projEmpClassTrade = projEmpClassTrade;
	}
	
	public String getProjEmpClassTrade(String projEmpClassTrade) {
		return projEmpClassTrade;
	}
	
	public void setProjEmpClassUnion(String projEmpClassUnion) {
		this.projEmpClassUnion = projEmpClassUnion;
	}
	
	public String getProjEmpClassUnion() {
		return projEmpClassUnion;
	}
	
	public String toString() {
		return "DOBirth: "+DOBirth+" localNonLocal: "+localNonLocal+" empType: "+employmentType+" currentStatus: "+currentStatus+" empClassCode: "+empClassCode;
	}
	}