package com.rjtech.role.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SavePermissionReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3727827430057100894L;

    private long roleId;
    private List<PermissionReq> permissions = new ArrayList<PermissionReq>();

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public List<PermissionReq> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionReq> permissions) {
        this.permissions = permissions;
    }
}
