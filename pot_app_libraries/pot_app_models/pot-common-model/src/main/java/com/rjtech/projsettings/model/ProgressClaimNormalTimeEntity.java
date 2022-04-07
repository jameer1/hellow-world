package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the proj_progress_claim database table.
 * 
 */

@Entity
@Table(name = "proj_progress_claim")
public class ProgressClaimNormalTimeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGC_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PGC_EPM_ID")
    private ProjMstrEntity projId;

    @Column(name = "PGC_TYPE")
    private String claimType;

    @Column(name = "PGC_CUT_OFF_DAYS")
    private Long cutOffDays;

    @Column(name = "PGC_CUT_OFF_TIME")
    private Time cutOffTime;

    @Column(name = "PGC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PGC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PGC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGC_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "PGC_CUT_OFF_HOURS")
    private Integer cutOffHours;

    @Column(name = "PGC_CUT_OFF_MINUTES")
    private Integer cutOffMinutes;

    @Column(name = "PGC_IS_DEFAULT")
    private String isDefault;

    public ProgressClaimNormalTimeEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public Long getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Long cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
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

    public Integer getCutOffHours() {
        return cutOffHours;
    }

    public void setCutOffHours(Integer cutOffHours) {
        this.cutOffHours = cutOffHours;
    }

    public Integer getCutOffMinutes() {
        return cutOffMinutes;
    }

    public void setCutOffMinutes(Integer cutOffMinutes) {
        this.cutOffMinutes = cutOffMinutes;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
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

}
