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
@Table(name = "fixedasset_purchase_records_dtl")
public class FixedAssetPurchaseRecordsDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FPR_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPR_DATEOFPURCHASE")
    private Date dateOfPurchase;

    @Column(name = "FPR_PURCHASE_TYPE")
    private String purchaseType;

    @Column(name = "FPR_VENDOR_NAME")
    private String vendorName;

    @Column(name = "FPR_VENDOR_ADDRESS")
    private String vendorAddress;

    @Column(name = "FPR_LAND_SURVEY_LOT_DETAILS")
    private String landSurveyLotDetails;

    @Column(name = "FPR_LOT_IDENTIFICATION_DETAILS")
    private String lotIdentificationDetails;

    @Column(name = "FPR_LAND_SIZE_DIMENSIONS")
    private String landSizeAndDimensions;

    @Column(name = "FPR_STRUCTURE_DETAILS")
    private String structureDetails;

    @Column(name = "FPR_PLANT_EQUIPMENT_DETAILS")
    private String plantAndEquipmentDetails;

    @Column(name = "FPR_LAND_REGISTOR_OFFICE_DETAILS")
    private String landRegisterOfficeDetails;

    @Column(name = "FPR_LAND_VALUATION")
    private Float landValuation;

    @Column(name = "FPR_STRUCTURE_VALUATION")
    private Float structureValuation;

    @Column(name = "FPR_PLANT_EQUITPMENT_VALUATION")
    private Float plantAndEquipmentValuation;

    @Column(name = "FPR_TOTAL_VALUATION")
    private Float totalValuation;

    @Column(name = "FPR_TOTAL_PURCHASE_AMOUNT")
    private Float totalPurachaseAmount;

    @Column(name = "FPR_INITIAL_MARGIN_AMOUNT_PAID")
    private Float initialMarginAmountPaid;
	
	@Column(name = "FPR_TAX_AMOUNT_WITH_HELD")
    private Float taxAmountWithHeld;

    @Column(name = "FPR_LOAN_AMOUNT")
    private Float loanAmount;

    @Column(name = "FPR_LOAN_AVAILED_FROM")
    private String loanAvailedFrom;

    @Column(name = "FPR_LOAN_PERIOD")
    private String loanPeriod;

    @Column(name = "FPR_RATE_TYPES")
    private String fixedRateOfInterest;

    @Column(name = "FPR_ANNUAL_RATEOF_INTEREST")
    private Float annualRateOfInterest;

    @Column(name = "FPR_BUYER_SOLICITOR")
    private String buyerSolicitor;

    @Column(name = "FPR_VENDOR_SOLICITOR")
    private String vendorSolicitor;

    @Column(name = "FPR_PURCHASE_RECORDS_UPLOAD")
    private String purchaseRecordsUpload;

    @Column(name = "FPR_PURCHASE_RECORDS_DETAILS_FILE_NAME")
    private String purchaseRecordsDetailsFileName;

    @Column(name = "FPR_PURCHASE_RECORDS_DETAILS_FILE_SIZE")
    private Long purchaseRecordsDetailsFileSize;

    @Column(name = "FPR_PURCHASE_RECORDS_DETAILS_FILE_TYPE")
    private String purchaseRecordsDetailsFileType;

    @ManyToOne
    @JoinColumn(name = "FPR_CREATED_BY")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPR_CREATED_ON")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FPR__UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPR_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "FPR_PAR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FPR_FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "FPR_DOC_KEY")
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

    public Float getInitialMarginAmountPaid() {
        return initialMarginAmountPaid;
    }

    public void setInitialMarginAmountPaid(Float initialMarginAmountPaid) {
        this.initialMarginAmountPaid = initialMarginAmountPaid;
    }
	
	public Float getTaxAmountWithHeld() {
        return taxAmountWithHeld;
    }

    public void setTaxAmountWithHeld(Float taxAmountWithHeld) {
        this.taxAmountWithHeld = taxAmountWithHeld;
    }

    public Float getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Float loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAvailedFrom() {
        return loanAvailedFrom;
    }

    public void setLoanAvailedFrom(String loanAvailedFrom) {
        this.loanAvailedFrom = loanAvailedFrom;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    
    public String getFixedRateOfInterest() {
        return fixedRateOfInterest;
    }

    public void setFixedRateOfInterest(String fixedRateOfInterest) {
        this.fixedRateOfInterest = fixedRateOfInterest;
    }

    public Float getAnnualRateOfInterest() {
        return annualRateOfInterest;
    }

    public void setAnnualRateOfInterest(Float annualRateOfInterest) {
        this.annualRateOfInterest = annualRateOfInterest;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getLandSurveyLotDetails() {
        return landSurveyLotDetails;
    }

    public void setLandSurveyLotDetails(String landSurveyLotDetails) {
        this.landSurveyLotDetails = landSurveyLotDetails;
    }

    public String getLotIdentificationDetails() {
        return lotIdentificationDetails;
    }

    public void setLotIdentificationDetails(String lotIdentificationDetails) {
        this.lotIdentificationDetails = lotIdentificationDetails;
    }

    public String getLandSizeAndDimensions() {
        return landSizeAndDimensions;
    }

    public void setLandSizeAndDimensions(String landSizeAndDimensions) {
        this.landSizeAndDimensions = landSizeAndDimensions;
    }

    public String getStructureDetails() {
        return structureDetails;
    }

    public void setStructureDetails(String structureDetails) {
        this.structureDetails = structureDetails;
    }

    public String getPlantAndEquipmentDetails() {
        return plantAndEquipmentDetails;
    }

    public void setPlantAndEquipmentDetails(String plantAndEquipmentDetails) {
        this.plantAndEquipmentDetails = plantAndEquipmentDetails;
    }

    public String getLandRegisterOfficeDetails() {
        return landRegisterOfficeDetails;
    }

    public void setLandRegisterOfficeDetails(String landRegisterOfficeDetails) {
        this.landRegisterOfficeDetails = landRegisterOfficeDetails;
    }

    public Float getLandValuation() {
        return landValuation;
    }

    public void setLandValuation(Float landValuation) {
        this.landValuation = landValuation;
    }

    public Float getStructureValuation() {
        return structureValuation;
    }

    public void setStructureValuation(Float structureValuation) {
        this.structureValuation = structureValuation;
    }

    public Float getPlantAndEquipmentValuation() {
        return plantAndEquipmentValuation;
    }

    public void setPlantAndEquipmentValuation(Float plantAndEquipmentValuation) {
        this.plantAndEquipmentValuation = plantAndEquipmentValuation;
    }

    public Float getTotalValuation() {
        return totalValuation;
    }

    public void setTotalValuation(Float totalValuation) {
        this.totalValuation = totalValuation;
    }

    public Float getTotalPurachaseAmount() {
        return totalPurachaseAmount;
    }

    public void setTotalPurachaseAmount(Float totalPurachaseAmount) {
        this.totalPurachaseAmount = totalPurachaseAmount;
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

    public String getPurchaseRecordsUpload() {
        return purchaseRecordsUpload;
    }

    public void setPurchaseRecordsUpload(String purchaseRecordsUpload) {
        this.purchaseRecordsUpload = purchaseRecordsUpload;
    }

    public String getPurchaseRecordsDetailsFileName() {
        return purchaseRecordsDetailsFileName;
    }

    public void setPurchaseRecordsDetailsFileName(String purchaseRecordsDetailsFileName) {
        this.purchaseRecordsDetailsFileName = purchaseRecordsDetailsFileName;
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

    public Long getPurchaseRecordsDetailsFileSize() {
        return purchaseRecordsDetailsFileSize;
    }

    public void setPurchaseRecordsDetailsFileSize(Long purchaseRecordsDetailsFileSize) {
        this.purchaseRecordsDetailsFileSize = purchaseRecordsDetailsFileSize;
    }

    public String getPurchaseRecordsDetailsFileType() {
        return purchaseRecordsDetailsFileType;
    }

    public void setPurchaseRecordsDetailsFileType(String purchaseRecordsDetailsFileType) {
        this.purchaseRecordsDetailsFileType = purchaseRecordsDetailsFileType;
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

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
    
    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
}
