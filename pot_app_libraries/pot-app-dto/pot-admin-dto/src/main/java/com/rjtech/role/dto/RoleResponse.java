package com.rjtech.role.dto;

import java.io.Serializable;
import java.util.List;

public class RoleResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5981409582939277038L;

    private Long roleId;
    private String roleName;
    private List<RolePermResp> rolePermissions;
    private List<String> permissions;
    
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
    
    public List<RolePermResp> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermResp> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
