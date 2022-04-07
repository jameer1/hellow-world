package com.rjtech.register.fixedassets.dto;

import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarParkingTollCollectionDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String date;
    private String modeOfPayment;
    private String bankName;
    private String bankCode;
    private String bankAc;
    private String transferReceiptNo;
    private Long netAmount;
    private Long taxAmount;
    private Long cumulativeNetAmount;
    private Long cumulativeTaxAmount;
    private Long forecastNetAmt;
    private Long forecastTaxAmt;
    private String cumulativeNetRevenue;
    private Long cumulativeTax;
    private byte[] carParkingTollDocuments;
    private String carParkingTollFileName;
    private Long carParkingTollFileSize;
    private String carParkingTollFileType;
    private String docKey;
    private URL docUrl;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAc() {
        return bankAc;
    }

    public void setBankAc(String bankAc) {
        this.bankAc = bankAc;
    }

    public String getTransferReceiptNo() {
        return transferReceiptNo;
    }

    public void setTransferReceiptNo(String transferReceiptNo) {
        this.transferReceiptNo = transferReceiptNo;
    }

    public Long getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Long netAmount) {
        this.netAmount = netAmount;
    }

    public Long getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Long taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Long getCumulativeNetAmount() {
        return cumulativeNetAmount;
    }

    public void setCumulativeNetAmount(Long cumulativeNetAmount) {
        this.cumulativeNetAmount = cumulativeNetAmount;
    }

    public Long getCumulativeTaxAmount() {
        return cumulativeTaxAmount;
    }

    public void setCumulativeTaxAmount(Long cumulativeTaxAmount) {
        this.cumulativeTaxAmount = cumulativeTaxAmount;
    }

    public Long getForecastNetAmt() {
        return forecastNetAmt;
    }

    public void setForecastNetAmt(Long forecastNetAmt) {
        this.forecastNetAmt = forecastNetAmt;
    }

    public Long getForecastTaxAmt() {
        return forecastTaxAmt;
    }

    public void setForecastTaxAmt(Long forecastTaxAmt) {
        this.forecastTaxAmt = forecastTaxAmt;
    }

    public String getCumulativeNetRevenue() {
        return cumulativeNetRevenue;
    }

    public void setCumulativeNetRevenue(String cumulativeNetRevenue) {
        this.cumulativeNetRevenue = cumulativeNetRevenue;
    }

    public Long getCumulativeTax() {
        return cumulativeTax;
    }

    public void setCumulativeTax(Long cumulativeTax) {
        this.cumulativeTax = cumulativeTax;
    }

    public byte[] getCarParkingTollDocuments() {
        return carParkingTollDocuments;
    }

    public void setCarParkingTollDocuments(byte[] carParkingTollDocuments) {
        this.carParkingTollDocuments = carParkingTollDocuments;
    }

    public String getCarParkingTollFileName() {
        return carParkingTollFileName;
    }

    public void setCarParkingTollFileName(String carParkingTollFileName) {
        this.carParkingTollFileName = carParkingTollFileName;
    }

    public Long getCarParkingTollFileSize() {
        return carParkingTollFileSize;
    }

    public void setCarParkingTollFileSize(Long carParkingTollFileSize) {
        this.carParkingTollFileSize = carParkingTollFileSize;
    }

    public String getCarParkingTollFileType() {
        return carParkingTollFileType;
    }

    public void setCarParkingTollFileType(String carParkingTollFileType) {
        this.carParkingTollFileType = carParkingTollFileType;
    }

    public URL getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(URL docUrl) {
        this.docUrl = docUrl;
    }

}
