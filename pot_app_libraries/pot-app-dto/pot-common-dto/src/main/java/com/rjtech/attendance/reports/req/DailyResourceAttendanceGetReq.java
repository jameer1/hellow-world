package com.rjtech.attendance.reports.req;

import java.util.List;

public class DailyResourceAttendanceGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = -8609613743526719257L;

    private List<Long> projIds;
    private List<Long> crewIds;
    private String reportDate;
    private List<String> resourceCategires;
    private List<String> shiftTypes;
    private String subReportType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getCrewIds() {
        return crewIds;
    }

    public void setCrewIds(List<Long> crewIds) {
        this.crewIds = crewIds;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public List<String> getResourceCategires() {
        return resourceCategires;
    }

    public void setResourceCategires(List<String> resourceCategires) {
        this.resourceCategires = resourceCategires;
    }

    public List<String> getShiftTypes() {
        return shiftTypes;
    }

    public void setShiftTypes(List<String> shiftTypes) {
        this.shiftTypes = shiftTypes;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
