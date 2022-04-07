package com.rjtech.notification.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.notification.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
//import com.rjtech.notification.model.ProjCostStmtDtlEntityCopy;

/**
 * The persistent class for the pre_contracts_emp_dtl database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_material_dtl")
public class PreContractsMaterialDtlEntity implements Serializable {

    private static final long serialVersionUID = -7052701831067042109L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCMD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCMD_PJCS_ID")
    private ProjCostStmtDtlEntity projcostStatement;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCMD_FINISH_DATE")
    private Date finishDate;

    @Column(name = "PCMD_ITM_DESC", length = 400)
    private String desc;

    @Column(name = "PCMD_LATEST")
    private Boolean latest;

    @Column(name = "PCMD_ESTIMATE_COST")
    private BigDecimal estimateCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCMD_MSM_GROUP_ID")
    private MaterialClassMstrEntity materialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCMD_PCD_ID")
    private ProcureCatgDtlEntity procureSubCatgId;

    @Column(name = "PCMD_QTY")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCMD_SM_ID")
    private StockMstrEntity storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCMD_SPM_ID")
    private ProjStoreStockMstrEntityCopy projStoreId;

    @Temporal(TemporalType.DATE)
    @Column(name = "PCMD_START_DATE")
    private Date startDate;

    @OneToMany(mappedBy = "preContractsMaterialDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities = new ArrayList<PreContractsMaterialCmpEntity>();

    @ManyToOne
    @JoinColumn(name = "PCMD_PRC_ID")
    private PreContractEntity preContractEntity;

    @Column(name = "PCMD_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCMD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCMD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCMD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCMD_UPDATED_ON")
    private Date updatedOn;

    public PreContractsMaterialDtlEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(BigDecimal estimateCost) {
        this.estimateCost = estimateCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<PreContractsMaterialCmpEntity> getPreContractsMaterialCmpEntities() {
        return preContractsMaterialCmpEntities;
    }

    public void setPreContractsMaterialCmpEntities(
            List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities) {
        this.preContractsMaterialCmpEntities = preContractsMaterialCmpEntities;
    }

    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public ProjCostStmtDtlEntity getProjcostStatement() {
        return projcostStatement;
    }

    public void setProjcostStatement(ProjCostStmtDtlEntity projcostStatement) {
        this.projcostStatement = projcostStatement;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public MaterialClassMstrEntity getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MaterialClassMstrEntity materialId) {
        this.materialId = materialId;
    }

    public ProcureCatgDtlEntity getProcureSubCatgId() {
        return procureSubCatgId;
    }

    public void setProcureSubCatgId(ProcureCatgDtlEntity procureSubCatgId) {
        this.procureSubCatgId = procureSubCatgId;
    }

    public StockMstrEntity getStoreId() {
        return storeId;
    }

    public void setStoreId(StockMstrEntity storeId) {
        this.storeId = storeId;
    }

    public ProjStoreStockMstrEntityCopy getProjStoreId() {
        return projStoreId;
    }

    public void setProjStoreId(ProjStoreStockMstrEntityCopy projStoreId) {
        this.projStoreId = projStoreId;
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

}