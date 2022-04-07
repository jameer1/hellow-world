package com.rjtech.workdairy.reports.req;

import java.util.ArrayList;
import java.util.List;

public class WorkDairyApprStatusGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = -8609613743526719257L;

    private List<Long> projIds = new ArrayList<Long>();
    private List<String> apprStatus = new ArrayList<String>();
    private String fromDate;
    private String toDate;
    private String subReportType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<String> getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(List<String> apprStatus) {
        this.apprStatus = apprStatus;
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

}
