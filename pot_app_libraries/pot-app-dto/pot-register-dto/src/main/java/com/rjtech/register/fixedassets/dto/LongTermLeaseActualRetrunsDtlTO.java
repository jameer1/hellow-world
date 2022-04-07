package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LongTermLeaseActualRetrunsDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String date;
    private Long lease;
    private String leaseAgreement;
    private String tenantId;
    private String tenantName;
    private String cumulativeNetRent;
    private Long cumulativeTaxAmount;
    private Long cumulativeNetRentTenant;
    private Long cumulativeTaxAmountTenant;
    private String shortFallRent;
    private String shortFallTax;
    private String modeOfPayment;
    private String receiverBankName;
    private String receiverBankCode;
    private Long receiverBankAC;
    private String transferReceiptNo;
    private Long rentalAmountReceived;
    private Long taxAmountReceived;
    private String docKey;
    private URL docUrl;
    private byte[] uploadMoneyDocument;
    private String uploadMoneyDocumentFileName;
    private Long uploadMoneyDocumentFileSize;
    private String uploadMoneyDocumentFileType;

    public String getUploadMoneyDocumentFileType() {
        return uploadMoneyDocumentFileType;
    }

    public void setUploadMoneyDocumentFileType(String uploadMoneyDocumentFileType) {
        this.uploadMoneyDocumentFileType = uploadMoneyDocumentFileType;
    }

    public Long getUploadMoneyDocumentFileSize() {
        return uploadMoneyDocumentFileSize;
    }

    public void setUploadMoneyDocumentFileSize(Long uploadMoneyDocumentFileSize) {
        this.uploadMoneyDocumentFileSize = uploadMoneyDocumentFileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getLease() {
        return lease;
    }

    public void setLease(Long lease) {
        this.lease = lease;
    }

    public String getLeaseAgreement() {
        return leaseAgreement;
    }

    public void setLeaseAgreement(String leaseAgreement) {
        this.leaseAgreement = leaseAgreement;
    }

    

    public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getCumulativeNetRent() {
        return cumulativeNetRent;
    }

    public void setCumulativeNetRent(String cumulativeNetRent) {
        this.cumulativeNetRent = cumulativeNetRent;
    }

    public Long getCumulativeTaxAmount() {
        return cumulativeTaxAmount;
    }

    public void setCumulativeTaxAmount(Long cumulativeTaxAmount) {
        this.cumulativeTaxAmount = cumulativeTaxAmount;
    }

    public Long getCumulativeNetRentTenant() {
        return cumulativeNetRentTenant;
    }

    public void setCumulativeNetRentTenant(Long cumulativeNetRentTenant) {
        this.cumulativeNetRentTenant = cumulativeNetRentTenant;
    }

    public Long getCumulativeTaxAmountTenant() {
        return cumulativeTaxAmountTenant;
    }

    public void setCumulativeTaxAmountTenant(Long cumulativeTaxAmountTenant) {
        this.cumulativeTaxAmountTenant = cumulativeTaxAmountTenant;
    }

    public String getShortFallRent() {
        return shortFallRent;
    }

    public void setShortFallRent(String shortFallRent) {
        this.shortFallRent = shortFallRent;
    }

    public String getShortFallTax() {
        return shortFallTax;
    }

    public void setShortFallTax(String shortFallTax) {
        this.shortFallTax = shortFallTax;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    public String getReceiverBankCode() {
        return receiverBankCode;
    }

    public void setReceiverBankCode(String receiverBankCode) {
        this.receiverBankCode = receiverBankCode;
    }

    public Long getReceiverBankAC() {
        return receiverBankAC;
    }

    public void setReceiverBankAC(Long receiverBankAC) {
        this.receiverBankAC = receiverBankAC;
    }

    public String getTransferReceiptNo() {
        return transferReceiptNo;
    }

    public void setTransferReceiptNo(String transferReceiptNo) {
        this.transferReceiptNo = transferReceiptNo;
    }

    public Long getRentalAmountReceived() {
        return rentalAmountReceived;
    }

    public void setRentalAmountReceived(Long rentalAmountReceived) {
        this.rentalAmountReceived = rentalAmountReceived;
    }

    public Long getTaxAmountReceived() {
        return taxAmountReceived;
    }

    public void setTaxAmountReceived(Long taxAmountReceived) {
        this.taxAmountReceived = taxAmountReceived;
    }

    public byte[] getUploadMoneyDocument() {
        return uploadMoneyDocument;
    }

    public void setUploadMoneyDocument(byte[] uploadMoneyDocument) {
        this.uploadMoneyDocument = uploadMoneyDocument;
    }

    public String getUploadMoneyDocumentFileName() {
        return uploadMoneyDocumentFileName;
    }

    public void setUploadMoneyDocumentFileName(String uploadMoneyDocumentFileName) {
        this.uploadMoneyDocumentFileName = uploadMoneyDocumentFileName;
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

}
