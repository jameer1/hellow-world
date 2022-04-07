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
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;

/**
 * The persistent class for the work_dairy_cost_codes database table.
 * 
 */
@Entity
@Table(name = "work_dairy_cost_codes")
public class WorkDairyCostCodeEntity implements Serializable {

    private static final long serialVersionUID = 7664132979936520632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDCC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDCC_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDCC_CCS_ID")
    private ProjCostItemEntity costId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDCC_WDM_ID", updatable = false)
    private WorkDairyEntity workDairyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDCC_CRW_ID")
    private ProjCrewMstrEntity crewId;

    @Column(name = "WDCC_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDCC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDCC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDCC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDCC_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkDairyEntity getWorkDairyEntity() {
        return workDairyEntity;
    }

    public void setWorkDairyEntity(WorkDairyEntity workDairyEntity) {
        this.workDairyEntity = workDairyEntity;
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

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public ProjCostItemEntity getCostId() {
        return costId;
    }

    public void setCostId(ProjCostItemEntity costId) {
        this.costId = costId;
    }

    public ProjCrewMstrEntity getCrewId() {
        return crewId;
    }

    public void setCrewId(ProjCrewMstrEntity crewId) {
        this.crewId = crewId;
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