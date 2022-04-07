package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class EmpWageDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> empWageIds = new ArrayList<Long>(5);

    public List<Long> getEmpWageIds() {
        return empWageIds;
    }

    public void setEmpWageIds(List<Long> empWageIds) {
        this.empWageIds = empWageIds;
    }

}
