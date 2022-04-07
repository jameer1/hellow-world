package com.rjtech.role.req;

import java.io.Serializable;

public class GetPermissionReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1404086250185373129L;

    private long clientId;

    private long roleId;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

}
