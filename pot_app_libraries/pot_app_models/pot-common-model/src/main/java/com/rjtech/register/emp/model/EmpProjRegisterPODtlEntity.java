package com.rjtech.register.emp.model;

import java.io.Serializable;
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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractsEmpCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
//import com.rjtech.procurement.model.PreContractsEmpCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsEmpDtlEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.model.PurchaseOrderEntity;

@Entity
@Table(name = "emp_proj_po_dtl")
public class EmpProjRegisterPODtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PEP_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PEP_PUR_ID")
    private PurchaseOrderEntity purchaseOrderEntity;

    @ManyToOne
    @JoinColumn(name = "PEP_PCE_ID")
    private PreContractsEmpDtlEntity preContractsEmpDtlEntity;

    @ManyToOne
    @JoinColumn(name = "PEP_CEC_ID")
    private PreContractsEmpCmpEntity preContractsEmpCmpEntity;

    @ManyToOne
    @JoinColumn(name = "PEP_PCS_ID")
    private PreContractsServiceDtlEntity preContractsServiceDtlEntity;

    @ManyToOne
    @JoinColumn(name = "PEP_CSC_ID")
    private PreContractsServiceCmpEntity preContractsServiceCmpEntity;

    @Column(name = "PEP_ACTUAL_ITEMS")
    private Long actualItems;

    @Column(name = "PEP_CUMULATIVE")
    private Long cumulative;

    @Column(name = "PEP_PCE_ITM_DESC")
    private String purchaseItemDesc;

    @Column(name = "PEP_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PEP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PEP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PEP_UPDATED_ON")
    private Timestamp updatedOn;
    
    @Column(name = "PEP_ITEM_ID")
    private Long purchaseSchItemId;
    
    @Column(name = "PEP_PCE_ITEM_CODE")
    private String purchaseSchItemCode;
    

    @OneToMany(mappedBy = "empProjRegisterPODtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpProjRigisterEntity> empProjRigisterEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public PurchaseOrderEntity getPurchaseOrderEntity() {
        return purchaseOrderEntity;
    }

    public void setPurchaseOrderEntity(PurchaseOrderEntity purchaseOrderEntity) {
        this.purchaseOrderEntity = purchaseOrderEntity;
    }

    public PreContractsEmpDtlEntity getPreContractsEmpDtlEntity() {
        return preContractsEmpDtlEntity;
    }

    public void setPreContractsEmpDtlEntity(PreContractsEmpDtlEntity preContractsEmpDtlEntity) {
        this.preContractsEmpDtlEntity = preContractsEmpDtlEntity;
    }

    public Long getActualItems() {
        return actualItems;
    }

    public void setActualItems(Long actualItems) {
        this.actualItems = actualItems;
    }

    public Long getCumulative() {
        return cumulative;
    }

    public void setCumulative(Long cumulative) {
        this.cumulative = cumulative;
    }

    public String getPurchaseItemDesc() {
        return purchaseItemDesc;
    }

    public void setPurchaseItemDesc(String purchaseItemDesc) {
        this.purchaseItemDesc = purchaseItemDesc;
    }
    
    public String getPurchaseSchItemCode() {
        return purchaseSchItemCode;
    }

    public void setPurchaseSchItemCode(String purchaseSchItemCode) {
        this.purchaseSchItemCode = purchaseSchItemCode;
    }
    
    public Long getPurchaseSchItemId() {
        return purchaseSchItemId;
    }

    public void setPurchaseSchItemId(Long purchaseSchItemId) {
        this.purchaseSchItemId = purchaseSchItemId;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<EmpProjRigisterEntity> getEmpProjRigisterEntities() {
        return empProjRigisterEntities;
    }

    public void setEmpProjRigisterEntities(List<EmpProjRigisterEntity> empProjRigisterEntities) {
        this.empProjRigisterEntities = empProjRigisterEntities;
    }

    public PreContractsEmpCmpEntity getPreContractsEmpCmpEntity() {
        return preContractsEmpCmpEntity;
    }

    public void setPreContractsEmpCmpEntity(PreContractsEmpCmpEntity preContractsEmpCmpEntity) {
        this.preContractsEmpCmpEntity = preContractsEmpCmpEntity;
    }

    public PreContractsServiceDtlEntity getPreContractsServiceDtlEntity() {
        return preContractsServiceDtlEntity;
    }

    public void setPreContractsServiceDtlEntity(PreContractsServiceDtlEntity preContractsServiceDtlEntity) {
        this.preContractsServiceDtlEntity = preContractsServiceDtlEntity;
    }

    public PreContractsServiceCmpEntity getPreContractsServiceCmpEntity() {
        return preContractsServiceCmpEntity;
    }

    public void setPreContractsServiceCmpEntity(PreContractsServiceCmpEntity preContractsServiceCmpEntity) {
        this.preContractsServiceCmpEntity = preContractsServiceCmpEntity;
    }

}
