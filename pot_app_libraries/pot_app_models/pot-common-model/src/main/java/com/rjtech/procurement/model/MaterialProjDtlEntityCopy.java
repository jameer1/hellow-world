package com.rjtech.procurement.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;

@Entity
@Table(name = "material_proj_dtl")
public class MaterialProjDtlEntityCopy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAP_ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "MAP_MSM_GROUP_ID")
    private MaterialClassMstrEntity materialClassId;
    
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
    
    @Column(name = "MAP_PCMD_ITEM_ID")
    private Long purchaseSchItemId;
    
    @ManyToOne
    @JoinColumn(name = "MAP_PCMD_ID")
    private PreContractsMaterialDtlEntity preContractMterialId;
    
    @Column(name = "MAP_PCMD_ITM_DESC")
    private String preContractMaterialSchDesc; 
    
    @Column(name = "MAP_PCMD_ITEM_CODE")
    private String preContractMaterialSchCode;
    
    @ManyToOne
    @JoinColumn(name = "MAP_PUR_ID")
    private PurchaseOrderEntity purchaseId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }
    
    public Long getPurchaseSchItemId() {
        return purchaseSchItemId;
    }

    public void setPurchaseSchItemId(Long purchaseSchItemId) {
        this.purchaseSchItemId = purchaseSchItemId;
    }
    
    public MaterialClassMstrEntity getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(MaterialClassMstrEntity materialClassId) {
        this.materialClassId = materialClassId;
    }
    
    public PreContractsMaterialDtlEntity getPreContractMterialId() {
        return preContractMterialId;
    }

    public void setPreContractMterialId(PreContractsMaterialDtlEntity preContractMterialId) {
        this.preContractMterialId = preContractMterialId;
    }
    
    public String getPreContractMaterialSchDesc() {
        return preContractMaterialSchDesc;
    }

    public void setPreContractMaterialSchDesc(String preContractMaterialSchDesc) {
        this.preContractMaterialSchDesc = preContractMaterialSchDesc;
    }
    
    public String getPreContractMaterialSchCode() {
        return preContractMaterialSchCode;
    }

    public void setPreContractMaterialSchCode(String preContractMaterialSchCode) {
        this.preContractMaterialSchCode = preContractMaterialSchCode;
    }
    
    public PurchaseOrderEntity getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(PurchaseOrderEntity purchaseId) {
        this.purchaseId = purchaseId;
    }
}
