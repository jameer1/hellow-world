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
@Table(name = "material_invoice_items")
public class MaterialInvoiceDocketItemEntity implements Serializable {

    private static final long serialVersionUID = 9086250289487815267L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MLID_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MLID_PUR_ID")
    private PurchaseOrderEntity purId;

    @ManyToOne
    @JoinColumn(name = "MLID_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "MLID_MAP_ID")
    private MaterialProjDtlEntityCopy materialId;

    @Column(name = "MLID_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MLID_MADD_ID")
    private MaterialPODeliveryDocketEntityCopy materialPOId;

    @ManyToOne
    @JoinColumn(name = "MLID_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MLID_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MLID_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MLID_UPDATED_ON")
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

    public MaterialProjDtlEntityCopy getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MaterialProjDtlEntityCopy materialId) {
        this.materialId = materialId;
    }

    public MaterialPODeliveryDocketEntityCopy getMaterialPOId() {
        return materialPOId;
    }

    public void setMaterialPOId(MaterialPODeliveryDocketEntityCopy materialPOId) {
        this.materialPOId = materialPOId;
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