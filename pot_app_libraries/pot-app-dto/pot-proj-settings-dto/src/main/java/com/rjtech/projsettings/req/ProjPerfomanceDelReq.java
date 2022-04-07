package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjPerfomanceDelReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<Long> projPerformanceIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjPerformanceIds() {
        return projPerformanceIds;
    }

    public void setProjPerformanceIds(List<Long> projPerformanceIds) {
        this.projPerformanceIds = projPerformanceIds;
    }

}
