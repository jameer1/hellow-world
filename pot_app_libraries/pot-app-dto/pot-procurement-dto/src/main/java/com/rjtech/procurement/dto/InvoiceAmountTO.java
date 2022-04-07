package com.rjtech.procurement.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class InvoiceAmountTO extends ProjectTO {

    private static final long serialVersionUID = -5666269349891261299L;
    private Long id;
    private Long purId;
    private BigDecimal invoiceAmount;
    private BigDecimal retainedAmount;
    private BigDecimal netPayableAmount;
    private BigDecimal taxAmount;
    private BigDecimal retainedTaxAmount;
    private BigDecimal netTaxPayableAmount;
    private Integer status;

    public InvoiceAmountTO() {
    }

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

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getRetainedAmount() {
        return retainedAmount;
    }

    public void setRetainedAmount(BigDecimal retainedAmount) {
        this.retainedAmount = retainedAmount;
    }

    public BigDecimal getNetPayableAmount() {
        return netPayableAmount;
    }

    public void setNetPayableAmount(BigDecimal netPayableAmount) {
        this.netPayableAmount = netPayableAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getRetainedTaxAmount() {
        return retainedTaxAmount;
    }

    public void setRetainedTaxAmount(BigDecimal retainedTaxAmount) {
        this.retainedTaxAmount = retainedTaxAmount;
    }

    public BigDecimal getNetTaxPayableAmount() {
        return netTaxPayableAmount;
    }

    public void setNetTaxPayableAmount(BigDecimal netTaxPayableAmount) {
        this.netTaxPayableAmount = netTaxPayableAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}