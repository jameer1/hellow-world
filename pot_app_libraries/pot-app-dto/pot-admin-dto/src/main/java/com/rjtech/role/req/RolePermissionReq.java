package com.rjtech.role.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.role.dto.RolePermissionTO;

public class RolePermissionReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<RolePermissionTO> rolePermissionTOs = new ArrayList<RolePermissionTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<RolePermissionTO> getRolePermissionTOs() {
        return rolePermissionTOs;
    }

    public void setRolePermissionTOs(List<RolePermissionTO> rolePermissionTOs) {
        this.rolePermissionTOs = rolePermissionTOs;
    }

}
