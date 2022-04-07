package com.rjtech.plant.reports.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class PlantCostCodeWiseGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<Long>();
    private List<Long> crewIds = new ArrayList<Long>();
    private List<Long> costCodeIds = new ArrayList<Long>();
    private String date;
    private String subReportType;

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

    public List<Long> getCostCodeIds() {
        return costCodeIds;
    }

    public void setCostCodeIds(List<Long> costCodeIds) {
        this.costCodeIds = costCodeIds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
