package com.rjtech.user.req;

import com.rjtech.common.dto.ClientTO;

public class UserProjGetReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private Long userId;
    private Long roleId;
    private String contractType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}
