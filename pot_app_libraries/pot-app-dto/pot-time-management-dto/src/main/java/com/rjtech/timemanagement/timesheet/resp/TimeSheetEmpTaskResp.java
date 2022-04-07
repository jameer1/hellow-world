package com.rjtech.timemanagement.timesheet.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.resp.AppResp;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpTaskTO;

public class TimeSheetEmpTaskResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private Map<String, TimeSheetEmpTaskTO> timeSheetEmpTaskMap = null;
    private List<String> timeSheetDays = new ArrayList<String>();

    public List<String> getTimeSheetDays() {
        return timeSheetDays;
    }

    public void setTimeSheetDays(List<String> timeSheetDays) {
        this.timeSheetDays = timeSheetDays;
    }

    public TimeSheetEmpTaskResp() {
        timeSheetEmpTaskMap = new HashMap<String, TimeSheetEmpTaskTO>();
    }

    public Map<String, TimeSheetEmpTaskTO> getTimeSheetEmpTaskMap() {
        return timeSheetEmpTaskMap;
    }

    public void setTimeSheetEmpTaskMap(Map<String, TimeSheetEmpTaskTO> timeSheetEmpTaskMap) {
        this.timeSheetEmpTaskMap = timeSheetEmpTaskMap;
    }

}
