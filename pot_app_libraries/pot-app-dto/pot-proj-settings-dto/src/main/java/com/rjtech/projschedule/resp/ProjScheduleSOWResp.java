package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleSOWTO;
import com.rjtech.projsettings.dto.ProjectReportsTO;

public class ProjScheduleSOWResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6428639300155396451L;
    private List<ProjScheduleSOWTO> projScheduleSOWTOs = null;
    private ProjectReportsTO projReportsTo = null;
    private List<String> calNonWorkingDays = null;
    private CalRegularDaysTO regularHolidays = null;
    private List<String> calSplWorkingDays = null;

    public ProjScheduleSOWResp() {
        projScheduleSOWTOs = new ArrayList<ProjScheduleSOWTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projReportsTo = new ProjectReportsTO();
        calNonWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        regularHolidays = new CalRegularDaysTO();
        calSplWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjScheduleSOWTO> getProjScheduleSOWTOs() {
        return projScheduleSOWTOs;
    }

    public void setProjScheduleSOWTOs(List<ProjScheduleSOWTO> projScheduleSOWTOs) {
        this.projScheduleSOWTOs = projScheduleSOWTOs;
    }

	public ProjectReportsTO getProjReportsTo() {
		return projReportsTo;
	}

	public void setProjReportsTo(ProjectReportsTO projReportsTo) {
		this.projReportsTo = projReportsTo;
	}

	public List<String> getCalNonWorkingDays() {
		return calNonWorkingDays;
	}

	public void setCalNonWorkingDays(List<String> calNonWorkingDays) {
		this.calNonWorkingDays = calNonWorkingDays;
	}

	public CalRegularDaysTO getRegularHolidays() {
		return regularHolidays;
	}

	public void setRegularHolidays(CalRegularDaysTO regularHolidays) {
		this.regularHolidays = regularHolidays;
	}

	public List<String> getCalSplWorkingDays() {
		return calSplWorkingDays;
	}

	public void setCalSplWorkingDays(List<String> calSplWorkingDays) {
		this.calSplWorkingDays = calSplWorkingDays;
	}

}
