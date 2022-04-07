package com.rjtech.calendar.service;

import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.resp.CalendarMapResp;

public interface CalendarMapService {

    CalendarMapResp getGlobalCalendarMap(CalGetReq calGetReq);

    CalendarMapResp getProjCalendarMap(CalGetReq calGetReq);
}
