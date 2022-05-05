package com.rjtech.register.plant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "plant_project_dtl")
public class PlantRegProjEntity implements Serializable {

    private static final long serialVersionUID = 489044341545995208L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPJD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PPJD_PLRD_ID")
    private PlantRegisterDtlEntity plantRegisterDtlEntity;

    @Column(name = "PPJD_DEPLOYMENT_ID")
    private Long deploymentId;

    @ManyToOne
    @JoinColumn(name = "PPJD_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "PPJD_PPP_ID")
    private PlantProjPODtlEntity plantProjPODtlEntity;

    @Column(name = "PPJD_IS_LATEST")
    private String isLatest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPJD_DEMOB_DATE")
    private Date deMobDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPJD_MOB_DATE")
    private Date mobDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPJD_ANTICIPATED_DEMOB_DATE")
    private Date anticipatedDeMobDate;

    @Column(name = "PPJD_POST_DEMOB_STATUS")
    private String postDeMobStatus;

    @Digits(integer = 15, fraction = 2)
    @Column(name = "PPJD_MOB_ODO_METER_REDG")
    private BigDecimal odoMeter;

    @Digits(integer = 15, fraction = 2)
    @Column(name = "PPJD_DEMOB_ODO_METER_REDG")
    private BigDecimal deMobOdoMeter;

    @Column(name = "PPJD_COMMENTS")
    private String comments;

    @Column(name = "PPJD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PPJD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPJD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PPJD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PPJD_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "PPJD_PLANT_DELIVERY_STATUS")
    private String plantDeliveryStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPJD_COMMISSIONING_DATE")
    private Date commissionDate;

    @Column(name = "PPJD_ASSIGN_STATUS")
    private String assignStatus;
    
    @OneToMany(mappedBy = "plantRegProjEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlantChargeOutRatesEntityCopy> plantChargeOutRatesEntities = new ArrayList<>();

    public PlantRegProjEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAnticipatedDeMobilisationDate() {
        return anticipatedDeMobDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

    public PlantProjPODtlEntity getPlantProjPODtlEntity() {
        return plantProjPODtlEntity;
    }

    public void setPlantProjPODtlEntity(PlantProjPODtlEntity plantProjPODtlEntity) {
        this.plantProjPODtlEntity = plantProjPODtlEntity;
    }

    public String getPlantDeliveryStatus() {
        return plantDeliveryStatus;
    }

    public void setPlantDeliveryStatus(String plantDeliveryStatus) {
        this.plantDeliveryStatus = plantDeliveryStatus;
    }

    public Long getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Date getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(Date commissionDate) {
        this.commissionDate = commissionDate;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public Date getDeMobDate() {
        return deMobDate;
    }

    public void setDeMobDate(Date deMobDate) {
        this.deMobDate = deMobDate;
    }

    public Date getMobDate() {
        return mobDate;
    }

    public void setMobDate(Date mobDate) {
        this.mobDate = mobDate;
    }

    public Date getAnticipatedDeMobDate() {
        return anticipatedDeMobDate;
    }

    public void setAnticipatedDeMobDate(Date anticipatedDeMobDate) {
        this.anticipatedDeMobDate = anticipatedDeMobDate;
    }

    public String getPostDeMobStatus() {
        return postDeMobStatus;
    }

    public void setPostDeMobStatus(String postDeMobStatus) {
        this.postDeMobStatus = postDeMobStatus;
    }

    public BigDecimal getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(BigDecimal odoMeter) {
        this.odoMeter = odoMeter;
    }

    public BigDecimal getDeMobOdoMeter() {
        return deMobOdoMeter;
    }

    public void setDeMobOdoMeter(BigDecimal deMobOdoMeter) {
        this.deMobOdoMeter = deMobOdoMeter;
    }

    public PlantRegisterDtlEntity getPlantRegisterDtlEntity() {
        return plantRegisterDtlEntity;
    }

    public void setPlantRegisterDtlEntity(PlantRegisterDtlEntity plantRegisterDtlEntity) {
        this.plantRegisterDtlEntity = plantRegisterDtlEntity;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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
