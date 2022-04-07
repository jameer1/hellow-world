package com.rjtech.projsettings.service.handler;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.utils.WeekDays;

public class ProjTimeSheetWeekMap {

    public static Map<String, String> convertWeekDaysNumberTOWeeakDaysMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (WeekDays weekDays : WeekDays.values()) {
            map.put(weekDays.getValue().toString(), weekDays.getName());
        }
        return map;
    }

    public static Map<String, String> convertWeekDaysTOWeeakDaysNumberMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (WeekDays weekDays : WeekDays.values()) {
            map.put(weekDays.getName(), weekDays.getValue().toString());
        }
        return map;
    }
}
