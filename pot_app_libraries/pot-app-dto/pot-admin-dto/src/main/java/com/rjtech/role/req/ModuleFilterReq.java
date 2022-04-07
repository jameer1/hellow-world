package com.rjtech.role.req;

import java.io.Serializable;

public class ModuleFilterReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;
    private Long moduleId;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

}
