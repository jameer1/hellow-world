package com.rjtech.centrallib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the weather_mstr database table.
 * 
 */
@Entity
@Table(name = "country_province_code")
public class CountryProvinceCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CP_ID")
    private Long id;

    @Column(name = "CP_COUNTRY_CODE")
    private String countrycode;
    
    @Column(name = "CP_COUNTRY_NAME")
    private String countryName;
    
    @Column(name = "CP_PROVISION_NAME")
    private String provisionName;
    
    @Column(name = "CP_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "CP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "CP_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "CP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CP_UPDATED_ON")
    private Date updatedOn;
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "FHY_ID")
    private FinancialHalfYearEntity financialHalfYearEntity;
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "FY_ID")
    private FinancialYearEntity financialYearEntity;
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "FQY_ID")
    private FinancialQuarterYearEntity financialQuarterYearEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvisionName() {
        return provisionName;
    }

    public void setProvisionName(String provisionName) {
        this.provisionName = provisionName;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public FinancialYearEntity getFinancialYearEntity() {
        return financialYearEntity;
    }

    public void setFinancialYearEntity(FinancialYearEntity financialYearEntity) {
        this.financialYearEntity = financialYearEntity;
    }

    public FinancialHalfYearEntity getFinancialHalfYearEntity() {
        return financialHalfYearEntity;
    }

    public void setFinancialHalfYearEntity(FinancialHalfYearEntity financialHalfYearEntity) {
        this.financialHalfYearEntity = financialHalfYearEntity;
    }

    public FinancialQuarterYearEntity getFinancialQuarterYearEntity() {
        return financialQuarterYearEntity;
    }

    public void setFinancialQuarterYearEntity(FinancialQuarterYearEntity financialQuarterYearEntity) {
        this.financialQuarterYearEntity = financialQuarterYearEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}