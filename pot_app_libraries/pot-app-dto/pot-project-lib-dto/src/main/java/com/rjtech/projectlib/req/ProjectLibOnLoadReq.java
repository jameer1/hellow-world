package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ProjectTO;

public class ProjectLibOnLoadReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ProjectLibOnLoadReq() {

    }

    private String procureCatg;

    public String getProcureCatg() {
        return procureCatg;
    }

    public void setProcureCatg(String procureCatg) {
        this.procureCatg = procureCatg;
    }

}
