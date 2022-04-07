package com.rjtech.projsettings.register.plant.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PlantChargeOutRateTO extends ProjectTO {

    private static final long serialVersionUID = -6102221760845989789L;

    private Long id;
    private Long plantRegId;

    private Long mobCostItemId;
    private BigDecimal mobRate;

    private Long deMobCostItemId;
    private BigDecimal deMobRate;

    private BigDecimal idleRate;
    private BigDecimal normalRate;
    private BigDecimal doubleRate;

    private String plantRateType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantRegId() {
        return plantRegId;
    }

    public void setPlantRegId(Long plantRegId) {
        this.plantRegId = plantRegId;
    }

    public Long getMobCostItemId() {
        return mobCostItemId;
    }

    public void setMobCostItemId(Long mobCostItemId) {
        this.mobCostItemId = mobCostItemId;
    }

    public BigDecimal getMobRate() {
        return mobRate;
    }

    public void setMobRate(BigDecimal mobRate) {
        this.mobRate = mobRate;
    }

    public Long getDeMobCostItemId() {
        return deMobCostItemId;
    }

    public void setDeMobCostItemId(Long deMobCostItemId) {
        this.deMobCostItemId = deMobCostItemId;
    }

    public BigDecimal getDeMobRate() {
        return deMobRate;
    }

    public void setDeMobRate(BigDecimal deMobRate) {
        this.deMobRate = deMobRate;
    }

    public BigDecimal getIdleRate() {
        return idleRate;
    }

    public void setIdleRate(BigDecimal idleRate) {
        this.idleRate = idleRate;
    }

    public BigDecimal getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(BigDecimal normalRate) {
        this.normalRate = normalRate;
    }

    public BigDecimal getDoubleRate() {
        return doubleRate;
    }

    public void setDoubleRate(BigDecimal doubleRate) {
        this.doubleRate = doubleRate;
    }

    public String getPlantRateType() {
        return plantRateType;
    }

    public void setPlantRateType(String plantRateType) {
        this.plantRateType = plantRateType;
    }

}
