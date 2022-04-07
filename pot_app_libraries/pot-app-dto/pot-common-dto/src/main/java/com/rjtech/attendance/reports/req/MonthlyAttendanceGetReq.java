package com.rjtech.attendance.reports.req;

import java.util.ArrayList;
import java.util.List;

public class MonthlyAttendanceGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<Long>();
    private List<Long> crewIds = new ArrayList<Long>();
    private String fromDate;
    private String toDate;
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
