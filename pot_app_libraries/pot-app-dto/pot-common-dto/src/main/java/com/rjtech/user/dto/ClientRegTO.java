package com.rjtech.user.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.AuditLog;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRegTO extends AuditLog {

    private static final long serialVersionUID = 2740448397989044791L;

    private Long id;
    private String code;
    private String name;
    private String businessType;
    private String email;
    private String fax;
    private String phone;
    private String mobile;
    private String remarks;
    private String country;
    private String webSiteURL;
    private Long registeredUsers;
    private String licence;
    private String contactPerson;
    private String address;
    private boolean saveFlag;
    private UserTO userTO = new UserTO();
    private byte[] clientDetails;
    private String clientDetailsFileName;
    private String clientFileType;
    private String clientFileSize;

    public byte[] getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(byte[] clientDetails) {
        this.clientDetails = clientDetails;
    }

    public String getClientDetailsFileName() {
        return clientDetailsFileName;
    }

    public void setClientDetailsFileName(String clientDetailsFileName) {
        this.clientDetailsFileName = clientDetailsFileName;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSiteURL() {
        return webSiteURL;
    }

    public void setWebSiteURL(String webSiteURL) {
        this.webSiteURL = webSiteURL;
    }

    public Long getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(Long registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public UserTO getUserTO() {
        return userTO;
    }

    public void setUserTO(UserTO userTO) {
        this.userTO = userTO;
    }

    public String getClientFileType() {
        return clientFileType;
    }

    public void setClientFileType(String clientFileType) {
        this.clientFileType = clientFileType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientRegTO other = (ClientRegTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    public String getClientFileSize() {
        return clientFileSize;
    }

    public void setClientFileSize( String clientFileSize ) {
        this.clientFileSize = clientFileSize;
    }
}
