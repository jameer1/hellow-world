package com.rjtech.common.utils;

public enum EmployeeType {

    EXSISTINGEMPLOYEE(1L, "Existing Employee"), NEWEMPLOYEE(2L, "New Employee");

    private final Long value;
    private final String name;

    private EmployeeType(Long value, String name) {
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
