package com.rjtech.calendar.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalSpecialDaysTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class CalSpecialDaysSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5270095009496332835L;
    private List<CalSpecialDaysTO> calenderSpecialDaysTOs = new ArrayList<CalSpecialDaysTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long calendarId;
    private String month;

    public List<CalSpecialDaysTO> getCalenderSpecialDaysTOs() {
        return calenderSpecialDaysTOs;
    }

    public void setCalenderSpecialDaysTOs(List<CalSpecialDaysTO> calenderSpecialDaysTOs) {
        this.calenderSpecialDaysTOs = calenderSpecialDaysTOs;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

}
