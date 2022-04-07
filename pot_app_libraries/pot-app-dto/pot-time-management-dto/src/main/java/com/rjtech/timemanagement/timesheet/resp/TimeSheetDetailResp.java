package com.rjtech.timemanagement.timesheet.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpDtlTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;

public class TimeSheetDetailResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;
    private List<TimeSheetEmpDtlTO> timeSheetEmpDtlTOs = null;
    private TimeSheetTO timeSheetTO = new TimeSheetTO();
    private Map<Long, Boolean> empMaxHrsBookedMap = new HashMap<Long, Boolean>();

    public TimeSheetDetailResp() {
        timeSheetEmpDtlTOs = new ArrayList<TimeSheetEmpDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

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

    public Map<Long, Boolean> getEmpMaxHrsBookedMap() {
        return empMaxHrsBookedMap;
    }

    public void setEmpMaxHrsBookedMap(Map<Long, Boolean> empMaxHrsBookedMap) {
        this.empMaxHrsBookedMap = empMaxHrsBookedMap;
    }

}
