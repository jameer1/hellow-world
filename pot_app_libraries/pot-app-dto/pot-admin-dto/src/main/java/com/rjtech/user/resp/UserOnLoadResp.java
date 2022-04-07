package com.rjtech.user.resp;

import com.rjtech.role.resp.RoleResp;
import com.rjtech.user.dto.UserTO;

public class UserOnLoadResp {

    private RoleResp roleResp = new RoleResp();
    private UserTO userTO = null;

    public UserOnLoadResp() {

        userTO = new UserTO();
    }

    public RoleResp getRoleResp() {
        return roleResp;
    }

    public void setRoleResp(RoleResp roleResp) {
        this.roleResp = roleResp;
    }

    public UserTO getUserTO() {
        return userTO;
    }

    public void setUserTO(UserTO userTO) {
        this.userTO = userTO;
    }

}
