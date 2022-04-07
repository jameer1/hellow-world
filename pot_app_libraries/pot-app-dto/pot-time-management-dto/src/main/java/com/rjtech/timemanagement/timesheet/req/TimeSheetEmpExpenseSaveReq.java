package com.rjtech.timemanagement.timesheet.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpExpenseTO;

public class TimeSheetEmpExpenseSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<TimeSheetEmpExpenseTO> timeSheetEmpExpenseTOs = new ArrayList<TimeSheetEmpExpenseTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long timeSheetId = null;
    private Long timeSheetEmpId = null;

    public List<TimeSheetEmpExpenseTO> getTimeSheetEmpExpenseTOs() {
        return timeSheetEmpExpenseTOs;
    }

    public void setTimeSheetEmpExpenseTOs(List<TimeSheetEmpExpenseTO> timeSheetEmpExpenseTOs) {
        this.timeSheetEmpExpenseTOs = timeSheetEmpExpenseTOs;
    }

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

}
