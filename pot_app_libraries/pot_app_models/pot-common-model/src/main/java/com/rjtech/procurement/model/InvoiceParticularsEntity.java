package com.rjtech.procurement.model;

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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "project_invoice_particulars")
public class InvoiceParticularsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PIP_PUR_ID")
    private PurchaseOrderEntity purId;

    @ManyToOne
    @JoinColumn(name = "PIP_EPM_ID")
    private ProjMstrEntity projId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIP_INVOICE_DATE")
    private Date invoicedate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIP_INVOICE_RECEIVED_DATE")
    private Date receivedInvoicedate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIP_PAY_DUE_DATE")
    private Date payDueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIP_PROPOSED_PAY_DUE_DATE")
    private Date proposedDueDate;

    @Column(name = "PIP_PAYMENT_IN_DAYS")
    private Integer paymentInDays;

    @Column(name = "PIP_STATUS")
    private Integer status;

    // TODO: remove this column (it is for storing code)
    @Column(name = "PIP_INVOICE_NUMBER")
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "PIP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PIP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIP_UPDATED_ON")
    private Date updatedOn;

    public InvoiceParticularsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(Date invoicedate) {
        this.invoicedate = invoicedate;
    }

    public Date getReceivedInvoicedate() {
        return receivedInvoicedate;
    }

    public void setReceivedInvoicedate(Date receivedInvoicedate) {
        this.receivedInvoicedate = receivedInvoicedate;
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

    public Integer getPaymentInDays() {
        return paymentInDays;
    }

    public void setPaymentInDays(Integer paymentInDays) {
        this.paymentInDays = paymentInDays;
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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