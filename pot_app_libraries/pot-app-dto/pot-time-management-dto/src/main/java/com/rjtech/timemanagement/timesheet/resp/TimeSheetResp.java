package com.rjtech.timemanagement.timesheet.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;

public class TimeSheetResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;
    private TimeSheetTO timeSheetTO = new TimeSheetTO();
    private List<TimeSheetTO> timeSheetTOs = new ArrayList<TimeSheetTO>();
    private List<String> timeSheetDays = new ArrayList<String>();
    private boolean enableTimeSheet;

    private Map<Long, LabelKeyTO> costCodeMap = null;
    private Map<Long, LabelKeyTO> empWageFactorMap = null;
    private Map<Long, LabelKeyTO> empRegMap = null;

    public TimeSheetResp() {
        costCodeMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        empWageFactorMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        empRegMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public TimeSheetTO getTimeSheetTO() {
        return timeSheetTO;
    }

    public void setTimeSheetTO(TimeSheetTO timeSheetTO) {
        this.timeSheetTO = timeSheetTO;
    }

    public List<TimeSheetTO> getTimeSheetTOs() {
        return timeSheetTOs;
    }

    public void setTimeSheetTOs(List<TimeSheetTO> timeSheetTOs) {
        this.timeSheetTOs = timeSheetTOs;
    }

    public List<String> getTimeSheetDays() {
        return timeSheetDays;
    }

    public void setTimeSheetDays(List<String> timeSheetDays) {
        this.timeSheetDays = timeSheetDays;
    }

    public boolean isEnableTimeSheet() {
        return enableTimeSheet;
    }

    public void setEnableTimeSheet(boolean enableTimeSheet) {
        this.enableTimeSheet = enableTimeSheet;
    }

    public Map<Long, LabelKeyTO> getCostCodeMap() {
        return costCodeMap;
    }

    public void setCostCodeMap(Map<Long, LabelKeyTO> costCodeMap) {
        this.costCodeMap = costCodeMap;
    }

    public Map<Long, LabelKeyTO> getEmpWageFactorMap() {
        return empWageFactorMap;
    }

    public void setEmpWageFactorMap(Map<Long, LabelKeyTO> empWageFactorMap) {
        this.empWageFactorMap = empWageFactorMap;
    }

    public Map<Long, LabelKeyTO> getEmpRegMap() {
        return empRegMap;
    }

    public void setEmpRegMap(Map<Long, LabelKeyTO> empRegMap) {
        this.empRegMap = empRegMap;
    }

}
