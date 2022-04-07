package com.rjtech.common.utils;

public enum Locality {
    LOCALEMPLOYEE(1L, "Local Employee"), NONLOCALEMPLOYEE(2L, "NonLocal Employee");

    private final Long value;
    private final String name;

    private Locality(Long value, String name) {
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
