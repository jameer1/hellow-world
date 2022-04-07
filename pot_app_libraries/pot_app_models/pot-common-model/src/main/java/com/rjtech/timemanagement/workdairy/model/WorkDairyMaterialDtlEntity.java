package com.rjtech.timemanagement.workdairy.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
//import com.rjtech.register.material.model.MaterialPODeliveryDocketEntityCopy;
//import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntityCopy;
//import com.rjtech.register.material.model.MaterialProjDtlEntityCopy;
import com.rjtech.register.material.model.MaterialProjDtlEntity;

@Entity
@Table(name = "work_dairy_material_dtl")
public class WorkDairyMaterialDtlEntity implements Serializable {

    private static final long serialVersionUID = 7444945736624142240L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDMD_ID")
    private Long id;

    @Column(name = "WDMD_SOURCE_TYPE")
    private String sourceType;

    @Column(name = "WDMD_DOCKET_TYPE")
    private String docketType;

    @Column(name = "WDMD_DOCKET_NUM")
    private String docketNum;

    @Temporal(TemporalType.DATE)
    @Column(name = "WDMD_DOCKET_DATE")
    private Date docketDate;

    @Column(name = "WDMD_RECIEVED_QTY")
    private BigDecimal receivedQty;
    
    @Column(name = "WDMD_CONSUMPTION_QTY")
    private BigDecimal consumptionQty;
    
    @Column(name = "WDMD_OPENING_STOCK")
    private BigDecimal openingStock;
    
    @Column(name = "WDMD_CLOSING_STOCK")
    private BigDecimal closingStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMD_MPSC_ID")
    private MaterialProjDocketSchItemsEntity projDocketSchId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "WORKDIARY_MATERIAL_PO_DELIVERY_DOCKET", joinColumns = @JoinColumn(name = "WORKDIARY_ID", columnDefinition = "int default 0"), inverseJoinColumns = @JoinColumn(name = "MATERIAL_PO_DELIVERY_DOCKET", columnDefinition = "int default 0"))
    private List<MaterialPODeliveryDocketEntity> materialPODeliveryDocketEntity = new ArrayList<MaterialPODeliveryDocketEntity>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "WORKDIARY_MATERIAL_PROJ_DTL", joinColumns = @JoinColumn(name = "WORKDIARY_ID", columnDefinition = "int default 0"), inverseJoinColumns = @JoinColumn(name = "MATERIAL_ID", columnDefinition = "int default 0"))
    private List<MaterialProjDtlEntity> materialProjDtlEntity = new ArrayList<MaterialProjDtlEntity>();

    @Column(name = "WDMD_IS_CONSUPTION_UPDATED")
    private boolean consumpitonUpdated;

    @Column(name = "WDMD_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "WDMD_WDM_ID")
    private WorkDairyEntity workDairyEntity;

    @OneToMany(mappedBy = "workDairyMaterialDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkDairyMaterialStatusEntity> workDairyMaterialStatusEntities = new ArrayList<>();

    @Column(name = "WDMD_COMMENTS")
    private String defectComments;

    @Column(name = "WDMD_LOCATION")
    private String deliveryPlace;

    @Column(name = "IS_SUPPLIER_DOCKET", columnDefinition = "int default 0", nullable = false)
    private boolean isSupplierDocket;

    @Column(name = "WDMD_APPR_COMMENTS")
    private String comments;

    public BigDecimal getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(BigDecimal consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public BigDecimal getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(BigDecimal openingStock) {
        this.openingStock = openingStock;
    }

    public BigDecimal getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(BigDecimal closingStock) {
        this.closingStock = closingStock;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isSupplierDocket() {
        return isSupplierDocket;
    }

    public void setSupplierDocket(boolean isSupplierDocket) {
        this.isSupplierDocket = isSupplierDocket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDocketType() {
        return docketType;
    }

    public void setDocketType(String docketType) {
        this.docketType = docketType;
    }

    public String getDocketNum() {
        return docketNum;
    }

    public void setDocketNum(String docketNum) {
        this.docketNum = docketNum;
    }

    public Date getDocketDate() {
        return docketDate;
    }

    public void setDocketDate(Date docketDate) {
        this.docketDate = docketDate;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

    public boolean isConsumpitonUpdated() {
        return consumpitonUpdated;
    }

    public void setConsumpitonUpdated(boolean consumpitonUpdated) {
        this.consumpitonUpdated = consumpitonUpdated;
    }

    public List<MaterialPODeliveryDocketEntity> getMaterialPODeliveryDocketEntity() {
        return materialPODeliveryDocketEntity;
    }

    public void setMaterialPODeliveryDocketEntity(
            List<MaterialPODeliveryDocketEntity> materialPODeliveryDocketEntity) {
        this.materialPODeliveryDocketEntity = materialPODeliveryDocketEntity;
    }

    public List<MaterialProjDtlEntity> getMaterialProjDtlEntity() {
        return materialProjDtlEntity;
    }

    public void setMaterialProjDtlEntity(List<MaterialProjDtlEntity> materialProjDtlEntity) {
        this.materialProjDtlEntity = materialProjDtlEntity;
    }

    public WorkDairyEntity getWorkDairyEntity() {
        return workDairyEntity;
    }

    public void setWorkDairyEntity(WorkDairyEntity workDairyEntity) {
        this.workDairyEntity = workDairyEntity;
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

    public List<WorkDairyMaterialStatusEntity> getWorkDairyMaterialStatusEntities() {
        return workDairyMaterialStatusEntities;
    }

    public void setWorkDairyMaterialStatusEntities(
            List<WorkDairyMaterialStatusEntity> workDairyMaterialStatusEntities) {
        this.workDairyMaterialStatusEntities = workDairyMaterialStatusEntities;
    }

    public String getDefectComments() {
        return defectComments;
    }

    public void setDefectComments(String defectComments) {
        this.defectComments = defectComments;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public MaterialProjDocketSchItemsEntity getProjDocketSchId() {
        return projDocketSchId;
    }

    public void setProjDocketSchId(MaterialProjDocketSchItemsEntity projDocketSchId) {
        this.projDocketSchId = projDocketSchId;
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