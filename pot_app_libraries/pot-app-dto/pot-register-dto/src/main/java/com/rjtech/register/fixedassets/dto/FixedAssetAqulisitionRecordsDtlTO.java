package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixedAssetAqulisitionRecordsDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String dateOfPurchase;
    private String purchaseType;
    private String vendorName;
    private String vendorAddress;
    private String landSurveyLotDetails;
    private String lotIdentificationDetails;
    private String landSizeAndDimesions;
    private String structureDetails;
    private String plantAndEquipmentDetails;
    private String landRegisterOfficeDetails;
    private Float landValuation;
    private Float structureValuation;
    private Float plantAndEquipmentValuation;
    private Float totalValuation;
    private Float totalPurchaseAmount;
	private Float taxAmountWithHeld;
    private Float initialMarginAmountPaid;
    private Float loanAmount;
    private String loanAvailedFrom;
    private String loanPeriod;
    private String fixedRateOfInterest;
    private Float annualRateOfInterest;
    private String buyerSolicitor;
    private String vendorSolicitor;
    private String isLatest;
    private byte[] prachaseRecordDetails;
    private String purchaseRecordsDetailsFileName;
    private Long purchaseRecordsDetailsDocumentFileSize;
    private String purchaseRecordsDetailsFileType;
    private String docKey;
    private URL docUrl;

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getPurchaseRecordsDetailsFileName() {
        return purchaseRecordsDetailsFileName;
    }

    public void setPurchaseRecordsDetailsFileName(String purchaseRecordsDetailsFileName) {
        this.purchaseRecordsDetailsFileName = purchaseRecordsDetailsFileName;
    }

    public Long getPurchaseRecordsDetailsDocumentFileSize() {
        return purchaseRecordsDetailsDocumentFileSize;
    }

    public void setPurchaseRecordsDetailsDocumentFileSize(Long purchaseRecordsDetailsDocumentFileSize) {
        this.purchaseRecordsDetailsDocumentFileSize = purchaseRecordsDetailsDocumentFileSize;
    }

    public String getPurchaseRecordsDetailsFileType() {
        return purchaseRecordsDetailsFileType;
    }

    public void setPurchaseRecordsDetailsFileType(String purchaseRecordsDetailsFileType) {
        this.purchaseRecordsDetailsFileType = purchaseRecordsDetailsFileType;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
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

    public String getLandSizeAndDimesions() {
        return landSizeAndDimesions;
    }

    public void setLandSizeAndDimesions(String landSizeAndDimesions) {
        this.landSizeAndDimesions = landSizeAndDimesions;
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

    public String getLotIdentificationDetails() {
        return lotIdentificationDetails;
    }

    public void setLotIdentificationDetails(String lotIdentificationDetails) {
        this.lotIdentificationDetails = lotIdentificationDetails;
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

    public Float getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(Float totalPurchaseAmount) {
        this.totalPurchaseAmount = totalPurchaseAmount;
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

    public byte[] getPrachaseRecordDetails() {
        return prachaseRecordDetails;
    }

    public void setPrachaseRecordDetails(byte[] prachaseRecordDetails) {
        this.prachaseRecordDetails = prachaseRecordDetails;
    }

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }
}
