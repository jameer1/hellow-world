package com.rjtech.register.material.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;

@Entity
@Table(name = "material_sch_count_loc")
public class MaterialSchLocCountEntity implements Serializable {

    private static final long serialVersionUID = 9004622138350081175L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSCL_ID")
    private Long id;

    @Column(name = "MSCL_TOTAL_QTY")
    private BigDecimal totalQty;

    @Column(name = "MSCL_AVL_QTY")
    private BigDecimal avlQty;

    @Column(name = "MSCL_IS_DEFAULT")
    private boolean baseLocation;

    @Column(name = "MSCL_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MSCL_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MSCL_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MSCL_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MSCL_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "MSCL_MAP_ID")
    private MaterialProjDtlEntity materialProjDtlEntity;

    @ManyToOne
    @JoinColumn(name = "MSCL_SM_ID")
    private StockMstrEntity stockId;

    // TODO remove it, and get code from ID
    @Column(name = "MSCL_SM_CODE")
    private String stockCode;

    @ManyToOne
    @JoinColumn(name = "MSCL_SPM_ID")
    private ProjStoreStockMstrEntity projStockId;

    // TODO remove it, and get code from ID
    @Column(name = "MSCL_SPM_CODE")
    private String projStockCode;

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public BigDecimal getAvlQty() {
        return avlQty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public MaterialProjDtlEntity getMaterialProjDtlEntity() {
        return materialProjDtlEntity;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getProjStockCode() {
        return projStockCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBaseLocation() {
        return baseLocation;
    }

    public void setBaseLocation(boolean baseLocation) {
        this.baseLocation = baseLocation;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public void setAvlQty(BigDecimal avlQty) {
        this.avlQty = avlQty;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setMaterialProjDtlEntity(MaterialProjDtlEntity materialProjDtlEntity) {
        this.materialProjDtlEntity = materialProjDtlEntity;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public void setProjStockCode(String projStockCode) {
        this.projStockCode = projStockCode;
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

    public StockMstrEntity getStockId() {
        return stockId;
    }

    public void setStockId(StockMstrEntity stockId) {
        this.stockId = stockId;
    }

    public ProjStoreStockMstrEntity getProjStockId() {
        return projStockId;
    }

    public void setProjStockId(ProjStoreStockMstrEntity projStockId) {
        this.projStockId = projStockId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

}
