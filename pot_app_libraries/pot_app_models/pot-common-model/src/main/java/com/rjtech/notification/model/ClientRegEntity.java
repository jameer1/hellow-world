package com.rjtech.notification.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.document.model.ProjDocFileEntity;

/**
 * The persistent class for the client_registration_mstr database table.
 * 
 */
@Entity
@Table(name = "client_registration_mstr")
public class ClientRegEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRM_ID")
    private Long clientId;

    @Column(name = "CRM_BUS_TYPE")
    private String businessType;

    @Column(name = "CRM_CODE")
    private String code;

    @Column(name = "CRM_STATUS")
    private Integer status;

    @Column(name = "CRM_COUNTRY")
    private String country;

    @Column(name = "CRM_WEB_URL")
    private String webSiteURL;

    @Column(name = "CRM_LICENCE_RENWAL")
    private Date licence;

    @Column(name = "CRM_CONTACT_PERSON")
    private String contactPerson;

    @Column(name = "CRM_ADDRESS")
    private String address;

    @ManyToOne
    @JoinColumn(name = "CRM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "CRM_EMAIL")
    private String email;

    @Column(name = "CRM_FAX")
    private String fax;

    @Column(name = "CRM_MOBILE_NUM")
    private String mobile;

    @Column(name = "CRM_NAME")
    private String name;

    @Column(name = "CRM_PHONE_NUM")
    private String phone;

    @Lob
    @Column(name = "CRM_REMARKS")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "CRM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRM_UPDATED_ON")
    private Date updatedOn;
    
    @ManyToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;

    public ClientRegEntity() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getLicence() {
        return licence;
    }

    public void setLicence(Date licence) {
        this.licence = licence;
    }

    public String getWebSiteURL() {
        return webSiteURL;
    }

    public void setWebSiteURL(String webSiteURL) {
        this.webSiteURL = webSiteURL;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    
    public ProjDocFileEntity getProjDocFileEntity() {
        return projDocFileEntity;
    }

    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
        this.projDocFileEntity = projDocFileEntity;
    }
}