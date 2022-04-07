package com.rjtech.common.model;

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

import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the user_mstr database table.
 * 
 */
@Entity
@Table(name = "user_project_map")
public class UserProjectsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "UPR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UPR_USR_ID")
    private UserMstrEntity userId;

    @ManyToOne
    @JoinColumn(name = "UPR_EPM_ID")
    private ProjMstrEntity projectMstrEntity;

    @Column(name = "UPR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "UPR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "UPR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPR_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getProjectMstrEntity() {
        return projectMstrEntity;
    }

    public void setProjectMstrEntity(ProjMstrEntity projectMstrEntity) {
        this.projectMstrEntity = projectMstrEntity;
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
}