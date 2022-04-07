package com.rjtech.timemanagement.timesheet.model;

import java.io.Serializable;
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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;

/**
 * The persistent class for the time_sheet_mstr database table.
 * 
 */
@Entity
@Table(name = "time_sheet_mstr")
public class TimeSheetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TSM_ID")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "TSM_WEEK_START_DAY")
    private Date weekStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TSM_WEEK_END_DAY")
    private Date weekEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSM_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSM_CRW_ID")
    private ProjCrewMstrEntity projCrewMstrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSM_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @Column(name = "TSM_IS_ADDITIONAL")
    private Integer additional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSM_REQ_USR_ID")
    private UserMstrEntity reqUserMstrEntity;

    @Column(name = "TSM_REQ_COMMENTS")
    private String reqComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSM_APPR_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @Column(name = "TSM_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "TSM_APPR_STATUS")
    private String apprStatus;

    @Column(name = "TSM_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSM_UPDATED_ON")
    private Date updatedOn;

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

    public ProjCrewMstrEntity getProjCrewMstrEntity() {
        return projCrewMstrEntity;
    }

    public void setProjCrewMstrEntity(ProjCrewMstrEntity projCrewMstrEntity) {
        this.projCrewMstrEntity = projCrewMstrEntity;
    }

    public UserMstrEntity getApprUserMstrEntity() {
        return apprUserMstrEntity;
    }

    public void setApprUserMstrEntity(UserMstrEntity apprUserMstrEntity) {
        this.apprUserMstrEntity = apprUserMstrEntity;
    }

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public Integer getAdditional() {
        return additional;
    }

    public void setAdditional(Integer additional) {
        this.additional = additional;
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

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public Date getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(Date weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public Date getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(Date weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public UserMstrEntity getReqUserMstrEntity() {
        return reqUserMstrEntity;
    }

    public void setReqUserMstrEntity(UserMstrEntity reqUserMstrEntity) {
        this.reqUserMstrEntity = reqUserMstrEntity;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

}
