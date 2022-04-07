package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class DocumentTransmittalTO extends ProjectTO {

    private static final long serialVersionUID = 3918203944778522213L;
    private Long id;
    private Long contractId;
    private String desc;
    private String issuedBy;
    private String acceptedBy;
    private String sign;
    private String name;
    private String designation;
    private String companyRep;
    private String vendorRep;
    private String vendorName;
    private String vendorSign;
    private String vendorDesignation;

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

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompanyRep() {
        return companyRep;
    }

    public void setCompanyRep(String companyRep) {
        this.companyRep = companyRep;
    }

    public String getVendorRep() {
        return vendorRep;
    }

    public void setVendorRep(String vendorRep) {
        this.vendorRep = vendorRep;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorSign() {
        return vendorSign;
    }

    public void setVendorSign(String vendorSign) {
        this.vendorSign = vendorSign;
    }

    public String getVendorDesignation() {
        return vendorDesignation;
    }

    public void setVendorDesignation(String vendorDesignation) {
        this.vendorDesignation = vendorDesignation;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

}
