package com.rjtech.calendar.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.calendar.dto.CalDaysTO;
import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.calendar.dto.CalSpecialDaysTO;
import com.rjtech.calendar.dto.CalTO;
import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.calendar.model.GlobalCalRegularDaysEntity;
import com.rjtech.calendar.model.GlobalCalSpecialDaysEntity;
import com.rjtech.calendar.repository.GlobalCalRegularDaysRepository;
import com.rjtech.calendar.repository.GlobalCalSpecialDaysRepository;
import com.rjtech.calendar.repository.GlobalCalendarRepository;
import com.rjtech.calendar.req.CalDeactiveReq;
import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.req.CalRegularDaysSaveReq;
import com.rjtech.calendar.req.CalSaveReq;
import com.rjtech.calendar.req.CalSpecialDaysSaveReq;
import com.rjtech.calendar.resp.CalDaysResp;
import com.rjtech.calendar.resp.CalRegularDaysResp;
import com.rjtech.calendar.resp.CalSpecialDaysResp;
import com.rjtech.calendar.resp.CalendarResp;
import com.rjtech.calendar.service.GlobalCalendarService;
import com.rjtech.calendar.service.handler.GlobalCalHandler;
import com.rjtech.calendar.service.handler.GlobalCalRegularDaysHandler;
import com.rjtech.calendar.service.handler.GlobalCalSpecialDaysHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "globalCalendarService")
@RJSService(modulecode = "globalCalendarService")
@Transactional
public class GlobalCalendarServiceImpl implements GlobalCalendarService {

    private static final Logger log = LoggerFactory.getLogger(GlobalCalendarServiceImpl.class);

    @Autowired
    private GlobalCalendarRepository globalCalendarRepository;

    @Autowired
    private GlobalCalRegularDaysRepository globalCalRegularDaysRepository;

    @Autowired
    private GlobalCalSpecialDaysRepository globalCalSpecialDaysRepository;

    @Autowired
    private ProjGeneralRepository projGeneralRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    private boolean assignedFlag;

    private String globalCalType = "GCAL";

    private String projectCalType = "PCAL";

    public CalendarResp getGlobalCalendarsByClientId(CalGetReq calGetReq) {
        List<GlobalCalEntity> globalCalenderEntities = globalCalendarRepository
                .findGlobalCalendarsByClientId(AppUserUtils.getClientId(), calGetReq.getStatus(), globalCalType);
        CalendarResp globalCalResp = new CalendarResp();
        for (GlobalCalEntity globalCalenderEntity : globalCalenderEntities) {
            if (null != globalCalenderEntity.getId()) {
                assignedFlag = checkCalenderProjAssign(globalCalenderEntity.getId());
            }
            globalCalenderEntity.setProjectAssigned(assignedFlag);
            globalCalResp.getCalenderTOs().add(GlobalCalHandler.convertEntityToPOJO(globalCalenderEntity));
        }
        return globalCalResp;
    }

    public CalendarResp getProjCalendarsByProject(CalGetReq calGetReq) {
        List<GlobalCalEntity> calenderEntities = globalCalendarRepository
                .findProjCalendarsByProject(calGetReq.getProjId(), calGetReq.getStatus());
        CalendarResp globalCalResp = new CalendarResp();
        for (GlobalCalEntity calenderEntity : calenderEntities) {
            if (null != calenderEntity.getId()) {
                assignedFlag = checkCalenderProjAssign(calenderEntity.getId());
            }
            calenderEntity.setProjectAssigned(assignedFlag);
            globalCalResp.getCalenderTOs().add(GlobalCalHandler.convertEntityToPOJO(calenderEntity));
        }
        return globalCalResp;
    }

    public CalendarResp getProjCalendarsByClientId(CalGetReq calGetReq) {
        log.info("Client Id {}  - Status {}", AppUserUtils.getClientId(), calGetReq.getStatus());
        List<GlobalCalEntity> calenderEntities = globalCalendarRepository
                .findProjCalendarsByClientId(AppUserUtils.getClientId(), calGetReq.getStatus());
        CalendarResp globalCalResp = new CalendarResp();
        log.info("Calendar Entities Size {}", calenderEntities.size());
        for (GlobalCalEntity calenderEntity : calenderEntities) {
            globalCalResp.getCalenderTOs().add(GlobalCalHandler.convertEntityToPOJO(calenderEntity));
        }
        return globalCalResp;
    }

