package com.rjtech.mw.service.impl.calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.calendar.constans.CalendarURLConstants;
import com.rjtech.calendar.req.CalDeactiveReq;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.req.CalRegularDaysSaveReq;
import com.rjtech.calendar.req.CalSaveReq;
import com.rjtech.calendar.req.CalSpecialDaysSaveReq;
import com.rjtech.calendar.resp.CalDaysResp;
import com.rjtech.calendar.resp.CalendarResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.calendar.MWCalendarService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwCalendarService")
@RJSService(modulecode = "mwCalendarService")
@Transactional
public class MWCalendarServiceImpl extends RestConfigServiceImpl implements MWCalendarService {

    public CalendarResp getCalendars(CalGetReq calGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calGetReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.GET_CALENDARS);
        return AppUtils.fromJson(strResponse.getBody(), CalendarResp.class);
    }

    public CalendarResp getCalendarById(CalGetReq calGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calGetReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.GET_CALENDAR_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), CalendarResp.class);
    }

    public CalendarResp saveCalendars(CalSaveReq calSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calSaveReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.SAVE_CALENDARS);
        return AppUtils.fromJson(strResponse.getBody(), CalendarResp.class);
    }

    public CalDaysResp saveCalRegularDays(CalRegularDaysSaveReq calRegularDaysSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calRegularDaysSaveReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.SAVE_CAL_REGULAR_DAYS);
        return AppUtils.fromJson(strResponse.getBody(), CalDaysResp.class);
    }

    public CalDaysResp saveCalSpecialDays(CalSpecialDaysSaveReq calSpecialDaysSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calSpecialDaysSaveReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.SAVE_CAL_SPECIAL_DAYS);
        return AppUtils.fromJson(strResponse.getBody(), CalDaysResp.class);
    }

    public CalDaysResp getCalDays(CalGetReq calGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calGetReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.GET_CAL_DAYS);
        return AppUtils.fromJson(strResponse.getBody(), CalDaysResp.class);
    }

    public CalendarResp getCalendarsByClientId(CalGetReq calGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calGetReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.GET_CALENDARS_BY_CLIENT_ID);
        return AppUtils.fromJson(strResponse.getBody(), CalendarResp.class);
    }

    public CalendarResp copyCalendar(CalSaveReq calSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calSaveReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.COPY_CALENDAR);
        return AppUtils.fromJson(strResponse.getBody(), CalendarResp.class);
    }

    public CalendarResp deleteCalendars(CalDeactiveReq calGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calGetReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.DELEATE_CALENDAR);
        return AppUtils.fromJson(strResponse.getBody(), CalendarResp.class);
    }

}
