package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjWorkShiftDelReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<Long> projWorkShiftIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjWorkShiftIds() {
        return projWorkShiftIds;
    }

    public void setProjWorkShiftIds(List<Long> projWorkShiftIds) {
        this.projWorkShiftIds = projWorkShiftIds;
    }
}
