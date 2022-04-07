package com.rjtech.progress.reports.req;

import java.util.ArrayList;
import java.util.List;

public class ProgressDateWiseReportGetReq {

    /**
     * 
     */

    private List<Long> projIds = new ArrayList<Long>();
    private List<Long> costCodeIds = new ArrayList<Long>();
    private List<Long> sowIds = new ArrayList<Long>();
    private String fromDate;
    private String toDate;
    private String subReportType;

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

    public void setSowIds(List<Long> sowIds) {
        this.sowIds = sowIds;
    }

    public List<Long> getSowIds() {
        return sowIds;
    }

    public List<Long> getCostCodeIds() {
        return costCodeIds;
    }

    public void setCostCodeIds(List<Long> costCodeIds) {
        this.costCodeIds = costCodeIds;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }
}
