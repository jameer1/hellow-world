package com.rjtech.centrallib.dto;

import com.rjtech.common.dto.ClientTO;

public class ContactsTO extends ClientTO {
    /**
     * 
     */
    private static final long serialVersionUID = 2950084862079755848L;
    private Long contactId;
    private Long companyId;
    private String contactName;
    private String email;
    private String mobile;
    private String phone;
    private String fax;
    private String designation;
    private boolean defaultContact;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}