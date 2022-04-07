package com.rjtech.timemanagement.workdairy.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;

/**
 * The persistent class for the proj_emp_attendence_dtl database table.
 * 
 */
@Entity
@Table(name = "work_dairy_progress_dtl")
public class WorkDairyProgressDtlEntity implements Serializable {

    private static final long serialVersionUID = -3058612720470750333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDPR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_WDM_ID")
    private WorkDairyEntity workDairyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_SOW_ID")
    private ProjSOWItemEntity sowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_CCS_ID")
    private ProjCostItemEntity costId;

    @Column(name = "WDPR_VALUE")
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_USR_ID")
    private UserMstrEntity userId;

    @Column(name = "WDPR_APPRV_STATUS")
    private String apprStatus;

    @Column(name = "WDPR_APPRV_COMMENTS")
    private String apprComments;

    @Column(name = "WDPR_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPR_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "FILE_NAME")
    private String fileName;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
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

    public WorkDairyEntity getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(WorkDairyEntity workDairyId) {
        this.workDairyId = workDairyId;
    }

    public ProjSOWItemEntity getSowId() {
        return sowId;
    }

    public void setSowId(ProjSOWItemEntity sowId) {
        this.sowId = sowId;
    }

    public ProjCostItemEntity getCostId() {
        return costId;
    }

    public void setCostId(ProjCostItemEntity costId) {
        this.costId = costId;
    }

    public UserMstrEntity getUserId() {
        return userId;
    }

    public void setUserId(UserMstrEntity userId) {
        this.userId = userId;
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
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    } 
    
    public ProjDocFileEntity getProjDocFile() {
        return projDocFile;
    }

    public void setProjDocFile( ProjDocFileEntity projDocFile ) {
        this.projDocFile = projDocFile;
    }

}