    public CalendarResp getProjCalendarById(CalGetReq calGetReq) {
        List<GlobalCalEntity> calenderEntities = globalCalendarRepository.findProjCalendarById(calGetReq.getId(),
                calGetReq.getStatus());
        CalendarResp globalCalResp = new CalendarResp();
        for (GlobalCalEntity calenderEntity : calenderEntities) {
            globalCalResp.getCalenderTOs().add(GlobalCalHandler.convertEntityToPOJO(calenderEntity));
        }
        return globalCalResp;
    }

    public boolean checkCalenderProjAssign(Long id) {
        List<Object[]> projAssignedList = projGeneralRepository.isAssignedToProject(id, 1);
        boolean flag = projAssignedList.size() > 0 ? true : false;
        return flag;
    }

    public CalendarResp getGlobalCalendarById(CalGetReq calGetReq) {
        List<GlobalCalEntity> calenderEntities = globalCalendarRepository.findGlobalCalendarById(calGetReq.getId(),
                calGetReq.getStatus());
        CalendarResp globalCalResp = new CalendarResp();
        for (GlobalCalEntity calenderEntity : calenderEntities) {
            globalCalResp.getCalenderTOs().add(GlobalCalHandler.convertEntityToPOJO(calenderEntity));
        }
        return globalCalResp;
    }

    public void saveGlobalCalendars(CalSaveReq calSaveReq) {
        List<GlobalCalEntity> globalCalenderEntities = new ArrayList<GlobalCalEntity>();
        for (CalTO calenderTO : calSaveReq.getCalenderTOs()) {
            Integer defaultValue = calenderTO.getCalDefaultValue();
            if (CommonUtil.objectNotNull(defaultValue) && defaultValue == 1) {
                GlobalCalEntity defaultCalEntity = globalCalendarRepository
                        .getDefaultGlobalCalendar(AppUserUtils.getClientId());
                if (CommonUtil.objectNotNull(defaultCalEntity)) {
                    defaultCalEntity.setCalDefaultValue(null);
                    globalCalenderEntities.add(defaultCalEntity);
                }
            }
        }
        for (CalTO calenderTO : calSaveReq.getCalenderTOs()) {
            globalCalenderEntities.add(GlobalCalHandler.convertPOJOToEntity(calenderTO, epsProjRepository));
        }
        globalCalendarRepository.save(globalCalenderEntities);
    }

    public CalRegularDaysResp getGlobalCalRegularDays(CalGetReq calGetReq) {
        List<GlobalCalRegularDaysEntity> regularDaysEntities = globalCalRegularDaysRepository
                .findGlobalCalRegularDays(calGetReq.getCalendarId(), calGetReq.getStatus());
        CalRegularDaysResp globalCalRegularDaysResp = new CalRegularDaysResp();
        for (GlobalCalRegularDaysEntity regularDaysEntity : regularDaysEntities) {
            globalCalRegularDaysResp.getCalenderRegularDaysTOs()
                    .add(GlobalCalRegularDaysHandler.convertEntityToPOJO(regularDaysEntity));
        }
        return globalCalRegularDaysResp;
    }

