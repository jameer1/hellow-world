package com.rjtech.register.plant.model;

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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
//import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;

@Entity
@Table(name = "plant_project_po_dtl")
public class PlantProjPODtlEntity implements Serializable {

    private static final long serialVersionUID = 916934347444561277L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PPP_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "PPP_PCP_ID")
    private PreContractsPlantDtlEntityCopy purchasePlantTypeId;

    @ManyToOne
    @JoinColumn(name = "PPP_CPC_ID")
    private PreContractsPlantCmpEntityCopy scheduleCompanyPlantId;

    @ManyToOne
    @JoinColumn(name = "PPP_PCS_ID")
    private PreContractsServiceDtlEntity preContractsServiceDtlEntity;

    @ManyToOne
    @JoinColumn(name = "PPP_CSC_ID")
    private PreContractsServiceCmpEntity preContractsServiceCmpEntity;

    @ManyToOne
    @JoinColumn(name = "PPP_PUR_ID")
    private PurchaseOrderEntity purchaseTypeId;

    @Column(name = "PPP_USED_ITEMS")
    private BigDecimal usedItems;

    @Column(name = "PPP_ACTUAL_ITEMS")
    private BigDecimal actualItems;

    @ManyToOne
    @JoinColumn(name = "PPP_RECEIVED_BY")
    private UserMstrEntity receivedBy;

    @Column(name = "PPP_CUMULATIVE")
    private BigDecimal cumulative;

    @Column(name = "PPP_STATUS")
    private Integer status;
    
    @Column(name = "PPP_ITEM_ID")
    private Long schdItemId;
    
    @Column(name = "PPP_ITEM_CODE")
    private String schdItemCode;
   
    @ManyToOne
    @JoinColumn(name = "PPP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PPP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PPP_UPDATED_ON")
    private Timestamp updatedOn;

    @OneToMany(mappedBy = "plantProjPODtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlantPODocketDtlEntity> plantPODocketDtlEntities = new ArrayList<>();

    @OneToMany(mappedBy = "plantProjPODtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlantRegProjEntity> plantRegProjEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUsedItems() {
        return usedItems;
    }

    public void setUsedItems(BigDecimal usedItems) {
        this.usedItems = usedItems;
    }

    public BigDecimal getActualItems() {
        return actualItems;
    }

    public void setActualItems(BigDecimal actualItems) {
        this.actualItems = actualItems;
    }

    public BigDecimal getCumulative() {
        return cumulative;
    }

    public void setCumulative(BigDecimal cumulative) {
        this.cumulative = cumulative;
    }

    public Integer getStatus() {
        return status;
    }
    
    public Long getSchdItemId() {
        return schdItemId;
    }

    public void setSchdItemId(Long schdItemId) {
        this.schdItemId = schdItemId;
    }
    
    public String getSchdItemCode() {
        return schdItemCode;
    }

    public void setSchdItemCode(String schdItemCode) {
        this.schdItemCode = schdItemCode;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<PlantPODocketDtlEntity> getPlantPODocketDtlEntities() {
        return plantPODocketDtlEntities;
    }

    public void setPlantPODocketDtlEntities(List<PlantPODocketDtlEntity> plantPODocketDtlEntities) {
        this.plantPODocketDtlEntities = plantPODocketDtlEntities;
    }

    public List<PlantRegProjEntity> getPlantRegProjEntities() {
        return plantRegProjEntities;
    }

    public void setPlantRegProjEntities(List<PlantRegProjEntity> plantRegProjEntities) {
        this.plantRegProjEntities = plantRegProjEntities;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public PreContractsPlantDtlEntityCopy getPurchasePlantTypeId() {
        return purchasePlantTypeId;
    }

    public void setPurchasePlantTypeId(PreContractsPlantDtlEntityCopy purchasePlantTypeId) {
        this.purchasePlantTypeId = purchasePlantTypeId;
    }

    public PreContractsPlantCmpEntityCopy getScheduleCompanyPlantId() {
        return scheduleCompanyPlantId;
    }

    public void setScheduleCompanyPlantId(PreContractsPlantCmpEntityCopy scheduleCompanyPlantId) {
        this.scheduleCompanyPlantId = scheduleCompanyPlantId;
    }

    public PurchaseOrderEntity getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(PurchaseOrderEntity purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public UserMstrEntity getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(UserMstrEntity receivedBy) {
        this.receivedBy = receivedBy;
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

    public PreContractsServiceCmpEntity getPreContractsServiceCmpEntity() {
        return preContractsServiceCmpEntity;
    }

    public void setPreContractsServiceCmpEntity(PreContractsServiceCmpEntity preContractsServiceCmpEntity) {
        this.preContractsServiceCmpEntity = preContractsServiceCmpEntity;
    }

}
