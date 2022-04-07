package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectReportsTO;

public class ProjReportsSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5370329721106836660L;
    private List<ProjectReportsTO> projectReportsTOs = new ArrayList<ProjectReportsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjectReportsTO> getProjectReportsTOs() {
        return projectReportsTOs;
    }

    public void setProjectReportsTOs(List<ProjectReportsTO> projectReportsTOs) {
        this.projectReportsTOs = projectReportsTOs;
    }

}
