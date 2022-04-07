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

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "schedule_material_base_line")
public class ProjScheduleMaterialEntity implements Serializable {

    private static final long serialVersionUID = 7588204285993998520L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHMB_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHMB_MCM_ID")
    private MaterialClassMstrEntity materialClassId;

    @ManyToOne
    @JoinColumn(name = "SHMB_SCHB_ID")
    private ProjScheduleBaseLineEntity projScheduleBaseLineEntity;

    @Column(name = "SHMB_ORIGINAL_QTY")
    private BigDecimal originalQty;

    @Column(name = "SHMB_REVISED_QTY")
    private BigDecimal revisedQty;

    @Column(name = "SHMB_ACTUAL_QTY")
    private BigDecimal actualQty;

    @Column(name = "SHMB_REMAINING_QTY")
    private BigDecimal remainingQty;

    @Column(name = "SHMB_EST_COMPLETE_QTY")
    private BigDecimal estimateComplete;

    @Column(name = "SHMB_EST_COMPLETION_QTY")
    private BigDecimal estimateCompletion;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHMB_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHMB_FINISH_DATE")
    private Date finishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHMB_RC_ID")
    private ResourceCurveEntity resourceCurveId;

    @Column(name = "SHMB_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHMB_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHMB_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHMB_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "SHMB_UPDATED_ON")
    private Date updatedOn;

    public ProjScheduleMaterialEntity() {

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

    public MaterialClassMstrEntity getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(MaterialClassMstrEntity materialClassId) {
        this.materialClassId = materialClassId;
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
