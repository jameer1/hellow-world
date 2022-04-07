package com.rjtech.common.utils;

public enum UserTypes {
    INTERNAL(1), EXTERNAL(2);
    private final Integer value;

    private UserTypes(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
