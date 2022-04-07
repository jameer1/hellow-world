package com.rjtech.timemanagement.timesheet.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpExpenseTO;

public class TimeSheetEmpExpenseResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<TimeSheetEmpExpenseTO> timeSheetEmpExpenseTOs = null;

    public TimeSheetEmpExpenseResp() {
        timeSheetEmpExpenseTOs = new ArrayList<TimeSheetEmpExpenseTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<TimeSheetEmpExpenseTO> getTimeSheetEmpExpenseTOs() {
        return timeSheetEmpExpenseTOs;
    }

    public void setTimeSheetEmpExpenseTOs(List<TimeSheetEmpExpenseTO> timeSheetEmpExpenseTOs) {
        this.timeSheetEmpExpenseTOs = timeSheetEmpExpenseTOs;
    }

}
