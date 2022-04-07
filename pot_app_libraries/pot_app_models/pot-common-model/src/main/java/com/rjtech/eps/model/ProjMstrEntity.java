package com.rjtech.eps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the object_mstr database table.
 * 
 */
@Entity
@Table(name = "erp_proj_mstr")
public class ProjMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPM_ID")
    private Long projectId;

    @Column(name = "EPM_CODE")
    private String code;

    @Column(name = "EPM_DESC")
    private String projName;

    @ManyToOne
    @JoinColumn(name = "EPM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    // Load parent eagerly, we are getting parent most of the times to get EPS details
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EPM_PARENT_ID")
    private ProjMstrEntity parentProjectMstrEntity;

    @OneToMany(mappedBy = "parentProjectMstrEntity", cascade = CascadeType.ALL)
    private List<ProjMstrEntity> childEntities = new ArrayList<ProjMstrEntity>();

    @Column(name = "EPM_IS_PROJ")
    private boolean proj;

    @Column(name = "EPM_IS_ASSIGNED", columnDefinition = "int default 0")
    private boolean assignedStatus;

    @ManyToOne
    @JoinColumn(name = "EPM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "EPM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPM_UPDATED_ON")
    private Date updatedOn;

    public ProjMstrEntity() {
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
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

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<ProjMstrEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProjMstrEntity> childEntities) {
        this.childEntities = childEntities;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public ProjMstrEntity getParentProjectMstrEntity() {
        return parentProjectMstrEntity;
    }

    public void setParentProjectMstrEntity(ProjMstrEntity parentProjectMstrEntity) {
        this.parentProjectMstrEntity = parentProjectMstrEntity;
    }

    public boolean isProj() {
        return proj;
    }

    public void setProj(boolean proj) {
        this.proj = proj;
    }

    public boolean isAssignedStatus() {
        return assignedStatus;
    }

    public void setAssignedStatus(boolean assignedStatus) {
        this.assignedStatus = assignedStatus;
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