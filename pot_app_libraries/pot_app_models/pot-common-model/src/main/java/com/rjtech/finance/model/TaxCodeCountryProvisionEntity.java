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
@Table(name = "finance_tax_code_country_provision")
public class TaxCodeCountryProvisionEntity implements Serializable {

    private static final long serialVersionUID = 9142606475648330506L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FTC_ID")
    private Long id;

    @Column(name = "FTC_TAX_APPLCIABLE")
    private String taxStatus;

    @Column(name = "FTC_PERIOD_CYCLE")
    private String periodCycle;

    @Column(name = "FTC_DUE_DATE")
    private Date dueDate;

    @Column(name = "FTC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FTC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FTC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FTC_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FTC_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "FTC_FCD_ID")
    private TaxCountryProvisionEntity taxCountryProvisionEntity;

    @ManyToOne
    @JoinColumn(name = "FTC_FTCM_ID")
    private TaxCodesEntity taxCodesEntity;

    public TaxCodeCountryProvisionEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaxCodesEntity getTaxCodesEntity() {
        return taxCodesEntity;
    }

    public void setTaxCodesEntity(TaxCodesEntity taxCodesEntity) {
        this.taxCodesEntity = taxCodesEntity;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getPeriodCycle() {
        return periodCycle;
    }

    public void setPeriodCycle(String periodCycle) {
        this.periodCycle = periodCycle;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    public TaxCountryProvisionEntity getTaxCountryProvisionEntity() {
        return taxCountryProvisionEntity;
    }

    public void setTaxCountryProvisionEntity(TaxCountryProvisionEntity taxCountryProvisionEntity) {
        this.taxCountryProvisionEntity = taxCountryProvisionEntity;
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
