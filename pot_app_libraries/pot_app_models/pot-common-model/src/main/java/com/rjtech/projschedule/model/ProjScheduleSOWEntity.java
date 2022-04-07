package com.rjtech.projschedule.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;

@Entity
@Table(name = "schedule_sow_base_line")
public class ProjScheduleSOWEntity implements Serializable {

    private static final long serialVersionUID = -4520671289056314854L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHSB_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHSB_SOW_ID")
    private ProjSOWItemEntity sowId;

    @ManyToOne
    @JoinColumn(name = "SHSB_SCHB_ID")
    private ProjScheduleBaseLineEntity projScheduleBaseLineEntity;

    @Column(name = "SHSB_ORIGINAL_QTY")
    private BigDecimal originalQty;

    @Column(name = "SHSB_REVISED_QTY")
    private BigDecimal revisedQty;

    @Column(name = "SHSB_ACTUAL_QTY")
    private BigDecimal actualQty;

    @Column(name = "SHSB_REMAINING_QTY")
    private BigDecimal remainingQty;

    @Column(name = "SHSB_EST_COMPLETE_QTY")
    private BigDecimal estimateComplete;

    @Column(name = "SHSB_EST_COMPLETION_QTY")
    private BigDecimal estimateCompletion;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHSB_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHSB_FINISH_DATE")
    private Date finishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHSB_RC_ID")
    private ResourceCurveEntity resourceCurveId;

    @Column(name = "SHSB_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHSB_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHSB_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHSB_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHSB_UPDATED_ON")
    private Date updatedOn;

    public ProjScheduleSOWEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjScheduleBaseLineEntity getProjScheduleBaseLineEntity() {
        return projScheduleBaseLineEntity;
    }

    public void setProjScheduleBaseLineEntity(ProjScheduleBaseLineEntity projScheduleBaseLineEntity) {
        this.projScheduleBaseLineEntity = projScheduleBaseLineEntity;
    }

    public BigDecimal getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(BigDecimal originalQty) {
        this.originalQty = originalQty;
    }

    public BigDecimal getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(BigDecimal revisedQty) {
        this.revisedQty = revisedQty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(BigDecimal remainingQty) {
        this.remainingQty = remainingQty;
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

    public ProjSOWItemEntity getSowId() {
        return sowId;
    }

    public void setSowId(ProjSOWItemEntity sowId) {
        this.sowId = sowId;
    }

    public ResourceCurveEntity getResourceCurveId() {
        return resourceCurveId;
    }

    public void setResourceCurveId(ResourceCurveEntity resourceCurveId) {
        this.resourceCurveId = resourceCurveId;
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

    public BigDecimal getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(BigDecimal estimateComplete) {
        this.estimateComplete = estimateComplete;
    }

    public BigDecimal getEstimateCompletion() {
        return estimateCompletion;
    }

    public void setEstimateCompletion(BigDecimal estimateCompletion) {
        this.estimateCompletion = estimateCompletion;
    }

}
