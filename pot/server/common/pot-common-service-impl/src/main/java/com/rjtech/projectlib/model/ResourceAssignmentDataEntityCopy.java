package com.rjtech.projectlib.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ScheduleActivityDataSetEntityCopy;
import com.rjtech.projectlib.model.ResourceAssignmentDataValueEntityCopy;

@Entity
@Table(name = "P6_RESOURCE_ASSIGNMENT_DATA")
public class ResourceAssignmentDataEntityCopy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "P6_RAD_ID")
	private Long id;

	@NotNull
	@Column(name = "P6_RAD_RESOURCE_ID")
	private String code;

	@Column(name = "p6_rad_resource_ref_id")
	private Long referenceId;

	@Column(name = "p6_rad_resource_ref_type")
	private String referenceType;

	@Column(name = "p6_rad_resource_name")
	private String name;

	@NotNull
	@Column(name = "P6_RAD_RESOURCE_TYPE")
	private String type;

	@NotNull
	@Column(name = "P6_RAD_ACTIVITY_ID")
	private String activityCode;

	@Column(name = "p6_rad_activity_name")
	private String activityName;

	@NotNull
	@Column(name = "P6_RAD_WBS")
	private String wbsCode;

	@Column(name = "P6_RAD_WBS_NAME")
	private String wbsName;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "P6_RAD_SOE") private ProjSOEItemEntityCopy soe;
	 */

	@Temporal(TemporalType.DATE)
	@Column(name = "P6_RAD_START_DT")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "P6_RAD_FINISH_DT")
	private Date finishDate;

	@Column(name = "p6_rad_actual_start_flag")
	private String startDateFlag;

	@Column(name = "p6_rad_actual_finish_flag")
	private String finishDateFlag;

	@Column(name = "P6_RAD_UNIT_OF_MEASURE")
	private String unitOfMeasure;

	@Column(name = "P6_RAD_BUDGET_UNITS")
	private Long budgetUnits;

	@Column(name = "P6_RAD_ACTUAL_UNITS")
	private Long actualUnits;

	@Column(name = "P6_RAD_CALENDER")
	private String calendar;

	@Column(name = "P6_RAD_CURVE")
	private String curve;

	@Column(name = "P6_RAD_SPREADSHEET_FIELD")
	private String spreadsheetField;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p6_rad_dataset_id") private ScheduleActivityDataSetEntityCopy scheduleActivityDataSetEntity;

	@Column(name = "P6_RAD_STATUS")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p6_rad_created_by", updatable = false)
	private UserMstrEntity createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "p6_rad_created_on", updatable = false)
	private Date createdOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p6_rad_updated_by")
	private UserMstrEntity updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "p6_rad_updated_on")
	private Date updatedOn;

	
	  @OneToMany(cascade=CascadeType.ALL, mappedBy="resourceAssignmentDataEntity")
	  private List<ResourceAssignmentDataValueEntityCopy> resourceAssignmentDataValueEntities;
	 

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

	/*
	 * public ProjSOEItemEntityCopy getSoe() { return soe; }
	 * 
	 * public void setSoe(ProjSOEItemEntityCopy soe) { this.soe = soe; }
	 */

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getStartDateFlag() {
		return startDateFlag;
	}

	public void setStartDateFlag(String startDateFlag) {
		this.startDateFlag = startDateFlag;
	}

	public String getFinishDateFlag() {
		return finishDateFlag;
	}

	public void setFinishDateFlag(String finishDateFlag) {
		this.finishDateFlag = finishDateFlag;
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

	
	  public ScheduleActivityDataSetEntityCopy getScheduleActivityDataSetEntity() {
	  return scheduleActivityDataSetEntity; }
	  
	  public void setScheduleActivityDataSetEntity(ScheduleActivityDataSetEntityCopy
	  scheduleActivityDataSetEntity) { this.scheduleActivityDataSetEntity =
	  scheduleActivityDataSetEntity; }
	 

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserMstrEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserMstrEntity createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public UserMstrEntity getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserMstrEntity updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	
	  public List<ResourceAssignmentDataValueEntityCopy>
	  getResourceAssignmentDataValueEntities() { return
	  resourceAssignmentDataValueEntities; }
	  
	  public void setResourceAssignmentDataValueEntities(
	  List<ResourceAssignmentDataValueEntityCopy> resourceAssignmentDataValueEntities)
	  { this.resourceAssignmentDataValueEntities =
	  resourceAssignmentDataValueEntities; }
	 

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

