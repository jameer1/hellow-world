package com.rjtech.mw.service.impl.calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.calendar.constans.CalendarURLConstants;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.resp.CalendarMapResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.calendar.MWCalendarMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwCalendarMapService")
@RJSService(modulecode = "mwCalendarMapService")
@Transactional
public class MWCalendarMapServiceImpl extends RestConfigServiceImpl implements MWCalendarMapService {

    public CalendarMapResp getGlobalCalendarMap(CalGetReq calGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calGetReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.GET_GLOBAL_CALENDAR_MAP);
        return AppUtils.fromJson(strResponse.getBody(), CalendarMapResp.class);
    }

    public CalendarMapResp getProjCalendarMap(CalGetReq calGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(calGetReq),
                CalendarURLConstants.CALENDAR_PARH_URL + CalendarURLConstants.GET_PROJECT_CALENDAR_MAP);
        return AppUtils.fromJson(strResponse.getBody(), CalendarMapResp.class);
    }

}
