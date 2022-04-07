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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "proj_status_milestones")
public class ProjStatusMileStonesEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7503001874754959515L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PSM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PSM_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "PSM_MILESTONE")
    private String mileStones;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSM_ORIGINAL_DATE")
    private Date mileStoneOriginal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSM_FORECAST_DATE")
    private Date mileStoneForeCast;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSM_ACTUAL_DATE")
    private Date mileStoneActual;

    @Column(name = "PSM_VARIANCE_DAYS")
    private String mileStoneVariance;

    @Column(name = "PSM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PSM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PSM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSM_UPDATED_ON")
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

    public String getMileStones() {
        return mileStones;
    }

    public void setMileStones(String mileStones) {
        this.mileStones = mileStones;
    }

    public Date getMileStoneOriginal() {
        return mileStoneOriginal;
    }

    public void setMileStoneOriginal(Date mileStoneOriginal) {
        this.mileStoneOriginal = mileStoneOriginal;
    }

    public Date getMileStoneForeCast() {
        return mileStoneForeCast;
    }

    public void setMileStoneForeCast(Date mileStoneForeCast) {
        this.mileStoneForeCast = mileStoneForeCast;
    }

    public Date getMileStoneActual() {
        return mileStoneActual;
    }

    public void setMileStoneActual(Date mileStoneActual) {
        this.mileStoneActual = mileStoneActual;
    }

    public String getMileStoneVariance() {
        return mileStoneVariance;
    }

    public void setMileStoneVariance(String mileStoneVariance) {
        this.mileStoneVariance = mileStoneVariance;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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

}
