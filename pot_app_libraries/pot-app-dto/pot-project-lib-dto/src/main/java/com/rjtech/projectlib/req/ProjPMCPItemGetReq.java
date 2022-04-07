package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ProjectTO;

import java.util.List;

public class ProjPMCPItemGetReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<Long> projIds;
    private List<Long> costcodeIds;
    private List<String> categories;
    private List<Long> cmpIds;
    private String fromDate;
    private String toDate;
    private boolean isCallFromActualCostModule=false;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getCostcodeIds() {
        return costcodeIds;
    }

    public void setCostcodeIds(List<Long> costcodeIds) {
        this.costcodeIds = costcodeIds;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Long> getCmpIds() {
        return cmpIds;
    }

    public void setCmpIds(List<Long> cmpIds) {
        this.cmpIds = cmpIds;
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

    public boolean isCallFromActualCostModule() {
        return isCallFromActualCostModule;
    }

    public void setCallFromActualCostModule(boolean callFromActualCostModule) {
        isCallFromActualCostModule = callFromActualCostModule;
    }
}
