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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the project_crew_mstr database table.
 * 
 * 
 * UI Ref : Projects/Project Library/Crew List
 * 
 */
@Entity
@Table(name = "project_crew_mstr")
public class ProjCrewMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRW_ID")
    private Long id;

    @Column(name = "CRW_CODE")
    private String code;

    @Column(name = "CRW_DESC")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "CRW_SHF_ID")
    private ProjWorkShiftMstrEntity projWorkShiftMstrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRW_EPM_ID")
    private ProjMstrEntity projId;

    @Column(name = "CRW_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRW_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRW_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "CRW_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "CRW_UPDATED_ON")
    private Date updatedOn;

    public ProjCrewMstrEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ProjWorkShiftMstrEntity getProjWorkShiftMstrEntity() {
        return projWorkShiftMstrEntity;
    }

    public void setProjWorkShiftMstrEntity(ProjWorkShiftMstrEntity projWorkShiftMstrEntity) {
        this.projWorkShiftMstrEntity = projWorkShiftMstrEntity;
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