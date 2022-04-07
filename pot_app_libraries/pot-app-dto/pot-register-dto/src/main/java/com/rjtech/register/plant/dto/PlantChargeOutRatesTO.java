package com.rjtech.register.plant.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PlantChargeOutRatesTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Double rateWithFualNRShift;
    private Double rateWithOutFualNRShift;
    private Double chargeOutRates;
    private Long projMobCostItemId;
    private String projMobCostItemCode;
    private Long projDeMobCostItemId;
    private String projDeMobCostItemCode;
    private String commentes;
    private Double rateWithFualDBShift;
    private Double rateWithoutFualDBShift;
    private Double mobChargeOutRate;
    private Double deMobChargeOutRate;
    private String category;
    private String assertId;
    private Long projGenId;
    private Double idleChargeOutRate;
    private boolean latest;
    private String effectiveTO;
    private String effectiveFrom;
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRateWithFualNRShift() {
        return rateWithFualNRShift;
    }

    public void setRateWithFualNRShift(Double rateWithFualNRShift) {
        this.rateWithFualNRShift = rateWithFualNRShift;
    }

    public Double getRateWithOutFualNRShift() {
        return rateWithOutFualNRShift;
    }

    public void setRateWithOutFualNRShift(Double rateWithOutFualNRShift) {
        this.rateWithOutFualNRShift = rateWithOutFualNRShift;
    }

    public Double getChargeOutRates() {
        return chargeOutRates;
    }

    public void setChargeOutRates(Double chargeOutRates) {
        this.chargeOutRates = chargeOutRates;
    }

    public Long getProjMobCostItemId() {
        return projMobCostItemId;
    }

    public void setProjMobCostItemId(Long projMobCostItemId) {
        this.projMobCostItemId = projMobCostItemId;
    }

    public String getProjMobCostItemCode() {
        return projMobCostItemCode;
    }

    public void setProjMobCostItemCode(String projMobCostItemCode) {
        this.projMobCostItemCode = projMobCostItemCode;
    }

    public Long getProjDeMobCostItemId() {
        return projDeMobCostItemId;
    }

    public void setProjDeMobCostItemId(Long projDeMobCostItemId) {
        this.projDeMobCostItemId = projDeMobCostItemId;
    }

    public String getProjDeMobCostItemCode() {
        return projDeMobCostItemCode;
    }

    public void setProjDeMobCostItemCode(String projDeMobCostItemCode) {
        this.projDeMobCostItemCode = projDeMobCostItemCode;
    }

    public String getCommentes() {
        return commentes;
    }

    public void setCommentes(String commentes) {
        this.commentes = commentes;
    }

    public Double getRateWithFualDBShift() {
        return rateWithFualDBShift;
    }

    public void setRateWithFualDBShift(Double rateWithFualDBShift) {
        this.rateWithFualDBShift = rateWithFualDBShift;
    }

    public Double getRateWithoutFualDBShift() {
        return rateWithoutFualDBShift;
    }

    public void setRateWithoutFualDBShift(Double rateWithoutFualDBShift) {
        this.rateWithoutFualDBShift = rateWithoutFualDBShift;
    }

    public Double getMobChargeOutRate() {
        return mobChargeOutRate;
    }

    public void setMobChargeOutRate(Double mobChargeOutRate) {
        this.mobChargeOutRate = mobChargeOutRate;
    }

    public Double getDeMobChargeOutRate() {
        return deMobChargeOutRate;
    }

    public void setDeMobChargeOutRate(Double deMobChargeOutRate) {
        this.deMobChargeOutRate = deMobChargeOutRate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getProjGenId() {
        return projGenId;
    }

    public void setProjGenId(Long projGenId) {
        this.projGenId = projGenId;
    }

    public Double getIdleChargeOutRate() {
        return idleChargeOutRate;
    }

    public void setIdleChargeOutRate(Double idleChargeOutRate) {
        this.idleChargeOutRate = idleChargeOutRate;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public String getEffectiveTO() {
        return effectiveTO;
    }

    public void setEffectiveTO(String effectiveTO) {
        this.effectiveTO = effectiveTO;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
    
    public String getAssertId() {
        return assertId;
    }

    public void setAssertId(String assertId) {
        this.assertId = assertId;
    }

    public String toString() {
    	return "rateWithOutFualNRShift: "+rateWithOutFualNRShift+" rateWithoutFualDBShift : "+rateWithoutFualDBShift+" mobChargeOutRate: "+mobChargeOutRate;
    }
}
