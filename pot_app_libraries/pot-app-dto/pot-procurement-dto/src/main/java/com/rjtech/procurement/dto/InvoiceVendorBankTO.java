package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class InvoiceVendorBankTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3813725928585401142L;
    private Long id;
    private Long purId;
    private String bankName;
    private String accountName;
    private String bankCode;
    private Long accountNum;
    private Integer accDetailsVerified;
    private Long apprId;
    private Integer status;

    public InvoiceVendorBankTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public Integer getAccDetailsVerified() {
        return accDetailsVerified;
    }

    public void setAccDetailsVerified(Integer accDetailsVerified) {
        this.accDetailsVerified = accDetailsVerified;
    }

    public Long getApprId() {
        return apprId;
    }

    public void setApprId(Long apprId) {
        this.apprId = apprId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}