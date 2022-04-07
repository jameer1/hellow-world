package com.rjtech.projsettings.model;

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
@Table(name="sch_of_rates")
public class SchofRatesNormalTimeEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SOR_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="SOR_EPM_ID")
	private ProjMstrEntity projId;
	
	@Column(name="SOR_TYPE_ID")
	private Long apprTypeId;
	
	@Column(name="SOR_CUT_OFF_DAYS")
	private Integer cutOffDays;
	
	@Column(name="SOR_CUT_OFF_TIME")
	private Time cutOffTime;
	
	@Column(name="SOR_TYPE")
	private String apprType;
	
	@Column(name="SOR_STATUS")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name="SOR_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SOR_CREATED_ON", updatable = false)
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name="SOR_UPDATED_BY")
	private UserMstrEntity updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SOR_UPDATED_ON")
	private Date updatedOn;
	
	@Column(name="SOR_DEF_STATUS")
	private String defaultStatus;
	
	@Column(name="SOR_CUT_OFF_HOURS")
	private Integer cutOffHours;
	
	@Column(name="SOR_CUT_OFF_MINUTES")
	private Integer cutOffMinutes;
	
	@Column(name="SOR_IS_DEFAULT")
	private String isDefault;
	
	public SchofRatesNormalTimeEntity() {
		
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