package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjPlantClassTO;
import com.rjtech.projsettings.dto.ProjectPlantsDtlTO;

public class ProjectPlantOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 8224390186832588246L;
    private List<ProjPlantClassTO> projPlantClassTOs = null;
    private ProjectPlantsDtlTO projectPlantsDtlTO = null;
    private List<ProjectPlantsDtlTO> projectPlantsDtlTOs = null;

    public ProjectPlantOnLoadResp() {
        projectPlantsDtlTO = new ProjectPlantsDtlTO();
        projPlantClassTOs = new ArrayList<ProjPlantClassTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projectPlantsDtlTOs = new ArrayList<ProjectPlantsDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPlantClassTO> getProjPlantClassTOs() {
        return projPlantClassTOs;
    }

    public void setProjPlantClassTOs(List<ProjPlantClassTO> projPlantClassTOs) {
        this.projPlantClassTOs = projPlantClassTOs;
    }

    public ProjectPlantsDtlTO getProjectPlantsDtlTO() {
        return projectPlantsDtlTO;
    }

    public void setProjectPlantsDtlTO(ProjectPlantsDtlTO projectPlantsDtlTO) {
        this.projectPlantsDtlTO = projectPlantsDtlTO;
    }

    public List<ProjectPlantsDtlTO> getProjectPlantsDtlTOs() {
        return projectPlantsDtlTOs;
    }

    public void setProjectPlantsDtlTOs(List<ProjectPlantsDtlTO> projectPlantsDtlTOs) {
        this.projectPlantsDtlTOs = projectPlantsDtlTOs;
    }

}
