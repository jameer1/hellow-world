package com.rjtech.manpower.reports.req;

import java.util.ArrayList;
import java.util.List;

public class ManpowerCostCodeGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<Long>();
    private String date;
    private String subReportType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
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
