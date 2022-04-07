package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesRecordsDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 5048003796093964935L;
    private Long id;
    private String dateOfSale;
    private String saleType;
    private String buyerName;
    private String buyerAddress;
    private String landRegisterOfficeDetails;
    private Double landValudation;
    private Double structureValuation;
    private Double plantEquipmentValutaion;
    private Double totalValuation;
    private Double totalSalesAmount;
    private String buyerSolicitor;
    private String vendorSolicitor;
    private byte[] salesRecordsDetails;
    private String salesRecordsDetailsFileName;
    private Long salesRecordsDetailsDocumentFileSize;
    private String salesRecordsDetailsFileType;
    private String docKey;
    private URL docUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
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

    public byte[] getSalesRecordsDetails() {
        return salesRecordsDetails;
    }

    public void setSalesRecordsDetails(byte[] salesRecordsDetails) {
        this.salesRecordsDetails = salesRecordsDetails;
    }

    public String getSalesRecordsDetailsFileName() {
        return salesRecordsDetailsFileName;
    }

    public void setSalesRecordsDetailsFileName(String salesRecordsDetailsFileName) {
        this.salesRecordsDetailsFileName = salesRecordsDetailsFileName;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }
    
    public String toString() {
    	return "landValudation:"+landValudation+" structureValuation:"+structureValuation+" plantEquipmentValutaion:"+plantEquipmentValutaion+" totalValuation:"+totalValuation+" totalSalesAmount:"+totalSalesAmount;
    }
}
