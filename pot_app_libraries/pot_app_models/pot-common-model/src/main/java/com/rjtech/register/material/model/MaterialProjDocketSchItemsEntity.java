package com.rjtech.register.material.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "material_proj_docket_sch_items")
public class MaterialProjDocketSchItemsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MPSC_ID")
    private Long id;

    @Column(name = "MPSC_QTY")
    private BigDecimal qty;

    @Column(name = "MPSC_TRASNIT_QTY")
    private BigDecimal transitQty;

    @Column(name = "MPSC_COMMENTS")
    private String comments;

    @Column(name = "MPSC_STATUS")
    private Integer status;

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

    @ManyToOne
    @JoinColumn(name = "MPSC_MAPD_ID", referencedColumnName = "MAPD_ID")
    private MaterialProjDocketEntity materialProjDocketEntity;

    @ManyToOne
    @JoinColumn(name = "MPSC_MAP_ID", referencedColumnName = "MAP_ID")
    private MaterialProjDtlEntity materialProjDtlEntity;

    @ManyToOne
    @JoinColumn(name = "MPSC_TO_MAP_ID", referencedColumnName = "MAP_ID")
    private MaterialProjDtlEntity toProjMaterialProjDtlEntity;

    @Column(name = "MPSC_SUPPLIER_DOCKET")
    private Boolean supplierDocket = false;
    
    @Column(name = "MAPD_OPENING_STOCK")
    private BigDecimal openingStock;

    @Column(name = "MAPD_CLOSING_STOCK")
    private BigDecimal closingStock;
    
    @Column(name = "MPSC_AVL_QTY")
    private BigDecimal availableQty;
    
    @Column(name = "MPSC_WORK_DAIRY_ENTRY", columnDefinition = "int default 0")
    private boolean workDairyEntry;
    
    public boolean isWorkDairyEntry() {
        return workDairyEntry;
    }

    public void setWorkDairyEntry(boolean workDairyEntry) {
        this.workDairyEntry = workDairyEntry;
    }

    public Boolean getSupplierDocket() {
        return supplierDocket;
    }

    public void setSupplierDocket(Boolean supplierDocket) {
        this.supplierDocket = supplierDocket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(BigDecimal transitQty) {
        this.transitQty = transitQty;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public MaterialProjDocketEntity getMaterialProjDocketEntity() {
        return materialProjDocketEntity;
    }

    public void setMaterialProjDocketEntity(MaterialProjDocketEntity materialProjDocketEntity) {
        this.materialProjDocketEntity = materialProjDocketEntity;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public MaterialProjDtlEntity getMaterialProjDtlEntity() {
        return materialProjDtlEntity;
    }

    public void setMaterialProjDtlEntity(MaterialProjDtlEntity materialProjDtlEntity) {
        this.materialProjDtlEntity = materialProjDtlEntity;
    }

    public MaterialProjDtlEntity getToProjMaterialProjDtlEntity() {
        return toProjMaterialProjDtlEntity;
    }

    public void setToProjMaterialProjDtlEntity(MaterialProjDtlEntity toProjMaterialProjDtlEntity) {
        this.toProjMaterialProjDtlEntity = toProjMaterialProjDtlEntity;
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

    public BigDecimal getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(BigDecimal openingStock) {
        this.openingStock = openingStock;
    }

    public BigDecimal getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(BigDecimal closingStock) {
        this.closingStock = closingStock;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }
}
