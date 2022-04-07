package com.rjtech.register.plant.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlantDepriciationSalvageTO implements Serializable {

    private static final long serialVersionUID = 1011267116755389065L;

    private Long id;
    private Long estimateLife;
    private Long remainingLife;
    private Long projGenId;
    private Long orginalValue;
    private BigDecimal depriciationValue;
    private BigDecimal remaining;
    private Long salvageValue;
    private String comments;
    private Integer status;
    private Long plantLogId;
    private BigDecimal endMeterReading;
    private boolean latest;
    private String effectiveTO;
    private String effectiveFrom;
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

    public Long getId() {
        return id;
    }

    public Long getEstimateLife() {
        return estimateLife;
    }

    public Long getRemainingLife() {
        return remainingLife;
    }

    public Long getPlantLogId() {
        return plantLogId;
    }

    public Long getProjGenId() {
        return projGenId;
    }

    public Long getOrginalValue() {
        return orginalValue;
    }

    public Long getSalvageValue() {
        return salvageValue;
    }

    public String getComments() {
        return comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEstimateLife(Long estimateLife) {
        this.estimateLife = estimateLife;
    }

    public void setRemainingLife(Long remainingLife) {
        this.remainingLife = remainingLife;
    }

    public void setPlantLogId(Long plantLogId) {
        this.plantLogId = plantLogId;
    }

    public void setProjGenId(Long projGenId) {
        this.projGenId = projGenId;
    }

    public void setOrginalValue(Long orginalValue) {
        this.orginalValue = orginalValue;
    }

    public void setSalvageValue(Long salvageValue) {
        this.salvageValue = salvageValue;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getEndMeterReading() {
        return endMeterReading;
    }

    public void setEndMeterReading(BigDecimal endMeterReading) {
        this.endMeterReading = endMeterReading;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveTO(String effectiveTO) {
        this.effectiveTO = effectiveTO;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public BigDecimal getDepriciationValue() {
        return depriciationValue;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setDepriciationValue(BigDecimal depriciationValue) {
        this.depriciationValue = depriciationValue;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }

}
