package com.rjtech.calendar.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class CalRegularDaysSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5270095009496332835L;
    private List<CalRegularDaysTO> calenderRegularDaysTOs = new ArrayList<CalRegularDaysTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long id;
    private Long calendarId;
    private String month;

    public List<CalRegularDaysTO> getCalenderRegularDaysTOs() {
        return calenderRegularDaysTOs;
    }

    public void setCalenderRegularDaysTOs(List<CalRegularDaysTO> calenderRegularDaysTOs) {
        this.calenderRegularDaysTOs = calenderRegularDaysTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}
