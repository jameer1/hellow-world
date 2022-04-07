package com.rjtech.role.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.role.dto.PermissionTO;

public class ActionResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PermissionTO> permissionTOs = null;

    public ActionResp() {
        permissionTOs = new ArrayList<PermissionTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PermissionTO> getPermissionTOs() {
        return permissionTOs;
    }

    public void setPermissionTOs(List<PermissionTO> permissionTOs) {
        this.permissionTOs = permissionTOs;
    }

}
