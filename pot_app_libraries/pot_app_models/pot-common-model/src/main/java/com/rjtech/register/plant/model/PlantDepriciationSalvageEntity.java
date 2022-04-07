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
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;

@Entity
@Table(name = "plant_depriciation_salvage")
public class PlantDepriciationSalvageEntity implements Serializable {

    private static final long serialVersionUID = -6383422273022069881L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDS_ID")
    private Long id;

    @Column(name = "PDS_ESTIMATE_LIFE")
    private Long estimateLife;

    @Column(name = "PDS_REMAINING_LIFE")
    private Long remainingLife;

    @ManyToOne
    @JoinColumn(name = "PDS_PLR_ID")
    private PlantLogRecordEntity plantLogRecordsEntity;

    @ManyToOne
    @JoinColumn(name = "PDS_PGV_ID")
    private ProjGeneralMstrEntity projGenId;

    @Column(name = "PDS_ORIGINAL_VALUE")
    private Long orginalValue;

    @Column(name = "PDS_DEPRICIATION_VALUE")
    private BigDecimal depriciationValue;

    @Column(name = "PDS_REMAINING")
    private BigDecimal remaining;

    @Column(name = "PDS_SALVAGE_VALUE")
    private Long salvageValue;

    @Column(name = "PDS_COMMENTS")
    private String comments;

    @Column(name = "PDS_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PDS_PPJD_ID")
    private PlantRegProjEntity plantRegProjEntity;

    @ManyToOne
    @JoinColumn(name = "PDS_PLRD_ID")
    private PlantRegisterDtlEntity plantId;

    @ManyToOne
    @JoinColumn(name = "PDS_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDS_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PDS_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PDS_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "PDS_IS_LATEST")
    private boolean latest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDS_EFFECTIVE_FROM")
    private Date effectiveFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDS_EFFECTIVE_TO")
    private Date effectiveTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getEstimateLife() {
        return estimateLife;
    }

    public Long getRemainingLife() {
        return remainingLife;
    }

    public PlantLogRecordEntity getPlantLogRecordsEntity() {
        return plantLogRecordsEntity;
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

    public void setEstimateLife(Long estimateLife) {
        this.estimateLife = estimateLife;
    }

    public void setRemainingLife(Long remainingLife) {
        this.remainingLife = remainingLife;
    }

    public void setPlantLogRecordsEntity(PlantLogRecordEntity plantLogRecordsEntity) {
        this.plantLogRecordsEntity = plantLogRecordsEntity;
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

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
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

    public ProjGeneralMstrEntity getProjGenId() {
        return projGenId;
    }

    public void setProjGenId(ProjGeneralMstrEntity projGenId) {
        this.projGenId = projGenId;
    }

    public PlantRegisterDtlEntity getPlantId() {
        return plantId;
    }

    public void setPlantId(PlantRegisterDtlEntity plantId) {
        this.plantId = plantId;
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
