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
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

@Entity
@Table(name = "proj_progress_claim_approval")
public class ProgressClaimAddtionalTimeApprEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGA_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PGA_PGC_ID")
    private ProgressClaimNormalTimeEntity claimId;

    @ManyToOne
    @JoinColumn(name = "PGA_USR_ID")
    private UserMstrEntity apprUserId;

    @Column(name = "PGA_CLAIM_PERIOD")
    private Long claimPeriod;

    @Column(name = "PGA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PGA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PGA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGA_UPDATED_ON")
    private Date updatedOn;

    public ProgressClaimAddtionalTimeApprEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClaimPeriod() {
        return claimPeriod;
    }

    public void setClaimPeriod(Long claimPeriod) {
        this.claimPeriod = claimPeriod;
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

    public ProgressClaimNormalTimeEntity getClaimId() {
        return claimId;
    }

    public void setClaimId(ProgressClaimNormalTimeEntity claimId) {
        this.claimId = claimId;
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
