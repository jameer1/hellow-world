package com.rjtech.timemanagement.timesheet.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpDtlTO;

public class TimeSheetEmpDtlSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<TimeSheetEmpDtlTO> timeSheetEmpDtlTOs = new ArrayList<TimeSheetEmpDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long timeSheetId;

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public List<TimeSheetEmpDtlTO> getTimeSheetEmpDtlTOs() {
        return timeSheetEmpDtlTOs;
    }

    public void setTimeSheetEmpDtlTOs(List<TimeSheetEmpDtlTO> timeSheetEmpDtlTOs) {
        this.timeSheetEmpDtlTOs = timeSheetEmpDtlTOs;
    }

}
