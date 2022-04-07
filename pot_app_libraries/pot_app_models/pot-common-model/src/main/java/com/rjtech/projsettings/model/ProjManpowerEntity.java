package com.rjtech.projsettings.model;

import java.io.Serializable;
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

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "project_manpower_dtl")
public class ProjManpowerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MPD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MPD_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "MPD_ORIGINAL_QTY")
    private Double originalQty;

    @Column(name = "MPD_REVISED_QTY")
    private Double revisedQty;

    @Column(name = "MPD_ESTIMATE")
    private Double estimateComplete;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MPD_SCHEDULE_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MPD_SCHEDULE_FINISH_DATE")
    private Date finishDate;

    @Column(name = "MPD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MPD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MPD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MPD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MPD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "MPD_ECM_ID", referencedColumnName = "ECM_ID")
    private EmpClassMstrEntity empClassMstrEntity;

    @Column(name = "MPD_EMPLOYEE_CATEGORY")
    private String projEmpCategory;

    @ManyToOne
    @JoinColumn(name = "MPD_MT_ID", referencedColumnName = "MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;
    
    @Column(name="MANPOWER_ITEM_STATUS")
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

    public String getRequestedFrom() {
		return requestedFrom;
	}

	public void setRequestedFrom(String requestedFrom) {
		this.requestedFrom = requestedFrom;
	}

	public ProjManpowerEntity() {
    }

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

    public Double getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(Double originalQty) {
        this.originalQty = originalQty;
    }

    public Double getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(Double revisedQty) {
        this.revisedQty = revisedQty;
    }

    public Double getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(Double estimateComplete) {
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

    public EmpClassMstrEntity getEmpClassMstrEntity() {
        return empClassMstrEntity;
    }

    public void setEmpClassMstrEntity(EmpClassMstrEntity empClassMstrEntity) {
        this.empClassMstrEntity = empClassMstrEntity;
    }

    public String getProjEmpCategory() {
        return projEmpCategory;
    }

    public void setProjEmpCategory(String projEmpCategory) {
        this.projEmpCategory = projEmpCategory;
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
}
