package com.rjtech.notification.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name="sch_of_estimates")
public class SchofEstimateNormalTimeEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SOE_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="SOE_EPM_ID")
	private ProjMstrEntity projId;
	
	@Column(name="SOE_TYPE_ID")
	private Long apprTypeId;
	
	@Column(name="SOE_CUT_OFF_DAYS")
	private Integer cutOffDays;
	
	@Column(name="SOE_CUT_OFF_TIME")
	private Time cutOffTime;
	
	@Column(name="SOE_TYPE")
	private String apprType;
	
	@Column(name="SOE_STATUS")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name="SOE_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SOE_CREATED_ON", updatable = false)
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name="SOE_UPDATED_BY")
	private UserMstrEntity updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SOE_UPDATED_ON")
	private Date updatedOn;
	
	@Column(name="SOE_DEF_STATUS")
	private String defaultStatus;
	
	@Column(name="SOE_CUT_OFF_HOURS")
	private Integer cutOffHours;
	
	@Column(name="SOE_CUT_OFF_MINUTES")
	private Integer cutOffMinutes;
	
	@Column(name="SOE_IS_DEFAULT")
	private String isDefault;
	
	public SchofEstimateNormalTimeEntity() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getApprTypeId() {
		return apprTypeId;
	}
	
	public void setApprTypeId(Long apprTypeId) {
		this.apprTypeId = apprTypeId;
	}
	
	public Integer getCutOffDays() {
		return cutOffDays;
	}
	
	public void setCutOffDays(Integer cutOffDays) {
		this.cutOffDays = cutOffDays;
	}
	
	public Time getCutOffTime() {
		return cutOffTime;
	}
	
	public void setCutOffTime(Time cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	
	public String getApprType() {
		return apprType;
	}
	
	public void setApprType(String apprType) {
		this.apprType = apprType;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Date getUpdatedOn() {
		return updatedOn;
	}
	
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public String getDefaultStatus() {
		return defaultStatus;
	}
	
	public void setDefaultStatus(String defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
	
	public Integer getCutOffHours() {
		return cutOffHours;
	}
	
	public void setCutOffHours(Integer cutOffHours) {
		this.cutOffHours = cutOffHours;
	}
	
	public Integer getCutOffMinutes() {
		return cutOffMinutes;
	}
	
	public void setCutOffMinutes(Integer cutOffMinutes) {
		this.cutOffMinutes = cutOffMinutes;
	}
	
	public String getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	public UserMstrEntity getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(UserMstrEntity createdBy) {
		this.createdBy = createdBy;
	}
	
	public UserMstrEntity getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(UserMstrEntity updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public ProjMstrEntity getProjId() {
		return projId;
	}
	
	public void setProjId(ProjMstrEntity projId) {
		this.projId = projId;
	}
}