package com.rjtech.centrallib.model;

import java.io.Serializable;
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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "plant_service_classification_mstr")
public class PlantServiceClassificationMstrEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PSCM_ID")
    private Long id;

    @Column(name = "PSCM_CODE")
    private String code;

    @Column(name = "PSCM_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PSCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "PSCM_IS_ITEM")
    private Integer isItem;

    @Column(name = "PSCM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PSCM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "PSCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PSCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "PSCM_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PSCM_PARENT_ID")
    private PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity;

    @OneToMany(mappedBy = "plantServiceClassificationMstrEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlantServiceClassificationMstrEntity> childEntities = new ArrayList<PlantServiceClassificationMstrEntity>();

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsItem() {
        return isItem;
    }

    public void setIsItem(Integer isItem) {
        this.isItem = isItem;
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

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public PlantServiceClassificationMstrEntity getPlantServiceClassificationMstrEntity() {
        return plantServiceClassificationMstrEntity;
    }

    public void setPlantServiceClassificationMstrEntity(
            PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity) {
        this.plantServiceClassificationMstrEntity = plantServiceClassificationMstrEntity;
    }

    public List<PlantServiceClassificationMstrEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<PlantServiceClassificationMstrEntity> childEntities) {
        this.childEntities = childEntities;
    }
}
