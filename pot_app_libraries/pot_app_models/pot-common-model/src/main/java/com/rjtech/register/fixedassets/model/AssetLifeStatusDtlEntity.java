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
@Table(name = "asset_life_status")
public class AssetLifeStatusDtlEntity implements Serializable {
    private static final long serialVersionUID = -8795406421033703996L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fa_asset_life_status_id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fa_als_effectiveDate")
    private Date effectiveDate;

    @Column(name = "fa_als_buildStructure")
    private String buildStructure;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fa_als_plantCommissioningDate")
    private Date plantCommissioningDate;

    @Column(name = "fa_als_structureTotal")
    private String structureTotal;

    @Column(name = "fa_als_plantEquipmentTotal")
    private String plantEquipmentTotal;

    @Column(name = "fa_als_ageStructure")
    private String ageStructure;

    @Column(name = "fa_als_ageEquipment")
    private String ageEquipment;

    @Column(name = "fa_als_remainingStruture")
    private String remainingStruture;

    @Column(name = "fa_als_remainingEquipment")
    private String remainingEquipment;

    @Column(name = "fa_als_lifeAssignmentRecordsDocumentFileName")
    private String lifeAssignmentRecordsDocumentFileName;

    @Column(name = "fa_als_lifeAssignmentRecordsDocumentFileType")
    private String lifeAssignmentRecordsDocumentFileType;

    @Column(name = "fa_als_STATUS")
    private int status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "fa_als_DOC_KEY")
    private String docKey;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity; 

    public String getDocKey() {
        return docKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getPlantCommissioningDate() {
        return plantCommissioningDate;
    }

    public void setPlantCommissioningDate(Date plantCommissioningDate) {
        this.plantCommissioningDate = plantCommissioningDate;
    }

    public String getLifeAssignmentRecordsDocumentFileName() {
        return lifeAssignmentRecordsDocumentFileName;
    }

    public void setLifeAssignmentRecordsDocumentFileName(String lifeAssignmentRecordsDocumentFileName) {
        this.lifeAssignmentRecordsDocumentFileName = lifeAssignmentRecordsDocumentFileName;
    }

    public String getLifeAssignmentRecordsDocumentFileType() {
        return lifeAssignmentRecordsDocumentFileType;
    }

    public void setLifeAssignmentRecordsDocumentFileType(String lifeAssignmentRecordsDocumentFileType) {
        this.lifeAssignmentRecordsDocumentFileType = lifeAssignmentRecordsDocumentFileType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getBuildStructure() {
        return buildStructure;
    }

    public void setBuildStructure(String buildStructure) {
        this.buildStructure = buildStructure;
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

    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
    
    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
}
