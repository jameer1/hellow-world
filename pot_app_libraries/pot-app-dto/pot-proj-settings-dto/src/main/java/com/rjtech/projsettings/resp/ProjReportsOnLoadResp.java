package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectReportsTO;

public class ProjReportsOnLoadResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<String> weeakDays = null;
    private List<String> years = null;
    private List<String> monthly = null;
    private List<ProjectReportsTO> projectReportsTOs = null;

    public ProjReportsOnLoadResp() {
        weeakDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        years = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        monthly = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projectReportsTOs = new ArrayList<ProjectReportsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<String> getWeeakDays() {
        return weeakDays;
    }

    public void setWeeakDays(List<String> weeakDays) {
        this.weeakDays = weeakDays;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<String> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<String> monthly) {
        this.monthly = monthly;
    }

    public List<ProjectReportsTO> getProjectReportsTOs() {
        return projectReportsTOs;
    }

    public void setProjectReportsTOs(List<ProjectReportsTO> projectReportsTOs) {
        this.projectReportsTOs = projectReportsTOs;
    }

}
