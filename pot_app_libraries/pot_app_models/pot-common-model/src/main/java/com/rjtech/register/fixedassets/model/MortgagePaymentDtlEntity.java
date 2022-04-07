package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "mortgage_payment_dtl")
public class MortgagePaymentDtlEntity implements Serializable {
    private static final long serialVersionUID = -8795406421033703994L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MortgagePaymentId")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVEDATE")
    private Date effectiveDate;

    @Column(name = "mortgageeName")
    private String mortgageeName;

    @Column(name = "mortgageeAdress")
    private String mortgageeAdress;

    @Column(name = "originalLoanPrincipalAmount")
    private Float originalLoanPrincipalAmount;

    @Column(name = "remainingLoanPrinicipalAmount")
    private Float remainingLoanPrinicipalAmount;

    @Column(name = "rateOfInterestPerAnnum")
    private Float rateOfInterestPerAnnum;

    @Column(name = "paymentCycle")
    private String paymentCycle;

  //  @Temporal(TemporalType.DATE)
    @Column(name = "paymentCycleDueDate")
    private String paymentCycleDueDate;

    @Column(name = "paymentAmountPerCycel")
    private Float paymentAmountPerCycel;
    
    @Column(name = "taxAmount")
    private Float taxAmount;
    
    @Column(name = "netAmount")
    private Float netAmount;

    @Column(name = "receiverBank")
    private String receiverBank;

    @Column(name = "receiverBankCode")
    private Long receiverBankCode;

    @Column(name = "receiverBankAC")
    private Long receiverBankAC;

    @Column(name = "accountStatus")
    private String accountStatus;

    @ManyToOne
    @JoinColumn(name = "FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserMstrEntity createdBy;

    @Column(name = "CREATED_ON", updatable = false)
    private Timestamp createdOn;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserMstrEntity updatedBy;

    @Column(name = "UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "STATUS")
    private Integer status;

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

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

}
