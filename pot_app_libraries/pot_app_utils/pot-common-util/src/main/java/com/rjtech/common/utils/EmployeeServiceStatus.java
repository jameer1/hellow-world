package com.rjtech.common.utils;

public enum EmployeeServiceStatus {

    TRANSFERED("Transfered", "On Transfer"), RELIEVED("Relieved", "Relieved");

    private final String value;
    private final String name;

    private EmployeeServiceStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
