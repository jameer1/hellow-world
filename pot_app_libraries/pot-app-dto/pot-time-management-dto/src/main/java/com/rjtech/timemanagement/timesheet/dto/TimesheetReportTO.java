package com.rjtech.timemanagement.timesheet.dto;

import com.rjtech.common.dto.ProjectTO;

public class TimesheetReportTO extends TimeSheetTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ProjectTO projectTO;
    private String crewName;
    private String crewCode;
    private String apprUserCode;
    private String reqUserCode;

    public ProjectTO getProjectTO() {
        return projectTO;
    }

    public void setProjectTO(ProjectTO projectTO) {
        this.projectTO = projectTO;
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public String getCrewCode() {
        return crewCode;
    }

    public void setCrewCode(String crewCode) {
        this.crewCode = crewCode;
    }

    public String getApprUserCode() {
        return apprUserCode;
    }

    public void setApprUserCode(String apprUserCode) {
        this.apprUserCode = apprUserCode;
    }

    public String getReqUserCode() {
        return reqUserCode;
    }

    public void setReqUserCode(String reqUserCode) {
        this.reqUserCode = reqUserCode;
    }

}
