package com.rjtech.common.dto;

import java.io.Serializable;

public class TaxPaymentReceiverCodes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String code;

    private String description;

    private String nameofDepartment;
    private String registerdAddress;
    private String accountNumber;
    private String bankInstituteName;
    private String bankCode;
    private String taxPaymentReceiverDisplayId;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameofDepartment() {
        return nameofDepartment;
    }

    public void setNameofDepartment(String nameofDepartment) {
        this.nameofDepartment = nameofDepartment;
    }

    public String getRegisterdAddress() {
        return registerdAddress;
    }

    public void setRegisterdAddress(String registerdAddress) {
        this.registerdAddress = registerdAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankInstituteName() {
        return bankInstituteName;
    }

    public void setBankInstituteName(String bankInstituteName) {
        this.bankInstituteName = bankInstituteName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTaxPaymentReceiverDisplayId() {
        return taxPaymentReceiverDisplayId;
    }

    public void setTaxPaymentReceiverDisplayId(String taxPaymentReceiverDisplayId) {
        this.taxPaymentReceiverDisplayId = taxPaymentReceiverDisplayId;
    }
}
