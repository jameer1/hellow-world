package com.rjtech.centrallib.req;

import com.rjtech.common.dto.ClientTO;

public class ProcureCatgFilterReq extends ClientTO {

    private static final long serialVersionUID = 7325836979462597917L;
    private String procureClassName;
    private String subProcureName;

    public String getSubProcureName() {
        return subProcureName;
    }

    public void setSubProcureName(String subProcureName) {
        this.subProcureName = subProcureName;
    }

    public String getProcureClassName() {
        return procureClassName;
    }

    public void setProcureClassName(String procureClassName) {
        this.procureClassName = procureClassName;
    }

}
