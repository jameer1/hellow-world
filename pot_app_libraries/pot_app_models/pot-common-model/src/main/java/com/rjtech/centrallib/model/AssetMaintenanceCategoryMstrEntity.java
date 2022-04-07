package com.rjtech.centrallib.model;

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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "asset_maintenance_category_mstr")
public class AssetMaintenanceCategoryMstrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AMCM_ID")
    private Long id;

    @Column(name = "AMCM_CODE")
    private String code;

    @Column(name = "AMCM_NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "AMCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "AMCM_IS_ITEM")
    private boolean item;

    @ManyToOne
    @JoinColumn(name = "AMCM_PARENT_ID")
    private AssetMaintenanceCategoryMstrEntity assetMaintenanceCategoryMstrEntity;

    @OneToMany(mappedBy = "assetMaintenanceCategoryMstrEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssetMaintenanceCategoryMstrEntity> childEntities = new ArrayList<AssetMaintenanceCategoryMstrEntity>();

    @Column(name = "AMCM_STATUS")
    private Integer status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "AMCM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AMCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "AMCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AMCM_UPDATED_ON")
    private Date updatedOn;

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

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public AssetMaintenanceCategoryMstrEntity getAssetMaintenanceCategoryMstrEntity() {
        return assetMaintenanceCategoryMstrEntity;
    }

    public void setAssetMaintenanceCategoryMstrEntity(
            AssetMaintenanceCategoryMstrEntity assetMaintenanceCategoryMstrEntity) {
        this.assetMaintenanceCategoryMstrEntity = assetMaintenanceCategoryMstrEntity;
    }

    public List<AssetMaintenanceCategoryMstrEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<AssetMaintenanceCategoryMstrEntity> childEntities) {
        this.childEntities = childEntities;
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

}
