package com.rjtech.user.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.user.dto.UserRoleTO;

public class UserFiletReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String employeeCode;
    private String employeeDesignation;
    private String userName;
    private String password;
    private String userRegParentID;
    private Long userAccountLocked;
    private Long clientRegId;
    private Integer userType;
    private String userDispName;
    private String email;
    private String phoneNumber;
    private String mobileNumber;
    private String remarks;

    List<UserRoleTO> userRoles = new ArrayList<UserRoleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRegParentID() {
        return userRegParentID;
    }

    public void setUserRegParentID(String userRegParentID) {
        this.userRegParentID = userRegParentID;
    }

    public Long getUserAccountLocked() {
        return userAccountLocked;
    }

    public void setUserAccountLocked(Long userAccountLocked) {
        this.userAccountLocked = userAccountLocked;
    }

    public Long getClientRegId() {
        return clientRegId;
    }

    public void setClientRegId(Long clientRegId) {
        this.clientRegId = clientRegId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserDispName() {
        return userDispName;
    }

    public void setUserDispName(String userDispName) {
        this.userDispName = userDispName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<UserRoleTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleTO> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientRegId == null) ? 0 : clientRegId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserFiletReq other = (UserFiletReq) obj;
        if (clientRegId == null) {
            if (other.clientRegId != null)
                return false;
        } else if (!clientRegId.equals(other.clientRegId))
            return false;
        return true;
    }

}
