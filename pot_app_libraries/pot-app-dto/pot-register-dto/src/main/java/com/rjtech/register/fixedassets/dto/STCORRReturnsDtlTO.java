package com.rjtech.register.fixedassets.dto;

import java.net.URL;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class STCORRReturnsDtlTO extends SubAssetDtlTO {

    private static final long serialVersionUID = 8950084862079755848L;

    private Long id;
    private String fromDate;
    private String toDate;
    private String startDate;
    private String finishDate;
   	private String tenantName;
    private String tenantRegisteredAddress;
    private String emailAddrees;
    private Long tenantPhoneNumber;
    private String checkIn;
    private String checkOut;
    private Long noOfDays;
    private Long dailyNetRent;
    private Long tax;
    private Long rentAmountReceivable;
    private Long taxAmount;
    private Long grossAmount;
    private String refundofRemainingAdvanceamount;
    private Long netTaxAmountReceived;
    private Long netAmountOfRentRecived;
    private byte[] tenantRecordDetails;
    private String tenantRecordDetailsFileName;
    private Long tenantRecordDetailsFileSize;
    private String tenantRecordDetailsFileType;
    private String currentStatus;
    private String docKey;
    private Long advancePaid;
    private Long subsequentRentalRecepits;
    private List<AdvanceRentalRecepitsDtlTO> advancePaidList;
    private List<SubSequentRentalRecepitsDtlTO> subSequentRentalRecepitsList;
    private URL docUrl;
    private String tenantId;
    
    public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getTenantRecordDetailsFileSize() {
        return tenantRecordDetailsFileSize;
    }

    public void setTenantRecordDetailsFileSize(Long tenantRecordDetailsFileSize) {
        this.tenantRecordDetailsFileSize = tenantRecordDetailsFileSize;
    }

    public String getTenantRecordDetailsFileType() {
        return tenantRecordDetailsFileType;
    }

    public void setTenantRecordDetailsFileType(String tenantRecordDetailsFileType) {
        this.tenantRecordDetailsFileType = tenantRecordDetailsFileType;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantRegisteredAddress() {
        return tenantRegisteredAddress;
    }

    public void setTenantRegisteredAddress(String tenantRegisteredAddress) {
        this.tenantRegisteredAddress = tenantRegisteredAddress;
    }

    public String getEmailAddrees() {
        return emailAddrees;
    }

    public void setEmailAddrees(String emailAddrees) {
        this.emailAddrees = emailAddrees;
    }

    public Long getTenantPhoneNumber() {
        return tenantPhoneNumber;
    }

    public void setTenantPhoneNumber(Long tenantPhoneNumber) {
        this.tenantPhoneNumber = tenantPhoneNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Long getDailyNetRent() {
        return dailyNetRent;
    }

    public void setDailyNetRent(Long dailyNetRent) {
        this.dailyNetRent = dailyNetRent;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public Long getRentAmountReceivable() {
        return rentAmountReceivable;
    }

    public void setRentAmountReceivable(Long rentAmountReceivable) {
        this.rentAmountReceivable = rentAmountReceivable;
    }

    public Long getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Long taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Long getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Long grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getRefundofRemainingAdvanceamount() {
        return refundofRemainingAdvanceamount;
    }

    public void setRefundofRemainingAdvanceamount(String refundofRemainingAdvanceamount) {
        this.refundofRemainingAdvanceamount = refundofRemainingAdvanceamount;
    }

    public Long getNetTaxAmountReceived() {
        return netTaxAmountReceived;
    }

    public void setNetTaxAmountReceived(Long netTaxAmountReceived) {
        this.netTaxAmountReceived = netTaxAmountReceived;
    }

    public Long getNetAmountOfRentRecived() {
        return netAmountOfRentRecived;
    }

    public void setNetAmountOfRentRecived(Long netAmountOfRentRecived) {
        this.netAmountOfRentRecived = netAmountOfRentRecived;
    }

    public byte[] getTenantRecordDetails() {
        return tenantRecordDetails;
    }

    public void setTenantRecordDetails(byte[] tenantRecordDetails) {
        this.tenantRecordDetails = tenantRecordDetails;
    }

    public String getTenantRecordDetailsFileName() {
        return tenantRecordDetailsFileName;
    }

    public void setTenantRecordDetailsFileName(String tenantRecordDetailsFileName) {
        this.tenantRecordDetailsFileName = tenantRecordDetailsFileName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public Long getAdvancePaid() {
        return advancePaid;
    }

    public void setAdvancePaid(Long advancePaid) {
        this.advancePaid = advancePaid;
    }

    public Long getSubsequentRentalRecepits() {
        return subsequentRentalRecepits;
    }

    public void setSubsequentRentalRecepits(Long subsequentRentalRecepits) {
        this.subsequentRentalRecepits = subsequentRentalRecepits;
    }

    public List<AdvanceRentalRecepitsDtlTO> getAdvancePaidList() {
        return advancePaidList;
    }

    public void setAdvancePaidList(List<AdvanceRentalRecepitsDtlTO> advancePaidList) {
        this.advancePaidList = advancePaidList;
    }

    public List<SubSequentRentalRecepitsDtlTO> getSubSequentRentalRecepitsList() {
        return subSequentRentalRecepitsList;
    }

    public void setSubSequentRentalRecepitsList(List<SubSequentRentalRecepitsDtlTO> subSequentRentalRecepitsList) {
        this.subSequentRentalRecepitsList = subSequentRentalRecepitsList;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }
}
