package com.rjtech.timemanagement.timesheet.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpDtlTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;

public class TimeSheetDtlSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<TimeSheetEmpDtlTO> timeSheetEmpDtlTOs = new ArrayList<TimeSheetEmpDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private TimeSheetTO timeSheetTO = new TimeSheetTO();

    public List<TimeSheetEmpDtlTO> getTimeSheetEmpDtlTOs() {
        return timeSheetEmpDtlTOs;
    }

    public void setTimeSheetEmpDtlTOs(List<TimeSheetEmpDtlTO> timeSheetEmpDtlTOs) {
        this.timeSheetEmpDtlTOs = timeSheetEmpDtlTOs;
    }

    public TimeSheetTO getTimeSheetTO() {
        return timeSheetTO;
    }

    public void setTimeSheetTO(TimeSheetTO timeSheetTO) {
        this.timeSheetTO = timeSheetTO;
    }

}
