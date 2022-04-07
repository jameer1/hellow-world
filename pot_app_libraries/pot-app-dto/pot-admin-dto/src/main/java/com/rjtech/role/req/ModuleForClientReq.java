package com.rjtech.role.req;

import java.io.Serializable;

public class ModuleForClientReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;
    private Long moduleId;
    private String moduleType;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

}
