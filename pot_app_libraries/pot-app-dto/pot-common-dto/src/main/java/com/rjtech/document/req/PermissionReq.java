package com.rjtech.document.req;

import com.rjtech.common.dto.ProjectTO;

public class PermissionReq extends ProjectTO {

    private Long projId;

    private Long userId;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
