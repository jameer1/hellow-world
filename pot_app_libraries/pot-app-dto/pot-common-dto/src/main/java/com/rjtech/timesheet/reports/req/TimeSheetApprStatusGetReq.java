package com.rjtech.timesheet.reports.req;

import java.util.ArrayList;
import java.util.List;

public class TimeSheetApprStatusGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = -8609613743526719257L;

    private List<Long> projIds = new ArrayList<Long>();
    private List<String> apprStatus = new ArrayList<String>();
    private List<Long> supervisorIds = new ArrayList<Long>();
    private String fromDate;
    private String toDate;
    private String subReportType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<String> getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(List<String> apprStatus) {
        this.apprStatus = apprStatus;
    }

    public List<Long> getSupervisorIds() {
        return supervisorIds;
    }

    public void setSupervisorIds(List<Long> supervisorIds) {
        this.supervisorIds = supervisorIds;
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
