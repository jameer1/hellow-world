package com.rjtech.calendar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.calendar.repository.GlobalCalendarRepository;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.resp.CalendarMapResp;
import com.rjtech.calendar.service.CalendarMapService;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "calendarMapService")
@RJSService(modulecode = "calendarMapService")
@Transactional
public class CalendarMapServiceImpl implements CalendarMapService {

    @Autowired
    private GlobalCalendarRepository globalCalendarRepository;

    private String globalCalender = "GCAL";

    public CalendarMapResp getGlobalCalendarMap(CalGetReq calGetReq) {
        CalendarMapResp calendarMapResp = new CalendarMapResp();

        List<GlobalCalEntity> globalCalEntities = globalCalendarRepository
                .findGlobalCalendarsByClientId(AppUserUtils.getClientId(), calGetReq.getStatus(), globalCalender);
        String name = null;
        if (CommonUtil.isListHasData(globalCalEntities)) {
            for (GlobalCalEntity globalCalEntity : globalCalEntities) {
                if (CommonUtil.isNotBlankStr(globalCalEntity.getName())) {
                    name = globalCalEntity.getName();
                    calendarMapResp.getCalendarUniqueMap().put(name.toUpperCase().trim(), globalCalEntity.getStatus());
                }
            }
        }
        return calendarMapResp;
    }

    public CalendarMapResp getProjCalendarMap(CalGetReq calGetReq) {
        CalendarMapResp calendarMapResp = new CalendarMapResp();
        List<GlobalCalEntity> projCalEntities = globalCalendarRepository.findAllCalendars(calGetReq.getProjId(),
                calGetReq.getStatus());
        String name = null;
        if (CommonUtil.isListHasData(projCalEntities)) {
            for (GlobalCalEntity projCalEntity : projCalEntities) {
                if (CommonUtil.isNotBlankStr(projCalEntity.getName())) {
                    name = projCalEntity.getName();
                    calendarMapResp.getCalendarUniqueMap().put(name.toUpperCase().trim(), projCalEntity.getStatus());
                }
            }
        }
        return calendarMapResp;
    }

}
