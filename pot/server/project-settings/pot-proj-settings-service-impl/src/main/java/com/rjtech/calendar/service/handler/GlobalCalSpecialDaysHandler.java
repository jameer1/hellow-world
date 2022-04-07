package com.rjtech.calendar.service.handler;

import com.rjtech.calendar.dto.CalSpecialDaysTO;
import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.calendar.model.GlobalCalSpecialDaysEntity;
import com.rjtech.common.utils.CommonUtil;

public class GlobalCalSpecialDaysHandler {

    public static CalSpecialDaysTO convertEntityToPOJO(GlobalCalSpecialDaysEntity calSpecialDaysEntity) {
        CalSpecialDaysTO calenderSpecialDaysTO = new CalSpecialDaysTO();

        calenderSpecialDaysTO.setId(calenderSpecialDaysTO.getId());
        if (CommonUtil.objectNotNull(calSpecialDaysEntity.getGlobalCalEntity())) {
            calenderSpecialDaysTO.setCalendarId(calSpecialDaysEntity.getGlobalCalEntity().getId());
        }

        if (CommonUtil.isNotBlankDate(calSpecialDaysEntity.getDate())) {
            calenderSpecialDaysTO.setDate(CommonUtil.convertDateToDDMMYYYYString(calSpecialDaysEntity.getDate()));
        }
        calenderSpecialDaysTO.setDesc(calSpecialDaysEntity.getDesc());
        calenderSpecialDaysTO.setHoliday(calSpecialDaysEntity.isHoliday());

        calenderSpecialDaysTO.setStatus(calSpecialDaysEntity.getStatus());
        return calenderSpecialDaysTO;

    }

    public static GlobalCalSpecialDaysEntity convertPOJOToEntity(CalSpecialDaysTO calenderSpecialDaysTO) {
        GlobalCalSpecialDaysEntity calSpecialDaysEntity = new GlobalCalSpecialDaysEntity();

        if (CommonUtil.isNonBlankLong(calenderSpecialDaysTO.getId())) {
            calSpecialDaysEntity.setId(calenderSpecialDaysTO.getId());
        }
        if (CommonUtil.isNonBlankLong(calenderSpecialDaysTO.getCalendarId())) {
            GlobalCalEntity globalCalEntity = new GlobalCalEntity();
            globalCalEntity.setId(calenderSpecialDaysTO.getCalendarId());
            calSpecialDaysEntity.setGlobalCalEntity(globalCalEntity);
        }
        if (CommonUtil.isNotBlankStr(calenderSpecialDaysTO.getDate())) {
            calSpecialDaysEntity.setDate(CommonUtil.convertDDMMYYYYStringToDate(calenderSpecialDaysTO.getDate()));
        }
        calSpecialDaysEntity.setDesc(calenderSpecialDaysTO.getDesc());
        calSpecialDaysEntity.setHoliday(calenderSpecialDaysTO.isHoliday());

        calSpecialDaysEntity.setStatus(calenderSpecialDaysTO.getStatus());

        return calSpecialDaysEntity;
    }

}
