package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MaterialClassTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectMaterialDtlTO;

public class ProjectMaterialsOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 5167932479601028269L;
    private List<MaterialClassTO> materialClassTOs = null;
    private ProjectMaterialDtlTO projectMaterialDtlTO = null;
    private List<ProjectMaterialDtlTO> projectMaterialDtlTOs = null;

    public ProjectMaterialsOnLoadResp() {
        materialClassTOs = new ArrayList<MaterialClassTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projectMaterialDtlTO = new ProjectMaterialDtlTO();
        projectMaterialDtlTOs = new ArrayList<ProjectMaterialDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public ProjectMaterialDtlTO getProjectMaterialDtlTO() {
        return projectMaterialDtlTO;
    }

    public void setProjectMaterialDtlTO(ProjectMaterialDtlTO projectMaterialDtlTO) {
        this.projectMaterialDtlTO = projectMaterialDtlTO;
    }

    public List<ProjectMaterialDtlTO> getProjectMaterialDtlTOs() {
        return projectMaterialDtlTOs;
    }

    public void setProjectMaterialDtlTOs(List<ProjectMaterialDtlTO> projectMaterialDtlTOs) {
        this.projectMaterialDtlTOs = projectMaterialDtlTOs;
    }

    public List<MaterialClassTO> getMaterialClassTOs() {
        return materialClassTOs;
    }

    public void setMaterialClassTOs(List<MaterialClassTO> materialClassTOs) {
        this.materialClassTOs = materialClassTOs;
    }

}
