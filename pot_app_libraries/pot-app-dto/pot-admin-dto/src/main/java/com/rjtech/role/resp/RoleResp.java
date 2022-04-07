package com.rjtech.role.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.role.dto.RoleTO;

public class RoleResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<RoleTO> roleTOs = null;
    private boolean error;

    public RoleResp() {
        roleTOs = new ArrayList<RoleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<RoleTO> getRoleTOs() {
        return roleTOs;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
