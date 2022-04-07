package com.rjtech.role.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.role.dto.ModuleTO;

public class ModuleResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private Long moduleId;
    private String moduleName;
    private Integer moduleDispOrder;
    private Long moduleParentId;
    private boolean rootFlag;
    private List<ModuleTO> moduleTOs = new ArrayList<ModuleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

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

    public List<ModuleTO> getModuleTOs() {
        return moduleTOs;
    }

    public void setModuleTOs(List<ModuleTO> moduleTOs) {
        this.moduleTOs = moduleTOs;
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
        ModuleResp other = (ModuleResp) obj;
        if (moduleId == null) {
            if (other.moduleId != null)
                return false;
        } else if (!moduleId.equals(other.moduleId))
            return false;
        return true;
    }

}
