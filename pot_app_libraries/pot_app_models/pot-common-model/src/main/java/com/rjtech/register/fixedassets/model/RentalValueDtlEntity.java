package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "rentalvalue_dtl")
public class RentalValueDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effecitve_date")
    private Date effectiveDate;

    @Column(name = "asset_current_life_sataus")
    private String assetCurrentLifeSataus;

    @Column(name = "owener_ship_status")
    private String owenerShipStatus;

    @Column(name = "revenue_collection_cycle")
    private String revenueCollectionCycle;

    @Column(name = "fixed_or_rent_income")
    private String fixedOrRentIncome;

    @Column(name = "estimated_rent_per_cycle")
    private Double estimatedRentPerCycle;

    @Column(name = "applicable_revenue")
    private String applicableRevenue;

    @ManyToOne
    @JoinColumn(name = "FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "RD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fda_updated_on")
    private Date updatedOn;

    public String getAssetCurrentLifeSataus() {
        return assetCurrentLifeSataus;
    }

    public void setAssetCurrentLifeSataus(String assetCurrentLifeSataus) {
        this.assetCurrentLifeSataus = assetCurrentLifeSataus;
    }

    public String getOwenerShipStatus() {
        return owenerShipStatus;
    }

    public void setOwenerShipStatus(String owenerShipStatus) {
        this.owenerShipStatus = owenerShipStatus;
    }

    public String getRevenueCollectionCycle() {
        return revenueCollectionCycle;
    }

    public void setRevenueCollectionCycle(String revenueCollectionCycle) {
        this.revenueCollectionCycle = revenueCollectionCycle;
    }

    public String getFixedOrRentIncome() {
        return fixedOrRentIncome;
    }

    public void setFixedOrRentIncome(String fixedOrRentIncome) {
        this.fixedOrRentIncome = fixedOrRentIncome;
    }

    public Double getEstimatedRentPerCycle() {
        return estimatedRentPerCycle;
    }

    public void setEstimatedRentPerCycle(Double estimatedRentPerCycle) {
        this.estimatedRentPerCycle = estimatedRentPerCycle;
    }

    public String getApplicableRevenue() {
        return applicableRevenue;
    }

    public void setApplicableRevenue(String applicableRevenue) {
        this.applicableRevenue = applicableRevenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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

    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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
