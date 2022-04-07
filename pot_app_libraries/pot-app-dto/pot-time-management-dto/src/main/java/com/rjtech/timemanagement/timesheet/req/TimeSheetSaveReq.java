package com.rjtech.timemanagement.timesheet.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;

public class TimeSheetSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<TimeSheetTO> timeSheetTOs = new ArrayList<TimeSheetTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private Long timeSheetId;

    public List<TimeSheetTO> getTimeSheetTOs() {
        return timeSheetTOs;
    }

    public void setTimeSheetTOs(List<TimeSheetTO> timeSheetTOs) {
        this.timeSheetTOs = timeSheetTOs;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

}
