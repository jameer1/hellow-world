package com.rjtech.mw.service.calendar;

import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.resp.CalendarMapResp;

public interface MWCalendarMapService {

    CalendarMapResp getGlobalCalendarMap(CalGetReq calGetReq);

    CalendarMapResp getProjCalendarMap(CalGetReq calGetReq);

}
