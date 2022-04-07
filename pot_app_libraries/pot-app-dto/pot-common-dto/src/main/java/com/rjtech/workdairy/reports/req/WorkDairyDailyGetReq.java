package com.rjtech.workdairy.reports.req;

import java.util.ArrayList;
import java.util.List;

public class WorkDairyDailyGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<Long>();
    private String workDairyId;
    private List<Long> crewIds = new ArrayList<Long>();
    private List<String> apprStatuses = new ArrayList<String>();
    private String workDairyDate;
    private String subReportType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public String getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(String workDairyId) {
        this.workDairyId = workDairyId;
    }

    public List<Long> getCrewIds() {
        return crewIds;
    }

    public void setCrewIds(List<Long> crewIds) {
        this.crewIds = crewIds;
    }

    public List<String> getApprStatuses() {
        return apprStatuses;
    }

    public void setApprStatuses(List<String> apprStatuses) {
        this.apprStatuses = apprStatuses;
    }

    public String getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(String workDairyDate) {
        this.workDairyDate = workDairyDate;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
