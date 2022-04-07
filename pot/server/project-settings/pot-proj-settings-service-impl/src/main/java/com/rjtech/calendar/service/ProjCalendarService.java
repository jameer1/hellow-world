package com.rjtech.calendar.service;

import com.rjtech.calendar.req.CalDeactiveReq;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.req.CalRegularDaysSaveReq;
import com.rjtech.calendar.req.CalSaveReq;
import com.rjtech.calendar.req.CalSpecialDaysSaveReq;
import com.rjtech.calendar.resp.CalDaysResp;
import com.rjtech.calendar.resp.CalRegularDaysResp;
import com.rjtech.calendar.resp.CalSpecialDaysResp;
import com.rjtech.calendar.resp.CalendarResp;

public interface ProjCalendarService {

    CalendarResp getProjCalendarsByProject(CalGetReq calGetReq);

    CalendarResp getProjCalendarById(CalGetReq calGetReq);

    void saveProjCalendars(CalSaveReq calSaveReq);

    CalRegularDaysResp getProjCalRegularDays(CalGetReq calGetReq);

    CalSpecialDaysResp getProjCalSpecialDaysByMonth(CalGetReq calGetReq);

    void saveProjCalRegularDays(CalRegularDaysSaveReq calRegularDaysSaveReq);

    void saveProjCalSpecialDays(CalSpecialDaysSaveReq calSpecialDaysSaveReq);

    CalDaysResp getProjCalDays(CalGetReq calGetReq);

    void copyProjCalendar(CalSaveReq calSaveReq);

    CalendarResp getProjCalendarsByClientId(CalGetReq calGetReq);

    void deactiveProjCalendarsByProject(CalDeactiveReq calGetReq);

}
