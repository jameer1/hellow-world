package com.rjtech.attendance.reports.req;

import java.util.List;

public class CrewWiseAttendanceGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds;
    private List<Long> crewIds;
    private String date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
