package com.rjtech.timemanagement.manpower.reports.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManpowerPeroidicalHrsGetReq implements Serializable {

    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<>();
    private List<Long> cmpIds = new ArrayList<>();
    private String category;
    private String fromDate;
    private String toDate;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
