package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ClientTO;

public class ProjFilterReq extends ClientTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private Long userId;
    private Long clientId;
    private Long projId;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProjId() {
        return this.projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }
}
