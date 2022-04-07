package com.rjtech.procurement.model;

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

import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
//import com.rjtech.projectlib.model.copy.ProjStoreStockMstrEntityCopy;
//import com.rjtech.projsettings.model.copy.ProjCostStmtDtlEntityCopy;

/**
 * The persistent class for the pre_contracts_emp_dtl database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_plant_dtl")
public class PreContractsPlantDtlEntity implements Serializable {

    private static final long serialVersionUID = -8325686122574804337L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCP_PJCS_ID")
    private ProjCostStmtDtlEntity projcostStatement;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCP_FINISH_DATE")
    private Date finishDate;

    @Column(name = "PCP_LATEST")
    private Boolean latest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCP_PCM_ID")
    private PlantMstrEntity plantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCP_PCD_ID")
    private ProcureCatgDtlEntity procureSubCatgId;

    @Column(name = "PCP_ESTIMATE_COST")
    private BigDecimal estimateCost;

    @Column(name = "PCP_QTY")
    private Integer quantity;

    @Column(name = "PCP_DELIVERY_PLACE")
    private String deliveryPlace;

    @Temporal(TemporalType.DATE)
    @Column(name = "PCP_START_DATE")
    private Date startDate;

    @Column(name = "PCP_ITM_DESC", length = 400)
    private String desc;

    @OneToMany(mappedBy = "preContractsPlantDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities = new ArrayList<PreContractsPlantCmpEntity>();

    @ManyToOne
    @JoinColumn(name = "PCP_PRC_ID")
    private PreContractEntity preContractEntity;

    @Column(name = "PCP_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCP_UPDATED_ON")
    private Date updatedOn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCP_SM_ID")
    private StockMstrEntity storeId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCP_SPM_ID")
    private ProjStoreStockMstrEntity projStoreId;
    
    @Column(name = "PCP_UNIT_MEASURE")
    private String unitMeasure;
     

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

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
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

    public List<PreContractsPlantCmpEntity> getPreContractsPlantCmpEntities() {
        return preContractsPlantCmpEntities;
    }

    public void setPreContractsPlantCmpEntities(List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities) {
        this.preContractsPlantCmpEntities = preContractsPlantCmpEntities;
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

    public PlantMstrEntity getPlantId() {
        return plantId;
    }

    public void setPlantId(PlantMstrEntity plantId) {
        this.plantId = plantId;
    }

    public ProcureCatgDtlEntity getProcureSubCatgId() {
        return procureSubCatgId;
    }

    public void setProcureSubCatgId(ProcureCatgDtlEntity procureSubCatgId) {
        this.procureSubCatgId = procureSubCatgId;
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

	public StockMstrEntity getStoreId() {
		return storeId;
	}

	public void setStoreId(StockMstrEntity storeId) {
		this.storeId = storeId;
	}

	public ProjStoreStockMstrEntity getProjStoreId() {
		return projStoreId;
	}

	public void setProjStoreId(ProjStoreStockMstrEntity projStoreId) {
		this.projStoreId = projStoreId;
	}
    
	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

}