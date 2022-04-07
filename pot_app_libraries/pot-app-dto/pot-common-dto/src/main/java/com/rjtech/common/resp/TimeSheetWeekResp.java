package com.rjtech.common.resp;

import com.rjtech.common.dto.TimeSheetWeekDtlTO;

public class TimeSheetWeekResp extends AppResp {

    private static final long serialVersionUID = 6734601521856185372L;
    private TimeSheetWeekDtlTO timeSheetWeekDtlTO = null;

    public TimeSheetWeekDtlTO getTimeSheetWeekDtlTO() {
        return timeSheetWeekDtlTO;
    }

    public void setTimeSheetWeekDtlTO(TimeSheetWeekDtlTO timeSheetWeekDtlTO) {
        this.timeSheetWeekDtlTO = timeSheetWeekDtlTO;
    }

}
