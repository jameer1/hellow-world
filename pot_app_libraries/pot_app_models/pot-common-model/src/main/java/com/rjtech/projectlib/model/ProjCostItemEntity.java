
package com.rjtech.projectlib.model;

import java.io.Serializable;
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

import com.rjtech.centrallib.model.CostMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the object_mstr database table.
 * 
 * UI Ref : Projects/Project Library/Cost code schedule
 * 
 */
@Entity
@Table(name = "proj_cost_code_item")
public class ProjCostItemEntity implements Serializable {

    private static final long serialVersionUID = -2715290319480646620L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CCS_ID")
    private Long id;

    @Column(name = "CCS_CODE")
    private String code;

    @Column(name = "CCS_NAME")
    private String name;

    @Column(name = "CCS_IS_ITEM")
    private Integer item;

    @Column(name = "CCS_IS_ITEM_PARENT")
    private boolean itemParent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CCS_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CCS_FINISH_DATE")
    private Date finishDate;

    @Column(name = "CCS_WORK_DIARY_STATUS")
    private boolean workDairyStatus;

    @Column(name = "CCS_COMMENTS")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCS_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCS_CM_ID")
    private CostMstrEntity costMstrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCS_PARENT_ID")
    private ProjCostItemEntity projCostItemEntity;

    @OneToMany(mappedBy = "projCostItemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjCostItemEntity> childEntities = new ArrayList<ProjCostItemEntity>();

    @Column(name = "CCS_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCS_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CCS_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCS_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CCS_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name="CCS_ITEM_STATUS")
    private String itemStatus;
    
    @Column(name="CCS_IS_ITEM_RETURNED")
    private Integer isItemReturned;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVER_USER_ID")
    private UserMstrEntity approverUserId;
    
    @Column(name="APPROVER_COMMENTS")
    private String approverComments;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORIGINATOR_USER_ID")
    private UserMstrEntity originatorUserId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETURNED_BY_USER_ID")
    private UserMstrEntity returnedUserId;
    
    @Column(name="RETURN_COMMENTS")
    private String returnComments;

    public ProjCostItemEntity() {
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

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public boolean isItemParent() {
        return itemParent;
    }

    public void setItemParent(boolean itemParent) {
        this.itemParent = itemParent;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CostMstrEntity getCostMstrEntity() {
        return costMstrEntity;
    }

    public void setCostMstrEntity(CostMstrEntity costMstrEntity) {
        this.costMstrEntity = costMstrEntity;
    }

    public ProjCostItemEntity getProjCostItemEntity() {
        return projCostItemEntity;
    }

    public void setProjCostItemEntity(ProjCostItemEntity projCostItemEntity) {
        this.projCostItemEntity = projCostItemEntity;
    }

    public List<ProjCostItemEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProjCostItemEntity> childEntities) {
        this.childEntities = childEntities;
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

    public boolean isWorkDairyStatus() {
        return workDairyStatus;
    }

    public void setWorkDairyStatus(boolean workDairyStatus) {
        this.workDairyStatus = workDairyStatus;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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
    
    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus( String itemStatus ) {
        this.itemStatus = itemStatus;
    }
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
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
    
    public UserMstrEntity getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( UserMstrEntity originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }
    
    public UserMstrEntity getReturnedUserId() {
        return returnedUserId;
    }

    public void setReturnedUserId( UserMstrEntity returnedUserId ) {
        this.returnedUserId = returnedUserId;
    }
    
    public String getReturnComments() {
        return returnComments;
    }

    public void setReturnComments( String returnComments ) {
        this.returnComments = returnComments;
    }
}