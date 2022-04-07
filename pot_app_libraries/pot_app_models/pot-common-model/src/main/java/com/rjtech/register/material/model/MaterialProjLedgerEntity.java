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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "material_proj_ledger")
public class MaterialProjLedgerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAPL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MAPL_RECEIVED_FROM_EPM_ID")
    private ProjMstrEntity receivedFromProjId;

    @Column(name = "MAPL_OPEN_BALANCE")
    private BigDecimal openBalance;

    @Column(name = "MAPL_CREDIT")
    private BigDecimal credit;

    @Column(name = "MAPL_DEBIT")
    private BigDecimal debit;

    @Column(name = "MAPL_AVAIL_BALANCE")
    private BigDecimal availableBalance;

    @Column(name = "MAPL_TRANSIT_TYPE")
    private String transitType;

    @Column(name = "MAPL_TRANSIT")
    private BigDecimal transit;

    @Column(name = "MAPL_TOTAL")
    private BigDecimal total;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAPL_EFFECTIVE_DATE")
    private Date effectiveDate;

    @Column(name = "MAPL_IS_LATEST")
    private String latest;

    @Column(name = "MAPL_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MAPL_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAPL_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MAPL_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MAPL_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "MAPL_MAP_ID")
    private MaterialProjDtlEntity materialProjDtlEntity;

    @ManyToOne
    @JoinColumn(name = "MAPL_MPSC_ID")
    private MaterialProjDocketSchItemsEntity materialProjDocketSchItemsEntity;

    @ManyToOne
    @JoinColumn(name = "MAPL_MADD_ID")
    private MaterialPODeliveryDocketEntity materialPODeliveryDocketEntity;

    @Column(name = "MAPL_DOCKET_TYPE")
    private String docketType;

    @Column(name = "MAPL_IS_DOCKET_LATEST")
    private String docketLatest;

    @Column(name = "MAPL_DOCKET_TRASNIT_QTY")
    private BigDecimal docketTrasnitQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getTransitType() {
        return transitType;
    }

    public void setTransitType(String transitType) {
        this.transitType = transitType;
    }

    public BigDecimal getTransit() {
        return transit;
    }

    public void setTransit(BigDecimal transit) {
        this.transit = transit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
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

    public MaterialProjDocketSchItemsEntity getMaterialProjDocketSchItemsEntity() {
        return materialProjDocketSchItemsEntity;
    }

    public void setMaterialProjDocketSchItemsEntity(MaterialProjDocketSchItemsEntity materialProjDocketSchItemsEntity) {
        this.materialProjDocketSchItemsEntity = materialProjDocketSchItemsEntity;
    }

    public MaterialPODeliveryDocketEntity getMaterialPODeliveryDocketEntity() {
        return materialPODeliveryDocketEntity;
    }

    public void setMaterialPODeliveryDocketEntity(MaterialPODeliveryDocketEntity materialPODeliveryDocketEntity2) {
        this.materialPODeliveryDocketEntity = materialPODeliveryDocketEntity2;
    }

    public String getDocketType() {
        return docketType;
    }

    public void setDocketType(String docketType) {
        this.docketType = docketType;
    }

    public String getDocketLatest() {
        return docketLatest;
    }

    public void setDocketLatest(String docketLatest) {
        this.docketLatest = docketLatest;
    }

    public BigDecimal getDocketTrasnitQty() {
        return docketTrasnitQty;
    }

    public void setDocketTrasnitQty(BigDecimal docketTrasnitQty) {
        this.docketTrasnitQty = docketTrasnitQty;
    }

    public ProjMstrEntity getReceivedFromProjId() {
        return receivedFromProjId;
    }

    public void setReceivedFromProjId(ProjMstrEntity receivedFromProjId) {
        this.receivedFromProjId = receivedFromProjId;
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
