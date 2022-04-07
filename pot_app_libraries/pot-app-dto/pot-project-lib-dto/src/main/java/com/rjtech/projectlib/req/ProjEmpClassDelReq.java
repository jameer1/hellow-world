package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjEmpClassDelReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<Long> projEmpClassIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjEmpClassIds() {
        return projEmpClassIds;
    }

    public void setProjEmpClassIds(List<Long> projEmpClassIds) {
        this.projEmpClassIds = projEmpClassIds;
    }
}
