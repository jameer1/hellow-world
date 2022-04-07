package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjectPlantsDelReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -5382904729811609331L;
    private List<Long> projectPlantsIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjectPlantsIds() {
        return projectPlantsIds;
    }

    public void setProjectPlantsIds(List<Long> projectPlantsIds) {
        this.projectPlantsIds = projectPlantsIds;
    }

}
