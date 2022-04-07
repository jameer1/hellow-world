package com.rjtech.timemanagement.workdairy.req;

import java.util.List;

public class WorkDairyApprovalGetReq extends WorkDairyGetReq {

    private static final long serialVersionUID = 1L;

    private List<Long> projIds;
    private String fromDate;
    private String toDate;

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

}
