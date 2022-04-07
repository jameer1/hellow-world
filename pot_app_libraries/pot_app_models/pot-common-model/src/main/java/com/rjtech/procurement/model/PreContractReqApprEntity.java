package com.rjtech.procurement.model;

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

/**
 * The persistent class for the pre_contracts_req_appr database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_req_appr")
public class PreContractReqApprEntity implements Serializable {

    private static final long serialVersionUID = -5074969014864448152L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRA_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRA_REQ_DATE")
    private Date reqDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRA_APPR_DATE")
    private Date apprDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRA_REQ_USR_ID")
    private UserMstrEntity reqUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRA_APPR_USR_ID")
    private UserMstrEntity apprUserId;

    @Column(name = "CRA_REQ_COMMENTS")
    private String reqcomments;

    @Column(name = "CRA_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "CRA_NOTES")
    private String notes;

    @Column(name = "CRA_IS_LATEST")
    private boolean latest;

    @Column(name = "CRA_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "CRA_PRC_ID")
    private PreContractEntity preContractEntity;

    public PreContractReqApprEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public Date getApprDate() {
        return apprDate;
    }

    public void setApprDate(Date apprDate) {
        this.apprDate = apprDate;
    }

    public String getReqcomments() {
        return reqcomments;
    }

    public void setReqcomments(String reqcomments) {
        this.reqcomments = reqcomments;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UserMstrEntity getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(UserMstrEntity reqUserId) {
        this.reqUserId = reqUserId;
    }

    public UserMstrEntity getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(UserMstrEntity apprUserId) {
        this.apprUserId = apprUserId;
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
}