package com.rjtech.projectlib.model;

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
import com.rjtech.projectlib.model.ProjSOWItemEntityCopy1;
import com.rjtech.projectlib.model.WorkDairyEntityCopy1;


/**
 * The persistent class for the work_dairy_progress_dtl database table.
 * 
 */
@Entity
@Table(name = "work_dairy_progress_dtl")
public class WorkDairyProgressDtlEntityCopy1 implements Serializable {

    private static final long serialVersionUID = 5504405804154347672L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDPR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_SOW_ID")
    private ProjSOWItemEntityCopy1 sowId; 

    @Column(name = "WDPR_VALUE")
    private double value;

    @Column(name = "WDPR_APPRV_STATUS")
    private String apprStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPR_UPDATED_ON")
    private Date updatedOn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_WDM_ID")
    private WorkDairyEntityCopy1 workDairyId; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjSOWItemEntityCopy1 getSowId() {
        return sowId;
    }

    public void setSowId(ProjSOWItemEntityCopy1 sowId) {
        this.sowId = sowId;
    } 

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
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

    public WorkDairyEntityCopy1 getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(WorkDairyEntityCopy1 workDairyId) {
        this.workDairyId = workDairyId;
    } 
}
