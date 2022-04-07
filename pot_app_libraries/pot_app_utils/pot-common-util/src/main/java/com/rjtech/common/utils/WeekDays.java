package com.rjtech.common.utils;

public enum WeekDays {
    SUNDAY(0L, "Sunday"), Monday(1L, "Monday"), TUESDAY(2L, "Tuesday"), WEDNESDAY(3L, "Wednesday"),
    THURSDAY(4L, "Thursday"), FRIDAY(5L, "Friday"), SATURDAY(6L, "Saturday");

    private final Long value;
    private final String name;

    private WeekDays(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
