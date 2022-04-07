package com.rjtech.common.utils;

public enum Gender {

    MALE(1L, "Male"), FEMALE(2L, "Female");

    private final Long value;
    private final String name;

    private Gender(Long value, String name) {
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
