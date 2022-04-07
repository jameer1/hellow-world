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

import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "plant_service_history")
public class PlantServiceHistoryEntity implements Serializable {

    private static final long serialVersionUID = -4502771326196491024L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PSH_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSH_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "PSH_CUREENT_PSCM_ID")
    private PlantServiceClassificationMstrEntity currentPlantServiceId;

    @ManyToOne
    @JoinColumn(name = "PSH_LAST_PSCM_ID")
    private PlantServiceClassificationMstrEntity prevPlantServiceId;

    @Column(name = "PSH_CURRENT_ODO_METER_RDG")
    private BigDecimal currentOdoMeter;

    @Column(name = "PSH_LAST_ODO_METER_RDG")
    private BigDecimal prevOdoMeter;

    @Column(name = "PSH_COMMENTS")
    private String comments;

    @Column(name = "PSH_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PSH_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSH_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PSH_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PSH_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "PSH_PPJD_ID")
    private PlantRegProjEntity plantProjId;

    @ManyToOne
    @JoinColumn(name = "PSH_PLRD_ID")
    private PlantRegisterDtlEntity plantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCurrentOdoMeter() {
        return currentOdoMeter;
    }

    public void setCurrentOdoMeter(BigDecimal currentOdoMeter) {
        this.currentOdoMeter = currentOdoMeter;
    }

    public BigDecimal getPrevOdoMeter() {
        return prevOdoMeter;
    }

    public void setPrevOdoMeter(BigDecimal prevOdoMeter) {
        this.prevOdoMeter = prevOdoMeter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public PlantServiceClassificationMstrEntity getCurrentPlantServiceId() {
        return currentPlantServiceId;
    }

    public void setCurrentPlantServiceId(PlantServiceClassificationMstrEntity currentPlantServiceId) {
        this.currentPlantServiceId = currentPlantServiceId;
    }

    public PlantServiceClassificationMstrEntity getPrevPlantServiceId() {
        return prevPlantServiceId;
    }

    public void setPrevPlantServiceId(PlantServiceClassificationMstrEntity prevPlantServiceId) {
        this.prevPlantServiceId = prevPlantServiceId;
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

    public PlantRegProjEntity getPlantProjId() {
        return plantProjId;
    }

    public void setPlantProjId(PlantRegProjEntity plantProjId) {
        this.plantProjId = plantProjId;
    }

    public PlantRegisterDtlEntity getPlantId() {
        return plantId;
    }

    public void setPlantId(PlantRegisterDtlEntity plantId) {
        this.plantId = plantId;
    }

}
