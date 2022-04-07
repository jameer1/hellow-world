package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetLifeStatusDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String effectiveDate;
    private String buildStructure;
    private String plantCommissioningDate;
    private String structureTotal;
    private String plantEquipmentTotal;
    private String ageStructure;
    private String ageEquipment;
    private String remainingStruture;
    private String remainingEquipment;

    private byte[] lifeAssignmentRecords;
    private String lifeAssignmentRecordsDocumentFileName;
    private Long lifeAssignmentRecordsDocumentFileSize;
    private String lifeAssignmentRecordsDocumentFileType;
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

    public String getBuildStructure() {
        return buildStructure;
    }

    public void setBuildStructure(String buildStructure) {
        this.buildStructure = buildStructure;
    }

    public String getPlantCommissioningDate() {
        return plantCommissioningDate;
    }

    public void setPlantCommissioningDate(String plantCommissioningDate) {
        this.plantCommissioningDate = plantCommissioningDate;
    }

    public String getStructureTotal() {
        return structureTotal;
    }

    public void setStructureTotal(String structureTotal) {
        this.structureTotal = structureTotal;
    }

    public String getPlantEquipmentTotal() {
        return plantEquipmentTotal;
    }

    public void setPlantEquipmentTotal(String plantEquipmentTotal) {
        this.plantEquipmentTotal = plantEquipmentTotal;
    }

    public String getAgeStructure() {
        return ageStructure;
    }

    public void setAgeStructure(String ageStructure) {
        this.ageStructure = ageStructure;
    }

    public String getAgeEquipment() {
        return ageEquipment;
    }

    public void setAgeEquipment(String ageEquipment) {
        this.ageEquipment = ageEquipment;
    }

    public String getRemainingStruture() {
        return remainingStruture;
    }

    public void setRemainingStruture(String remainingStruture) {
        this.remainingStruture = remainingStruture;
    }

    public String getRemainingEquipment() {
        return remainingEquipment;
    }

    public void setRemainingEquipment(String remainingEquipment) {
        this.remainingEquipment = remainingEquipment;
    }

    public byte[] getLifeAssignmentRecords() {
        return lifeAssignmentRecords;
    }

    public void setLifeAssignmentRecords(byte[] lifeAssignmentRecords) {
        this.lifeAssignmentRecords = lifeAssignmentRecords;
    }

    public String getLifeAssignmentRecordsDocumentFileName() {
        return lifeAssignmentRecordsDocumentFileName;
    }

    public void setLifeAssignmentRecordsDocumentFileName(String lifeAssignmentRecordsDocumentFileName) {
        this.lifeAssignmentRecordsDocumentFileName = lifeAssignmentRecordsDocumentFileName;
    }

    public Long getLifeAssignmentRecordsDocumentFileSize() {
        return lifeAssignmentRecordsDocumentFileSize;
    }

    public void setLifeAssignmentRecordsDocumentFileSize(Long lifeAssignmentRecordsDocumentFileSize) {
        this.lifeAssignmentRecordsDocumentFileSize = lifeAssignmentRecordsDocumentFileSize;
    }

    public String getLifeAssignmentRecordsDocumentFileType() {
        return lifeAssignmentRecordsDocumentFileType;
    }

    public void setLifeAssignmentRecordsDocumentFileType(String lifeAssignmentRecordsDocumentFileType) {
        this.lifeAssignmentRecordsDocumentFileType = lifeAssignmentRecordsDocumentFileType;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }

}
