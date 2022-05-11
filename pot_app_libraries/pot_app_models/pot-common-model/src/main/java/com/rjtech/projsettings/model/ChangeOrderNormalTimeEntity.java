package com.rjtech.projsettings.model;

import java.sql.*;
import java.util.Date;

import javax.persistence.*;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;


@Entity
@Table(name="change_order_normalTime")
public class ChangeOrderNormalTimeEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "CON_EPM_ID")
	private ProjMstrEntity projId;

	@Column(name = "CON_TYPE_ID")
	private Long apprTypeId;

	@Column(name = "CON_CUT_OFF_DAYS")
	private Integer cutOffDays;

	@Column(name = "CON_CUT_OFF_TIME")
	private Time cutOffTime;

	@Column(name = "CON_TYPE")
	private String apprType;

	@Column(name = "CON_STATUS")
	private Integer status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CON_CREATED_ON", updatable = false)
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "CON_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CON_UPDATED_ON")
	private Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "CON_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Column(name = "CON_DEF_STATUS")
	private String defaultStatus;

	@Column(name = "CON_CUT_OFF_HOURS")
	private Integer cutOffHours;

	@Column(name = "CON_CUT_OFF_MINUTES")
	private Integer cutOffMinutes;

	@Column(name = "CON_IS_DEFAULT")
	private String isDefault;
	 
	 
	
	public  ChangeOrderNormalTimeEntity()
	 {
		 
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjMstrEntity getProjId() {
		return projId;
	}

	public void setProjId(ProjMstrEntity projId) {
		this.projId = projId;
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

	public UserMstrEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserMstrEntity createdBy) {
		this.createdBy = createdBy;
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
	

}
