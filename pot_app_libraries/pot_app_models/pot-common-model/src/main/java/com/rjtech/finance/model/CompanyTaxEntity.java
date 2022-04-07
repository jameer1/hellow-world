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
@Table(name = "finance_company_tax")
public class CompanyTaxEntity implements Serializable {

    private static final long serialVersionUID = 6366066738434814635L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FCT_ID")
    private Long id;

    @Column(name = "FCT_ANNUAL_MIN_TAX")
    private Double annualMinTax;

    @Column(name = "FCT_ANNUAL_MAX_TAX")
    private Double annualMaxTax;

    @Column(name = "FCT_FIXED_TAX")
    private Double fixedTax;

    @Column(name = "FCT_VARIABLE_TAX")
    private Double variableTax;

    @Column(name = "FCT_COMMENTS")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "FCT_FTC_ID")
    private TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity;

    @Column(name = "FCT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FCT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FCT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FCT_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FCT_UPDATED_ON")
    private Date updatedOn;

    public CompanyTaxEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAnnualMinTax() {
        return annualMinTax;
    }

    public void setAnnualMinTax(Double annualMinTax) {
        this.annualMinTax = annualMinTax;
    }

    public Double getAnnualMaxTax() {
        return annualMaxTax;
    }

    public void setAnnualMaxTax(Double annualMaxTax) {
        this.annualMaxTax = annualMaxTax;
    }

    public Double getFixedTax() {
        return fixedTax;
    }

    public void setFixedTax(Double fixedTax) {
        this.fixedTax = fixedTax;
    }

    public Double getVariableTax() {
        return variableTax;
    }

    public void setVariableTax(Double variableTax) {
        this.variableTax = variableTax;
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

    public TaxCodeCountryProvisionEntity getTaxCodeCountryProvisionEntity() {
        return taxCodeCountryProvisionEntity;
    }

    public void setTaxCodeCountryProvisionEntity(TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity) {
        this.taxCodeCountryProvisionEntity = taxCodeCountryProvisionEntity;
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
