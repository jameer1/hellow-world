package com.rjtech.timemanagement.timesheet.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpDtlTO;

public class TimeSheetEmpDetailResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;
    private TimeSheetEmpDtlTO timeSheetEmpDtlTO = new TimeSheetEmpDtlTO();
    private List<String> timeSheetDays = new ArrayList<String>();

    public TimeSheetEmpDetailResp() {

    }

    public TimeSheetEmpDtlTO getTimeSheetEmpDtlTO() {
        return timeSheetEmpDtlTO;
    }

    public void setTimeSheetEmpDtlTO(TimeSheetEmpDtlTO timeSheetEmpDtlTO) {
        this.timeSheetEmpDtlTO = timeSheetEmpDtlTO;
    }

    public List<String> getTimeSheetDays() {
        return timeSheetDays;
    }

    public void setTimeSheetDays(List<String> timeSheetDays) {
        this.timeSheetDays = timeSheetDays;
    }

}
