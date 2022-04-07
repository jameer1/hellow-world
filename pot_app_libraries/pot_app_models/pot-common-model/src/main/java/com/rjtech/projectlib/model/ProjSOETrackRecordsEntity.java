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
 * The persistent class for the schedule_of_estimation database table.
 * 
 */
@Entity
@Table(name = "soe_track_records")
public class ProjSOETrackRecordsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STR_ID")
    private Long id;

    @Column(name = "STR_CODE")
    private String code;

    @Column(name = "STR_NAME")
    private String name;

    @Column(name = "STR_IS_ITEM")
    private boolean item;

    @Column(name = "STR_QUANTITY")
    private BigDecimal quantity;

    @Column(name = "STR_COMMENTS")
    private String comments;
    
    @Column(name = "STR_MAN_HRS")
    private Long manHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STR_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "STR_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @ManyToOne
    @JoinColumn(name = "STR_PARENT_ID")
    private ProjSOETrackRecordsEntity projSOETrackRecordsEntity;
    
    @OneToMany(mappedBy = "projSOETrackRecordsEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjSOETrackRecordsEntity> childEntities = new ArrayList<ProjSOETrackRecordsEntity>();

    @Column(name = "STR_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STR_UPDATED_ON")
    private Date updatedOn;

    @Column(name="STR_ITEM_STATUS")
    private String soeItemStatus;
    
    @Column(name="STR_INTERNAL_APPROVER_COMMENTS")
    private String internalApproverComments;
    
    @Column(name="STR_EXTERNAL_APPROVER_COMMENTS")
    private String externalApproverComments;
 
    @Column(name="STR_IS_ITEM_RETURNED")
    private Integer isItemReturned;

    public ProjSOETrackRecordsEntity() {
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

    public List<ProjSOETrackRecordsEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProjSOETrackRecordsEntity> childEntities) {
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

	public Long getManHours() {
		return manHours;
	}

	public void setManHours(Long manHours) {
		this.manHours = manHours;
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
 
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
    }
    
    public ProjSOETrackRecordsEntity getProjSOETrackRecordsEntity() {
        return projSOETrackRecordsEntity;
    }

    public void setProjSOETrackRecordsEntity(ProjSOETrackRecordsEntity projSOETrackRecordsEntity) {
        this.projSOETrackRecordsEntity = projSOETrackRecordsEntity;
    }
    
}
