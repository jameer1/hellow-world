package com.rjtech.role.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.role.dto.ModuleTO;

public class ModuleSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<ModuleTO> moduleTOs = new ArrayList<ModuleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long roleId;

    public List<ModuleTO> getModuleTOs() {
        return moduleTOs;
    }

    public void setModuleTOs(List<ModuleTO> moduleTOs) {
        this.moduleTOs = moduleTOs;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
