package com.rjtech.mw.service.calendar;

import com.rjtech.calendar.req.CalDeactiveReq;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.req.CalRegularDaysSaveReq;
import com.rjtech.calendar.req.CalSaveReq;
import com.rjtech.calendar.req.CalSpecialDaysSaveReq;
import com.rjtech.calendar.resp.CalDaysResp;
import com.rjtech.calendar.resp.CalendarResp;

public interface MWCalendarService {

    CalendarResp getCalendars(CalGetReq calGetReq);

    CalendarResp getCalendarById(CalGetReq calGetReq);

    CalendarResp saveCalendars(CalSaveReq calSaveReq);

    CalDaysResp saveCalRegularDays(CalRegularDaysSaveReq calRegularDaysSaveReq);

    CalDaysResp saveCalSpecialDays(CalSpecialDaysSaveReq calSpecialDaysSaveReq);

    CalDaysResp getCalDays(CalGetReq calGetReq);

    CalendarResp getCalendarsByClientId(CalGetReq calGetReq);

    CalendarResp copyCalendar(CalSaveReq calSaveReq);

    CalendarResp deleteCalendars(CalDeactiveReq calGetReq);

}
