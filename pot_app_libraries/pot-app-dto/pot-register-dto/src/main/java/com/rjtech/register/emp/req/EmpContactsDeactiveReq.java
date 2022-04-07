package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class EmpContactsDeactiveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4424226357416089405L;
    private List<Long> empContactIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getEmpContactIds() {
        return empContactIds;
    }

    public void setEmpContactIds(List<Long> empContactIds) {
        this.empContactIds = empContactIds;
    }

}
