package com.rjtech.projschedule.req;

import com.rjtech.common.dto.ProjectTO;

public class ProjScheduleBaseLineGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4787172210978834529L;

    private Long id;
    private Long baseLineId;
    private String scheduleType;
    private String timeScale;
    private String scheduleItemType;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(Long baseLineId) {
        this.baseLineId = baseLineId;
    }

    public String getScheduleItemType() {
        return scheduleItemType;
    }

    public void setScheduleItemType(String scheduleItemType) {
        this.scheduleItemType = scheduleItemType;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(String timeScale) {
        this.timeScale = timeScale;
    }
    
    public void setStatus(Integer status) {
    	this.status = status;
    }
    
    public Integer getStatus() {
    	return status;
    }

}
