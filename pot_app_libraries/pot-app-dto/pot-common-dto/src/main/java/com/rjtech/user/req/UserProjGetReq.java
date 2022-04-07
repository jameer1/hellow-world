package com.rjtech.user.req;

import com.rjtech.common.dto.ProjectTO;

public class UserProjGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private Long userId;
    private String contractType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}
