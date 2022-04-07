package com.rjtech.projschedule.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResourceAssignmentDataTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;
	private String name;
	private Long referenceId;
	private String referenceType;
	private String type;
	private String activityCode;
	private String activityName;
	private String wbsCode;
	private String wbsName;
	private Long soeId;
	private String soeCode;
	private Date startDate;
	private boolean isStartDateFinal;
    private Date finishDate;
    private boolean isFinishDateFinal;
	private String unitOfMeasure;
	private Long budgetUnits;
	private Long actualUnits;
	private String calendar;
	private String curve;
	private String spreadsheetField;
	private String status;
	private List<ResourceAssignmentDataValueTO> resourceAssignmentDataValueTOs;
	private boolean isValid;
	private List<String> validationMessages = new ArrayList<String>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getWbsCode() {
		return wbsCode;
	}
	public void setWbsCode(String wbsCode) {
		this.wbsCode = wbsCode;
	}
	public String getWbsName() {
		return wbsName;
	}
	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}
	public Long getSoeId() {
		return soeId;
	}
	public void setSoeId(Long soeId) {
		this.soeId = soeId;
	}
	public String getSoeCode() {
		return soeCode;
	}
	public void setSoeCode(String soeCode) {
		this.soeCode = soeCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public boolean isStartDateFinal() {
		return isStartDateFinal;
	}
	public void setStartDateFinal(boolean isStartDateFinal) {
		this.isStartDateFinal = isStartDateFinal;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public boolean isFinishDateFinal() {
		return isFinishDateFinal;
	}
	public void setFinishDateFinal(boolean isFinishDateFinal) {
		this.isFinishDateFinal = isFinishDateFinal;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public Long getBudgetUnits() {
		return budgetUnits;
	}
	public void setBudgetUnits(Long budgetUnits) {
		this.budgetUnits = budgetUnits;
	}
	public Long getActualUnits() {
		return actualUnits;
	}
	public void setActualUnits(Long actualUnits) {
		this.actualUnits = actualUnits;
	}
	public String getCalendar() {
		return calendar;
	}
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	public String getCurve() {
		return curve;
	}
	public void setCurve(String curve) {
		this.curve = curve;
	}
	public String getSpreadsheetField() {
		return spreadsheetField;
	}
	public void setSpreadsheetField(String spreadsheetField) {
		this.spreadsheetField = spreadsheetField;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public List<ResourceAssignmentDataValueTO> getResourceAssignmentDataValueTOs() {
		return resourceAssignmentDataValueTOs;
	}
	public void setResourceAssignmentDataValueTOs(List<ResourceAssignmentDataValueTO> resourceAssignmentDataValueTOs) {
		this.resourceAssignmentDataValueTOs = resourceAssignmentDataValueTOs;
	}
	public List<String> getValidationMessages() {
		return validationMessages;
	}
	public void setValidationMessages(List<String> validationMessages) {
		this.validationMessages = validationMessages;
	}
	public void addValidationMessage(String validationMessage) {
		this.validationMessages.add(validationMessage);
	}
	public Long getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
