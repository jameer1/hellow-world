package com.rjtech.centrallib.req;

import com.rjtech.common.dto.ClientTO;

public class CmpCurrentProjsDeactiveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long cmpCurrentProjId;

    public Long getCmpCurrentProjId() {
        return cmpCurrentProjId;
    }

    public void setCmpCurrentProjId(Long cmpCurrentProjId) {
        this.cmpCurrentProjId = cmpCurrentProjId;
    }

}
