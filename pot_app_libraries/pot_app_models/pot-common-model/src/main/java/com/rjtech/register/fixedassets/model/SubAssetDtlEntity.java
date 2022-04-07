package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.AssetCategoryMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.finance.model.ProfitCentreEntity;
//import com.rjtech.finance.model.ProfitCentreEntityCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

@Entity
@Table(name = "subasset_dtl")
public class SubAssetDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subid")
    private Long subid;

    @Column(name = "sub_asset_id")
    private String subAssetId;

    @Column(name = "sub_asset_description")
    private String subAssetDescription;

    @Column(name = "subAssetCategory")
    private String subAssetCategory;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "yearBuild")
    private Date yearBuild;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateCommissioned")
    private Date dateCommissioned;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fda_updated_on")
    private Date updatedOn;

    @Column(name = "SA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "SA_CRM_ID")
    private ClientRegEntity clientRegEntity;

    @ManyToOne
    @JoinColumn(name = "FPC_PROFIT_ID")
    private ProfitCentreEntity profitCentreEntity;

    @ManyToOne
    @JoinColumn(name = "ACM_CATEGORY_ID")
    private AssetCategoryMstrEntity assetCategoryMstrEntity;

    @ManyToOne
    @JoinColumn(name = "FAD_ASSET_ID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public AssetCategoryMstrEntity getAssetCategoryMstrEntity() {
        return assetCategoryMstrEntity;
    }

    public void setAssetCategoryMstrEntity(AssetCategoryMstrEntity assetCategoryMstrEntity) {
        this.assetCategoryMstrEntity = assetCategoryMstrEntity;
    }

    public ProfitCentreEntity getProfitCentreEntity() {
        return profitCentreEntity;
    }

    public void setProfitCentreEntity(ProfitCentreEntity profitCentreEntity) {
        this.profitCentreEntity = profitCentreEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubAssetCategory() {
        return subAssetCategory;
    }

    public void setSubAssetCategory(String subAssetCategory) {
        this.subAssetCategory = subAssetCategory;
    }

    public Date getYearBuild() {
        return yearBuild;
    }

    public void setYearBuild(Date yearBuild) {
        this.yearBuild = yearBuild;
    }

    public Date getDateCommissioned() {
        return dateCommissioned;
    }

    public void setDateCommissioned(Date dateCommissioned) {
        this.dateCommissioned = dateCommissioned;
    }

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public String getSubAssetId() {
        return subAssetId;
    }

    public void setSubAssetId(String subAssetId) {
        this.subAssetId = subAssetId;
    }

    public String getSubAssetDescription() {
        return subAssetDescription;
    }

    public void setSubAssetDescription(String subAssetDescription) {
        this.subAssetDescription = subAssetDescription;
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

    public ClientRegEntity getClientRegEntity() {
        return clientRegEntity;
    }

    public void setClientRegEntity(ClientRegEntity clientRegEntity) {
        this.clientRegEntity = clientRegEntity;
    }

    @PostLoad
    @PostPersist
    private void setFormattedId() {
        String str = AppUserUtils.getClientCode();
        this.subAssetId = str + "-" + fixedAssetsRegisterDtlEntity.getAssetId() + "-" + subid;

    }

}
