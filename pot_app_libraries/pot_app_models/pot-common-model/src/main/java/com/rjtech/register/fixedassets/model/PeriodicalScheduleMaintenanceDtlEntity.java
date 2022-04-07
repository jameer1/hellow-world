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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "periodical_schedule_maintenance")
public class PeriodicalScheduleMaintenanceDtlEntity implements Serializable {
    private static final long serialVersionUID = -8795406421033703996L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FP_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FP_DUE_DATE")
    private Date dueDate;

    @Column(name = "FP_MAINTANCECATEGORY")
    private String maintanceCategory;

    @Column(name = "FP_MAINTANCESUBCATEGORY")
    private String maintanceSubCategory;

    @Column(name = "FP_ACTUALRESPONSIBLESUPERVISOR")
    private String actualResponsibleSupervisor;

    @Column(name = "FP_PLANRESPONSIBLESUPERVISOR")
    private String planResponsibleSupervisor;

    @Column(name = "FP_CURRENTSTATUS")
    private String currentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FP_STARTDATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FP_FINSHDATE")
    private Date finishDate;

    @Column(name = "FP_PURCHASEORDERNUMBER")
    private String purchaseOrderNumber;

    @Column(name = "FP_MATERIALCONSUMPTIONRECORDS")
    private String materialConsumptionRecords;

    @Column(name = "FP_planDocKey")
    private String planDocKey;
    
    @Column(name = "FP_actualDocKey")
    private String actualDocKey;

    @Column(name = "FP_STATUS")
    private int status;

    @Column(name = "FP_CREATEDBY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FP_CREATEDON")
    private Date createdOn;

    @Column(name = "FP_UPDATEDBY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FP_UPDATEDON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientRegEntity clientRegEntity;

    @ManyToOne
    @JoinColumn(name = "FP_FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @Column(name = "FP_PLANRECORDSDETAILSFILENAME")
    private String planRecordsDetailsFileName;

    @Column(name = "FP_PLANRECORDSDETAILSDOCUMENTFILESIZE")
    private Long planRecordsDetailsDocumentFileSize;

    @Column(name = "FP_PLANRECORDSDETAILSFILETYPE")
    private String planRecordsDetailsFileType;

    @Column(name = "FP_ACTUALRECORDSDETAILSFILENAME")
    private String actualRecordsDetailsFileName;

    @Column(name = "FP_ACTUALRECORDSDETAILSDOCUMENTFILESIZE")
    private Long actualRecordsDetailsDocumentFileSize;

    @Column(name = "FP_ACTUALRECORDSDETAILSFILETYPE")
    private String actualRecordsDetailsFileType;

    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getMaintanceCategory() {
        return maintanceCategory;
    }

    public void setMaintanceCategory(String maintanceCategory) {
        this.maintanceCategory = maintanceCategory;
    }

    public String getMaintanceSubCategory() {
        return maintanceSubCategory;
    }

    public void setMaintanceSubCategory(String maintanceSubCategory) {
        this.maintanceSubCategory = maintanceSubCategory;
    }

    public String getActualResponsibleSupervisor() {
        return actualResponsibleSupervisor;
    }

    public void setActualResponsibleSupervisor(String actualResponsibleSupervisor) {
        this.actualResponsibleSupervisor = actualResponsibleSupervisor;
    }

    public String getPlanResponsibleSupervisor() {
        return planResponsibleSupervisor;
    }

    public void setPlanResponsibleSupervisor(String planResponsibleSupervisor) {
        this.planResponsibleSupervisor = planResponsibleSupervisor;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getMaterialConsumptionRecords() {
        return materialConsumptionRecords;
    }

    public void setMaterialConsumptionRecords(String materialConsumptionRecords) {
        this.materialConsumptionRecords = materialConsumptionRecords;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ClientRegEntity getClientRegEntity() {
        return clientRegEntity;
    }

    public void setClientRegEntity(ClientRegEntity clientRegEntity) {
        this.clientRegEntity = clientRegEntity;
    }

    public String getPlanRecordsDetailsFileName() {
        return planRecordsDetailsFileName;
    }

    public void setPlanRecordsDetailsFileName(String planRecordsDetailsFileName) {
        this.planRecordsDetailsFileName = planRecordsDetailsFileName;
    }

    public Long getPlanRecordsDetailsDocumentFileSize() {
        return planRecordsDetailsDocumentFileSize;
    }

    public void setPlanRecordsDetailsDocumentFileSize(Long planRecordsDetailsDocumentFileSize) {
        this.planRecordsDetailsDocumentFileSize = planRecordsDetailsDocumentFileSize;
    }

    public String getPlanRecordsDetailsFileType() {
        return planRecordsDetailsFileType;
    }

    public void setPlanRecordsDetailsFileType(String planRecordsDetailsFileType) {
        this.planRecordsDetailsFileType = planRecordsDetailsFileType;
    }

    public String getActualRecordsDetailsFileName() {
        return actualRecordsDetailsFileName;
    }

    public void setActualRecordsDetailsFileName(String actualRecordsDetailsFileName) {
        this.actualRecordsDetailsFileName = actualRecordsDetailsFileName;
    }

    public Long getActualRecordsDetailsDocumentFileSize() {
        return actualRecordsDetailsDocumentFileSize;
    }

    public void setActualRecordsDetailsDocumentFileSize(Long actualRecordsDetailsDocumentFileSize) {
        this.actualRecordsDetailsDocumentFileSize = actualRecordsDetailsDocumentFileSize;
    }

    public String getActualRecordsDetailsFileType() {
        return actualRecordsDetailsFileType;
    }

    public void setActualRecordsDetailsFileType(String actualRecordsDetailsFileType) {
        this.actualRecordsDetailsFileType = actualRecordsDetailsFileType;
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

    public ProjDocFileEntity getProjDocFileEntity() {
        return projDocFileEntity;
    }

    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
        this.projDocFileEntity = projDocFileEntity;
    }
}
