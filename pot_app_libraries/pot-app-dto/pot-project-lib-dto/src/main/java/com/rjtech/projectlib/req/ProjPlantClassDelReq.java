package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjPlantClassDelReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<Long> projPlantClassIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjPlantClassIds() {
        return projPlantClassIds;
    }

    public void setProjPlantClassIds(List<Long> projPlantClassIds) {
        this.projPlantClassIds = projPlantClassIds;
    }

}
