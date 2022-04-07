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
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

/**
 * The persistent class for the work_dairy_plant_timecost database table.
 * 
 */
@Entity
@Table(name = "work_dairy_plant_timecost")
public class WorkDairyPlantCostEntity implements Serializable {

    private static final long serialVersionUID = 3446297405804223181L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WPTC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WPTC_WDPS_ID")
    private WorkDairyPlantStatusEntity workDairyPlantStatusEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WPTC_WDM_ID")
    private WorkDairyEntity workDairyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WPTC_CCS_ID")
    private ProjCostItemEntity costId;

    @Column(name = "WPTC_USED_TIME")
    private double usedTime;

    @Column(name = "WPTC_IDLE_TIME")
    private double idleTime;

    @Column(name = "WPTC_STATUS")
    private Integer status;

    @Column(name = "WPTC_IS_LATEST")
    private boolean isLatest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WPTC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WPTC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WPTC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WPTC_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(double usedTime) {
        this.usedTime = usedTime;
    }

    public boolean isIsLatest() {
        return isLatest;
    }

    public void setIsLatest(boolean isLatest) {
        this.isLatest = isLatest;
    }

    public double getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(double idleTime) {
        this.idleTime = idleTime;
    }

    public WorkDairyPlantStatusEntity getWorkDairyPlantStatusEntity() {
        return workDairyPlantStatusEntity;
    }

    public void setWorkDairyPlantStatusEntity(WorkDairyPlantStatusEntity workDairyPlantStatusEntity) {
        this.workDairyPlantStatusEntity = workDairyPlantStatusEntity;
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