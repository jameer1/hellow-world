package com.rjtech.timemanagement.timesheet.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpDtlTO;

public class TimeSheetEmpWorkDtlSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private TimeSheetEmpDtlTO timeSheetEmpDtlTO = new TimeSheetEmpDtlTO();
    private Long timeSheetId;

    public TimeSheetEmpDtlTO getTimeSheetEmpDtlTO() {
        return timeSheetEmpDtlTO;
    }

    public void setTimeSheetEmpDtlTO(TimeSheetEmpDtlTO timeSheetEmpDtlTO) {
        this.timeSheetEmpDtlTO = timeSheetEmpDtlTO;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

}
