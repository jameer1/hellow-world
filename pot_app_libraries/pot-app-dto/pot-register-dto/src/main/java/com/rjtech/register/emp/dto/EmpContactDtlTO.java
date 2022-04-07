package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpContactDtlTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -990934625644508553L;

    private Long id;
    private Long empRegId;
    private String fromDate;
    private String residentAddr;
    private String postalAddr;
    private String phoneNumber;
    private String alternatePhone;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostalAddr() {
        return postalAddr;
    }

    public void setPostalAddr(String postalAddr) {
        this.postalAddr = postalAddr;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getResidentAddr() {
        return residentAddr;
    }

    public void setResidentAddr(String residentAddr) {
        this.residentAddr = residentAddr;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
