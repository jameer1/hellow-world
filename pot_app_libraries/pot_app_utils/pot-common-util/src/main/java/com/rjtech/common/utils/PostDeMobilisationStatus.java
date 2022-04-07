package com.rjtech.common.utils;

public enum PostDeMobilisationStatus {

    ONTRANSFER(1L, "On Transfer"), RETURNTOSUPPLIER(2L, "Return To Supplier"), SALVEGED(3L, "Salveged");

    private final Long value;
    private final String name;

    private PostDeMobilisationStatus(Long value, String name) {
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
