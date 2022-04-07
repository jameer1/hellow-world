package com.rjtech.projectlib.model;

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
 * The persistent class for the schedule_of_rates database table.
 * 
 */
@Entity
@Table(name = "schedule_of_rates")
public class ProjSORItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOR_ID")
    private Long id;

    @Column(name = "SOR_CODE")
    private String code;

    @Column(name = "SOR_NAME")
    private String name;

    @Column(name = "SOR_IS_ITEM")
    private boolean item;

    @Column(name = "SOR_QUANTITY")
    private BigDecimal quantity;

    @Column(name = "SOR_COMMENTS")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOR_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "SOR_MAN_HRS")
    private Long manPowerHrs;

    @Column(name = "SOR_LABOUR_RATE")
    private BigDecimal labourRate;

    @Column(name = "SOR_MATERIAL_RATE")
    private BigDecimal materialRate;

    @Column(name = "SOR_PLANT_RATE")
    private BigDecimal plantRate;

    @Column(name = "SOR_OTHER_RATE")
    private BigDecimal othersRate;

    @ManyToOne
    @JoinColumn(name = "SOR_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @ManyToOne
    @JoinColumn(name = "SOR_PARENT_ID")
    private ProjSORItemEntity projSORItemEntity;

    @OneToMany(mappedBy = "projSORItemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjSORItemEntity> childEntities = new ArrayList<ProjSORItemEntity>();
    
    @OneToMany(mappedBy = "projSORItemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjSORActivityLogEntity> projSORActivityLogEntites = new ArrayList<ProjSORActivityLogEntity>();
    
    @Column(name = "SOR_STATUS")
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
    
    @Column(name= "TOTAL_RATE_IF_NO_SUB_CATEGORY")
    private BigDecimal totalRateIfNoSubCategory;
    
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
    
    @Column(name="SOR_ITEM_STATUS")
    private String sorItemStatus;
    
    @Column(name="INTERNAL_APPROVER_COMMENTS")
    private String internalApproverComments;
    
    @Column(name="EXTERNAL_APPROVER_COMMENTS")
    private String externalApproverComments;
    
    @Column(name="IS_ITEM_RETURNED")
    private Integer isItemReturned;
    
    @ManyToOne
    @JoinColumn(name = "ORIGINATOR_USER_ID")
    private UserMstrEntity originatorUserId;

    public ProjSORItemEntity() {
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

    public Long getManPowerHrs() {
        return manPowerHrs;
    }

    public void setManPowerHrs(Long manPowerHrs) {
        this.manPowerHrs = manPowerHrs;
    }

    public BigDecimal getLabourRate() {
        return labourRate;
    }

    public void setLabourRate(BigDecimal labourRate) {
        this.labourRate = labourRate;
    }

    public BigDecimal getMaterialRate() {
        return materialRate;
    }

    public void setMaterialRate(BigDecimal materialRate) {
        this.materialRate = materialRate;
    }

    public BigDecimal getPlantRate() {
        return plantRate;
    }

    public void setPlantRate(BigDecimal plantRate) {
        this.plantRate = plantRate;
    }

    public BigDecimal getOthersRate() {
        return othersRate;
    }

    public void setOthersRate(BigDecimal othersRate) {
        this.othersRate = othersRate;
    }

    public ProjSORItemEntity getProjSORItemEntity() {
        return projSORItemEntity;
    }

    public void setProjSORItemEntity(ProjSORItemEntity projSORItemEntity) {
        this.projSORItemEntity = projSORItemEntity;
    }

    public List<ProjSORItemEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProjSORItemEntity> childEntities) {
        this.childEntities = childEntities;
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

	public BigDecimal getTotalRateIfNoSubCategory() {
		return totalRateIfNoSubCategory;
	}

	public void setTotalRateIfNoSubCategory(BigDecimal totalRateIfNoSubCategory) {
		this.totalRateIfNoSubCategory = totalRateIfNoSubCategory;
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
    
    public String getSorItemStatus() {
        return sorItemStatus;
    }

    public void setSorItemStatus( String sorItemStatus ) {
        this.sorItemStatus = sorItemStatus;
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
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
    }
    
    public UserMstrEntity getOriginatorUserId() {
    	return originatorUserId;
    }
    
    public void setOriginatorUserId( UserMstrEntity originatorUserId ) {
    	this.originatorUserId = originatorUserId;
    }
    
    public List<ProjSORActivityLogEntity> getProjSORActivityLogEntites() {
        return projSORActivityLogEntites;
    }

    public void setProjSORActivityLogEntites(List<ProjSORActivityLogEntity> projSORActivityLogEntites) {
        this.projSORActivityLogEntites = projSORActivityLogEntites;
    }
    
    public String toString() {
    	return "id:"+id+" name:"+name;
    }
}
