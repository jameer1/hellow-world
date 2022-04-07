package com.rjtech.mw.dto.login;

import java.io.Serializable;
import java.util.Date;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -2713775541588932798L;

    private Long clientId;
    private String clientCode;
    private Long userId;
    private Integer userType;
    private Date licence;
    private Long adminClientId;
    private String designation;

    public Date getLicence() {
        return licence;
    }

    public void setLicence(Date licence) {
        this.licence = licence;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Long getAdminClientId() {
        return adminClientId;
    }

    public void setAdminClientId(Long adminClientId) {
        this.adminClientId = adminClientId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}
