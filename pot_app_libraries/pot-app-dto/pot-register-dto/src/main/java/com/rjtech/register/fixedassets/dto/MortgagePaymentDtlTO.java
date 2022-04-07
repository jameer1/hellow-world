package com.rjtech.register.fixedassets.dto;

public class MortgagePaymentDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 8950084862079755848L;

    private Long id;
    private String effectiveDate;
    private String mortgageeName;
    private String mortgageeAdress;
    private Float originalLoanPrincipalAmount;
    private Float remainingLoanPrinicipalAmount;
    private Float rateOfInterestPerAnnum;
    private String paymentCycle;
    private String paymentCycleDueDate;
    private Float paymentAmountPerCycel;
    private Float taxAmount;
    private Float netAmount;
    private String receiverBank;
    private Long receiverBankCode;
    private Long receiverBankAC;
    private String accountStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getMortgageeName() {
        return mortgageeName;
    }

    public void setMortgageeName(String mortgageeName) {
        this.mortgageeName = mortgageeName;
    }

    public String getMortgageeAdress() {
        return mortgageeAdress;
    }

    public void setMortgageeAdress(String mortgageeAdress) {
        this.mortgageeAdress = mortgageeAdress;
    }

    public Float getOriginalLoanPrincipalAmount() {
        return originalLoanPrincipalAmount;
    }

    public void setOriginalLoanPrincipalAmount(Float originalLoanPrincipalAmount) {
        this.originalLoanPrincipalAmount = originalLoanPrincipalAmount;
    }

    public Float getRemainingLoanPrinicipalAmount() {
        return remainingLoanPrinicipalAmount;
    }

    public void setRemainingLoanPrinicipalAmount(Float remainingLoanPrinicipalAmount) {
        this.remainingLoanPrinicipalAmount = remainingLoanPrinicipalAmount;
    }

    public Float getRateOfInterestPerAnnum() {
        return rateOfInterestPerAnnum;
    }

    public void setRateOfInterestPerAnnum(Float rateOfInterestPerAnnum) {
        this.rateOfInterestPerAnnum = rateOfInterestPerAnnum;
    }

    public String getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(String paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public String getPaymentCycleDueDate() {
        return paymentCycleDueDate;
    }

    public void setPaymentCycleDueDate(String paymentCycleDueDate) {
        this.paymentCycleDueDate = paymentCycleDueDate;
    }

    public Float getPaymentAmountPerCycel() {
        return paymentAmountPerCycel;
    }

    public void setPaymentAmountPerCycel(Float paymentAmountPerCycel) {
        this.paymentAmountPerCycel = paymentAmountPerCycel;
    }    

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Float netAmount) {
        this.netAmount = netAmount;
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public void setReceiverBank(String receiverBank) {
        this.receiverBank = receiverBank;
    }

    public Long getReceiverBankCode() {
        return receiverBankCode;
    }

    public void setReceiverBankCode(Long receiverBankCode) {
        this.receiverBankCode = receiverBankCode;
    }

    public Long getReceiverBankAC() {
        return receiverBankAC;
    }

    public void setReceiverBankAC(Long receiverBankAC) {
        this.receiverBankAC = receiverBankAC;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

}
