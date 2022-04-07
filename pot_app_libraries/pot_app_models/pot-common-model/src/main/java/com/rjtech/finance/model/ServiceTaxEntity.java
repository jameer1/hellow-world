package com.rjtech.finance.model;

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

@Entity
@Table(name = "finance_service_tax")
public class ServiceTaxEntity implements Serializable {

    private static final long serialVersionUID = -2757155591700581666L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FST_ID")
    private Long id;

    @Column(name = "FST_INVOICE_AMOUNT")
    private Double invoiceAmount;

    @Column(name = "FST_TAX_RATE")
    private Double taxRate;

    @Column(name = "FST_COMMENTS")
    private String comments;

    @Column(name = "FST_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FST_FCD_ID")
    private TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity;

    @ManyToOne
    @JoinColumn(name = "FST_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FST_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FST_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FST_UPDATED_ON")
    private Date updatedOn;

    public ServiceTaxEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public TaxCodeCountryProvisionEntity getTaxCodeCountryProvisionEntity() {
        return taxCodeCountryProvisionEntity;
    }

    public void setTaxCodeCountryProvisionEntity(TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity) {
        this.taxCodeCountryProvisionEntity = taxCodeCountryProvisionEntity;
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
