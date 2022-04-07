package com.rjtech.projsettings.req;

import com.rjtech.common.dto.ProjectTO;

public class ProjEmpTransGetReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private String emptransType;

    public String getEmptransType() {
        return emptransType;
    }

    public void setEmptransType(String emptransType) {
        this.emptransType = emptransType;
    }

}
