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
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "fixedasset_repairs_records_dtl")
public class FixedAssetRepairsDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FR_ID")
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FR_STARTDATE")
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FR_FINISHDATE")
    private Date finishDate;
    
    @Column(name = "FR_RESPONSIBLESUPERVISOR")
    private String responsibleSupervisor;
    
    @Column(name = "FR_MAINTENANCECATEGORY")
    private String maintenanceCategory;
    
    @Column(name = "FR_MAINTENANCESUBCATEGORY")
    private String maintenanceSubCategory;
    
    @Column(name = "FR_MATERIALSCONSUMPTIONRECORDS")
    private String materialsConsumptionRecords;
    
    @Column(name = "FR_REPAIRSRECORDSDETAILSFILENAME")
    private String repairsRecordsDetailsFileName;
    
    @Column(name = "FR_REPAIRSRECORDSDETAILSDOCUMENTFILESIZE")
    private Long repairsRecordsDetailsDocumentFileSize;
    
    @Column(name = "FR_REPAIRSRECORDSDETAILSFILETYPE")
    private String repairsRecordsDetailsFileType;
    
    @Column(name = "FR_DOCKEY")
    private String docKey;
    
    @ManyToOne
    @JoinColumn(name = "FR_CREATED_BY")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FR_CREATED_ON")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FR__UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FR_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "FR_PAR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FR_FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;
    
    @Column(name = "FR_PURCHASEORDERNUMBER")
    private String purchaseOrderNumber;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
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
    
    public ProjDocFileEntity getProjDocFileEntity() {
        return projDocFileEntity;
    }

    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
        this.projDocFileEntity = projDocFileEntity;
    }
  }
