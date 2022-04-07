package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class EmpNokDeactiveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3943894350413515280L;

    List<Long> empNokIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getEmpNokIds() {
        return empNokIds;
    }

    public void setEmpNokIds(List<Long> empNokIds) {
        this.empNokIds = empNokIds;
    }

}
