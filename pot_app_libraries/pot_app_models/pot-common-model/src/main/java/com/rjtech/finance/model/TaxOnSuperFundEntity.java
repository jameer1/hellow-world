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
@Table(name = "finance_fund_tax")
public class TaxOnSuperFundEntity implements Serializable {

    private static final long serialVersionUID = -7750775497155921367L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FFT_ID")
    private Long id;

    @Column(name = "FFT_LIMIT_INCOME")
    private Double limitIncome;

    @Column(name = "FFT_FUND_AMOUNT")
    private Double fundAmount;

    @Column(name = "FFT_TAX_RATE")
    private Double taxRate;

    @Column(name = "FFT_COMMENTS")
    private String comments;

    @Column(name = "FFT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FFT_FTC_ID")
    private TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity;

    @ManyToOne
    @JoinColumn(name = "FFT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FFT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FFT_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FFT_UPDATED_ON")
    private Date updatedOn;

    public TaxOnSuperFundEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLimitIncome() {
        return limitIncome;
    }

    public void setLimitIncome(Double limitIncome) {
        this.limitIncome = limitIncome;
    }

    public Double getFundAmount() {
        return fundAmount;
    }

    public void setFundAmount(Double fundAmount) {
        this.fundAmount = fundAmount;
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
