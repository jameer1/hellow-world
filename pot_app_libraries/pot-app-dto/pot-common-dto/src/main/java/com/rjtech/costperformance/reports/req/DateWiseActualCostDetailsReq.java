package com.rjtech.costperformance.reports.req;

import java.util.ArrayList;
import java.util.List;

public class DateWiseActualCostDetailsReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;
    private List<Long> projIds = new ArrayList<Long>();
    private List<Long> cmpIds = new ArrayList<Long>();
    private List<String> categories = new ArrayList<String>();
    private List<String> costcodeIds = new ArrayList<String>();
    private String fromDate;
    private String toDate;
    private String subReportType;
    private String isReportForActCostPerctBasis;


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

    public String getIsReportForActCostPerctBasis() {
        return isReportForActCostPerctBasis;
    }

    public void setIsReportForActCostPerctBasis(String isReportForActCostPerctBasis) {
        this.isReportForActCostPerctBasis = isReportForActCostPerctBasis;
    }
}
