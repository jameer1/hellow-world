package com.rjtech.calendar.controller;

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
import com.rjtech.calendar.resp.CalendarResp;
import com.rjtech.calendar.service.GlobalCalendarService;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;

@RestController
@RequestMapping(CalendarURLConstants.CALENDAR_PARH_URL)
public class CalendarController {

    @Autowired
    private GlobalCalendarService globalCalendarService;

    @RequestMapping(value = CalendarURLConstants.GET_CALENDAR_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> getCalendarById(@RequestBody CalGetReq calGetReq) {
        if (CommonUtil.isNonBlankLong(calGetReq.getProjId())) {
            return new ResponseEntity<CalendarResp>(globalCalendarService.getProjCalendarById(calGetReq),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<CalendarResp>(globalCalendarService.getGlobalCalendarById(calGetReq),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = CalendarURLConstants.GET_CALENDARS, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> getCalendars(@RequestBody CalGetReq calGetReq) {
        if (CommonUtil.isNonBlankLong(calGetReq.getProjId())) {
            return new ResponseEntity<CalendarResp>(globalCalendarService.getProjCalendarsByProject(calGetReq),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<CalendarResp>(globalCalendarService.getGlobalCalendarsByClientId(calGetReq),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = CalendarURLConstants.SAVE_CALENDARS, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> saveCalendars(@RequestBody CalSaveReq calSaveReq) {
        CalGetReq calGetReq = new CalGetReq();
        calGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        globalCalendarService.saveGlobalCalendars(calSaveReq);
        if (calSaveReq.getProjId() != null) {
            calGetReq.setProjId(calSaveReq.getProjId());
            calGetReq.setStatus(calSaveReq.getStatus());
            return new ResponseEntity<CalendarResp>(globalCalendarService.getProjCalendarsByProject(calGetReq),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<CalendarResp>(globalCalendarService.getGlobalCalendarsByClientId(calGetReq),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = CalendarURLConstants.SAVE_CAL_REGULAR_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalDaysResp> saveCalRegularDays(@RequestBody CalRegularDaysSaveReq calRegularDaysSaveReq) {
        CalGetReq calGetReq = new CalGetReq();
        calGetReq.setId(calRegularDaysSaveReq.getId());
        calGetReq.setMonth(calRegularDaysSaveReq.getMonth());
        calGetReq.setCalendarId(calRegularDaysSaveReq.getCalendarId());
        calGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isNonBlankLong(calRegularDaysSaveReq.getProjId())) {
            globalCalendarService.saveProjCalRegularDays(calRegularDaysSaveReq);
            calGetReq.setProjId(calRegularDaysSaveReq.getProjId());
            return new ResponseEntity<CalDaysResp>(globalCalendarService.getProjCalDays(calGetReq), HttpStatus.OK);
        } else {
            globalCalendarService.saveGlobalCalRegularDays(calRegularDaysSaveReq);
            return new ResponseEntity<CalDaysResp>(globalCalendarService.getGlobalCalDays(calGetReq), HttpStatus.OK);
        }
    }

    @RequestMapping(value = CalendarURLConstants.SAVE_CAL_SPECIAL_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalDaysResp> saveCalSpecialDays(@RequestBody CalSpecialDaysSaveReq calSpecialDaysSaveReq) {
        CalGetReq calGetReq = new CalGetReq();
        calGetReq.setMonth(calSpecialDaysSaveReq.getMonth());
        calGetReq.setCalendarId(calSpecialDaysSaveReq.getCalendarId());
        calGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isNonBlankLong(calSpecialDaysSaveReq.getProjId())) {
            globalCalendarService.saveProjCalSpecialDays(calSpecialDaysSaveReq);
            calGetReq.setProjId(calSpecialDaysSaveReq.getProjId());
            return new ResponseEntity<CalDaysResp>(globalCalendarService.getProjCalDays(calGetReq), HttpStatus.OK);
        } else {
            globalCalendarService.saveGlobalCalSpecialDays(calSpecialDaysSaveReq);
            return new ResponseEntity<CalDaysResp>(globalCalendarService.getGlobalCalDays(calGetReq), HttpStatus.OK);
        }

    }

    @RequestMapping(value = CalendarURLConstants.GET_CAL_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalDaysResp> getCalDays(@RequestBody CalGetReq calGetReq) {
        if (CommonUtil.isNonBlankLong(calGetReq.getProjId())) {
            return new ResponseEntity<CalDaysResp>(globalCalendarService.getGlobalCalDays(calGetReq), HttpStatus.OK);
        } else {
            return new ResponseEntity<CalDaysResp>(globalCalendarService.getGlobalCalDays(calGetReq), HttpStatus.OK);
        }
    }

    @RequestMapping(value = CalendarURLConstants.GET_CALENDARS_BY_CLIENT_ID, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> getCalendarsByClientId(@RequestBody CalSaveReq calSaveReq) {
        CalGetReq calGetReq = new CalGetReq();
        calGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isNonBlankLong(calSaveReq.getProjId())) {
            return new ResponseEntity<CalendarResp>(globalCalendarService.getProjCalendarsByClientId(calGetReq),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<CalendarResp>(globalCalendarService.getGlobalCalendarsByClientId(calGetReq),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = CalendarURLConstants.COPY_CALENDAR, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> copyCalendar(@RequestBody CalSaveReq calSaveReq) {
        CalGetReq calGetReq = new CalGetReq();
        calGetReq.setCalendarId(calSaveReq.getId());
        calGetReq.setProjId(calSaveReq.getProjId());
        calGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isNonBlankLong(calSaveReq.getProjId())) {
            globalCalendarService.copyGlobalCalendar(calSaveReq);
            return new ResponseEntity<CalendarResp>(globalCalendarService.getProjCalendarsByProject(calGetReq),
                    HttpStatus.OK);
        } else {
            globalCalendarService.copyGlobalCalendar(calSaveReq);
            return new ResponseEntity<CalendarResp>(globalCalendarService.getGlobalCalendarsByClientId(calGetReq),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = CalendarURLConstants.DELEATE_CALENDAR, method = RequestMethod.POST)
    public ResponseEntity<CalendarResp> deactiveCalendars(@RequestBody CalDeactiveReq calGetReq) {
        CalendarResp calResp = new CalendarResp();
        CalGetReq cal = new CalGetReq();
        cal.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isNonBlankLong(calGetReq.getProjId())) {
            globalCalendarService.deactiveProjCalendarsByProject(calGetReq);
            calResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
            return new ResponseEntity<CalendarResp>(globalCalendarService.getProjCalendarsByProject(cal),
                    HttpStatus.OK);

        } else {
            globalCalendarService.deactiveGlobalCalendarsByClientId(calGetReq);
            calResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
            return new ResponseEntity<CalendarResp>(globalCalendarService.getGlobalCalendarsByClientId(cal),
                    HttpStatus.OK);

        }

    }

}
