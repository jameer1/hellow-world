package com.rjtech.calendar.resp;

import java.util.HashMap;
import java.util.Map;

public class CalendarMapResp {

    Map<String, Integer> calendarUniqueMap = new HashMap<String, Integer>();

    public Map<String, Integer> getCalendarUniqueMap() {
        return calendarUniqueMap;
    }

    public void setCalendarUniqueMap(Map<String, Integer> calendarUniqueMap) {
        this.calendarUniqueMap = calendarUniqueMap;
    }

}
