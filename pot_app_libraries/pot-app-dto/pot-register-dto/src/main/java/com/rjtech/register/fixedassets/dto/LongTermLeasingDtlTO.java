package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LongTermLeasingDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 5048003796093964935L;

    private Long id;
    private String leaseAgreement;
    private String leaseStart;
    private String leaseFinish;
    private String tenantName;
    private String tenantAddress;
    private String paymentCycle;
    private Float netRentAmountPerCycle;
    private String maintenanceCharges;
    private Float assetMaintenanceCharges;
    private Float taxableAmount;
    private Float tax;
    private Float taxAmount;
    private Float grossRent;
    private String paymentCycleDueDate;
    private String leaseExtendedFinshDate;
    private String leaseActualFinishFinshDate;

    private byte[] leaseDocumentDetails;
    private String leaseDocumentDetailsFileName;
    private Long leaseDocumentDetailsFileSize;
    private String leaseDocumentDetailsFileType;
    private String docKey;
    private URL docUrl;
    private String currentStatus;
    private String tenantid;

        
    public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public Long getLeaseDocumentDetailsFileSize() {
        return leaseDocumentDetailsFileSize;
    }

    public void setLeaseDocumentDetailsFileSize(Long leaseDocumentDetailsFileSize) {
        this.leaseDocumentDetailsFileSize = leaseDocumentDetailsFileSize;
    }

    public String getLeaseDocumentDetailsFileType() {
        return leaseDocumentDetailsFileType;
    }

    public void setLeaseDocumentDetailsFileType(String leaseDocumentDetailsFileType) {
        this.leaseDocumentDetailsFileType = leaseDocumentDetailsFileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeaseAgreement() {
        return leaseAgreement;
    }

    public void setLeaseAgreement(String leaseAgreement) {
        this.leaseAgreement = leaseAgreement;
    }

    public String getLeaseStart() {
        return leaseStart;
    }

    public void setLeaseStart(String leaseStart) {
        this.leaseStart = leaseStart;
    }

    public String getLeaseFinish() {
        return leaseFinish;
    }

    public void setLeaseFinish(String leaseFinish) {
        this.leaseFinish = leaseFinish;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

    public String getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(String paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public Float getNetRentAmountPerCycle() {
        return netRentAmountPerCycle;
    }

    public void setNetRentAmountPerCycle(Float netRentAmountPerCycle) {
        this.netRentAmountPerCycle = netRentAmountPerCycle;
    }

    public String getMaintenanceCharges() {
        return maintenanceCharges;
    }

    public void setMaintenanceCharges(String maintenanceCharges) {
        this.maintenanceCharges = maintenanceCharges;
    }

    public Float getAssetMaintenanceCharges() {
        return assetMaintenanceCharges;
    }

    public void setAssetMaintenanceCharges(Float assetMaintenanceCharges) {
        this.assetMaintenanceCharges = assetMaintenanceCharges;
    }

    public Float getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(Float taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getGrossRent() {
        return grossRent;
    }

    public void setGrossRent(Float grossRent) {
        this.grossRent = grossRent;
    }

    public String getPaymentCycleDueDate() {
        return paymentCycleDueDate;
    }

    public void setPaymentCycleDueDate(String paymentCycleDueDate) {
        this.paymentCycleDueDate = paymentCycleDueDate;
    }

    public String getLeaseExtendedFinshDate() {
        return leaseExtendedFinshDate;
    }

    public void setLeaseExtendedFinshDate(String leaseExtendedFinshDate) {
        this.leaseExtendedFinshDate = leaseExtendedFinshDate;
    }

    public String getLeaseActualFinishFinshDate() {
        return leaseActualFinishFinshDate;
    }

    public void setLeaseActualFinishFinshDate(String leaseActualFinishFinshDate) {
        this.leaseActualFinishFinshDate = leaseActualFinishFinshDate;
    }

    public byte[] getLeaseDocumentDetails() {
        return leaseDocumentDetails;
    }

    public void setLeaseDocumentDetails(byte[] leaseDocumentDetails) {
        this.leaseDocumentDetails = leaseDocumentDetails;
    }

    public String getLeaseDocumentDetailsFileName() {
        return leaseDocumentDetailsFileName;
    }

    public void setLeaseDocumentDetailsFileName(String leaseDocumentDetailsFileName) {
        this.leaseDocumentDetailsFileName = leaseDocumentDetailsFileName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }
}
