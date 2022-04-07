package com.rjtech.timemanagement.timesheet.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;

public class TimeSheetDaysResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private TimeSheetTO timeSheetTO = new TimeSheetTO();

    public TimeSheetDaysResp() {
    }

    public TimeSheetTO getTimeSheetTO() {
        return timeSheetTO;
    }

    public void setTimeSheetTO(TimeSheetTO timeSheetTO) {
        this.timeSheetTO = timeSheetTO;
    }

}
