package com.rjtech.calendar.service.handler;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.calendar.model.GlobalCalRegularDaysEntity;
import com.rjtech.common.utils.CommonUtil;

public class GlobalCalRegularDaysHandler {

    public static CalRegularDaysTO convertEntityToPOJO(GlobalCalRegularDaysEntity calRegularDaysEntity) {
        CalRegularDaysTO calenderRegularDaysTO = new CalRegularDaysTO();

        calenderRegularDaysTO.setId(calRegularDaysEntity.getId());

        if (CommonUtil.objectNotNull(calRegularDaysEntity.getGlobalCalEntity())) {
            calenderRegularDaysTO.setCalendarId(calRegularDaysEntity.getGlobalCalEntity().getId());
        }
        calenderRegularDaysTO.setSunday(calRegularDaysEntity.isSunday());
        calenderRegularDaysTO.setMonday(calRegularDaysEntity.isMonday());
        calenderRegularDaysTO.setTuesday(calRegularDaysEntity.isTuesday());
        calenderRegularDaysTO.setWednesday(calRegularDaysEntity.isWednesday());
        calenderRegularDaysTO.setThursday(calRegularDaysEntity.isThursday());
        calenderRegularDaysTO.setFriday(calRegularDaysEntity.isFriday());
        calenderRegularDaysTO.setSaturday(calRegularDaysEntity.isSaturday());

        calenderRegularDaysTO.setLatest(calRegularDaysEntity.isLatest());
        calenderRegularDaysTO.setFromDate(calRegularDaysEntity.getFromDate());
        calenderRegularDaysTO.setToDate(calRegularDaysEntity.getToDate());

        calenderRegularDaysTO.setStatus(calRegularDaysEntity.getStatus());
        return calenderRegularDaysTO;

    }

    public static CalRegularDaysTO getRegularHolidays(GlobalCalRegularDaysEntity calRegularDaysEntity) {
        CalRegularDaysTO calenderRegularDaysTO = new CalRegularDaysTO();

        calenderRegularDaysTO.setId(calRegularDaysEntity.getId());

        if (CommonUtil.objectNotNull(calRegularDaysEntity.getGlobalCalEntity())) {
            calenderRegularDaysTO.setCalendarId(calRegularDaysEntity.getGlobalCalEntity().getId());
        }
        calenderRegularDaysTO.setSunday(!calRegularDaysEntity.isSunday());
        calenderRegularDaysTO.setMonday(!calRegularDaysEntity.isMonday());
        calenderRegularDaysTO.setTuesday(!calRegularDaysEntity.isTuesday());
        calenderRegularDaysTO.setWednesday(!calRegularDaysEntity.isWednesday());
        calenderRegularDaysTO.setThursday(!calRegularDaysEntity.isThursday());
        calenderRegularDaysTO.setFriday(!calRegularDaysEntity.isFriday());
        calenderRegularDaysTO.setSaturday(!calRegularDaysEntity.isSaturday());

        calenderRegularDaysTO.setLatest(calRegularDaysEntity.isLatest());
        calenderRegularDaysTO.setFromDate(calRegularDaysEntity.getFromDate());
        calenderRegularDaysTO.setToDate(calRegularDaysEntity.getToDate());

        calenderRegularDaysTO.setStatus(calRegularDaysEntity.getStatus());
        return calenderRegularDaysTO;

    }

    public static GlobalCalRegularDaysEntity convertPOJOToEntity(CalRegularDaysTO calenderRegularDaysTO) {
        GlobalCalRegularDaysEntity calRegularDaysEntity = new GlobalCalRegularDaysEntity();

        if (CommonUtil.isNonBlankLong(calenderRegularDaysTO.getId())) {
            calRegularDaysEntity.setId(calenderRegularDaysTO.getId());
        }

        if (CommonUtil.isNonBlankLong(calenderRegularDaysTO.getCalendarId())) {
            GlobalCalEntity globalCalEntity = new GlobalCalEntity();
            globalCalEntity.setId(calenderRegularDaysTO.getCalendarId());
            calRegularDaysEntity.setGlobalCalEntity(globalCalEntity);
        }

        calRegularDaysEntity.setSunday(calenderRegularDaysTO.isSunday());
        calRegularDaysEntity.setMonday(calenderRegularDaysTO.isMonday());
        calRegularDaysEntity.setTuesday(calenderRegularDaysTO.isTuesday());
        calRegularDaysEntity.setWednesday(calenderRegularDaysTO.isWednesday());
        calRegularDaysEntity.setThursday(calenderRegularDaysTO.isThursday());
        calRegularDaysEntity.setFriday(calenderRegularDaysTO.isFriday());
        calRegularDaysEntity.setSaturday(calenderRegularDaysTO.isSaturday());

        calRegularDaysEntity.setLatest(calenderRegularDaysTO.isLatest());
        calRegularDaysEntity.setFromDate(calenderRegularDaysTO.getFromDate());
        calRegularDaysEntity.setToDate(calenderRegularDaysTO.getToDate());

        calRegularDaysEntity.setStatus(calenderRegularDaysTO.getStatus());
        return calRegularDaysEntity;
    }

}
