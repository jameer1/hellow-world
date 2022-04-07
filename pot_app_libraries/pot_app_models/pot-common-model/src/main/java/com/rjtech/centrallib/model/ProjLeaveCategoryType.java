package com.rjtech.centrallib.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "proj_leave_category_type_mstr")
public class ProjLeaveCategoryType {

    @Id
    @GeneratedValue
    @Column(name = "PLCT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PLCT_PCD_ID")
    private ProcureCatgDtlEntity procureCatgDtlEntity;

    @Column(name = "PLCT_PAY_TYPE")
    private String payType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PLCT_PJL_ID")
    private ProjLeaveTypeEntity projLeaveTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLCT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLCT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLCT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLCT_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcureCatgDtlEntity getProcureCatgDtlEntity() {
        return procureCatgDtlEntity;
    }

    public void setProcureCatgDtlEntity(ProcureCatgDtlEntity procureCatgDtlEntity) {
        this.procureCatgDtlEntity = procureCatgDtlEntity;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public ProjLeaveTypeEntity getProjLeaveTypeEntity() {
        return projLeaveTypeEntity;
    }

    public void setProjLeaveTypeEntity(ProjLeaveTypeEntity projLeaveTypeEntity) {
        this.projLeaveTypeEntity = projLeaveTypeEntity;
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

}
