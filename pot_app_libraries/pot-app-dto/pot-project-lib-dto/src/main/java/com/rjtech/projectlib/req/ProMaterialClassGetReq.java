package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ClientTO;

public class ProMaterialClassGetReq extends ClientTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private Long epsId;
    private Long projId;

    private String transferType;

    public Long getEpsId() {
        return epsId;
    }

    public void setEpsId(Long epsId) {
        this.epsId = epsId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

}
