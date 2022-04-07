package com.rjtech.timemanagement.timesheet.model;

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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

/**
 * The persistent class for the time_sheet_emp_expenses database table.
 * 
 */
@Entity
@Table(name = "time_sheet_emp_expenses")
public class TimeSheetEmpExpenseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TSDE_ID")
    private Long id;

    @Column(name = "TSDE_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "TSDE_CCS_ID")
    private ProjCostItemEntity projCostItemEntity;

    @Column(name = "TSDE_AMOUNT")
    private BigDecimal amount;

    @Column(name = "TSDE_COMMENTS")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "TSDE_PARENT_ID")
    private TimeSheetEmpExpenseEntity parentTimeSheetEmpExpenseEntity;

    @ManyToOne
    @JoinColumn(name = "TSDE_APPR_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @Column(name = "TSDE_APPR_STATUS")
    private String apprStatus;

    @Column(name = "TSDE_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TSDE_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSDE_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TSDE_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSDE_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "TSDE_TSED_ID")
    private TimeSheetEmpDtlEntity timeSheetEmpDtlEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProjCostItemEntity getProjCostItemEntity() {
        return projCostItemEntity;
    }

    public void setProjCostItemEntity(ProjCostItemEntity projCostItemEntity) {
        this.projCostItemEntity = projCostItemEntity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public TimeSheetEmpExpenseEntity getParentTimeSheetEmpExpenseEntity() {
        return parentTimeSheetEmpExpenseEntity;
    }

    public void setParentTimeSheetEmpExpenseEntity(TimeSheetEmpExpenseEntity parentTimeSheetEmpExpenseEntity) {
        this.parentTimeSheetEmpExpenseEntity = parentTimeSheetEmpExpenseEntity;
    }

    public UserMstrEntity getApprUserMstrEntity() {
        return apprUserMstrEntity;
    }

    public void setApprUserMstrEntity(UserMstrEntity apprUserMstrEntity) {
        this.apprUserMstrEntity = apprUserMstrEntity;
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

    public TimeSheetEmpDtlEntity getTimeSheetEmpDtlEntity() {
        return timeSheetEmpDtlEntity;
    }

    public void setTimeSheetEmpDtlEntity(TimeSheetEmpDtlEntity timeSheetEmpDtlEntity) {
        this.timeSheetEmpDtlEntity = timeSheetEmpDtlEntity;
    }

}
