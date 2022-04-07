package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class EmpRegDeactivateReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -4287656828700438515L;

    List<Long> empRegIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getEmpRegIds() {
        return empRegIds;
    }

    public void setEmpRegIds(List<Long> empRegIds) {
        this.empRegIds = empRegIds;
    }

}
