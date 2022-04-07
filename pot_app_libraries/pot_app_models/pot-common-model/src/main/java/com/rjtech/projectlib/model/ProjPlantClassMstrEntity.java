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

import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the proj_plant_classsfication_mstr database table.
 *
 * 
 * UI - Reference : Projects/Projects Library/Plant Classification
 * 
 */
@Entity
@Table(name = "proj_plant_classification_mstr")
public class ProjPlantClassMstrEntity implements Serializable {

    private static final long serialVersionUID = -7469582437210724116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PPC_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "PPC_PCM_ID")
    private PlantMstrEntity plantMstrEntity;

    @Column(name = "PPC_PLANT_CONTR_NAME")
    private String plantContrName;

    @Column(name = "PPC_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PPC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PPC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPC_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPC_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlantMstrEntity getPlantMstrEntity() {
        return plantMstrEntity;
    }

    public void setPlantMstrEntity(PlantMstrEntity plantMstrEntity) {
        this.plantMstrEntity = plantMstrEntity;
    }

    public String getPlantContrName() {
        return plantContrName;
    }

    public void setPlantContrName(String plantContrName) {
        this.plantContrName = plantContrName;
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