    public CalDaysResp getGlobalCalDays(CalGetReq calGetReq) {
        CalDaysResp calDaysResp = new CalDaysResp();
        Date dateFormat = null;
        SimpleDateFormat sm = new SimpleDateFormat("MM-yyyy");

        try {
            if (CommonUtil.isBlankStr(calGetReq.getMonth())) {
                dateFormat = new Date();

            } else {
                dateFormat = sm.parse(calGetReq.getMonth());

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String month = null;
        try {
            month = sm.format(dateFormat);

        } catch (Exception e) {
            e.printStackTrace();
        }
        calGetReq.setMonth(month);
        Map<Integer, CalDaysTO> calenderDaysMap = new HashMap<Integer, CalDaysTO>();
        List<GlobalCalSpecialDaysEntity> specialDaysEntities = globalCalSpecialDaysRepository
                .findGlobalCalSpecialDaysByMonth(calGetReq.getCalendarId(), calGetReq.getStatus(),
                        calGetReq.getMonth());
        CalDaysTO calenderDaysTO = null;
        for (GlobalCalSpecialDaysEntity specialDaysEntity : specialDaysEntities) {
            Calendar date = Calendar.getInstance();
            date.setTime(specialDaysEntity.getDate());
            if (calenderDaysMap.get(date.get(Calendar.DAY_OF_MONTH)) == null) {
                calenderDaysTO = new CalDaysTO();
                calenderDaysTO.setDate(specialDaysEntity.getDate());
                if (specialDaysEntity.isHoliday()) {
                    calenderDaysTO.setDayType(2);
                    calenderDaysTO.setHoliday(true);
                } else {
                    calenderDaysTO.setDayType(3);
                }
                calenderDaysMap.put(date.get(Calendar.DAY_OF_MONTH), calenderDaysTO);
            }
        }

        Calendar dateFormatDate = Calendar.getInstance();
        dateFormatDate.setTime(dateFormat);
        Calendar mycal = new GregorianCalendar(dateFormatDate.get(Calendar.YEAR), dateFormatDate.get(Calendar.MONTH),
                dateFormatDate.get(Calendar.DAY_OF_MONTH));

        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<GlobalCalRegularDaysEntity> regularDaysEntities = globalCalRegularDaysRepository
                .findGlobalCalRegularDays(calGetReq.getCalendarId(), calGetReq.getStatus());
        CalRegularDaysTO calenderRegularDaysTO = null;
        if (regularDaysEntities.size() > 0) {
            calenderRegularDaysTO = GlobalCalRegularDaysHandler.convertEntityToPOJO(regularDaysEntities.get(0));
        } else {
            calenderRegularDaysTO = new CalRegularDaysTO();
        }
        calDaysResp.setCalenderRegularDaysTO(calenderRegularDaysTO);
        calDaysResp.setCalenderDaysMap(calenderDaysMap);
        Map<Integer, Boolean> daysMap = new HashMap<Integer, Boolean>();
        daysMap.put(1, calenderRegularDaysTO.isSunday());
        daysMap.put(2, calenderRegularDaysTO.isMonday());
        daysMap.put(3, calenderRegularDaysTO.isTuesday());
        daysMap.put(4, calenderRegularDaysTO.isWednesday());
        daysMap.put(5, calenderRegularDaysTO.isThursday());
        daysMap.put(6, calenderRegularDaysTO.isFriday());
        daysMap.put(7, calenderRegularDaysTO.isSaturday());
        int day = 1;
        for (int i = 1; i <= daysInMonth; i++) {
            if (calenderDaysMap.get(i) == null) {
                calenderDaysTO = new CalDaysTO();
                calenderDaysMap.put(i, calenderDaysTO);
                mycal.set(dateFormatDate.get(Calendar.YEAR), dateFormatDate.get(Calendar.MONTH) + 1, i);
                calenderDaysTO.setDate(mycal.getTime());
                if ((i + (dateFormatDate.get(Calendar.DAY_OF_WEEK) - 1)) % 7 == 0) {
                    day = 7;
                } else {
                    day = (i + (dateFormatDate.get(Calendar.DAY_OF_WEEK) - 1)) % 7;
                }
                log.info("Day Print {}", day);
                if (daysMap.get(day)) {
                    calenderDaysTO.setDayType(1);
                } else {
                    calenderDaysTO.setDayType(2);

                }
                mycal.set(dateFormatDate.get(Calendar.YEAR), dateFormatDate.get(Calendar.MONTH),
                        dateFormatDate.get(Calendar.DAY_OF_MONTH) + i-1);
                
                calenderDaysTO.setDate(mycal.getTime());

            }
        }
        return calDaysResp;

    }

    public void saveGlobalCalRegularDays(CalRegularDaysSaveReq calRegularDaysSaveReq) {
        List<GlobalCalRegularDaysEntity> regularDaysEntities = new ArrayList<GlobalCalRegularDaysEntity>();
        GlobalCalRegularDaysEntity calRegularDaysEntity = null;
        for (CalRegularDaysTO calenderRegularDaysTO : calRegularDaysSaveReq.getCalenderRegularDaysTOs()) {
            if (CommonUtil.isNonBlankLong(calenderRegularDaysTO.getId())) {
                calRegularDaysEntity = globalCalRegularDaysRepository.findOne(calenderRegularDaysTO.getId());
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, -1);
                calRegularDaysEntity.setToDate(c.getTime());
                calRegularDaysEntity.setLatest(false);
                regularDaysEntities.add(calRegularDaysEntity);
                calenderRegularDaysTO.setId(null);
            }
            calenderRegularDaysTO.setLatest(true);
            calenderRegularDaysTO.setFromDate(new Date());
            regularDaysEntities.add(GlobalCalRegularDaysHandler.convertPOJOToEntity(calenderRegularDaysTO));
        }
        globalCalRegularDaysRepository.save(regularDaysEntities);

    }

    public void saveProjCalRegularDays(CalRegularDaysSaveReq calRegularDaysSaveReq) {
        List<GlobalCalRegularDaysEntity> regularDaysEntities = new ArrayList<GlobalCalRegularDaysEntity>();
        GlobalCalRegularDaysEntity projCalRegularDaysEntity = null;
        for (CalRegularDaysTO calenderRegularDaysTO : calRegularDaysSaveReq.getCalenderRegularDaysTOs()) {
            if (CommonUtil.isNonBlankLong(calenderRegularDaysTO.getId())) {
                projCalRegularDaysEntity = globalCalRegularDaysRepository.findOne(calenderRegularDaysTO.getId());
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, -1);
                projCalRegularDaysEntity.setToDate(c.getTime());
                projCalRegularDaysEntity.setLatest(false);
                regularDaysEntities.add(projCalRegularDaysEntity);
                calenderRegularDaysTO.setId(null);
            }
            calenderRegularDaysTO.setLatest(true);
            calenderRegularDaysTO.setFromDate(new Date());
            regularDaysEntities.add(GlobalCalRegularDaysHandler.convertPOJOToEntity(calenderRegularDaysTO));
        }
        globalCalRegularDaysRepository.save(regularDaysEntities);
    }

