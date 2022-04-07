package com.rjtech.plant.reports.req;

import java.util.ArrayList;
import java.util.List;

public class CurrentPlantsReportGetReq {

    private List<Long> projIds = new ArrayList<Long>();
    private List<Long> cmpIds = new ArrayList<Long>();

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

}
