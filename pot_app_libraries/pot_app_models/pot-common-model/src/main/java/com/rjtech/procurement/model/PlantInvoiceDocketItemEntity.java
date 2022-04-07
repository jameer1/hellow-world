package com.rjtech.procurement.model;

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

/**
 * The persistent class for the pre_contracts database table.
 * 
 */
@Entity
@Table(name = "plant_invoice_items")
public class PlantInvoiceDocketItemEntity implements Serializable {

    private static final long serialVersionUID = -4770099817777976258L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PID_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PID_PUR_ID")
    private PurchaseOrderEntity purId;

    @ManyToOne
    @JoinColumn(name = "PID_PLRD_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "PID_EPM_ID")
    private PlantRegisterDtlEntityCopy plantId;

    @ManyToOne
    @JoinColumn(name = "PID_PPP_ID")
    private PlantProjPODtlEntityCopy plantPOId;

    @Column(name = "PID_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PID_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PID_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PID_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PID_UPDATED_ON")
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PurchaseOrderEntity getPurId() {
        return purId;
    }

    public void setPurId(PurchaseOrderEntity purId) {
        this.purId = purId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public PlantRegisterDtlEntityCopy getPlantId() {
        return plantId;
    }

    public void setPlantId(PlantRegisterDtlEntityCopy plantId) {
        this.plantId = plantId;
    }

    public PlantProjPODtlEntityCopy getPlantPOId() {
        return plantPOId;
    }

    public void setPlantPOId(PlantProjPODtlEntityCopy plantPOId) {
        this.plantPOId = plantPOId;
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