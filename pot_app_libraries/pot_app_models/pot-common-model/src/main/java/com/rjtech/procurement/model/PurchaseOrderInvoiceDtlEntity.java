package com.rjtech.procurement.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.copy.ProjCostItemEntityCopy;

@Entity
@Table(name = "purchase_order_invoice_dtl")
public class PurchaseOrderInvoiceDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POIN_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POIN_PUR_ID")
    private PurchaseOrderEntity purId;

    @ManyToOne
    @JoinColumn(name = "POIN_EPM_ID")
    private ProjMstrEntity projId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POIN_INVOICE_DATE")
    private Date invoiceDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POIN_RECEIVED_DATE")
    private Date receivedDate;

    @Column(name = "POIN_PAYMENT_DAYS")
    private Integer paymentInDays;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POIN_PAY_DUE_DATE")
    private Date payDueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POIN_PROPOSED_DUE_DATE")
    private Date proposedDueDate;

    // TODO : delete this column (used for storing code)
    @Column(name = "POIN_INVOICE_NUM")
    private String invoiceNum;

    @Column(name = "POIN_NET_AMOUNT")
    private BigDecimal netAmount;

    @Column(name = "POIN_TAX_AMOUNT")
    private BigDecimal taxAmount;

    @Column(name = "POIN_RETAINED_NET_AMOUNT")
    private BigDecimal retainedNetAmount;

    @Column(name = "POIN_RETAINED_TAX_AMOUNT")
    private BigDecimal retainedTaxAmount;

    @ManyToOne
    @JoinColumn(name = "POIN_COST_ID")
    private ProjCostItemEntity costId;

    @ManyToOne
    @JoinColumn(name = "POIN_FINANCE_USER_ID")
    private UserMstrEntity financeUserId;

    @Column(name = "POIN_SPLIT")
    private Integer split;

    @Column(name = "POIN_COST_AMOUNT")
    private BigDecimal costAmount;

    @Column(name = "POIN_BANK_NAME")
    private String bankName;

    @Column(name = "POIN_ACC_NAME")
    private String accName;

    @Column(name = "POIN_BANK_CODE")
    private String bankCode;

    @Column(name = "POIN_ACC_NUM")
    private Long accNum;

    @Column(name = "POIN_ACC_DTLS_VERIFIED")
    private Integer accDtlsVerified;

    @Column(name = "POIN_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "POIN_REQ_USER_ID")
    private UserMstrEntity reqUserId;

    @ManyToOne
    @JoinColumn(name = "POIN_APPR_USER_ID")
    private UserMstrEntity apprUserId;

    @Column(name = "POIN_COMMENTS")
    private String comments;

    @Column(name = "POIN_CURRENT_STATUS")
    private String currentStatus;

    @Column(name = "POIN_PAYMENT_STATUS")
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "POIN_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POIN_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "POIN_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "POIN_UPDATED_ON")
    private Date updatedOn;

    public PurchaseOrderInvoiceDtlEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Integer getPaymentInDays() {
        return paymentInDays;
    }

    public void setPaymentInDays(Integer paymentInDays) {
        this.paymentInDays = paymentInDays;
    }

    public Date getPayDueDate() {
        return payDueDate;
    }

    public void setPayDueDate(Date payDueDate) {
        this.payDueDate = payDueDate;
    }

    public Date getProposedDueDate() {
        return proposedDueDate;
    }

    public void setProposedDueDate(Date proposedDueDate) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public PurchaseOrderEntity getPurId() {
        return purId;
    }

    public void setPurId(PurchaseOrderEntity purId) {
        this.purId = purId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public ProjCostItemEntity getCostId() {
        return costId;
    }

    public void setCostId(ProjCostItemEntity costId) {
        this.costId = costId;
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

    public UserMstrEntity getFinanceUserId() {
        return financeUserId;
    }

    public void setFinanceUserId(UserMstrEntity financeUserId) {
        this.financeUserId = financeUserId;
    }

    public UserMstrEntity getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(UserMstrEntity reqUserId) {
        this.reqUserId = reqUserId;
    }

    public UserMstrEntity getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(UserMstrEntity apprUserId) {
        this.apprUserId = apprUserId;
    }

}