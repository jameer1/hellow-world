package com.rjtech.register.plant.model;

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
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

@Entity
@Table(name = "plant_charge_out_rates")
public class PlantChargeOutRatesEntityCopy implements Serializable {

    private static final long serialVersionUID = 4785930749500090412L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCR_ID")
    private Long id;

    @Column(name = "PCR_RATE_WITH_FUEL_NRSHIFT")
    private BigDecimal rateWithFualNRShift;

    @Column(name = "PCR_RATE_WITHOUT_FUEL_NRSHIFT")
    private BigDecimal rateWithOutFualNRShift;

    @Column(name = "PCR_CHARGE_OUT_RATE")
    private BigDecimal chargeOutRates;

    @ManyToOne
    @JoinColumn(name = "PCR_MOB_COST_CODE")
    private ProjCostItemEntity projMobCostItem;

    @ManyToOne
    @JoinColumn(name = "PCR_DEMOB_COST_CODE")
    private ProjCostItemEntity projDemobCostItem;

    @Column(name = "PCR_COMMENTS")
    private String commentes;

    @Column(name = "PCR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PCR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PCR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PCR_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "PCR_RATE_WITH_FUEL_DBSHIFT")
    private BigDecimal rateWithFualDBShift;

    @Column(name = "PCR_RATE_WITHOUT_FUEL_DBSHIFT")
    private BigDecimal rateWithoutFualDBShift;

    @Column(name = "PCR_MOB_CHARGE_OUT_RATE")
    private BigDecimal mobChargeOutRate;

    @Column(name = "PCR_DEMOB_CHARGE_OUT_RATE")
    private BigDecimal deMobChargeOutRate;

    @Column(name = "PCR_CATEGORY")
    private String category;

    @ManyToOne
    @JoinColumn(name = "PCR_PGV_ID")
    private ProjGeneralMstrEntity projGenId;

    @Column(name = "PCR_IDLE_CHARGE_OUT_RATE")
    private BigDecimal idleChargeOutRate;

    @Column(name = "PCR_IS_LATEST")
    private boolean latest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCR_EFFECTIVE_FROM")
    private Date effectiveFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCR_EFFECTIVE_TO")
    private Date effectiveTo;

    @ManyToOne
    @JoinColumn(name = "PCR_PPJD_ID")
    private PlantRegProjEntity plantRegProjEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRateWithFualNRShift() {
        return rateWithFualNRShift;
    }

    public void setRateWithFualNRShift(BigDecimal rateWithFualNRShift) {
        this.rateWithFualNRShift = rateWithFualNRShift;
    }

    public BigDecimal getRateWithOutFualNRShift() {
        return rateWithOutFualNRShift;
    }

    public void setRateWithOutFualNRShift(BigDecimal rateWithOutFualNRShift) {
        this.rateWithOutFualNRShift = rateWithOutFualNRShift;
    }

    public BigDecimal getChargeOutRates() {
        return chargeOutRates;
    }

    public void setChargeOutRates(BigDecimal chargeOutRates) {
        this.chargeOutRates = chargeOutRates;
    }

    public ProjCostItemEntity getProjMobCostItem() {
        return projMobCostItem;
    }

    public void setProjMobCostItem(ProjCostItemEntity projMobCostItem) {
        this.projMobCostItem = projMobCostItem;
    }

    public ProjCostItemEntity getProjDemobCostItem() {
        return projDemobCostItem;
    }

    public void setProjDemobCostItem(ProjCostItemEntity projDemobCostItem) {
        this.projDemobCostItem = projDemobCostItem;
    }

    public String getCommentes() {
        return commentes;
    }

    public void setCommentes(String commentes) {
        this.commentes = commentes;
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

    public BigDecimal getRateWithFualDBShift() {
        return rateWithFualDBShift;
    }

    public void setRateWithFualDBShift(BigDecimal rateWithFualDBShift) {
        this.rateWithFualDBShift = rateWithFualDBShift;
    }

    public BigDecimal getRateWithoutFualDBShift() {
        return rateWithoutFualDBShift;
    }

    public void setRateWithoutFualDBShift(BigDecimal rateWithoutFualDBShift) {
        this.rateWithoutFualDBShift = rateWithoutFualDBShift;
    }

    public BigDecimal getMobChargeOutRate() {
        return mobChargeOutRate;
    }

    public void setMobChargeOutRate(BigDecimal mobChargeOutRate) {
        this.mobChargeOutRate = mobChargeOutRate;
    }

    public BigDecimal getDeMobChargeOutRate() {
        return deMobChargeOutRate;
    }

    public void setDeMobChargeOutRate(BigDecimal deMobChargeOutRate) {
        this.deMobChargeOutRate = deMobChargeOutRate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getIdleChargeOutRate() {
        return idleChargeOutRate;
    }

    public void setIdleChargeOutRate(BigDecimal idleChargeOutRate) {
        this.idleChargeOutRate = idleChargeOutRate;
    }

    public PlantRegProjEntity getPlantRegProjEntity() {
        return plantRegProjEntity;
    }

    public void setPlantRegProjEntity(PlantRegProjEntity plantRegProjEntity) {
        this.plantRegProjEntity = plantRegProjEntity;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
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

    public ProjGeneralMstrEntity getProjGenId() {
        return projGenId;
    }

    public void setProjGenId(ProjGeneralMstrEntity projGenId) {
        this.projGenId = projGenId;
    }
}
