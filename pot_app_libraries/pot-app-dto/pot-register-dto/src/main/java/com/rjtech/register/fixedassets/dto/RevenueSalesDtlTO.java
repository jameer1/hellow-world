package com.rjtech.register.fixedassets.dto;

public class RevenueSalesDtlTO extends FixedAssetDtlTO {

    private static final long serialVersionUID = 8950084862079755848L;

    private Long id;
    private String effectiveDate;
    private String buyerName;
    private String buyerAddress;
    private Float totalSaleAmount;
    private Float initialDepositAmount;
    private Float remainingSaleAmount;
    private String paymentTermsForRemainingAmount;
    private String dueDateForSinglePayent;
    private String numberOfPartPayments;
    private String paymentCycleForInstallments;
    private String dueDatePerCycle;
    private String firstInstallmentDueDate;
    private String lastInstallmentsDueDate;
    private Float principalAmountPerCycle;
    private Float rateOfInterestPerAmount;
    private Float grossAmountDuePerCycle;
    private String payerBankName;
    private String payerBankCode;
    private Long payerBankAccount;
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

    public Float getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(Float totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }

    public Float getInitialDepositAmount() {
        return initialDepositAmount;
    }

    public void setInitialDepositAmount(Float initialDepositAmount) {
        this.initialDepositAmount = initialDepositAmount;
    }

    public Float getRemainingSaleAmount() {
        return remainingSaleAmount;
    }

    public void setRemainingSaleAmount(Float remainingSaleAmount) {
        this.remainingSaleAmount = remainingSaleAmount;
    }

    public String getPaymentTermsForRemainingAmount() {
        return paymentTermsForRemainingAmount;
    }

    public void setPaymentTermsForRemainingAmount(String paymentTermsForRemainingAmount) {
        this.paymentTermsForRemainingAmount = paymentTermsForRemainingAmount;
    }

    public String getDueDateForSinglePayent() {
        return dueDateForSinglePayent;
    }

    public void setDueDateForSinglePayent(String dueDateForSinglePayent) {
        this.dueDateForSinglePayent = dueDateForSinglePayent;
    }

    public String getNumberOfPartPayments() {
        return numberOfPartPayments;
    }

    public void setNumberOfPartPayments(String numberOfPartPayments) {
        this.numberOfPartPayments = numberOfPartPayments;
    }   

    public String getPaymentCycleForInstallments() {
        return paymentCycleForInstallments;
    }

    public void setPaymentCycleForInstallments(String paymentCycleForInstallments) {
        this.paymentCycleForInstallments = paymentCycleForInstallments;
    }

    public String getDueDatePerCycle() {
        return dueDatePerCycle;
    }

    public void setDueDatePerCycle(String dueDatePerCycle) {
        this.dueDatePerCycle = dueDatePerCycle;
    }

    public String getFirstInstallmentDueDate() {
        return firstInstallmentDueDate;
    }

    public void setFirstInstallmentDueDate(String firstInstallmentDueDate) {
        this.firstInstallmentDueDate = firstInstallmentDueDate;
    }

    public String getLastInstallmentsDueDate() {
        return lastInstallmentsDueDate;
    }

    public void setLastInstallmentsDueDate(String lastInstallmentsDueDate) {
        this.lastInstallmentsDueDate = lastInstallmentsDueDate;
    }

    public Float getPrincipalAmountPerCycle() {
        return principalAmountPerCycle;
    }

    public void setPrincipalAmountPerCycle(Float principalAmountPerCycle) {
        this.principalAmountPerCycle = principalAmountPerCycle;
    }

    public Float getRateOfInterestPerAmount() {
        return rateOfInterestPerAmount;
    }

    public void setRateOfInterestPerAmount(Float rateOfInterestPerAmount) {
        this.rateOfInterestPerAmount = rateOfInterestPerAmount;
    }

    public Float getGrossAmountDuePerCycle() {
        return grossAmountDuePerCycle;
    }

    public void setGrossAmountDuePerCycle(Float grossAmountDuePerCycle) {
        this.grossAmountDuePerCycle = grossAmountDuePerCycle;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerBankCode() {
        return payerBankCode;
    }

    public void setPayerBankCode(String payerBankCode) {
        this.payerBankCode = payerBankCode;
    }

    public Long getPayerBankAccount() {
        return payerBankAccount;
    }

    public void setPayerBankAccount(Long payerBankAccount) {
        this.payerBankAccount = payerBankAccount;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

}
