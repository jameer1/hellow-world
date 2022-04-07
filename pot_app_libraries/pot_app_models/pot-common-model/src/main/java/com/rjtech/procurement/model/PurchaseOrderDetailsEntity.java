package com.rjtech.procurement.model;

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

import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the purchse_order_details database table.
 * 
 */
@Entity
@Table(name = "purchse_order_details")
public class PurchaseOrderDetailsEntity implements Serializable {

    private static final long serialVersionUID = 3360101643651321800L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "ISSUED_BY")
    private String issuedBy;

    @Column(name = "ISSSUER_SIGN")
    private String issuerSign;

    @Column(name = "ISSSUER_NAME")
    private String issuerName;

    @Column(name = "ISSSUER_DESIGNATION")
    private String issuerDesignation;

    @Column(name = "ACCEPTED_BY")
    private String acceptedBy;

    @Column(name = "VENDOR_SIGN")
    private String vendorSign;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @Column(name = "VENDOR_DESIGNATION")
    private String vendorDesignation;

    @Column(name = "ISSUED_MANPOWER_QUANTITY")
    private int issuedManPowerQuantity;

    @Column(name = "ISSUED_PLANTS_QUANTITY")
    private int issuedPlantsQuantity;

    @Column(name = "ISSUED_MATERIALS_QUANTITY")
    private int issuedMaterialsQuantity;

    @Column(name = "ISSUED_SERVICES_QUANTITY")
    private int issuedServicesQuantity;

    @Column(name = "ISSUED_SOW_QUANTITY")
    private int issuedSOWQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TERMS_AND_CONDITIONS_ID_FK")
    private TermsAndConditionsEntity termsAndConditions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getIssuerSign() {
        return issuerSign;
    }

    public void setIssuerSign(String issuerSign) {
        this.issuerSign = issuerSign;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getIssuerDesignation() {
        return issuerDesignation;
    }

    public void setIssuerDesignation(String issuerDesignation) {
        this.issuerDesignation = issuerDesignation;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getVendorSign() {
        return vendorSign;
    }

    public void setVendorSign(String vendorSign) {
        this.vendorSign = vendorSign;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorDesignation() {
        return vendorDesignation;
    }

    public void setVendorDesignation(String vendorDesignation) {
        this.vendorDesignation = vendorDesignation;
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

    public int getIssuedManPowerQuantity() {
        return issuedManPowerQuantity;
    }

    public void setIssuedManPowerQuantity(int issuedManPowerQuantity) {
        this.issuedManPowerQuantity = issuedManPowerQuantity;
    }

    public int getIssuedPlantsQuantity() {
        return issuedPlantsQuantity;
    }

    public void setIssuedPlantsQuantity(int issuedPlantsQuantity) {
        this.issuedPlantsQuantity = issuedPlantsQuantity;
    }

    public int getIssuedMaterialsQuantity() {
        return issuedMaterialsQuantity;
    }

    public void setIssuedMaterialsQuantity(int issuedMaterialsQuantity) {
        this.issuedMaterialsQuantity = issuedMaterialsQuantity;
    }

    public int getIssuedServicesQuantity() {
        return issuedServicesQuantity;
    }

    public void setIssuedServicesQuantity(int issuedServicesQuantity) {
        this.issuedServicesQuantity = issuedServicesQuantity;
    }

    public int getIssuedSOWQuantity() {
        return issuedSOWQuantity;
    }

    public void setIssuedSOWQuantity(int issuedSOWQuantity) {
        this.issuedSOWQuantity = issuedSOWQuantity;
    }

    public TermsAndConditionsEntity getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(TermsAndConditionsEntity termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
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