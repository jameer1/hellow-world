package com.rjtech.timemanagement.workdairy.model;

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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;

/**
 * The persistent class for the work_dairy_manpower_timecost database table.
 * 
 */
@Entity
@Table(name = "work_dairy_manpower_timecost")
public class WorkDairyEmpCostEntity implements Serializable {

    private static final long serialVersionUID = 3242878236661574195L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WMTC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WMTC_WDMW_ID")
    private WorkDairyEmpWageEntity workDairyEmpWageEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WMTC_WDM_ID")
    private WorkDairyEntity workDairyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WMTC_CCS_ID")
    private ProjCostItemEntity costId;

    @Column(name = "WMTC_USED_TIME")
    private double usedTime;

    @Column(name = "WMTC_IDLE_TIME")
    private double idleTime;

    @Column(name = "WMTC_APPR_STATUS")
    private String apprStatus;

    @Column(name = "WMTC_IS_LATEST", columnDefinition = "int default 0")
    private Boolean isLatest;

    @Column(name = "WMTC_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WMTC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WMTC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WMTC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WMTC_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkDairyEmpWageEntity getWorkDairyEmpWageEntity() {
        return workDairyEmpWageEntity;
    }

    public void setWorkDairyEmpWageEntity(WorkDairyEmpWageEntity workDairyEmpWageEntity) {
        this.workDairyEmpWageEntity = workDairyEmpWageEntity;
    }

    public double getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(double usedTime) {
        this.usedTime = usedTime;
    }

    public double getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(double idleTime) {
        this.idleTime = idleTime;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
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

    public WorkDairyEntity getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(WorkDairyEntity workDairyId) {
        this.workDairyId = workDairyId;
    }

    public ProjCostItemEntity getCostId() {
        return costId;
    }

    public void setCostId(ProjCostItemEntity costId) {
        this.costId = costId;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(Boolean isLatest) {
        this.isLatest = isLatest;
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

}