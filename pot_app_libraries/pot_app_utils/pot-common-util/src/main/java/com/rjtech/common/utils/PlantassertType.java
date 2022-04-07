package com.rjtech.common.utils;

public enum PlantassertType {

    EXSISTINGPLANT(1L, "Existing Plant"), NEWPLANT(2L, "New Plant");
    private final Long value;
    private final String name;

    private PlantassertType(Long value, String name) {
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
