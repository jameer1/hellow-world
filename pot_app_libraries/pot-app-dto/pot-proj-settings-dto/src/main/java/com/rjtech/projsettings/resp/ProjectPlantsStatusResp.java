package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectPlantsStatusTO;

public class ProjectPlantsStatusResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1112275231989199090L;
    private List<ProjectPlantsStatusTO> projectPlantsStatusTOs = null;

    public ProjectPlantsStatusResp() {
        projectPlantsStatusTOs = new ArrayList<ProjectPlantsStatusTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjectPlantsStatusTO> getProjectPlantsStatusTOs() {
        return projectPlantsStatusTOs;
    }

    public void setProjectPlantsStatusTOs(List<ProjectPlantsStatusTO> projectPlantsStatusTOs) {
        this.projectPlantsStatusTOs = projectPlantsStatusTOs;
    }

}
