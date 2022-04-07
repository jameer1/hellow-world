package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectReportsTO;

public class ProjReportsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 2883203683069717924L;
    private List<ProjectReportsTO> projectReportsTOs = null;

    public ProjReportsResp() {
        projectReportsTOs = new ArrayList<ProjectReportsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjectReportsTO> getProjectReportsTOs() {
        return projectReportsTOs;
    }

    public void setProjectReportsTOs(List<ProjectReportsTO> projectReportsTOs) {
        this.projectReportsTOs = projectReportsTOs;
    }

}
