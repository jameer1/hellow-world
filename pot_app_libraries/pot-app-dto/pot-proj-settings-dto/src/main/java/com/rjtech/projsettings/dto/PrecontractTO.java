package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class PrecontractTO extends ProjectTO {

    private static final long serialVersionUID = 3279228587294417634L;
    private Long reqId;
    private String reqCode;

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

}
