package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectMaterialDtlTO;

public class ProjectMaterialSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7132441058456347173L;
    private List<ProjectMaterialDtlTO> projectMaterialDtlTOs = new ArrayList<ProjectMaterialDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjectMaterialDtlTO> getProjectMaterialDtlTOs() {
        return projectMaterialDtlTOs;
    }

    public void setProjectMaterialDtlTOs(List<ProjectMaterialDtlTO> projectMaterialDtlTOs) {
        this.projectMaterialDtlTOs = projectMaterialDtlTOs;
    }

}
