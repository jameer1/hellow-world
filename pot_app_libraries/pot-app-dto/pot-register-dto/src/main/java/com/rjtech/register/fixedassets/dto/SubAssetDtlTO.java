package com.rjtech.register.fixedassets.dto;

public class SubAssetDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 1L;

    private Long subid;
    private String subAssetId;
    private String subAssetDescription;
    private String subAssetCategory;
    private String yearBuild;
    private String dateCommissioned;

    private Long profitCenterId;
    private Long assetCategoryId;

    private Long fixedAssetId;

    public Long getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public Long getProfitCenterId() {
        return profitCenterId;
    }

    public void setProfitCenterId(Long profitCenterId) {
        this.profitCenterId = profitCenterId;
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

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public String getSubAssetCategory() {
        return subAssetCategory;
    }

    public void setSubAssetCategory(String subAssetCategory) {
        this.subAssetCategory = subAssetCategory;
    }

    public String getYearBuild() {
        return yearBuild;
    }

    public void setYearBuild(String yearBuild) {
        this.yearBuild = yearBuild;
    }

    public String getDateCommissioned() {
        return dateCommissioned;
    }

    public void setDateCommissioned(String dateCommissioned) {
        this.dateCommissioned = dateCommissioned;
    }

}
