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
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;

@Entity
@Table(name = "PLANT_PAYABLE_RATES")
public class PlantPayableRatesEntity implements Serializable {

	private static final long serialVersionUID = 4785930749500090412L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAY_ID")
    private Long id;

    @Column(name = "PAY_RATE_WITH_FUEL_NRSHIFT")
    private Double rateWithFualNRShift;

    @Column(name = "PAY_RATE_WITHOUT_FUEL_NRSHIFT")
    private Double rateWithOutFualNRShift;

    @Column(name = "PAY_CHARGE_OUT_RATE")
    private Double chargeOutRates;

    @ManyToOne
    @JoinColumn(name = "PAY_MOB_COST_CODE")
    private ProjCostItemEntity projMobCostItem;

    @ManyToOne
    @JoinColumn(name = "PAY_DEMOB_COST_CODE")
    private ProjCostItemEntity projDemobCostItem;

    @Column(name = "PAY_COMMENTS")
    private String commentes;

    @Column(name = "PAY_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PAY_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAY_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PAY_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PAY_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "PAY_RATE_WITH_FUEL_DBSHIFT")
    private Double rateWithFualDBShift;

    @Column(name = "PAY_RATE_WITHOUT_FUEL_DBSHIFT")
    private Double rateWithoutFualDBShift;

    @Column(name = "PAY_MOB_CHARGE_OUT_RATE")
    private Double mobChargeOutRate;

    @Column(name = "PAY_DEMOB_CHARGE_OUT_RATE")
    private Double deMobChargeOutRate;

    @Column(name = "PAY_CATEGORY")
    private String category;

    @ManyToOne
    @JoinColumn(name = "PAY_PGV_ID")
    private ProjGeneralMstrEntity projGenId;

    @Column(name = "PY_IDLE_CHARGE_OUT_RATE")
    private Double idleChargeOutRate;

    @Column(name = "PAY_IS_LATEST")
    private boolean latest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAY_EFFECTIVE_FROM")
    private Date effectiveFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAY_EFFECTIVE_TO")
    private Date effectiveTo;

    @ManyToOne
    @JoinColumn(name = "PAY_PPJD_ID")
    private PlantRegProjEntity plantRegProjEntity;

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

    public Double getIdleChargeOutRate() {
        return idleChargeOutRate;
    }

    public void setIdleChargeOutRate(Double idleChargeOutRate) {
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
    
    public String toString() {
    	return "rateWithOutFualNRShift: "+rateWithOutFualNRShift+" rateWithoutFualDBShift : "+rateWithoutFualDBShift+" mobChargeOutRate: "+mobChargeOutRate;
    }
}
