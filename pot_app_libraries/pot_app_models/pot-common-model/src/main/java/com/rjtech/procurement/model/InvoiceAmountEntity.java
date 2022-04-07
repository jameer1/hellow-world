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

@Entity
@Table(name = "project_invoice_amount")
public class InvoiceAmountEntity implements Serializable {

    private static final long serialVersionUID = -4881105893254716209L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIA_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PIA_PUR_ID")
    private PurchaseOrderEntity purId;

    @ManyToOne
    @JoinColumn(name = "PIA_EPM_ID")
    private ProjMstrEntity projId;

    @Column(name = "PIA_INVOICE_AMOUNT")
    private BigDecimal invoiceAmount;

    @Column(name = "PIA_RETAINED_AMOUNT")
    private BigDecimal retainedAmount;

    @Column(name = "PIA_NET_PAYABLE_AMOUNT")
    private BigDecimal netPayableAmount;

    @Column(name = "PIA_TAX_AMOUNT")
    private BigDecimal taxAmount;

    @Column(name = "PIA_RETAINED_TAX_AMOUNT")
    private BigDecimal retainedTaxAmount;

    @Column(name = "PIA_NET_TAX_PAYABLE_AMOUNT")
    private BigDecimal netTaxPayableAmount;

    @Column(name = "PIA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PIA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PIA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIA_UPDATED_ON")
    private Date updatedOn;

    public InvoiceAmountEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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