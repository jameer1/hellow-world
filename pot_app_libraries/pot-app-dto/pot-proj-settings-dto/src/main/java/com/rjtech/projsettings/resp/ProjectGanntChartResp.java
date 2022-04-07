package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.projsettings.dto.ProjectGanttChartTO;
import com.rjtech.common.resp.AppResp;

public class ProjectGanntChartResp  extends AppResp{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private List<ProjectGanttChartTO> projectGanttChartTOs = new ArrayList<ProjectGanttChartTO>();
	
    public List<ProjectGanttChartTO> getProjectGanttChartTOs() {
		return projectGanttChartTOs;
	}
	public void setProjectGanttChartTOs(List<ProjectGanttChartTO> projectGanttChartTOs) {
		this.projectGanttChartTOs = projectGanttChartTOs;
	}
}