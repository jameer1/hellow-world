package com.rjtech.procurement.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PurchaseOrderInvoiceDtlTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4695334691102214514L;

    private Long id;
    private Long purId;
    private String invoiceDate;
    private String receivedDate;
    private Integer paymentInDays;
    private String payDueDate;
    private String proposedDueDate;
    private String invoiceNum;
    private BigDecimal netAmount;
    private BigDecimal taxAmount;
    private BigDecimal retainedNetAmount;
    private BigDecimal retainedTaxAmount;
    private Long costId;
    private Integer split;
    private BigDecimal costAmount;
    private String bankName;
    private String accName;
    private String bankCode;
    private Long accNum;
    private Long reqUserId;
    private Long apprUserId;
    private Long financeUserId;
    private String currentStatus;
    private String paymentStatus;
    private Integer accDtlsVerified;
    private String comments;
    private String projCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public Integer getPaymentInDays() {
        return paymentInDays;
    }

    public void setPaymentInDays(Integer paymentInDays) {
        this.paymentInDays = paymentInDays;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getPayDueDate() {
        return payDueDate;
    }

    public void setPayDueDate(String payDueDate) {
        this.payDueDate = payDueDate;
    }

    public String getProposedDueDate() {
        return proposedDueDate;
    }

    public void setProposedDueDate(String proposedDueDate) {
        this.proposedDueDate = proposedDueDate;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getRetainedNetAmount() {
        return retainedNetAmount;
    }

    public void setRetainedNetAmount(BigDecimal retainedNetAmount) {
        this.retainedNetAmount = retainedNetAmount;
    }

    public BigDecimal getRetainedTaxAmount() {
        return retainedTaxAmount;
    }

    public void setRetainedTaxAmount(BigDecimal retainedTaxAmount) {
        this.retainedTaxAmount = retainedTaxAmount;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Integer getSplit() {
        return split;
    }

    public void setSplit(Integer split) {
        this.split = split;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getAccNum() {
        return accNum;
    }

    public void setAccNum(Long accNum) {
        this.accNum = accNum;
    }

    public Integer getAccDtlsVerified() {
        return accDtlsVerified;
    }

    public void setAccDtlsVerified(Integer accDtlsVerified) {
        this.accDtlsVerified = accDtlsVerified;
    }

    public Long getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId) {
        this.reqUserId = reqUserId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getFinanceUserId() {
        return financeUserId;
    }

    public void setFinanceUserId(Long financeUserId) {
        this.financeUserId = financeUserId;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

}