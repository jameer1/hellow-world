package com.rjtech.reports.cost.req;

import java.io.Serializable;
import java.util.List;

public class CostReportReq implements Serializable {

    private static final long serialVersionUID = 294147982184384201L;

    private List<Long> projIds;
    private List<Long> costcodeIds;
    private List<String> categories;
    private List<Long> cmpIds;
    private String fromDate;
    private String toDate;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getCostcodeIds() {
        return costcodeIds;
    }

    public void setCostcodeIds(List<Long> costcodeIds) {
        this.costcodeIds = costcodeIds;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Long> getCmpIds() {
        return cmpIds;
    }

    public void setCmpIds(List<Long> cmpIds) {
        this.cmpIds = cmpIds;
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

}
