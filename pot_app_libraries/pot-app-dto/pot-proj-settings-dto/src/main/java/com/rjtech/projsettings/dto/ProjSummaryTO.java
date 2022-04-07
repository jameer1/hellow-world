package com.rjtech.projsettings.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjSummaryTO extends ProjectTO {

    private static final long serialVersionUID = -6551511255306682354L;
    private List<ProjManpowerTO> projManpowerTOs = new ArrayList<ProjManpowerTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private List<ProjectPlantsDtlTO> projectPlantsDtlTOs = new ArrayList<ProjectPlantsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private List<ProjCostStmtDtlTO> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjManpowerTO> getProjManpowerTOs() {
        return projManpowerTOs;
    }

    public void setProjManpowerTOs(List<ProjManpowerTO> projManpowerTOs) {
        this.projManpowerTOs = projManpowerTOs;
    }

    public List<ProjectPlantsDtlTO> getProjectPlantsDtlTOs() {
        return projectPlantsDtlTOs;
    }

    public void setProjectPlantsDtlTOs(List<ProjectPlantsDtlTO> projectPlantsDtlTOs) {
        this.projectPlantsDtlTOs = projectPlantsDtlTOs;
    }

    public List<ProjCostStmtDtlTO> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTO> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

}
