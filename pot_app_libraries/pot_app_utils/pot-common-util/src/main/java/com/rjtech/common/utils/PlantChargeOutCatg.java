package com.rjtech.common.utils;

public enum PlantChargeOutCatg {

    WITH_FUEL(1, "WITH FUEL"), WITHOUT_FUAL(2, "WITHOUT FUEL");

    private final Integer value;
    private final String name;

    private PlantChargeOutCatg(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
