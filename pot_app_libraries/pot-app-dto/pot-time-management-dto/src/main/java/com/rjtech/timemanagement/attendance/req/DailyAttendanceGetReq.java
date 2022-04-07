package com.rjtech.timemanagement.attendance.req;

import java.util.List;

public class DailyAttendanceGetReq {

    private List<Long> projIds;
    private List<Long> crewIds;
    private String date;
    private String month;
    private Long year;
    private String subReportType;
    private String fromDate;
    private String toDate;
    private List<String> empCats;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getCrewIds() {
        return crewIds;
    }

    public void setCrewIds(List<Long> crewIds) {
        this.crewIds = crewIds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
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

    public List<String> getEmpCats() {
        return empCats;
    }

    public void setEmpCats(List<String> empCats) {
        this.empCats = empCats;
    }

}
