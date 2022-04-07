package com.rjtech.common.utils;

public enum AttendanceTypes {
    EMP("EMP"), PLANT("PLANT");

    private final String name;

    private AttendanceTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
