package com.rjtech.register.fixedassets.dto;

import java.math.BigDecimal;

public class RentalValueDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 8950084862079755848L;

    private Long id;
    private String effectiveDate;
    private String assetCurrentLifeSataus;
    private String owenerShipStatus;
    private String revenueCollectionCycle;
    private String fixedOrRentIncome;
    private Double estimatedRentPerCycle;
    private String applicableRevenue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

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

}
