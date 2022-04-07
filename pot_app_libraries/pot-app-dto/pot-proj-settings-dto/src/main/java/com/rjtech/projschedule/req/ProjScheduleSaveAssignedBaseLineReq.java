package com.rjtech.projschedule.req;

import java.io.Serializable;
import java.util.List;

public class ProjScheduleSaveAssignedBaseLineReq implements Serializable {

    private static final long serialVersionUID = 6383677273631876710L;

    private String scheduleTabType;

    private List<Long> scheduleItemIds;

    private Long projId;

    private Long selectedBaseLineId;

    public String getScheduleTabType() {
        return scheduleTabType;
    }

    public void setScheduleTabType(String scheduleTabType) {
        this.scheduleTabType = scheduleTabType;
    }

    public List<Long> getScheduleItemIds() {
        return scheduleItemIds;
    }

    public void setScheduleItemIds(List<Long> scheduleItemIds) {
        this.scheduleItemIds = scheduleItemIds;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getSelectedBaseLineId() {
        return selectedBaseLineId;
    }

    public void setSelectedBaseLineId(Long selectedBaseLineId) {
        this.selectedBaseLineId = selectedBaseLineId;
    }

}
