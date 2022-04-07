package com.rjtech.role.req;

public class PermissionReq {

    private Long rolePermId;
    private String permission;
    private boolean remove;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public Long getRolePermId() {
        return rolePermId;
    }

    public void setRolePermId(Long rolePermId) {
        this.rolePermId = rolePermId;
    }
}
