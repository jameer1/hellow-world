package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjLeaveTypeDelReq extends ProjectTO {

    private static final long serialVersionUID = -7778061991193425031L;
    private List<Long> projLeaveTypeIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjLeaveTypeIds() {
        return projLeaveTypeIds;
    }

    public void setProjLeaveTypeIds(List<Long> projLeaveTypeIds) {
        this.projLeaveTypeIds = projLeaveTypeIds;
    }

}
