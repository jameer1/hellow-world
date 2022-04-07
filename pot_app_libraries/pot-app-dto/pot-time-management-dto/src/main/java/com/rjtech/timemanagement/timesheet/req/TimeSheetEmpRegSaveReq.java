package com.rjtech.timemanagement.timesheet.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpRegTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;

public class TimeSheetEmpRegSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<TimeSheetEmpRegTO> timeSheetEmpRegTOs = new ArrayList<TimeSheetEmpRegTO>();
    private TimeSheetTO timeSheetTO = new TimeSheetTO();

    public List<TimeSheetEmpRegTO> getTimeSheetEmpRegTOs() {
        return timeSheetEmpRegTOs;
    }

    public void setTimeSheetEmpRegTOs(List<TimeSheetEmpRegTO> timeSheetEmpRegTOs) {
        this.timeSheetEmpRegTOs = timeSheetEmpRegTOs;
    }

    public TimeSheetTO getTimeSheetTO() {
        return timeSheetTO;
    }

    public void setTimeSheetTO(TimeSheetTO timeSheetTO) {
        this.timeSheetTO = timeSheetTO;
    }

}
