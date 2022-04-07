package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class WorkDairyEmpDelReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<Long> empIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(List<Long> empIds) {
        this.empIds = empIds;
    }

}
