package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectPlantsDtlTO;

public class ProjectPlantsSaveReq extends ProjectTO {

    private static final long serialVersionUID = -7957155235723935297L;

    private List<ProjectPlantsDtlTO> projectPlantsDtlTOs = new ArrayList<ProjectPlantsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjectPlantsDtlTO> getProjectPlantsDtlTOs() {
        return projectPlantsDtlTOs;
    }

    public void setProjectPlantsDtlTOs(List<ProjectPlantsDtlTO> projectPlantsDtlTOs) {
        this.projectPlantsDtlTOs = projectPlantsDtlTOs;
    }

}
