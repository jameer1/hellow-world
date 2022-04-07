package com.rjtech.timemanagement.manpower.reports.req;

import java.util.ArrayList;
import java.util.List;

public class ManpowerStandardHrsGetReq extends ManpowerPeroidicalHrsGetReq {

    private static final long serialVersionUID = 1L;

    private List<Long> crewIds = new ArrayList<>();

    private List<Long> costCodeIds = new ArrayList<>();

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

}
