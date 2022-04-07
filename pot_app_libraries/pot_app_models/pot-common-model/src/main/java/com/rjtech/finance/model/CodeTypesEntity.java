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
@Table(name = "finance_code_type_country_provision")
public class CodeTypesEntity implements Serializable {

    private static final long serialVersionUID = -5996497578813472512L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FTCT_ID")
    private Long id;

    @Column(name = "FTCT_TYPE")
    private String type;

    @Column(name = "FTCT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FTCT_FCD_ID")
    private TaxCountryProvisionEntity taxCountryProvisionEntity;

    @Column(name = "FINANCE_CODE_TYPE")
    private String financeCodeType;

    @ManyToOne
    @JoinColumn(name = "FTCT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FTCT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FTCT_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FTCT_UPDATED_ON")
    private Date updatedOn;

    public CodeTypesEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TaxCountryProvisionEntity getTaxCountryProvisionEntity() {
        return taxCountryProvisionEntity;
    }

    public void setTaxCountryProvisionEntity(TaxCountryProvisionEntity taxCountryProvisionEntity) {
        this.taxCountryProvisionEntity = taxCountryProvisionEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFinanceCodeType() {
        return financeCodeType;
    }

    public void setFinanceCodeType(String financeCodeType) {
        this.financeCodeType = financeCodeType;
    }

}
