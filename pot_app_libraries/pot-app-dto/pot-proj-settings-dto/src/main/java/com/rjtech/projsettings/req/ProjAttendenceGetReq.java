package com.rjtech.projsettings.req;

import com.rjtech.common.dto.ProjectTO;

public class ProjAttendenceGetReq extends ProjectTO {

    private static final long serialVersionUID = 3419009257687674509L;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
