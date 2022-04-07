package com.rjtech.timemanagement.timesheet.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class TimeSheetProjSettingResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private Integer maxHrs = 0;
    private Long weekSeqId;
    private Integer weeekStartDay = 0;
    private Integer weeekEndDay = 0;

    public TimeSheetProjSettingResp() {
    }

    public Integer getMaxHrs() {
        return maxHrs;
    }

    public void setMaxHrs(Integer maxHrs) {
        this.maxHrs = maxHrs;
    }

    public Long getWeekSeqId() {
        return weekSeqId;
    }

    public void setWeekSeqId(Long weekSeqId) {
        this.weekSeqId = weekSeqId;
    }

    public Integer getWeeekStartDay() {
        return weeekStartDay;
    }

    public void setWeeekStartDay(Integer weeekStartDay) {
        this.weeekStartDay = weeekStartDay;
    }

    public Integer getWeeekEndDay() {
        return weeekEndDay;
    }

    public void setWeeekEndDay(Integer weeekEndDay) {
        this.weeekEndDay = weeekEndDay;
    }

}
