package com.rjtech.projectlib.model;

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

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "proj_material_classification_mstr")
public class ProjMaterialMstrEntity implements Serializable {

    private static final long serialVersionUID = 571904159130869478L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMCM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_MCM_ID")
    private MaterialClassMstrEntity groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_EPM_ID")
    private ProjMstrEntity projectId;

    @Column(name = "PMCM_INTRL_APPROVED")
    private Boolean intrlApproved;

    @Column(name = "PMCM_EXTRL_APPROVED")
    private Boolean extrlApproved;

    @Column(name = "PMCM_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMCM_UPDATED_ON")
    private Date updatedOn;

    public ProjMaterialMstrEntity() {
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MaterialClassMstrEntity getGroupId() {
        return groupId;
    }

    public void setGroupId(MaterialClassMstrEntity groupId) {
        this.groupId = groupId;
    }

    public ProjMstrEntity getProjectId() {
        return projectId;
    }

    public void setProjectId(ProjMstrEntity projectId) {
        this.projectId = projectId;
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

    public Boolean getIntrlApproved() {
        return intrlApproved;
    }

    public void setIntrlApproved(Boolean intrlApproved) {
        this.intrlApproved = intrlApproved;
    }

    public Boolean getExtrlApproved() {
        return extrlApproved;
    }

    public void setExtrlApproved(Boolean extrlApproved) {
        this.extrlApproved = extrlApproved;
    }

}