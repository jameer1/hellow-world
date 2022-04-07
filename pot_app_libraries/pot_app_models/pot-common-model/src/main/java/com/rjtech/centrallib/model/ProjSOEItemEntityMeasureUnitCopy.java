package com.rjtech.centrallib.model;

import java.io.Serializable;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the schedule_of_estimation database table.
 * 
 */
@Entity
@Table(name = "schedule_of_estimation")
public class ProjSOEItemEntityMeasureUnitCopy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOE_ID")
    private Long id;

    @Column(name = "SOE_CODE")
    private String code;

    @Column(name = "SOE_NAME")
    private String name;

    @Column(name = "SOE_IS_ITEM")
    private boolean item;

    @Column(name = "SOE_QUANTITY")
    private BigDecimal quantity;

    @Column(name = "SOE_COMMENTS")
    private String comments;
    
    @Column(name = "MAN_HRS")
    private Long manHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOE_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "SOE_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @ManyToOne
    @JoinColumn(name = "SOE_PARENT_ID")
    private ProjSOEItemEntityMeasureUnitCopy projSOEItemEntityMeasureUnitCopy;

    @Column(name = "SOE_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOE_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOE_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOE_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOE_UPDATED_ON")
    private Date updatedOn;
    
    @ManyToOne
    @JoinColumn(name = "INTERNAL_APPROVER_USER_ID", updatable = false)
    private UserMstrEntity internalApproverUserId;
    
    @ManyToOne
    @JoinColumn(name = "EXTERNAL_APPROVER_USER_ID", updatable = false)
    private UserMstrEntity externalApproverUserId;
    
    @Column(name="INTERNAL_APPROVAL_STATUS")
    private String internalApprovalStatus;
    
    @Column(name="EXTERNAL_APPROVAL_STATUS")
    private String externalApprovalStatus;
    
    @Column(name="IS_EXTERNAL_APPROVAL_REQUIRED")
    private Character isExternalApprovalRequired;
    
    @Column(name="SOE_ITEM_STATUS")
    private String soeItemStatus;
    
    @Column(name="INTERNAL_APPROVER_COMMENTS")
    private String internalApproverComments;
    
    @Column(name="EXTERNAL_APPROVER_COMMENTS")
    private String externalApproverComments;
    
    @ManyToOne
    @JoinColumn(name = "ORIGINATOR_USER_ID", updatable = false)
    private UserMstrEntity originatorUserId;
    
    @Column(name="IS_ITEM_RETURNED")
    private Integer isItemReturned;

    public ProjSOEItemEntityMeasureUnitCopy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public MeasurmentMstrEntity getMeasurmentMstrEntity() {
        return measurmentMstrEntity;
    }

    public void setMeasurmentMstrEntity(MeasurmentMstrEntity measurmentMstrEntity) {
        this.measurmentMstrEntity = measurmentMstrEntity;
    }

    public ProjSOEItemEntityMeasureUnitCopy getProjSOEItemEntityMeasureUnitCopy() {
        return projSOEItemEntityMeasureUnitCopy;
    }

    public void setProjSOEItemEntityMeasureUnitCopy(ProjSOEItemEntityMeasureUnitCopy projSOEItemEntityMeasureUnitCopy) {
        this.projSOEItemEntityMeasureUnitCopy = projSOEItemEntityMeasureUnitCopy;
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

	public Long getManHours() {
		return manHours;
	}

	public void setManHours(Long manHours) {
		this.manHours = manHours;
	}

	public UserMstrEntity getInternalApproverUserId() {
    	return internalApproverUserId;
    }
    
    public void setInternalApproverUserId( UserMstrEntity internalApproverUserId ) {
    	this.internalApproverUserId = internalApproverUserId;
    }
    
    public UserMstrEntity getExternalApproverUserId() {
    	return externalApproverUserId;
    }
    
    public void setExternalApproverUserId( UserMstrEntity externalApproverUserId ) {
    	this.externalApproverUserId = externalApproverUserId;
    }
    
    public String getInternalApprovalStatus() {
    	return internalApprovalStatus;
    }
    
    public void setInternalApprovalStatus( String internalApprovalStatus ) {
    	this.internalApprovalStatus = internalApprovalStatus;
    }
    
    public String getExternalApprovalStatus() {
    	return externalApprovalStatus;
    }
    
    public void setExternalApprovalStatus( String externalApprovalStatus ) {
    	this.externalApprovalStatus = externalApprovalStatus;
    }
    
    public Character getIsExternalApprovalRequired() {
        return isExternalApprovalRequired;
    }

    public void setIsExternalApprovalRequired( Character isExternalApprovalRequired ) {
        this.isExternalApprovalRequired = isExternalApprovalRequired;
    }
    
    public String getSoeItemStatus() {
        return soeItemStatus;
    }

    public void setSoeItemStatus(String soeItemStatus) {
        this.soeItemStatus = soeItemStatus;
    }
    
    public String getInternalApproverComments() {
        return internalApproverComments;
    }

    public void setInternalApproverComments( String internalApproverComments ) {
        this.internalApproverComments = internalApproverComments;
    }
    
    public String getExternalApproverComments() {
        return externalApproverComments;
    }

    public void setExternalApproverComments( String externalApproverComments ) {
        this.externalApproverComments = externalApproverComments;
    }
    
    public UserMstrEntity getOriginatorUserId() {
    	return originatorUserId;
    }
    
    public void setOriginatorUserId( UserMstrEntity originatorUserId ) {
    	this.originatorUserId = originatorUserId;
    }
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
    }
    
    public String toString() {
    	return "id"+id+" soeItemStatus:"+soeItemStatus+" code:"+code+" name:"+name+" manhours:"+manHours+" quantity:"+quantity+" item:"+item+" status:"+status;
    }
}
