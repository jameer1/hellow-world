package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class TaxPaymentDetailsTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1118707933947516327L;

    private Long id;
    private String taxCode;
    private String taxDesc;
    private String deptName;
    private String regAddr;
    private String accNumber;
    private String bankName;
    private String bankCode;
    private Long codeTypeCountryProvisionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxDesc() {
        return taxDesc;
    }

    public void setTaxDesc(String taxDesc) {
        this.taxDesc = taxDesc;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getCodeTypeCountryProvisionId() {
        return codeTypeCountryProvisionId;
    }

    public void setCodeTypeCountryProvisionId(Long codeTypeCountryProvisionId) {
        this.codeTypeCountryProvisionId = codeTypeCountryProvisionId;
    }

}
