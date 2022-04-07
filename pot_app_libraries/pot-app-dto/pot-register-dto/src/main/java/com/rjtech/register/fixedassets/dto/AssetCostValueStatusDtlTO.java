package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetCostValueStatusDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String effectiveDate;
    private String currency;
    private Long landCost;
    private Long structureCost;
    private Long plantEquipmentCost;
    private Long totalCost;
    private Long assetMarket;
    private Long structureScrap;
    private Long equipmentScrapValue;
    private Long cummStructure;
    private Long cummPlant;
    private Long cummAsset;
    private Long yearlyStructure;
    private Long yearlyPlant;
    private Long yearlyTotalAmount;
    private Long remainAssetCost;
    private byte[] assetCostValueRecords;
    private String assetCostValueDocumentFileName;
    private Long assetCostValueFileSize;
    private String assetCostValueDocumentFileType;
    private String docKey;
    private URL docUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getAssetMarket() {
        return assetMarket;
    }

    public void setAssetMarket(Long assetMarket) {
        this.assetMarket = assetMarket;
    }

    public Long getStructureScrap() {
        return structureScrap;
    }

    public void setStructureScrap(Long structureScrap) {
        this.structureScrap = structureScrap;
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

    public byte[] getAssetCostValueRecords() {
        return assetCostValueRecords;
    }

    public void setAssetCostValueRecords(byte[] assetCostValueRecords) {
        this.assetCostValueRecords = assetCostValueRecords;
    }

    public String getAssetCostValueDocumentFileName() {
        return assetCostValueDocumentFileName;
    }

    public void setAssetCostValueDocumentFileName(String assetCostValueDocumentFileName) {
        this.assetCostValueDocumentFileName = assetCostValueDocumentFileName;
    }

    public Long getAssetCostValueFileSize() {
        return assetCostValueFileSize;
    }

    public void setAssetCostValueFileSize(Long assetCostValueFileSize) {
        this.assetCostValueFileSize = assetCostValueFileSize;
    }

    public String getAssetCostValueDocumentFileType() {
        return assetCostValueDocumentFileType;
    }

    public void setAssetCostValueDocumentFileType(String assetCostValueDocumentFileType) {
        this.assetCostValueDocumentFileType = assetCostValueDocumentFileType;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }
}
