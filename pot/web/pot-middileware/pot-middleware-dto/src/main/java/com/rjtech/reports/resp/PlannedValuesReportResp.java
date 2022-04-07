package com.rjtech.reports.resp;

import java.io.Serializable;
import java.util.List;

import com.rjtech.projschedule.resp.ProjScheduleSOWItemResp;

public class PlannedValuesReportResp implements Serializable {

    private static final long serialVersionUID = -3579703453504164002L;

    private ReportsResp reportsResp;

    private List<ProjScheduleSOWItemResp> sowList;

    public ReportsResp getReportsResp() {
        return reportsResp;
    }

    public void setReportsResp(ReportsResp reportsResp) {
        this.reportsResp = reportsResp;
    }

    public List<ProjScheduleSOWItemResp> getSowList() {
        return sowList;
    }

    public void setSowList(List<ProjScheduleSOWItemResp> sowList) {
        this.sowList = sowList;
    }

}
