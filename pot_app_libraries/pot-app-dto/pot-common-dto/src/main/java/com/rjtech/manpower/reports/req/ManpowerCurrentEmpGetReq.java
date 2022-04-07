package com.rjtech.manpower.reports.req;

import java.util.ArrayList;
import java.util.List;

public class ManpowerCurrentEmpGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<Long>();
    private List<Long> cmpIds = new ArrayList<Long>();
    private List<String> categories = new ArrayList<String>();
    private String subReportType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getCmpIds() {
        return cmpIds;
    }

    public void setCmpIds(List<Long> cmpIds) {
        this.cmpIds = cmpIds;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
