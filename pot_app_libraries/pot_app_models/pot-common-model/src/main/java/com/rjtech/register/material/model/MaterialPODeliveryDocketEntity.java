package com.rjtech.register.material.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;

@Entity
@Table(name = "material_po_delivery_docket")
public class MaterialPODeliveryDocketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MADD_ID")
    private Long id;

    @Column(name = "MADD_DOCT_NUM")
    private String docketNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MADD_DOCT_DATE")
    private Date docketDate;

    @ManyToOne
    @JoinColumn(name = "MADD_SM_ID")
    private StockMstrEntity stockId;

    @Column(name = "MADD_SM_CODE")
    private String stockCode;

    @ManyToOne
    @JoinColumn(name = "MADD_SPM_ID")
    private ProjStoreStockMstrEntity projStockId;

    @Column(name = "MADD_SPM_CODE")
    private String projStockCode;

    @Column(name = "MADD_RECEIVED_QTY")
    private BigDecimal receivedQty;

    @Column(name = "MADD_TRANSIT_QTY")
    private BigDecimal transitQty;

    @Column(name = "MADD_IS_COMPLETE")
    private String docketStatus;

    @Column(name = "MADD_SOURCE_TYPE")
    private String sourceType;

    @Column(name = "MADD_RECEIVED_BY")
    private String receivedBy;

    @Column(name = "MADD_DEFECT_COMMENTS")
    private String defectComments;

    @Column(name = "MADD_COMMENTS")
    private String comments;

    @Column(name = "MADD_LOC_TYPE")
    private String locType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MADD_SUPPLY_DELIVERY_DATE")
    private Date supplyDeliveryDate;

    @Column(name = "MADD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MADD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MADD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MADD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MADD_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "MADD_MAP_ID")
    private MaterialProjDtlEntity materialProjDtlEntity;

    @Column(name = "MADD_SUPPLIER_DOCKET")
    private Boolean supplierDocket = false;
    
    @Column(name = "PROJ_DOCKET_SCH_ID")
    private Long projDocketSchId;
    
    @Column(name = "MAP_FILE_NAME")
    private String fileName;

    @Column(name = "MAP_FILE_TYPE")
    private String fileType;

    @Column(name = "MAP_FILE_SIZE")
    private String fileSize;
    
    @Column(name = "MAP_FILE_KEY")
    private String fileKey;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    public Long getProjDocketSchId() {
        return projDocketSchId;
    }

    public void setProjDocketSchId(Long projDocketSchId) {
        this.projDocketSchId = projDocketSchId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaterialProjDtlEntity getMaterialProjDtlEntity() {
        return materialProjDtlEntity;
    }

    public void setMaterialProjDtlEntity(MaterialProjDtlEntity materialProjDtlEntity) {
        this.materialProjDtlEntity = materialProjDtlEntity;
    }

    public String getDocketNumber() {
        return docketNumber;
    }

    public void setDocketNumber(String docketNumber) {
        this.docketNumber = docketNumber;
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

    public BigDecimal getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(BigDecimal transitQty) {
        this.transitQty = transitQty;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getDefectComments() {
        return defectComments;
    }

    public void setDefectComments(String defectComments) {
        this.defectComments = defectComments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDocketStatus() {
        return docketStatus;
    }

    public void setDocketStatus(String docketStatus) {
        this.docketStatus = docketStatus;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Date getSupplyDeliveryDate() {
        return supplyDeliveryDate;
    }

    public void setSupplyDeliveryDate(Date supplyDeliveryDate) {
        this.supplyDeliveryDate = supplyDeliveryDate;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getSupplierDocket() {
        return supplierDocket;
    }

    public void setSupplierDocket(Boolean supplierDocket) {
        this.supplierDocket = supplierDocket;
    }

    public StockMstrEntity getStockId() {
        return stockId;
    }

    public void setStockId(StockMstrEntity stockId) {
        this.stockId = stockId;
    }

    public ProjStoreStockMstrEntity getProjStockId() {
        return projStockId;
    }

    public void setProjStockId(ProjStoreStockMstrEntity projStockId) {
        this.projStockId = projStockId;
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

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getProjStockCode() {
        return projStockCode;
    }

    public void setProjStockCode(String projStockCode) {
        this.projStockCode = projStockCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
    
    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
    
    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
}
