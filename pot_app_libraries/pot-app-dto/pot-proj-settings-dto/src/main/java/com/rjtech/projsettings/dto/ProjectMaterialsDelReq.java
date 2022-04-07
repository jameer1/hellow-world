package com.rjtech.projsettings.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjectMaterialsDelReq extends ProjectTO {

    private static final long serialVersionUID = 7277011158155939985L;
    private List<Long> projectPlantsIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjectPlantsIds() {
        return projectPlantsIds;
    }

    public void setProjectPlantsIds(List<Long> projectPlantsIds) {
        this.projectPlantsIds = projectPlantsIds;
    }

}
