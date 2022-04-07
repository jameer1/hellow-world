package com.rjtech.costperformance.reports.req;

import java.util.ArrayList;
import java.util.List;

public class CostSchedulePerformanceIndexReq {
    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds;
    private String subReportType;
    private String fromDate;
    private String toDate;
    private List<String> costcodeIds = new ArrayList<String>();

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
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

    public List<String> getCostcodeIds() {
        return costcodeIds;
    }

    public void setCostcodeIds(List<String> costcodeIds) {
        this.costcodeIds = costcodeIds;
    }

}
