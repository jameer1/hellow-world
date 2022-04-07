package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.projschedule.dto.GanttChartRowTO;

public class ScheduleActivityGanttDataResp {

	private List<GanttChartRowTO> ganttChartRow = new ArrayList<GanttChartRowTO>();

	public List<GanttChartRowTO> getGanttChartRow() {
		return ganttChartRow;
	}

	public void setGanttChartRow(List<GanttChartRowTO> ganttChartRow) {
		this.ganttChartRow = ganttChartRow;
	}
}
