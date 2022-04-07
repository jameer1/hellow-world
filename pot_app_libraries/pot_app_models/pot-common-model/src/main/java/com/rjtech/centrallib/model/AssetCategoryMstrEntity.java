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
@Table(name = "asset_category_mstr")
public class AssetCategoryMstrEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACM_ID")
    private Long id;

    @Column(name = "ACM_CODE")
    private String code;

    @Column(name = "ACM_NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "ACM_IS_ITEM")
    private boolean item;

    @ManyToOne
    @JoinColumn(name = "ACM_PARENT_ID")
    private AssetCategoryMstrEntity assetCategoryMstrEntity;

    @OneToMany(mappedBy = "assetCategoryMstrEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssetCategoryMstrEntity> childEntities = new ArrayList<AssetCategoryMstrEntity>();

    @Column(name = "ACM_STATUS")
    private Integer status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACM_UPDATED_ON")
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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public AssetCategoryMstrEntity getAssetCategoryMstrEntity() {
        return assetCategoryMstrEntity;
    }

    public void setAssetCategoryMstrEntity(AssetCategoryMstrEntity assetCategoryMstrEntity) {
        this.assetCategoryMstrEntity = assetCategoryMstrEntity;
    }

    public List<AssetCategoryMstrEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<AssetCategoryMstrEntity> childEntities) {
        this.childEntities = childEntities;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
