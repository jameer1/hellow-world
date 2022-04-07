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
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

@Entity
@Table(name = "schedule_cost_code_base_line")
public class ProjScheduleCostCodeEntity implements Serializable {

    private static final long serialVersionUID = 8293813768826094160L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHCB_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHCB_CCS_ID")
    private ProjCostItemEntity costCodeId;

    @ManyToOne
    @JoinColumn(name = "SHCB_SCHB_ID")
    private ProjScheduleBaseLineEntity projScheduleBaseLineEntity;

    @Column(name = "SHCB_ORIGINAL_QTY")
    private BigDecimal originalQty;

    @Column(name = "SHCB_REVISED_QTY")
    private BigDecimal revisedQty;

    @Column(name = "SHCB_ACTUAL_QTY")
    private BigDecimal actualQty;

    @Column(name = "SHCB_REMAINING_QTY")
    private BigDecimal remainingQty;

    @Column(name = "SHCB_EST_COMPLETE_QTY")
    private BigDecimal estimateComplete;

    @Column(name = "SHCB_EST_COMPLETION_QTY")
    private BigDecimal estimateCompletion;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHCB_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHCB_FINISH_DATE")
    private Date finishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHCB_RC_ID")
    private ResourceCurveEntity resourceCurveId;

    @Column(name = "SHCB_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHCB_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHCB_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHCB_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHCB_UPDATED_ON")
    private Date updatedOn;

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

    public ProjCostItemEntity getCostCodeId() {
        return costCodeId;
    }

    public void setCostCodeId(ProjCostItemEntity costCodeId) {
        this.costCodeId = costCodeId;
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

}
