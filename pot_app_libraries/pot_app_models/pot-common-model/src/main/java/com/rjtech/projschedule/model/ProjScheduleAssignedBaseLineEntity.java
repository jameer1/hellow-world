package com.rjtech.projschedule.model;

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
import javax.persistence.UniqueConstraint;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "PROJ_SCHEDULE_ASSIGNED_BASE_LINE", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "PROJ_ID", "BASELINE_ID" }) })
public class ProjScheduleAssignedBaseLineEntity implements Serializable {

    private static final long serialVersionUID = 5445939214556323725L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PROJ_ID")
    private ProjMstrEntity projectEntity;

    @OneToOne
    @JoinColumn(name = "BASELINE_ID")
    private ProjScheduleBaseLineEntity baseLineEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProjMstrEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjMstrEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public ProjScheduleBaseLineEntity getBaseLineEntity() {
        return baseLineEntity;
    }

    public void setBaseLineEntity(ProjScheduleBaseLineEntity baseLineEntity) {
        this.baseLineEntity = baseLineEntity;
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
