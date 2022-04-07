package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixedAssetRepairsRecordsDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String startDate;
    private String finishDate;
    private String responsibleSupervisor;
    private String maintenanceCategory;
    private String maintenanceSubCategory;
    private String materialsConsumptionRecords;
    private byte[] repairsRecordDetails;
    private String repairsRecordsDetailsFileName;
    private Long repairsRecordsDetailsDocumentFileSize;
    private String repairsRecordsDetailsFileType;
    private String docKey;
    private String purchaseOrderNumber;
    private URL docUrl;

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getResponsibleSupervisor() {
        return responsibleSupervisor;
    }

    public void setResponsibleSupervisor(String responsibleSupervisor) {
        this.responsibleSupervisor = responsibleSupervisor;
    }

    public String getMaintenanceCategory() {
        return maintenanceCategory;
    }

    public void setMaintenanceCategory(String maintenanceCategory) {
        this.maintenanceCategory = maintenanceCategory;
    }

    public String getMaintenanceSubCategory() {
        return maintenanceSubCategory;
    }

    public void setMaintenanceSubCategory(String maintenanceSubCategory) {
        this.maintenanceSubCategory = maintenanceSubCategory;
    }

    public String getMaterialsConsumptionRecords() {
        return materialsConsumptionRecords;
    }

    public void setMaterialsConsumptionRecords(String materialsConsumptionRecords) {
        this.materialsConsumptionRecords = materialsConsumptionRecords;
    }

    public byte[] getRepairsRecordDetails() {
        return repairsRecordDetails;
    }

    public void setRepairsRecordDetails(byte[] repairsRecordDetails) {
        this.repairsRecordDetails = repairsRecordDetails;
    }

    public String getRepairsRecordsDetailsFileName() {
        return repairsRecordsDetailsFileName;
    }

    public void setRepairsRecordsDetailsFileName(String repairsRecordsDetailsFileName) {
        this.repairsRecordsDetailsFileName = repairsRecordsDetailsFileName;
    }

    public Long getRepairsRecordsDetailsDocumentFileSize() {
        return repairsRecordsDetailsDocumentFileSize;
    }

    public void setRepairsRecordsDetailsDocumentFileSize(Long repairsRecordsDetailsDocumentFileSize) {
        this.repairsRecordsDetailsDocumentFileSize = repairsRecordsDetailsDocumentFileSize;
    }

    public String getRepairsRecordsDetailsFileType() {
        return repairsRecordsDetailsFileType;
    }

    public void setRepairsRecordsDetailsFileType(String repairsRecordsDetailsFileType) {
        this.repairsRecordsDetailsFileType = repairsRecordsDetailsFileType;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }
}
