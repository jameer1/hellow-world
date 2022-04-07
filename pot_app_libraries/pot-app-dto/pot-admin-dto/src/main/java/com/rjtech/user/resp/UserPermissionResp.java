package com.rjtech.user.resp;

import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.role.dto.UserPermissionTO;

public class UserPermissionResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private List<UserPermissionTO> roleIds = null;

    private List<UserPermissionTO> permissionKeys = null;

    public UserPermissionResp() {

    }

    public List<UserPermissionTO> getRoleIds() {
        return roleIds;
    }

    public List<UserPermissionTO> getPermissionKeys() {
        return permissionKeys;
    }

    public void setRoleIds(List<UserPermissionTO> roleIds) {
        this.roleIds = roleIds;
    }

    public void setPermissionKeys(List<UserPermissionTO> permissionKeys) {
        this.permissionKeys = permissionKeys;
    }

}
