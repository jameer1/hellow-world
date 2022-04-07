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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "revenue_sales_dtl")
public class RevenueSalesDtlEntity implements Serializable {
    private static final long serialVersionUID = -8795406421033703994L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MortgagePaymentId")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "effectiveDate")
    private Date effectiveDate;

    @Column(name = "buyerName")
    private String buyerName;

    @Column(name = "BUYERADDRESS")
    private String buyerAddress;

    @Column(name = "totalSaleAmount")
    private Float totalSaleAmount;

    @Column(name = "initialDepositAmount")
    private Float initialDepositAmount;

    @Column(name = "remainingSaleAmount")
    private Float remainingSaleAmount;

    @Column(name = "paymentTermsForRemainingAmount")
    private String paymentTermsForRemainingAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "dueDateForSinglePayent")
    private Date dueDateForSinglePayent;

    @Column(name = "numberOfPartPayments")
    private String numberOfPartPayments;

    @Column(name = "paymentCycleForInstallments")
    private String paymentCycleForInstallments;

    @Temporal(TemporalType.DATE)
    @Column(name = "dueDatePerCycle")
    private Date dueDatePerCycle;

    @Temporal(TemporalType.DATE)
    @Column(name = "firstInstallmentDueDate")
    private Date firstInstallmentDueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "lastInstallmentsDueDate")
    private Date lastInstallmentsDueDate;

    @Column(name = "principalAmountPerCycle")
    private Float principalAmountPerCycle;

    @Column(name = "rateOfInterestPerAmount")
    private Float rateOfInterestPerAmount;

    @Column(name = "grossAmountDuePerCycle")
    private Float grossAmountDuePerCycle;

    @Column(name = "payerBankName")
    private String payerBankName;

    @Column(name = "payerBankCode")
    private String payerBankCode;

    @Column(name = "payerBankAccount")
    private Long payerBankAccount;

    @Column(name = "accountStatus")
    private String accountStatus;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getDueDatePerCycle() {
        return dueDatePerCycle;
    }

    public void setDueDatePerCycle(Date dueDatePerCycle) {
        this.dueDatePerCycle = dueDatePerCycle;
    }

    public Date getFirstInstallmentDueDate() {
        return firstInstallmentDueDate;
    }

    public void setFirstInstallmentDueDate(Date firstInstallmentDueDate) {
        this.firstInstallmentDueDate = firstInstallmentDueDate;
    }

    public Date getLastInstallmentsDueDate() {
        return lastInstallmentsDueDate;
    }

    public void setLastInstallmentsDueDate(Date lastInstallmentsDueDate) {
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

    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public Date getDueDateForSinglePayent() {
        return dueDateForSinglePayent;
    }

    public void setDueDateForSinglePayent(Date dueDateForSinglePayent) {
        this.dueDateForSinglePayent = dueDateForSinglePayent;
    }

}
