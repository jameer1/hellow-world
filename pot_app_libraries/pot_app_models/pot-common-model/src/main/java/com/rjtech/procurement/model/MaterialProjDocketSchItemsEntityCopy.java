package com.rjtech.procurement.model;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;


@Entity
@Table(name = "material_proj_docket_sch_items")
public class MaterialProjDocketSchItemsEntityCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MPSC_ID")
    private Long id;

    @Column(name = "MPSC_SUPPLIER_DOCKET")
    private Boolean supplierDocket = false;

    @Column(name = "MPSC_STATUS")
    private Integer status;

    @Column(name = "MPSC_TRASNIT_QTY")
    private BigDecimal transitQty;

    @Column(name = "MPSC_QTY")
    private BigDecimal qty;

    @Column(name = "MPSC_COMMENTS")
    private String comments;
    
    @Column(name = "MPSC_AVL_QTY")
    private BigDecimal availableQty;
    
    @Column(name = "MPSC_WORK_DAIRY_ENTRY", columnDefinition = "int default 0")
    private boolean workDairyEntry;

    @ManyToOne
    @JoinColumn(name = "MPSC_MAP_ID", referencedColumnName = "MAP_ID")
    private MaterialProjDtlEntityCopy materialProjDtlEntity;

    @ManyToOne
    @JoinColumn(name = "MPSC_TO_MAP_ID", referencedColumnName = "MAP_ID")
    private MaterialProjDtlEntityCopy toProjMaterialProjDtlEntity;

    @ManyToOne
    @JoinColumn(name = "MPSC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MPSC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MPSC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MPSC_UPDATED_ON")
    private Timestamp updatedOn;
    
   
    public boolean isWorkDairyEntry() {
        return workDairyEntry;
    }

    public void setWorkDairyEntry(boolean workDairyEntry) {
        this.workDairyEntry = workDairyEntry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSupplierDocket() {
        return supplierDocket;
    }

    public void setSupplierDocket(Boolean supplierDocket) {
        this.supplierDocket = supplierDocket;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(BigDecimal transitQty) {
        this.transitQty = transitQty;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public MaterialProjDtlEntityCopy getMaterialProjDtlEntity() {
        return materialProjDtlEntity;
    }

    public void setMaterialProjDtlEntity(MaterialProjDtlEntityCopy materialProjDtlEntity) {
        this.materialProjDtlEntity = materialProjDtlEntity;
    }

    public MaterialProjDtlEntityCopy getToProjMaterialProjDtlEntity() {
        return toProjMaterialProjDtlEntity;
    }

    public void setToProjMaterialProjDtlEntity(MaterialProjDtlEntityCopy toProjMaterialProjDtlEntity) {
        this.toProjMaterialProjDtlEntity = toProjMaterialProjDtlEntity;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }
}

