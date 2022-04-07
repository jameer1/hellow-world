package com.rjtech.register.fixedassets.dto;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.dto.ClientTO;

public class FixedAssetDtlTO extends ClientTO {

    private static final long serialVersionUID = 5048003796093964935L;
    private Long fixedAssetid;
    private String assetId;
    private String assetDescription;
    private String assetCategoryName;
    private boolean expand = false;
    private boolean proj;
    private String settingStatus;
    private String address;
    private String currency;
    private String profitCentre;
    private String yearBuild;
    private String dateCommissioned;
    private String assetLifeStatus;
    private String ownershipStatus;
    private Long geonameId;
    private String isoAlpha3;
    private String countryName;
    private String provisionName;
    private String assignedStatus;
    private String subAssetId;
    private String subAssetDescription;
    private String subAssetCategory;
    private Long projectId;
    private String projectName;
    private String projectCode;
    private Long companyId;
    private String companyName;
    
    
    
    

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }


    private List<FixedAssetDtlTO> childProjs = new ArrayList<FixedAssetDtlTO>();
    private Long parentId;

    public String getAssignedStatus() {
        return assignedStatus;
    }

    public void setAssignedStatus(String assignedStatus) {
        this.assignedStatus = assignedStatus;
    }

    public String getSettingStatus() {
        return settingStatus;
    }

    public void setSettingStatus(String settingStatus) {
        this.settingStatus = settingStatus;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getSubAssetCategory() {
        return subAssetCategory;
    }

    public void setSubAssetCategory(String subAssetCategory) {
        this.subAssetCategory = subAssetCategory;
    }

    public boolean isProj() {
        return proj;
    }

    public void setProj(boolean proj) {
        this.proj = proj;
    }

    public List<FixedAssetDtlTO> getChildProjs() {
        return childProjs;
    }

    public void setChildProjs(List<FixedAssetDtlTO> childProjs) {
        this.childProjs = childProjs;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Long geonameId) {
        this.geonameId = geonameId;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvisionName() {
        return provisionName;
    }

    public void setProvisionName(String provisionName) {
        this.provisionName = provisionName;
    }

    public Long getFixedAssetid() {
        return fixedAssetid;
    }

    public void setFixedAssetid(Long fixedAssetid) {
        this.fixedAssetid = fixedAssetid;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public String getAssetCategoryName() {
        return assetCategoryName;
    }

    public void setAssetCategoryName(String assetCategoryName) {
        this.assetCategoryName = assetCategoryName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProfitCentre() {
        return profitCentre;
    }

    public void setProfitCentre(String profitCentre) {
        this.profitCentre = profitCentre;
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

    public String getAssetLifeStatus() {
        return assetLifeStatus;
    }

    public void setAssetLifeStatus(String assetLifeStatus) {
        this.assetLifeStatus = assetLifeStatus;
    }

    public String getOwnershipStatus() {
        return ownershipStatus;
    }

    public void setOwnershipStatus(String ownershipStatus) {
        this.ownershipStatus = ownershipStatus;
    }
    
    
}
