package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "fixedasset_sales_records_dtl")
public class SalesRecordDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FSRD_SALES_RECORD_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSRD_DATE_OF_SALE")
    private Date dateOfSale;

    @Column(name = "FSRD_SALE_TYPE")
    private String saleType;

    @Column(name = "FSRD_BUYER_NAME")
    private String buyerName;

    @Column(name = "FSRD_BUYER_ADDRESS")
    private String buyerAddress;

    @Column(name = "FSRD_LAND_REG_OFFICE_DETAILS")
    private String landRegisterOfficeDetails;

    @Column(name = "FSRD_LAND_VALUTION")
    private Double landValudation;

    @Column(name = "FSRD_STR_VALUTION")
    private Double structureValuation;

    @Column(name = "FSRD_PLT_EQP_VALUTION")
    private Double plantEquipmentValutaion;

    @Column(name = "FSRD_TOTAL_VALUTION")
    private Double totalValuation;

    @Column(name = "FSRD_TOTAL_SALE_AMT")
    private Double totalSalesAmount;

    @Column(name = "FSRD_BUYER_SOLICITOR")
    private String buyerSolicitor;

    @Column(name = "FSRD_VENDOR_SOLICITOR")
    private String vendorSolicitor;

    @Column(name = "FSRD_SALE_REC_DETAILS_FILE_NAME")
    private String salesRecordsDetailsFileName;

    @Column(name = "FSRD_SALE_REC_DETAILS_FILE_SIZE")
    private Long salesRecordsDetailsDocumentFileSize;

    @Column(name = "FSRD_SALE_REC_DETAILS_FILE_TYPE")
    private String salesRecordsDetailsFileType;

    @ManyToOne
    @JoinColumn(name = "FSRD_FIXED_ASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "FSRD_CLIENT_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "FSRD_CREATED_BY")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSRD_CREATED_ON")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FSRD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSRD_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "FSRD_STATUS")
    private Integer status;

    @Column(name = "FSRD_DOC_KEY")
    private String docKey;

    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getLandRegisterOfficeDetails() {
        return landRegisterOfficeDetails;
    }

    public void setLandRegisterOfficeDetails(String landRegisterOfficeDetails) {
        this.landRegisterOfficeDetails = landRegisterOfficeDetails;
    }

    public Double getLandValudation() {
        return landValudation;
    }

    public void setLandValudation(Double landValudation) {
        this.landValudation = landValudation;
    }

    public Double getStructureValuation() {
        return structureValuation;
    }

    public void setStructureValuation(Double structureValuation) {
        this.structureValuation = structureValuation;
    }

    public Double getPlantEquipmentValutaion() {
        return plantEquipmentValutaion;
    }

    public void setPlantEquipmentValutaion(Double plantEquipmentValutaion) {
        this.plantEquipmentValutaion = plantEquipmentValutaion;
    }

    public Double getTotalValuation() {
        return totalValuation;
    }

    public void setTotalValuation(Double totalValuation) {
        this.totalValuation = totalValuation;
    }

    public Double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(Double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public String getBuyerSolicitor() {
        return buyerSolicitor;
    }

    public void setBuyerSolicitor(String buyerSolicitor) {
        this.buyerSolicitor = buyerSolicitor;
    }

    public String getVendorSolicitor() {
        return vendorSolicitor;
    }

    public void setVendorSolicitor(String vendorSolicitor) {
        this.vendorSolicitor = vendorSolicitor;
    }

    public String getSalesRecordsDetailsFileName() {
        return salesRecordsDetailsFileName;
    }

    public void setSalesRecordsDetailsFileName(String salesRecordsDetailsFileName) {
        this.salesRecordsDetailsFileName = salesRecordsDetailsFileName;
    }

    public Long getSalesRecordsDetailsDocumentFileSize() {
        return salesRecordsDetailsDocumentFileSize;
    }

    public void setSalesRecordsDetailsDocumentFileSize(Long salesRecordsDetailsDocumentFileSize) {
        this.salesRecordsDetailsDocumentFileSize = salesRecordsDetailsDocumentFileSize;
    }

    public String getSalesRecordsDetailsFileType() {
        return salesRecordsDetailsFileType;
    }

    public void setSalesRecordsDetailsFileType(String salesRecordsDetailsFileType) {
        this.salesRecordsDetailsFileType = salesRecordsDetailsFileType;
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
    
    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
    
    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
    
    public String toString() {
    	return "id:"+this.id+" landValudation:"+this.landValudation+" totalSalesAmount:"+this.totalSalesAmount+" plantEquipmentValutaion:"+this.plantEquipmentValutaion+" totalValuation:"+this.totalValuation;
    }
}
