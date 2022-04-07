package com.rjtech.common.req;

import com.rjtech.common.dto.ClientTO;

public class UserGetReq extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private String moduleCode;
    private String actionCode;
    private String permission;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}