    public CalDaysResp getProjCalDays(CalGetReq calGetReq) {
        CalDaysResp calDaysResp = new CalDaysResp();
        Date dateFormat = null;
        SimpleDateFormat sm = new SimpleDateFormat("MM-yyyy");
        try {
            if (CommonUtil.isBlankStr(calGetReq.getMonth())) {
                dateFormat = new Date();
            } else {

                dateFormat = sm.parse(calGetReq.getMonth());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String month = null;
        try {
            month = sm.format(dateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calGetReq.setMonth(month);
        Map<Integer, CalDaysTO> calenderDaysMap = new HashMap<Integer, CalDaysTO>();
        List<GlobalCalSpecialDaysEntity> specialDaysEntities = globalCalRegularDaysRepository
                .findProjCalSpecialDaysByMonth(calGetReq.getCalendarId(), calGetReq.getStatus(), calGetReq.getMonth());
        CalDaysTO calenderDaysTO = null;
        for (GlobalCalSpecialDaysEntity specialDaysEntity : specialDaysEntities) {
            Calendar date = Calendar.getInstance();
            date.setTime(specialDaysEntity.getDate());
            if (calenderDaysMap.get(date.get(Calendar.DAY_OF_MONTH)) == null) {
                calenderDaysTO = new CalDaysTO();
                calenderDaysTO.setDate(specialDaysEntity.getDate());
                if (specialDaysEntity.isHoliday()) {
                    calenderDaysTO.setDayType(2);
                } else {
                    calenderDaysTO.setDayType(3);
                }
                calenderDaysMap.put(date.get(Calendar.DAY_OF_MONTH), calenderDaysTO);
            }
        }

        Calendar formatDate = Calendar.getInstance();
        formatDate.setTime(dateFormat);
        Calendar mycal = new GregorianCalendar(formatDate.get(Calendar.YEAR), formatDate.get(Calendar.MONTH),
                formatDate.get(Calendar.DAY_OF_MONTH));

        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<GlobalCalRegularDaysEntity> regularDaysEntities = globalCalRegularDaysRepository
                .findProjCalRegularDays(calGetReq.getCalendarId(), calGetReq.getStatus());
        CalRegularDaysTO calenderRegularDaysTO = null;
        if (regularDaysEntities.size() > 0) {
            calenderRegularDaysTO = GlobalCalRegularDaysHandler.convertEntityToPOJO(regularDaysEntities.get(0));
        } else {
            calenderRegularDaysTO = new CalRegularDaysTO();
        }
        calDaysResp.setCalenderRegularDaysTO(calenderRegularDaysTO);
        calDaysResp.setCalenderDaysMap(calenderDaysMap);
        Map<Integer, Boolean> daysMap = new HashMap<Integer, Boolean>();
        daysMap.put(1, calenderRegularDaysTO.isSunday());
        daysMap.put(2, calenderRegularDaysTO.isMonday());
        daysMap.put(3, calenderRegularDaysTO.isTuesday());
        daysMap.put(4, calenderRegularDaysTO.isWednesday());
        daysMap.put(5, calenderRegularDaysTO.isThursday());
        daysMap.put(6, calenderRegularDaysTO.isFriday());
        daysMap.put(7, calenderRegularDaysTO.isSaturday());
        int day = 1;
        for (int i = 1; i <= daysInMonth; i++) {
            if (calenderDaysMap.get(i) == null) {
                calenderDaysTO = new CalDaysTO();
                calenderDaysMap.put(i, calenderDaysTO);
                mycal.set(formatDate.get(Calendar.YEAR), formatDate.get(Calendar.MONTH) + 1, i);
                calenderDaysTO.setDate(mycal.getTime());
                if ((i + formatDate.get(Calendar.DAY_OF_WEEK)) % 7 == 0) {
                    day = 7;
                } else {
                    day = (i + formatDate.get(Calendar.DAY_OF_WEEK)) % 7;
                }
                if (daysMap.get(day)) {
                    calenderDaysTO.setDayType(1);
                } else {
                    calenderDaysTO.setDayType(2);
                }
                mycal.set(formatDate.get(Calendar.YEAR), formatDate.get(Calendar.MONTH),
                        formatDate.get(Calendar.DAY_OF_MONTH) + i);
                calenderDaysTO.setDate(mycal.getTime());
            }
        }
        return calDaysResp;

    }

    public CalSpecialDaysResp getGlobalCalSpecialDaysByMonth(CalGetReq calGetReq) {
        List<GlobalCalSpecialDaysEntity> specialDaysEntities = globalCalSpecialDaysRepository
                .findGlobalCalSpecialDaysByMonth(calGetReq.getCalendarId(), calGetReq.getStatus(),
                        calGetReq.getMonth());
        CalSpecialDaysResp globalCalSpecialDaysResp = new CalSpecialDaysResp();
        for (GlobalCalSpecialDaysEntity specialDaysEntity : specialDaysEntities) {
            globalCalSpecialDaysResp.getCalenderSpecialDaysTOs()
                    .add(GlobalCalSpecialDaysHandler.convertEntityToPOJO(specialDaysEntity));
        }
        return globalCalSpecialDaysResp;
    }

    public void saveGlobalCalSpecialDays(CalSpecialDaysSaveReq calSpecialDaysSaveReq) {
        GlobalCalSpecialDaysEntity globalCalSpecialDaysEntity = null;
        List<GlobalCalSpecialDaysEntity> specialDaysEntities = new ArrayList<GlobalCalSpecialDaysEntity>();
        for (CalSpecialDaysTO calenderSpecialDaysTO : calSpecialDaysSaveReq.getCalenderSpecialDaysTOs()) {
            globalCalSpecialDaysEntity = globalCalSpecialDaysRepository
                    .findExistingByCalDate(calenderSpecialDaysTO.getCalendarId(), calenderSpecialDaysTO.getDate());
            if (CommonUtil.objectNotNull(globalCalSpecialDaysEntity)) {
                calenderSpecialDaysTO.setId(globalCalSpecialDaysEntity.getId());
                globalCalSpecialDaysEntity.setDesc(calenderSpecialDaysTO.getDesc());
                globalCalSpecialDaysEntity.setHoliday(calenderSpecialDaysTO.isHoliday());
            } else {
                specialDaysEntities.add(GlobalCalSpecialDaysHandler.convertPOJOToEntity(calenderSpecialDaysTO));
            }
        }
        globalCalSpecialDaysRepository.save(specialDaysEntities);
    }

    public void saveProjCalSpecialDays(CalSpecialDaysSaveReq calSpecialDaysSaveReq) {
        List<GlobalCalSpecialDaysEntity> specialDaysEntities = new ArrayList<GlobalCalSpecialDaysEntity>();
        GlobalCalSpecialDaysEntity projCalSpecialDaysEntity = null;
        for (CalSpecialDaysTO calenderSpecialDaysTO : calSpecialDaysSaveReq.getCalenderSpecialDaysTOs()) {
            projCalSpecialDaysEntity = globalCalSpecialDaysRepository
                    .findExistingByCalDate(calenderSpecialDaysTO.getCalendarId(), calenderSpecialDaysTO.getDate());
            if (CommonUtil.objectNotNull(projCalSpecialDaysEntity)) {

                calenderSpecialDaysTO.setId(projCalSpecialDaysEntity.getId());
                projCalSpecialDaysEntity.setDesc(calenderSpecialDaysTO.getDesc());
                projCalSpecialDaysEntity.setHoliday(calenderSpecialDaysTO.isHoliday());
            } else {
                specialDaysEntities.add(GlobalCalSpecialDaysHandler.convertPOJOToEntity(calenderSpecialDaysTO));
            }
        }
        globalCalSpecialDaysRepository.save(specialDaysEntities);

    }

    public void copyGlobalCalendar(CalSaveReq calSaveReq) {
        GlobalCalEntity globalCalEntity = null;
        for (CalTO calTO : calSaveReq.getCalenderTOs()) {
            globalCalEntity = GlobalCalHandler.convertPOJOToEntity(calTO, epsProjRepository);

            List<GlobalCalRegularDaysEntity> regularDaysEntities = new ArrayList<GlobalCalRegularDaysEntity>();
            List<GlobalCalSpecialDaysEntity> specialDaysEntities = new ArrayList<GlobalCalSpecialDaysEntity>();

            regularDaysEntities = globalCalRegularDaysRepository.findGlobalCalRegularDays(calSaveReq.getId(),
                    calSaveReq.getStatus());
            specialDaysEntities = globalCalSpecialDaysRepository.findGlobalCalSpecialDays(calSaveReq.getId(),
                    calSaveReq.getStatus());

            List<GlobalCalRegularDaysEntity> calRegularDaysEntities = new ArrayList<GlobalCalRegularDaysEntity>();
            GlobalCalRegularDaysEntity globalCalRegularDaysEntity = null;
            for (GlobalCalRegularDaysEntity regularDaysEntity : regularDaysEntities) {
                globalCalRegularDaysEntity = new GlobalCalRegularDaysEntity();
                BeanUtils.copyProperties(regularDaysEntity, globalCalRegularDaysEntity);
                globalCalRegularDaysEntity.setId(null);
                globalCalRegularDaysEntity.setFromDate(new Date());
                globalCalRegularDaysEntity.setToDate(null);
                globalCalRegularDaysEntity.setLatest(true);
                globalCalRegularDaysEntity.setGlobalCalEntity(globalCalEntity);
                calRegularDaysEntities.add(globalCalRegularDaysEntity);
            }

            List<GlobalCalSpecialDaysEntity> calSpecialDaysEntities = new ArrayList<GlobalCalSpecialDaysEntity>();
            GlobalCalSpecialDaysEntity globalCalSpecialDaysEntity = null;
            for (GlobalCalSpecialDaysEntity specialDaysEntity : specialDaysEntities) {
                globalCalSpecialDaysEntity = new GlobalCalSpecialDaysEntity();
                BeanUtils.copyProperties(specialDaysEntity, globalCalSpecialDaysEntity);
                globalCalSpecialDaysEntity.setId(null);
                globalCalSpecialDaysEntity.setGlobalCalEntity(globalCalEntity);
                calSpecialDaysEntities.add(globalCalSpecialDaysEntity);
            }
            if (null != calSaveReq.getProjId()) {
                globalCalEntity.setCalType(projectCalType);
            } else {
                globalCalEntity.setCalType(globalCalType);
            }
            globalCalendarRepository.save(globalCalEntity);
            globalCalRegularDaysRepository.save(calRegularDaysEntities);
            globalCalSpecialDaysRepository.save(calSpecialDaysEntities);

        }
    }

    public void deactiveGlobalCalendarsByClientId(CalDeactiveReq calGetReq) {

        globalCalendarRepository.deleteGlobalCalendars(calGetReq.getId(), 1);
    }

    public void deactiveProjCalendarsByProject(CalDeactiveReq calGetReq) {
        globalCalendarRepository.deleteProjCalendarsByProject(calGetReq.getId(), 1);
    }
}
