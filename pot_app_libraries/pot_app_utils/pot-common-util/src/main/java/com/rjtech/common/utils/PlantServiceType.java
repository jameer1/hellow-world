package com.rjtech.common.utils;

public enum PlantServiceType {

    CURRENTORLASTSERVICE(1L, "CurrentOrLastService"), NextServiceDue(2L, "NextServiceDue");

    private final Long value;
    private final String name;

    private PlantServiceType(Long value, String name) {
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
