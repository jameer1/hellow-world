package com.rjtech.register.material.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PreContractsMaterialCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsMaterialDtlEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;

@Entity
@Table(name = "material_proj_dtl")
public class MaterialProjDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MAP_MSM_GROUP_ID")
    private MaterialClassMstrEntity materialClassId;

    @ManyToOne
    @JoinColumn(name = "MAP_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "MAP_CMP_ID")
    private CompanyMstrEntity companyMstrEntity;

    @ManyToOne
    @JoinColumn(name = "MAP_PUR_ID")
    private PurchaseOrderEntity purchaseId;

    @ManyToOne
    @JoinColumn(name = "MAP_PCMD_ID")
    private PreContractsMaterialDtlEntity preContractMterialId;

    @ManyToOne
    @JoinColumn(name = "MAP_CMC_ID")
    private PreContractsMaterialCmpEntity preContractsMaterialCmpEntity;

    @ManyToOne
    @JoinColumn(name = "MAP_PCS_ID")
    private PreContractsServiceDtlEntity preContractsServiceDtlEntity;

    @ManyToOne
    @JoinColumn(name = "MAP_CSC_ID")
    private PreContractsServiceCmpEntity preContractsServiceCmpEntityCopy;

    @Column(name = "MAP_PCMD_ITM_DESC")
    private String preContractMaterialName;
    
    @Column(name = "MAP_PCMD_ITEM_CODE")
    private String preContractMaterialSchCode;
    
    @Column(name = "MAP_PCMD_ITEM_ID")
    private Long preContractMaterialSchId;

    @Column(name = "MAP_QUANTITY")
    private BigDecimal quantity;

    @Column(name = "MAP_COMMENTS")
    private String comments;

    @Column(name = "MAP_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MAP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Column(name = "MAP_CREATED_ON", updatable = false)
    private Timestamp createdOn;

    @ManyToOne
    @JoinColumn(name = "MAP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MAP_UPDATED_ON")
    private Timestamp updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAP_SUPPLY_DELIVERY_DATE")
    private Date supplyDeliveryDate;

    @Column(name = "MAP_RATE")
    private BigDecimal rate;
    
    @OneToMany(mappedBy = "materialProjDtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MaterialPODeliveryDocketEntity> materialPODeliveryDocketEntities = new ArrayList<>();

    @OneToMany(mappedBy = "materialProjDtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MaterialSchLocCountEntity> materialSchLocCountEntities = new ArrayList<>();

    @OneToMany(mappedBy = "materialProjDtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MaterialProjLedgerEntity> materialProjLedgerEntities = new ArrayList<>();
    
    @Column(name="MAP_ASSET_ID")
    private String assetType;
    
	/*
	 * @Column(name = "MAP_PCMD_ITEM_ID") private Long purchaseSchItemId;
	 */
    
	/*
	 * public Long getPurchaseSchItemId() { return purchaseSchItemId; }
	 * 
	 * public void setPurchaseSchItemId(Long purchaseSchItemId) {
	 * this.purchaseSchItemId = purchaseSchItemId; }
	 */

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyMstrEntity getCompanyMstrEntity() {
        return companyMstrEntity;
    }

    public void setCompanyMstrEntity(CompanyMstrEntity companyMstrEntity) {
        this.companyMstrEntity = companyMstrEntity;
    }

    public String getPreContractMaterialName() {
        return preContractMaterialName;
    }

    public void setPreContractMaterialName(String preContractMaterialName) {
        this.preContractMaterialName = preContractMaterialName;
    }
    
    public String getPreContractMaterialSchCode() {
        return preContractMaterialSchCode;
    }

    public void setPreContractMaterialSchCode(String preContractMaterialSchCode) {
        this.preContractMaterialSchCode = preContractMaterialSchCode;
    }

    public Long getPreContractMaterialSchId() {
        return preContractMaterialSchId;
    }

    public void setPreContractMaterialSchId(Long preContractMaterialSchId) {
        this.preContractMaterialSchId = preContractMaterialSchId;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<MaterialPODeliveryDocketEntity> getMaterialPODeliveryDocketEntities() {
        return materialPODeliveryDocketEntities;
    }

    public void setMaterialPODeliveryDocketEntities(
            List<MaterialPODeliveryDocketEntity> materialPODeliveryDocketEntities) {
        this.materialPODeliveryDocketEntities = materialPODeliveryDocketEntities;
    }

    public List<MaterialProjLedgerEntity> getMaterialProjLedgerEntities() {
        return materialProjLedgerEntities;
    }

    public void setMaterialProjLedgerEntities(List<MaterialProjLedgerEntity> materialProjLedgerEntities) {
        this.materialProjLedgerEntities = materialProjLedgerEntities;
    }

    public PreContractsMaterialCmpEntity getPreContractsMaterialCmpEntity() {
        return preContractsMaterialCmpEntity;
    }

    public void setPreContractsMaterialCmpEntity(PreContractsMaterialCmpEntity preContractsMaterialCmpEntity) {
        this.preContractsMaterialCmpEntity = preContractsMaterialCmpEntity;
    }

    public Date getSupplyDeliveryDate() {
        return supplyDeliveryDate;
    }

    public void setSupplyDeliveryDate(Date supplyDeliveryDate) {
        this.supplyDeliveryDate = supplyDeliveryDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<MaterialSchLocCountEntity> getMaterialSchLocCountEntities() {
        return materialSchLocCountEntities;
    }

    public void setMaterialSchLocCountEntities(List<MaterialSchLocCountEntity> materialSchLocCountEntities) {
        this.materialSchLocCountEntities = materialSchLocCountEntities;
    }

    public MaterialClassMstrEntity getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(MaterialClassMstrEntity materialClassId) {
        this.materialClassId = materialClassId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public PurchaseOrderEntity getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(PurchaseOrderEntity purchaseId) {
        this.purchaseId = purchaseId;
    }

    public PreContractsMaterialDtlEntity getPreContractMterialId() {
        return preContractMterialId;
    }

    public void setPreContractMterialId(PreContractsMaterialDtlEntity preContractMterialId) {
        this.preContractMterialId = preContractMterialId;
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

    public PreContractsServiceDtlEntity getPreContractsServiceDtlEntity() {
        return preContractsServiceDtlEntity;
    }

    public void setPreContractsServiceDtlEntity(PreContractsServiceDtlEntity preContractsServiceDtlEntity) {
        this.preContractsServiceDtlEntity = preContractsServiceDtlEntity;
    }

    public PreContractsServiceCmpEntity getPreContractsServiceCmpEntityCopy() {
        return preContractsServiceCmpEntityCopy;
    }

    public void setPreContractsServiceCmpEntityCopy(PreContractsServiceCmpEntity preContractsServiceCmpEntityCopy) {
        this.preContractsServiceCmpEntityCopy = preContractsServiceCmpEntityCopy;
    }
    
    public void setAssetType(String assetType) {
    	this.assetType = assetType;
    }
    
    public String getAssetType() {
    	return assetType;
    }
}
