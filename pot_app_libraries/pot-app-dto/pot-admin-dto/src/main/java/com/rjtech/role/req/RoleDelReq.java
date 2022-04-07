package com.rjtech.role.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class RoleDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<Long> roleIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

}
