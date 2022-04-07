package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

@Entity
@Table(name = "project_cost_stmt")
public class ProjCostStmtDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PJCS_ID")
    private Long id;

    // TODO we calculate this on the fly, this column is not required.
    @Column(name = "PJCS_EARNED_VALUE")
    private BigDecimal earnedValue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJCS_ACTUAL_START_DATE")
    private Date actualStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJCS_ACTUAL_FINISH_DATE")
    private Date actualFinishDate;

    // TODO remove this, if not required
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJCS_START_DATE")
    private Date startDate;

    // TODO remove this, if not required
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJCS_FINISH_DATE")
    private Date finishDate;

    @Column(name = "PJCS_COMMENTS")
    private String notes;

    @Column(name = "PJCS_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PJCS_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJCS_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PJCS_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJCS_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "projCostStmtDtlEntity", cascade = CascadeType.ALL)
    private List<ProjCostBudgetEntity> projCostBudgetEntites = new ArrayList<ProjCostBudgetEntity>();

    @ManyToOne
    @JoinColumn(name = "PJCS_CCS_ID")
    private ProjCostItemEntity projCostItemEntity;

    @ManyToOne
    @JoinColumn(name = "PJCS_EPM_ID")
    private ProjMstrEntity projMstrEntity;
    
    @Column( name="COST_ITEM_STATUS")
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

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public ProjCostItemEntity getProjCostItemEntity() {
        return projCostItemEntity;
    }

    public void setProjCostItemEntity(ProjCostItemEntity projCostItemEntity) {
        this.projCostItemEntity = projCostItemEntity;
    }

    public BigDecimal getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(BigDecimal earnedValue) {
        this.earnedValue = earnedValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(Date actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
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

    public List<ProjCostBudgetEntity> getProjCostBudgetEntites() {
        return projCostBudgetEntites;
    }

    public void setProjCostBudgetEntites(List<ProjCostBudgetEntity> projCostBudgetEntites) {
        this.projCostBudgetEntites = projCostBudgetEntites;
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
    	return "ProjCostStmtDtlEntity id:"+id;
    }
}
