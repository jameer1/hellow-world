package com.rjtech.costperformance.reports.req;

import java.util.ArrayList;
import java.util.List;

public class DateWisePlantActualDeatailsReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<Long>();
    private String fromDate;
    private String toDate;
    private String subReportType;
    private List<String> costcodeIds = new ArrayList<String>();

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
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

    public List<String> getCostcodeIds() {
        return costcodeIds;
    }

    public void setCostcodeIds(List<String> costcodeIds) {
        this.costcodeIds = costcodeIds;
    }

}
