package com.rjtech.timemanagement.timesheet.req;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpTaskTO;

public class TimeSheetEmpTaskSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private Map<String, TimeSheetEmpTaskTO> timeSheetEmpTaskMap = new HashMap<String, TimeSheetEmpTaskTO>();
    private Long timeSheetId;
    private Long timeSheetEmpId;

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public Long getTimeSheetEmpId() {
        return timeSheetEmpId;
    }

    public void setTimeSheetEmpId(Long timeSheetEmpId) {
        this.timeSheetEmpId = timeSheetEmpId;
    }

    public Map<String, TimeSheetEmpTaskTO> getTimeSheetEmpTaskMap() {
        return timeSheetEmpTaskMap;
    }

    public void setTimeSheetEmpTaskMap(Map<String, TimeSheetEmpTaskTO> timeSheetEmpTaskMap) {
        this.timeSheetEmpTaskMap = timeSheetEmpTaskMap;
    }

}
