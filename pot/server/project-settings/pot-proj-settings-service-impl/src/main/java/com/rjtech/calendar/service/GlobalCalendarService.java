package com.rjtech.calendar.service;

import com.rjtech.calendar.req.CalDeactiveReq;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.req.CalRegularDaysSaveReq;
import com.rjtech.calendar.req.CalSaveReq;
import com.rjtech.calendar.req.CalSpecialDaysSaveReq;
import com.rjtech.calendar.resp.CalDaysResp;
import com.rjtech.calendar.resp.CalRegularDaysResp;
import com.rjtech.calendar.resp.CalendarResp;
import com.rjtech.calendar.resp.CalSpecialDaysResp;

public interface GlobalCalendarService {

    CalendarResp getGlobalCalendarsByClientId(CalGetReq calGetReq);

    CalendarResp getProjCalendarsByClientId(CalGetReq calGetReq);

    CalendarResp getGlobalCalendarById(CalGetReq calGetReq);

    public CalendarResp getProjCalendarById(CalGetReq calGetReq);

    CalendarResp getProjCalendarsByProject(CalGetReq calGetReq);

    void saveGlobalCalendars(CalSaveReq calSaveReq);

    CalRegularDaysResp getGlobalCalRegularDays(CalGetReq calGetReq);

    CalSpecialDaysResp getGlobalCalSpecialDaysByMonth(CalGetReq calGetReq);

    void saveGlobalCalRegularDays(CalRegularDaysSaveReq calRegularDaysSaveReq);

    public void saveProjCalRegularDays(CalRegularDaysSaveReq calRegularDaysSaveReq);

    void saveGlobalCalSpecialDays(CalSpecialDaysSaveReq calSpecialDaysSaveReq);

    public void saveProjCalSpecialDays(CalSpecialDaysSaveReq calSpecialDaysSaveReq);

    CalDaysResp getGlobalCalDays(CalGetReq calGetReq);

    public CalDaysResp getProjCalDays(CalGetReq calGetReq);

    void copyGlobalCalendar(CalSaveReq calSaveReq);

    void deactiveGlobalCalendarsByClientId(CalDeactiveReq calGetReq);

    public void deactiveProjCalendarsByProject(CalDeactiveReq calGetReq);

}
