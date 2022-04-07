package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpBankAccountDtlTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5048003796093964935L;
    private Long id;
    private Long empRegDtlId;
    private String bankName;
    private String address;
    private String ifscCode;
    private String accName;
    private Long accNumber;
    private String accType;
    private String accStatus;
    private String accComments;
    private String fromDate;
    private boolean latest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpRegDtlId() {
        return empRegDtlId;
    }

    public void setEmpRegDtlId(Long empRegDtlId) {
        this.empRegDtlId = empRegDtlId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public Long getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(Long accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getAccComments() {
        return accComments;
    }

    public void setAccComments(String accComments) {
        this.accComments = accComments;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

}
