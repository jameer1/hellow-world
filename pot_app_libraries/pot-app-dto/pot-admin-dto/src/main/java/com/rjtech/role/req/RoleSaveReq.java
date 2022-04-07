package com.rjtech.role.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.role.dto.RoleTO;

public class RoleSaveReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<RoleTO> roleTOs = new ArrayList<RoleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<RoleTO> getRoleTOs() {
        return roleTOs;
    }

    public void setRoleTOs(List<RoleTO> roleTOs) {
        this.roleTOs = roleTOs;
    }

}
