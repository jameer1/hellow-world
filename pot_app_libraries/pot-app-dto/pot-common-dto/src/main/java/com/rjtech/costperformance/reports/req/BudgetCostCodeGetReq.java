package com.rjtech.costperformance.reports.req;

import java.util.List;

public class BudgetCostCodeGetReq {

    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds;

    private String subReportType;

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

}
