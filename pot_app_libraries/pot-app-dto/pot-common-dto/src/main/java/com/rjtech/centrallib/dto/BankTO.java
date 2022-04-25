package com.rjtech.centrallib.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.common.dto.ClientTO;

public class BankTO extends ClientTO {
    /**
     * 
     */
	 private static final long serialVersionUID = 2950084862079755848L;
	    private Long bankAccountId;
	    private Long companyId;
	    private String accountName;
	    private String bankName;
	    private String bankCode;
	    private String accountNumber;
	    private String bankAddress;
	    private String designation;
	    private boolean defaultContact;
	    private String bankStatus;

	    public Long getBankAccountId() {
	        return bankAccountId;
	    }

	    public void setBankAccountId(Long bankAccountId) {
	        this.bankAccountId = bankAccountId;
	    }

	    public Long getCompanyId() {
	        return companyId;
	    }

	    public void setCompanyId(Long companyId) {
	        this.companyId = companyId;
	    }

	    public boolean isDefaultContact() {
	        return defaultContact;
	    }

	    public void setDefaultContact(boolean defaultContact) {
	        this.defaultContact = defaultContact;
	    }

	    public String getAccountName() {
	        return accountName;
	    }

	    public void setAccountName(String accountName) {
	        this.accountName = accountName;
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

	    public String getAccountNumber() {
	        return accountNumber;
	    }

	    public void setAccountNumber(String accountNumber) {
	        this.accountNumber = accountNumber;
	    }

	    public String getBankAddress() {
	        return bankAddress;
	    }

	    public void setBankAddress(String bankAddress) {
	        this.bankAddress = bankAddress;
	    }

	    public String getDesignation() {
	        return designation;
	    }

	    public void setDesignation(String designation) {
	        this.designation = designation;
	    }
	    
	    public String getBankStatus() {
	    	return bankStatus;
	    }
	    
	    public void setBankStatus(String bankStatus) {
	    	this.bankStatus = bankStatus;
	    }
	    
        public String toString() {
        	return bankAccountId+"accountName :"+accountName;
        }

}