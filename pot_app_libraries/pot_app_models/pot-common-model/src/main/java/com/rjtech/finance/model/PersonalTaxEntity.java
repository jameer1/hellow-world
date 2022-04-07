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
@Table(name = "finance_personal_tax")
public class PersonalTaxEntity implements Serializable {

    private static final long serialVersionUID = 7935756841433791785L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FPT_ID")
    private Long id;

    @Column(name = "FPT_ANNUAL_MIN_TAX")
    private Double annualMinTax;

    @Column(name = "FPT_ANNUAL_MAX_TAX")
    private Double annualMaxTax;

    @Column(name = "FPT_FIXED_TAX")
    private Double fixedTax;

    @Column(name = "FPT_VARIABLE_TAX")
    private Double variableTax;

    @Column(name = "FPT_COMMENTS")
    private String comments;

    @Column(name = "FPT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FPT_FCD_ID")
    private TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity;

    @ManyToOne
    @JoinColumn(name = "FPT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FPT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPT_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPT_UPDATED_ON")
    private Date updatedOn;

    public PersonalTaxEntity() {

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
