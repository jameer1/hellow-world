package com.rjtech.projsettings.model;

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

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "project_plants_dtl")
public class ProjectPlantsDtlEntity implements Serializable {

    private static final long serialVersionUID = 24423332913436632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PJP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PJP_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "PJP_ORIGINAL_QTY")
    private BigDecimal originalQty;

    @Column(name = "PJP_REVISED_QTY")
    private BigDecimal revisedQty;

    @Column(name = "PJP_ESTIMATE")
    private BigDecimal estimateComplete;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJP_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJP_FINISH_DATE")
    private Date finishDate;

    @Column(name = "PJP_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PJP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "PJP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PJP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "PJP_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PJP_PCM_ID")
    private PlantMstrEntity plantMstrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PJP_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;
    
    @Column(name="PLANT_ITEM_STATUS")
    private String itemStatus;
    
    @ManyToOne
    @JoinColumn(name = "APPROVER_USER_ID")
    private UserMstrEntity approverUserId;
    
    @Column(name="APPROVER_COMMENTS")
    private String approverComments;
    
    @Column(name="IS_ITEM_RETURNED")
    private Integer isItemReturned;
    
    @ManyToOne
    @JoinColumn(name = "RETURNED_BY_USER_ID")
    private UserMstrEntity returnedByUserId;
    
    @Column(name="RETURN_COMMENTS")
    private String returnedComments;
    
    @ManyToOne
    @JoinColumn(name = "ORIGINATOR_USER_ID")
    private UserMstrEntity originatorUserId;
    
    @Column(name="REQUESTED_FROM")
    private String requestedFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
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

    public BigDecimal getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(BigDecimal estimateComplete) {
        this.estimateComplete = estimateComplete;
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

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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

    public PlantMstrEntity getPlantMstrEntity() {
        return plantMstrEntity;
    }

    public void setPlantMstrEntity(PlantMstrEntity plantMstrEntity) {
        this.plantMstrEntity = plantMstrEntity;
    }

    public MeasurmentMstrEntity getMeasurmentMstrEntity() {
        return measurmentMstrEntity;
    }

    public void setMeasurmentMstrEntity(MeasurmentMstrEntity measurmentMstrEntity) {
        this.measurmentMstrEntity = measurmentMstrEntity;
    }
    
    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
    
    public UserMstrEntity getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId( UserMstrEntity approverUserId ) {
        this.approverUserId = approverUserId;
    }

    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments( String approverComments ) {
        this.approverComments = approverComments;
    }
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
    }

    public UserMstrEntity getReturnedByUserId() {
        return returnedByUserId;
    }

    public void setReturnedByUserId( UserMstrEntity returnedByUserId ) {
        this.returnedByUserId = returnedByUserId;
    }

    public String getReturnedComments() {
        return returnedComments;
    }

    public void setReturnedComments( String returnedComments ) {
        this.returnedComments = returnedComments;
    }
    
    public UserMstrEntity getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( UserMstrEntity originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }

	public String getRequestedFrom() {
		return requestedFrom;
	}

	public void setRequestedFrom(String requestedFrom) {
		this.requestedFrom = requestedFrom;
	}
    
    
}
