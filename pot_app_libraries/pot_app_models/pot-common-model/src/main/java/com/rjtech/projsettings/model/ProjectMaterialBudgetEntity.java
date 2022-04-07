package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "proj_material_budget")
public class ProjectMaterialBudgetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMB_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PMB_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "PMB_ESTIMATE")
    private BigDecimal estimateComplete;

    @Column(name = "PMB_ORIGINAL_QTY")
    private BigDecimal originalQty;

    @Column(name = "PMB_REVISED_QTY")
    private BigDecimal revisedQty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMB_SCHEDULE_FINISH_DATE")
    private Date finishDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMB_SCHEDULE_START_DATE")
    private Date startDate;

    @Column(name = "PMB_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PMB_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMB_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PMB_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMB_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PMB_MCM_ID")
    private MaterialClassMstrEntity materialClassMstrEntity;
    
    @Column(name="MATERIAL_ITEM_STATUS")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(BigDecimal estimateComplete) {
        this.estimateComplete = estimateComplete;
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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MaterialClassMstrEntity getMaterialClassMstrEntity() {
        return materialClassMstrEntity;
    }

    public void setMaterialClassMstrEntity(MaterialClassMstrEntity materialClassMstrEntity) {
        this.materialClassMstrEntity = materialClassMstrEntity;
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
    
    public String toString() {
    	return "id:"+id+" originalQty:"+originalQty;
    }
}
