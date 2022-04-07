package com.rjtech.role.dto;

import com.rjtech.common.dto.AuditLog;

public class PermissionTO extends AuditLog {

    /**
     * 
     */
    private static final long serialVersionUID = 7112257292445188967L;
    private Long perId;
    private Long moduleId;
    private Long rolePerId;
    private Long roleId;
    private Integer displayOder;
    private Long actionId;
    private String actionName;
    private boolean permission;
    private String permissionKey;

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public PermissionTO() {
    }

    public Long getPerId() {
        return perId;
    }

    public void setPerId(Long perId) {
        this.perId = perId;
    }

    public Long getRolePerId() {
        return rolePerId;
    }

    public void setRolePerId(Long rolePerId) {
        this.rolePerId = rolePerId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getDisplayOder() {
        return displayOder;
    }

    public void setDisplayOder(Integer displayOder) {
        this.displayOder = displayOder;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((perId == null) ? 0 : perId.hashCode());
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
        PermissionTO other = (PermissionTO) obj;
        if (perId == null) {
            if (other.perId != null)
                return false;
        } else if (!perId.equals(other.perId))
            return false;
        return true;
    }

}
