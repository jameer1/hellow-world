package com.rjtech.procurement.dto;

import java.io.Serializable;

public class PurchaseOrderDetailsTO implements Serializable {

    private static final long serialVersionUID = -1264771173054944733L;

    private Long id;

    private String desc;

    private String issuedBy;

    private String issuerSign;

    private String issuerName;

    private String issuerDesignation;

    private String acceptedBy;

    private String vendorSign;

    private String vendorName;

    private String vendorDesignation;

    private int issuedManPowerQuantity;

    private int issuedPlantsQuantity;

    private int issuedMaterialsQuantity;

    private int issuedServicesQuantity;

    private int issuedSOWQuantity;

    private Long purchaseOrderId;

    private TermsAndConditionsTO termsAndConditionsTO;

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

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public TermsAndConditionsTO getTermsAndConditionsTO() {
        return termsAndConditionsTO;
    }

    public void setTermsAndConditionsTO(TermsAndConditionsTO termsAndConditionsTO) {
        this.termsAndConditionsTO = termsAndConditionsTO;
    }

}