package com.rjtech.mw.controller.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.calendar.constans.CalendarURLConstants;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.resp.CalendarMapResp;
import com.rjtech.mw.service.calendar.MWCalendarMapService;

@RestController
@RequestMapping(CalendarURLConstants.CALENDAR_PARH_URL)
public class MWCalendarMapController {

    @Autowired
    private MWCalendarMapService mwCalendarMapService;

    @RequestMapping(value = CalendarURLConstants.GET_GLOBAL_CALENDAR_MAP, method = RequestMethod.POST)
    public ResponseEntity<CalendarMapResp> getGlobalCalendarMap(@RequestBody CalGetReq calGetReq) {
        return new ResponseEntity<CalendarMapResp>(mwCalendarMapService.getGlobalCalendarMap(calGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CalendarURLConstants.GET_PROJECT_CALENDAR_MAP, method = RequestMethod.POST)
    public ResponseEntity<CalendarMapResp> getProjCalendarMap(@RequestBody CalGetReq calGetReq) {
        return new ResponseEntity<CalendarMapResp>(mwCalendarMapService.getProjCalendarMap(calGetReq), HttpStatus.OK);
    }

}
