package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodicalScheduleMaintenanceDtlTO extends SubAssetDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String dueDate;
    private String maintenanceCategory;
    private String maintenanceSubCategory;
    private String planResponsibleSupervisor;
    private byte[] planRecordDocument;
    private String planRecordDocumentFileName;
    private Long planRecordDocumentFileSize;
    private String planRecordDocumentFileType;
    private String currentStatus;
    private String startDate;
    private String finishDate;
    private String actualResponsibleSupervisor;
    private String purchaseOrderNumber;
    private String materialsConsumptionRecords;
    private String planDocKey;
    private String actualDocKey;
    private byte[] actualRecordDocument;
    private String actualRecordsDocumentFileName;
    private Long actualnRecordsDocumentFileSize;
    private String actualRecordsDocumentFileType;
    private URL actualDocUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPlanResponsibleSupervisor() {
        return planResponsibleSupervisor;
    }

    public void setPlanResponsibleSupervisor(String planResponsibleSupervisor) {
        this.planResponsibleSupervisor = planResponsibleSupervisor;
    }

    public byte[] getPlanRecordDocument() {
        return planRecordDocument;
    }

    public void setPlanRecordDocument(byte[] planRecordDocument) {
        this.planRecordDocument = planRecordDocument;
    }

    public String getPlanRecordDocumentFileName() {
        return planRecordDocumentFileName;
    }

    public void setPlanRecordDocumentFileName(String planRecordDocumentFileName) {
        this.planRecordDocumentFileName = planRecordDocumentFileName;
    }

    public Long getPlanRecordDocumentFileSize() {
        return planRecordDocumentFileSize;
    }

    public void setPlanRecordDocumentFileSize(Long planRecordDocumentFileSize) {
        this.planRecordDocumentFileSize = planRecordDocumentFileSize;
    }

    public String getPlanRecordDocumentFileType() {
        return planRecordDocumentFileType;
    }

    public void setPlanRecordDocumentFileType(String planRecordDocumentFileType) {
        this.planRecordDocumentFileType = planRecordDocumentFileType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
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

    public String getActualResponsibleSupervisor() {
        return actualResponsibleSupervisor;
    }

    public void setActualResponsibleSupervisor(String actualResponsibleSupervisor) {
        this.actualResponsibleSupervisor = actualResponsibleSupervisor;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getPlanDocKey() {
        return planDocKey;
    }

    public void setPlanDocKey(String planDocKey) {
        this.planDocKey = planDocKey;
    }

    public String getActualDocKey() {
        return actualDocKey;
    }

    public void setActualDocKey(String actualDocKey) {
        this.actualDocKey = actualDocKey;
    }

    public byte[] getActualRecordDocument() {
        return actualRecordDocument;
    }

    public void setActualRecordDocument(byte[] actualRecordDocument) {
        this.actualRecordDocument = actualRecordDocument;
    }

    public String getActualRecordsDocumentFileName() {
        return actualRecordsDocumentFileName;
    }

    public void setActualRecordsDocumentFileName(String actualRecordsDocumentFileName) {
        this.actualRecordsDocumentFileName = actualRecordsDocumentFileName;
    }

    public Long getActualnRecordsDocumentFileSize() {
        return actualnRecordsDocumentFileSize;
    }

    public void setActualnRecordsDocumentFileSize(Long actualnRecordsDocumentFileSize) {
        this.actualnRecordsDocumentFileSize = actualnRecordsDocumentFileSize;
    }

    public String getActualRecordsDocumentFileType() {
        return actualRecordsDocumentFileType;
    }

    public void setActualRecordsDocumentFileType(String actualRecordsDocumentFileType) {
        this.actualRecordsDocumentFileType = actualRecordsDocumentFileType;
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

    public URL getActualDocUrl() {
        return actualDocUrl;
    }

    public void setActualDocUrl(URL actualDocUrl) {
        this.actualDocUrl = actualDocUrl;
    }
}
