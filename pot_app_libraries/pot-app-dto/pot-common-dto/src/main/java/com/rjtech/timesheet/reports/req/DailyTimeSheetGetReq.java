package com.rjtech.timesheet.reports.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class DailyTimeSheetGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private String weekCommenceDay;
    private String selectType;
    private List<Long> selectedTypeIds = new ArrayList<Long>();
    private List<String> apprStatus = new ArrayList<String>();
    private List<String> timeSheetIds = new ArrayList<String>();
    private List<Long> originatorIds = new ArrayList<Long>();
    private List<Long> approverIds = new ArrayList<Long>();
    private String subReportType;

    public String getWeekCommenceDay() {
        return weekCommenceDay;
    }

    public void setWeekCommenceDay(String weekCommenceDay) {
        this.weekCommenceDay = weekCommenceDay;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public List<Long> getSelectedTypeIds() {
        return selectedTypeIds;
    }

    public void setSelectedTypeIds(List<Long> selectedTypeIds) {
        this.selectedTypeIds = selectedTypeIds;
    }

    public List<String> getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(List<String> apprStatus) {
        this.apprStatus = apprStatus;
    }

    public List<String> getTimeSheetIds() {
        return timeSheetIds;
    }

    public void setTimeSheetIds(List<String> timeSheetIds) {
        this.timeSheetIds = timeSheetIds;
    }

    public List<Long> getOriginatorIds() {
        return originatorIds;
    }

    public void setOriginatorIds(List<Long> originatorIds) {
        this.originatorIds = originatorIds;
    }

    public List<Long> getApproverIds() {
        return approverIds;
    }

    public void setApproverIds(List<Long> approverIds) {
        this.approverIds = approverIds;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
