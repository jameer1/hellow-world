package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ClientTO;

public class ProjPlantClassGetReq extends ClientTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private Long projId;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

}
