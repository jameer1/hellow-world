package com.rjtech.register.emp.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManPowerGenderStatisticsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Long> projIds = new ArrayList<>();
    private List<Long> cmpIds = new ArrayList<>();
    private String category;
    private String date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
