package com.rjtech.finance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "finance_tax_country_provision")
public class TaxCountryProvisionEntity implements Serializable {

    private static final long serialVersionUID = 9142606475648330506L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FCD_ID")
    private Long id;

    @Column(name = "FCD_CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "FCD_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "FCD_CON_ID")
    private String countryName;

    @Column(name = "FCD_PROV_ID")
    private String provision;

    @Column(name = "FCD_FROM_DATE")
    private Date fromDate;

    @Column(name = "FCD_TO_DATE")
    private Date toDate;

    @Column(name = "FCD_EFFECTIVE_DATE")
    private Date effectiveDate;

    @Column(name = "FCD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FCD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FCD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FCD_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FCD_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "taxCountryProvisionEntity")
    private List<TaxCodeCountryProvisionEntity> taxCodeCountryProvisionEntities = new ArrayList<TaxCodeCountryProvisionEntity>();

    public TaxCountryProvisionEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TaxCodeCountryProvisionEntity> getTaxCodeCountryProvisionEntities() {
        return taxCodeCountryProvisionEntities;
    }

    public void setTaxCodeCountryProvisionEntities(
            List<TaxCodeCountryProvisionEntity> taxCodeCountryProvisionEntities) {
        this.taxCodeCountryProvisionEntities = taxCodeCountryProvisionEntities;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvision() {
        return provision;
    }

    public void setProvision(String provision) {
        this.provision = provision;
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
