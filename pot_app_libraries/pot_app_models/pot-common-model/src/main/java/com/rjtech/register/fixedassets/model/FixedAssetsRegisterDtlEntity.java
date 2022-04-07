package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.centrallib.model.CompanyMstrEntity;

@Entity
@Table(name = "fixedassets_register_dtl")
public class FixedAssetsRegisterDtlEntity implements Serializable {
    private static final long serialVersionUID = -8795406421033703994L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRD_ID")
    private Long fixedAssetid;

    @Column(name = "FRD_ASSET_ID")
    private String assetId;

    @Column(name = "FRD_ASSETDESCRIPTION")
    private String assetDescription;

    @Column(name = "FRD_ADDRESS")
    private String address;

    @Column(name = "FRD_PROFITCENTRE")
    private String profitCentre;

    @Column(name = "FRD_YEARBUILD")
    private String yearBuild;

    @Temporal(TemporalType.DATE)
    @Column(name = "FRD_DATECOMMISSIONED")
    private Date dateCommissioned;

    @Column(name = "FRD_ASSETLIFESTATUS")
    private String assetLifeStatus;

    @Column(name = "FRD_OWNERSHIPSTATUS")
    private String ownershipStatus;

    @ManyToOne
    @JoinColumn(name = "FRD_CREATED_BY")
    private UserMstrEntity createdBy;

    @Column(name = "FRD_CREATED_ON")
    private Timestamp createdOn;

    @ManyToOne
    @JoinColumn(name = "FRD_UPDATED_BY")
    private UserMstrEntity updatedBy;
    
    @ManyToOne
    @JoinColumn(name = "FRD_PROJECT")
    private ProjMstrEntity projMstrEntity;
    
    @ManyToOne
    @JoinColumn(name = "FRD_COMPANY")
    private CompanyMstrEntity companyMstrEntity;

    @Column(name = "FRD_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "FRD_STATUS")
    private Integer status;

    @Column(name = "FRD_CRM_CODE")
    private String clientCode;

    @Column(name = "FRD_CON_CODE")
    private String isoAlpha3;

    @Column(name = "FRD_GEONAME_ID")
    private Long geonameId;

    @Column(name = "FRD_CON_NAME")
    private String countryName;

    @Column(name = "FRD_PROVISION_NAME")
    private String provisionName;

    @Column(name = "FRD_CURRENCY")
    private String currency;

    @Column(name = "FRD_ASSETCATEGORY")
    private String assetCategoryName;
    

    public CompanyMstrEntity getCompanyMstrEntity() {
        return companyMstrEntity;
    }

    public void setCompanyMstrEntity(CompanyMstrEntity companyMstrEntity) {
        this.companyMstrEntity = companyMstrEntity;
    }

    @ManyToOne
    @JoinColumn(name = "FRD_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @OneToMany(mappedBy = "fixedAssetsRegisterDtlEntity", cascade = CascadeType.ALL)
    private List<FixedAssetsRegisterDtlEntity> childEntities = new ArrayList<FixedAssetsRegisterDtlEntity>();

    @ManyToOne
    @JoinColumn(name = "FRD_PARENT_ID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @Column(name = "FRD_IS_PROJ")
    private Integer proj;

    @Column(name = "FRD_IS_ASSIGNED")
    private String assignedStatus;

    @Column(name = "FRD_SUB_ASSETID")
    private String subAssetId;

    @Column(name = "FRD_SUB_ASSET_DESCRIPTION")
    private String subAssetDescription;

    @Column(name = "FRD_SUB_ASSET_CATEGORY")
    private String subAssetCategory;

    @Column(name = "FRD_SETTING_STATUS")
    private String settingStatus;

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public String getSettingStatus() {
        return settingStatus;
    }

    public void setSettingStatus(String settingStatus) {
        this.settingStatus = settingStatus;
    }

    public String getAssignedStatus() {
        return assignedStatus;
    }

    public void setAssignedStatus(String assignedStatus) {
        this.assignedStatus = assignedStatus;
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

    public List<FixedAssetsRegisterDtlEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<FixedAssetsRegisterDtlEntity> childEntities) {
        this.childEntities = childEntities;
    }

    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public Integer getProj() {
        return proj;
    }

    public void setProj(Integer proj) {
        this.proj = proj;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public String getAssetCategoryName() {
        return assetCategoryName;
    }

    public void setAssetCategoryName(String assetCategoryName) {
        this.assetCategoryName = assetCategoryName;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Long geonameId) {
        this.geonameId = geonameId;
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

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FixedAssetsRegisterDtlEntity() {

    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Date getDateCommissioned() {
        return dateCommissioned;
    }

    public void setDateCommissioned(Date dateCommissioned) {
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

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @PrePersist
    public void onPrePersist() {
        this.setClientCode(AppUserUtils.getClientCode());

    }

    @PostLoad
    @PostPersist
    private void setFormattedId() {
        if( this.fixedAssetsRegisterDtlEntity == null ) { // No Parents
            String clientCode = AppUserUtils.getClientCode();
            String clientCodeArray[] = clientCode.split(" ");
            
        	StringBuilder charBuffer = new StringBuilder();
            for (String alphabets : clientCodeArray) {
                charBuffer.append(alphabets.charAt(0));
            }

            String result ="A-" +(charBuffer.toString()) + "-" + String.valueOf( this.fixedAssetid );
        	this.assetId = result;
        } else { // Has Parent
        	String parentAssetId = this.fixedAssetsRegisterDtlEntity.getAssetId();
        	
        	List<FixedAssetsRegisterDtlEntity> siblings = this.fixedAssetsRegisterDtlEntity.getChildEntities();
        	int siblingsCount = 0;
        	String separator = "-";
        	
        	for(FixedAssetsRegisterDtlEntity sibling: siblings) {
        		++siblingsCount;
        		
        		if( (sibling.getAssetId() == null) && (this.assetId == null) ) {
        			if( siblingsCount < 10 ) {
        				separator = "-0";
        			}
        			
        			this.assetId = parentAssetId + separator + String.valueOf(siblingsCount);
        			break;
        		}
        	}
        }
    }

}
