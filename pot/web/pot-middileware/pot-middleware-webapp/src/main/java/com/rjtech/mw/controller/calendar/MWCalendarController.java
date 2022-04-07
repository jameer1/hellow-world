package com.rjtech.mw.controller.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.calendar.constans.CalendarURLConstants;
import com.rjtech.calendar.req.CalDeactiveReq;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.req.CalRegularDaysSaveReq;
import com.rjtech.calendar.req.CalSaveReq;
import com.rjtech.calendar.req.CalSpecialDaysSaveReq;
import com.rjtech.calendar.resp.CalDaysResp;
import com.rjtech.calendar.resp.CalRegularDaysResp;
import com.rjtech.calendar.resp.CalendarResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.calendar.resp.CalSpecialDaysResp;
import com.rjtech.mw.service.calendar.MWCalendarService;

@RestController
@RequestMapping(CalendarURLConstants.CALENDAR_PARH_URL)
public class MWCalendarController {

    @Autowired
    private MWCalendarService mwCalendarService;

    @RequestMapping(value = CalendarURLConstants.GET_CALENDAR_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> getCalendarById(@RequestBody CalGetReq calGetReq) {
        return new ResponseEntity<CalendarResp>(mwCalendarService.getCalendarById(calGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.GET_CALENDARS, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> getCalendars(@RequestBody CalGetReq calGetReq) {
        return new ResponseEntity<CalendarResp>(mwCalendarService.getCalendars(calGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.SAVE_CALENDARS, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> saveCalendars(@RequestBody CalSaveReq calSaveReq) {
        return new ResponseEntity<CalendarResp>(mwCalendarService.saveCalendars(calSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.SAVE_CAL_REGULAR_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalDaysResp> saveCalRegularDays(@RequestBody CalRegularDaysSaveReq calRegularDaysSaveReq) {
        return new ResponseEntity<CalDaysResp>(mwCalendarService.saveCalRegularDays(calRegularDaysSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.SAVE_CAL_SPECIAL_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalDaysResp> saveCalSpecialDays(@RequestBody CalSpecialDaysSaveReq calSpecialDaysSaveReq) {
        return new ResponseEntity<CalDaysResp>(mwCalendarService.saveCalSpecialDays(calSpecialDaysSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.GET_CAL_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalDaysResp> getCalDays(@RequestBody CalGetReq calGetReq) {
        return new ResponseEntity<CalDaysResp>(mwCalendarService.getCalDays(calGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.GET_CALENDARS_BY_CLIENT_ID, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> getCalendarsByClientId(@RequestBody CalGetReq calGetReq) {
        return new ResponseEntity<CalendarResp>(mwCalendarService.getCalendarsByClientId(calGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.COPY_CALENDAR, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> copyCalendar(@RequestBody CalSaveReq calSaveReq) {
        return new ResponseEntity<CalendarResp>(mwCalendarService.copyCalendar(calSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = CalendarURLConstants.DELEATE_CALENDAR, method = RequestMethod.POST)

    public ResponseEntity<CalendarResp> deactiveCalendars(@RequestBody CalDeactiveReq calGetReq) {
        return new ResponseEntity<CalendarResp>(mwCalendarService.deleteCalendars(calGetReq), HttpStatus.OK);

    }
}
