package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class InvoiceParticularsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long purId;
    private Integer paymentInDays;
    private String invoiceDate;
    private String invoiceReceivedDate;
    private String payDueDate;
    private String proposedPayDueDate;
    private String invoiceNumber;
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

    public String getInvoiceReceivedDate() {
        return invoiceReceivedDate;
    }

    public void setInvoiceReceivedDate(String invoiceReceivedDate) {
        this.invoiceReceivedDate = invoiceReceivedDate;
    }

    public String getPayDueDate() {
        return payDueDate;
    }

    public void setPayDueDate(String payDueDate) {
        this.payDueDate = payDueDate;
    }

    public String getProposedPayDueDate() {
        return proposedPayDueDate;
    }

    public void setProposedPayDueDate(String proposedPayDueDate) {
        this.proposedPayDueDate = proposedPayDueDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

}