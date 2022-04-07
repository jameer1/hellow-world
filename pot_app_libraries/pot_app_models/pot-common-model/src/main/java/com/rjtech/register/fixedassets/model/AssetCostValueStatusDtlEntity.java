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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import javax.persistence.OneToOne;

@Entity
@Table(name = "asset_cost_value_status")
public class AssetCostValueStatusDtlEntity implements Serializable {
    
    private static final long serialVersionUID = -8795406421033703979L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FACVS_id")
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACVS_EFFECTIVEDATE")
    private Date effectiveDate;
    
    @Column(name = "FACVS_CURRENCY")
    private String currency;

    @Column(name = "FACVS_LANDCOST")
    private Long landCost;
    
    @Column(name = "FACVS_STRUCTURECOST")
    private Long structureCost;
    
    @Column(name = "FACVS_PLANTEQUIPMENTCOST")
    private Long plantEquipmentCost;
    
    @Column(name = "FACVS_TOTALCOSTASSET")
    private Long totalCostAsset;
    
    @Column(name = "FACVS_STRUCTURESCRAPVALUE")
    private Long structureScrapValue;
    
    @Column(name = "FACVS_EQUIPMENTSCRAPVALUE")
    private Long equipmentScrapValue;
    
    @Column(name = "FACVS_CUMMSTRUCTURE")
    private Long cummStructure;
    
    @Column(name = "FACVS_CUMMPLANT")
    private Long cummPlant;
    
    @Column(name = "FACVS_CUMMASSET")
    private Long cummAsset;
    
    @Column(name = "FACVS_YEARLYSTRUCTURE")
    private Long yearlyStructure;
    
    @Column(name = "FACVS_YEARLYPLANT")
    private Long yearlyPlant;
    
    @Column(name = "FACVS_YEARLYTOTALAMOUNT")
    private Long yearlyTotalAmount;
    
    @Column(name = "FACVS_REMAINASSETCOST")
    private Long remainAssetCost;
    
    @Column(name = "FACVS_ASSETMARKETVALUE")
    private Long assetMarketValue;
   
    @Column(name = "FACVS_DOCUMENTFILENAME")
    private String assetCostValueDocumentFileName;
    
    @Column(name = "FACVS_DOCUMENTFILESIZE")
    private Long assetCostValueDocumentFileSize;
    
    @Column(name = "FACVS_DOCUMENTFILETYPE")
    private String assetCostValueDocumentFileType;

    @ManyToOne
    @JoinColumn(name = "FPR_CREATED_BY")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPR_CREATED_ON")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FPR__UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPR_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "FPR_PAR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FPR_FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "FPR_DOC_KEY")
    private String docKey;
    
    @OneToOne
    @JoinColumn(name = "PDFL_DOCS_ID_FK")
    private ProjDocFileEntity projDocFileEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Long getLandCost() {
        return landCost;
    }

    public void setLandCost(Long landCost) {
        this.landCost = landCost;
    }

    public Long getStructureCost() {
        return structureCost;
    }

    public void setStructureCost(Long structureCost) {
        this.structureCost = structureCost;
    }

    public Long getPlantEquipmentCost() {
        return plantEquipmentCost;
    }

    public void setPlantEquipmentCost(Long plantEquipmentCost) {
        this.plantEquipmentCost = plantEquipmentCost;
    }

    public Long getTotalCostAsset() {
        return totalCostAsset;
    }

    public void setTotalCostAsset(Long totalCostAsset) {
        this.totalCostAsset = totalCostAsset;
    }

    public Long getStructureScrapValue() {
        return structureScrapValue;
    }

    public void setStructureScrapValue(Long structureScrapValue) {
        this.structureScrapValue = structureScrapValue;
    }

    public Long getEquipmentScrapValue() {
        return equipmentScrapValue;
    }

    public void setEquipmentScrapValue(Long equipmentScrapValue) {
        this.equipmentScrapValue = equipmentScrapValue;
    }

    public Long getCummStructure() {
        return cummStructure;
    }

    public void setCummStructure(Long cummStructure) {
        this.cummStructure = cummStructure;
    }

    public Long getCummPlant() {
        return cummPlant;
    }

    public void setCummPlant(Long cummPlant) {
        this.cummPlant = cummPlant;
    }

    public Long getCummAsset() {
        return cummAsset;
    }

    public void setCummAsset(Long cummAsset) {
        this.cummAsset = cummAsset;
    }

    public Long getYearlyStructure() {
        return yearlyStructure;
    }

    public void setYearlyStructure(Long yearlyStructure) {
        this.yearlyStructure = yearlyStructure;
    }

    public Long getYearlyPlant() {
        return yearlyPlant;
    }

    public void setYearlyPlant(Long yearlyPlant) {
        this.yearlyPlant = yearlyPlant;
    }
    
    public Long getYearlyTotalAmount() {
        return yearlyTotalAmount;
    }

    public void setYearlyTotalAmount(Long yearlyTotalAmount) {
        this.yearlyTotalAmount = yearlyTotalAmount;
    }

    public Long getRemainAssetCost() {
        return remainAssetCost;
    }

    public void setRemainAssetCost(Long remainAssetCost) {
        this.remainAssetCost = remainAssetCost;
    }

    public Long getAssetMarketValue() {
        return assetMarketValue;
    }

    public void setAssetMarketValue(Long assetMarketValue) {
        this.assetMarketValue = assetMarketValue;
    }

    public String getAssetCostValueDocumentFileName() {
        return assetCostValueDocumentFileName;
    }

    public void setAssetCostValueDocumentFileName(String assetCostValueDocumentFileName) {
        this.assetCostValueDocumentFileName = assetCostValueDocumentFileName;
    }

    public Long getAssetCostValueDocumentFileSize() {
        return assetCostValueDocumentFileSize;
    }

    public void setAssetCostValueDocumentFileSize(Long assetCostValueDocumentFileSize) {
        this.assetCostValueDocumentFileSize = assetCostValueDocumentFileSize;
    }

    public String getAssetCostValueDocumentFileType() {
        return assetCostValueDocumentFileType;
    }

    public void setAssetCostValueDocumentFileType(String assetCostValueDocumentFileType) {
        this.assetCostValueDocumentFileType = assetCostValueDocumentFileType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
    
    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
}
