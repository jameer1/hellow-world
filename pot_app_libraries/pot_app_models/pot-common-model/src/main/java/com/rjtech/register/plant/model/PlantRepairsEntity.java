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

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;

@Entity
@Table(name = "plant_repairs")
public class PlantRepairsEntity implements Serializable {

    private static final long serialVersionUID = 3838132610904685527L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PLR_PPJD_ID")
    private PlantRegProjEntity plantProjId;

    @ManyToOne
    @JoinColumn(name = "PLR_PLRD_ID")
    private PlantRegisterDtlEntity plantId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLR_DATE")
    private Date date;

    @Column(name = "PLR_ODO_METER_READING")
    private BigDecimal odoMeter;

    @ManyToOne
    @JoinColumn(name = "PLR_PSCM_ID")
    private PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PLR_MSM_GROUP_ID")
    private MaterialClassMstrEntity materialId;

    @Column(name = "PLR_QTY")
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "PLR_MPSC_ID")
    private MaterialProjDocketSchItemsEntity projDockSchId;

    @Column(name = "PLR_PROJ_DOCKET")
    private String projDocket;

    @Column(name = "PLR_COMMENTS")
    private String comments;

    @Column(name = "PLR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PLR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PLR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PLR_UPDATED_ON")
    private Timestamp updatedOn;

    public PlantRepairsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(BigDecimal odoMeter) {
        this.odoMeter = odoMeter;
    }

    public String getProjDocket() {
        return projDocket;
    }

    public void setProjDocket(String projDocket) {
        this.projDocket = projDocket;
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

    public PlantServiceClassificationMstrEntity getPlantServiceClassificationMstrEntity() {
        return plantServiceClassificationMstrEntity;
    }

    public void setPlantServiceClassificationMstrEntity(
            PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity) {
        this.plantServiceClassificationMstrEntity = plantServiceClassificationMstrEntity;
    }

    public MaterialClassMstrEntity getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MaterialClassMstrEntity materialId) {
        this.materialId = materialId;
    }

    public MaterialProjDocketSchItemsEntity getProjDockSchId() {
        return projDockSchId;
    }

    public void setProjDockSchId(MaterialProjDocketSchItemsEntity projDockSchId) {
        this.projDockSchId = projDockSchId;
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
