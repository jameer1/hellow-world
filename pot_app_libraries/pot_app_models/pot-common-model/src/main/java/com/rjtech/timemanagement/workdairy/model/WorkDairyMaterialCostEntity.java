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

@Entity
@Table(name = "work_dairy_material_timecost")
public class WorkDairyMaterialCostEntity implements Serializable {

    private static final long serialVersionUID = -3911150328997716660L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDMT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMT_WDMS_ID")
    private WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMT_CCS_ID")
    private ProjCostItemEntity costId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMT_WDM_ID")
    private WorkDairyEntity workDairyId;

    @Column(name = "WDMT_QUANTITY")
    private double quantity;

    @Column(name = "WDMT_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMT_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkDairyMaterialStatusEntity getWorkDairyMaterialStatusEntity() {
        return workDairyMaterialStatusEntity;
    }

    public void setWorkDairyMaterialStatusEntity(WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity) {
        this.workDairyMaterialStatusEntity = workDairyMaterialStatusEntity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
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

    public ProjCostItemEntity getCostId() {
        return costId;
    }

    public void setCostId(ProjCostItemEntity costId) {
        this.costId = costId;
    }

    public WorkDairyEntity getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(WorkDairyEntity workDairyId) {
        this.workDairyId = workDairyId;
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