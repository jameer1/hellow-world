package com.rjtech.projschedule.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.ProjSOEItemEntityCopy;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;

@Entity
@Table(name = "P6_ACTIVITY_SCHEDULE_DATA")
public class ScheduleActivityDataEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P6_ASD_ID")
    private Long id;
	
	@Column(name = "P6_ASD_CRITICAL")
    private Integer critical;
	
	@NotNull
	@Column(name = "p6_asd_wbs_code")
	private String wbsCode;
	
	@Column(name = "p6_asd_wbs_name")
	private String wbsName;
	
	@Column(name = "P6_ASD_WBS_PATH")
	private String wbsPath;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P6_ASD_SOW_ID")
    private ProjSOWItemEntity sow;
	
	@NotNull
	@Column(name = "P6_ASD_ACTIVITY_CODE")
	private String code;
	
	@Column(name = "P6_ASD_ACTIVITY_NAME")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P6_ASD_SOE_ID")
    private ProjSOEItemEntity soe;
	
	@Column(name = "P6_ASD_ORG_DUR_CODE")
    private Long originalDuration;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "P6_ASD_START_DATE")
    private Date startDate;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "P6_ASD_FINISH_DATE")
    private Date finishDate;
	
	@Column(name = "P6_ASD_ACTUAL_START_FLAG")
	private String startDateFlag;
	
	@Column(name = "P6_ASD_ACTUAL_FINISH_FLAG")
	private String finishDateFlag;
	
	@Column(name = "P6_ASD_PHY_COMPLETE")
    private Long physicalComplete;
	
	@Column(name = "P6_ASD_CALENDAR")
	private String calendar;
	
	@Column(name = "P6_ASD_LEADLAG")
	private int leadLag;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P6_ASD_DATASET_ID")
    private ScheduleActivityDataSetEntity scheduleActivityDataSetEntity;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P6_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "P6_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P6_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "P6_UPDATED_ON")
    private Date updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCritical() {
		return critical;
	}

	public void setCritical(Integer critical) {
		this.critical = critical;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjSOEItemEntity getSoe() {
		return soe;
	}

	public void setSoe(ProjSOEItemEntity soe) {
		this.soe = soe;
	}

	public Long getOriginalDuration() {
		return originalDuration;
	}

	public void setOriginalDuration(Long originalDuration) {
		this.originalDuration = originalDuration;
	}

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

	public Long getPhysicalComplete() {
		return physicalComplete;
	}

	public void setPhysicalComplete(Long physicalComplete) {
		this.physicalComplete = physicalComplete;
	}

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	public ScheduleActivityDataSetEntity getScheduleActivityDataSetEntity() {
		return scheduleActivityDataSetEntity;
	}

	public void setScheduleActivityDataSetEntity(ScheduleActivityDataSetEntity scheduleActivityDataSetEntity) {
		this.scheduleActivityDataSetEntity = scheduleActivityDataSetEntity;
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

	public int getLeadLag() {
		return leadLag;
	}

	public void setLeadLag(int leadLag) {
		this.leadLag = leadLag;
	}

	public String getWbsPath() {
		return wbsPath;
	}

	public void setWbsPath(String wbsPath) {
		this.wbsPath = wbsPath;
	}

	public ProjSOWItemEntity getSow() {
		return sow;
	}

	public void setSow(ProjSOWItemEntity sow) {
		this.sow = sow;
	}

	    
}
