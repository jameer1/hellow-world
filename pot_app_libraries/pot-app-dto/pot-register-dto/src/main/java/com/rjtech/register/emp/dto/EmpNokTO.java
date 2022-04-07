package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpNokTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6781256461991052949L;

    private Long id;
    private Long empRegId;
    private String contactType;
    private String firstName;
    private String lastName;
    private String residentialAddr;
    private String relationWithEmp;
    private String postalAddr;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResidentialAddr() {
        return residentialAddr;
    }

    public void setResidentialAddr(String residentialAddr) {
        this.residentialAddr = residentialAddr;
    }

    public String getRelationWithEmp() {
        return relationWithEmp;
    }

    public void setRelationWithEmp(String relationWithEmp) {
        this.relationWithEmp = relationWithEmp;
    }

    public String getPostalAddr() {
        return postalAddr;
    }

    public void setPostalAddr(String postalAddr) {
        this.postalAddr = postalAddr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
