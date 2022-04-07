package com.rjtech.centrallib.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the stock_mstr database table.
 * 
 */
@Entity
@Table(name = "company_mstr")
public class CompanyMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMP_ID")
    private Long id;

    @Column(name = "CMP_CODE")
    private String code;

    @Column(name = "CMP_NAME")
    private String name;

    @Column(name = "CMP_REG_NUM")
    private String regNo;

    @Column(name = "CMP_TAX_FILE_NUM")
    private String taxFileNo;

    @Column(name = "CMP_ACTIVITY")
    private String cmpActivity;

    @Column(name = "CMP_BUSINESS_CATEGORY")
    private String businessCategory;

    @Column(name = "CMP_CATEGORY")
    private String companyCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMP_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CmpAddressEntity> cmpAddressEntities = new ArrayList<CmpAddressEntity>();

    @OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CmpContactsEntity> cmpContactsEntities = new ArrayList<CmpContactsEntity>();

    @OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CmpCurrentProjsEntity> cmpCurrentProjsEntities = new ArrayList<CmpCurrentProjsEntity>();
    
    @OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CmpBankAccountEntity> cmpBankAccountEntities = new ArrayList<CmpBankAccountEntity>();

    @Column(name = "CMP_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "CMP_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "CMP_UPDATED_ON")
    private Date updatedOn;

    public CompanyMstrEntity() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getTaxFileNo() {
        return taxFileNo;
    }

    public void setTaxFileNo(String taxFileNo) {
        this.taxFileNo = taxFileNo;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public List<CmpAddressEntity> getCmpAddressEntities() {
        return cmpAddressEntities;
    }

    public void setCmpAddressEntities(List<CmpAddressEntity> cmpAddressEntities) {
        this.cmpAddressEntities = cmpAddressEntities;
    }

    public List<CmpCurrentProjsEntity> getCmpCurrentProjsEntities() {
        return cmpCurrentProjsEntities;
    }

    public void setCmpCurrentProjsEntities(List<CmpCurrentProjsEntity> cmpCurrentProjsEntities) {
        this.cmpCurrentProjsEntities = cmpCurrentProjsEntities;
    }

    public List<CmpContactsEntity> getCmpContactsEntities() {
        return cmpContactsEntities;
    }

    public void setCmpContactsEntities(List<CmpContactsEntity> cmpContactsEntities) {
        this.cmpContactsEntities = cmpContactsEntities;
    }
    
    public List<CmpBankAccountEntity> getCmpBankAccountEntities() {
        return cmpBankAccountEntities;
    }

    public void setCmpBankAccountEntities(List<CmpBankAccountEntity> cmpBankAccountEntities) {
        this.cmpBankAccountEntities = cmpBankAccountEntities;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCmpActivity() {
        return cmpActivity;
    }

    public void setCmpActivity(String cmpActivity) {
        this.cmpActivity = cmpActivity;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

}
