package com.rjtech.role.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.AuditLog;

public class ModuleTO extends AuditLog {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private Long moduleId;
    private String moduleName;
    private Integer moduleDispOrder;
    private Long moduleParentId;
    private boolean rootFlag;
    private boolean tabValue = false;
    private boolean expand = true;
    private String moduleURLValue;
    private String moduleType;
    private List<ModuleTO> childModules = new ArrayList<ModuleTO>();
    private List<PermissionTO> permissionTOs = new ArrayList<PermissionTO>();

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getModuleDispOrder() {
        return moduleDispOrder;
    }

    public void setModuleDispOrder(Integer moduleDispOrder) {
        this.moduleDispOrder = moduleDispOrder;
    }

    public Long getModuleParentId() {
        return moduleParentId;
    }

    public void setModuleParentId(Long moduleParentId) {
        this.moduleParentId = moduleParentId;
    }

    public boolean isRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(boolean rootFlag) {
        this.rootFlag = rootFlag;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String getModuleURLValue() {
        return moduleURLValue;
    }

    public void setModuleURLValue(String moduleURLValue) {
        this.moduleURLValue = moduleURLValue;
    }

    public List<ModuleTO> getChildModules() {
        return childModules;
    }

    public void setChildModules(List<ModuleTO> childModules) {
        this.childModules = childModules;
    }

    public List<PermissionTO> getPermissionTOs() {
        return permissionTOs;
    }

    public void setPermissionTOs(List<PermissionTO> permissionTOs) {
        this.permissionTOs = permissionTOs;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public boolean isTabValue() {
        return tabValue;
    }

    public void setTabValue(boolean tabValue) {
        this.tabValue = tabValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
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
        ModuleTO other = (ModuleTO) obj;
        if (moduleId == null) {
            if (other.moduleId != null)
                return false;
        } else if (!moduleId.equals(other.moduleId))
            return false;
        return true;
    }

}
