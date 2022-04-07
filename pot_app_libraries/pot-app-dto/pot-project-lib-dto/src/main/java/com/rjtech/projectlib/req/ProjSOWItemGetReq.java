package com.rjtech.projectlib.req;

import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjSOWItemGetReq extends ProjectTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private Long sowId;
    private List<Long> projIds;
    private List<Long> costCodeIds;
    private Long workDairyId;

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getCostCodeIds() {
        return costCodeIds;
    }

    public void setCostCodeIds(List<Long> costCodeIds) {
        this.costCodeIds = costCodeIds;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }
}
