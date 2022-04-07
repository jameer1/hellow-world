package com.rjtech.role.dto;

import java.io.Serializable;

public class UserPermissionTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7717629028480811740L;

    private String permission;

    private Long roleId;

    private String roleName;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
