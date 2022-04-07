package com.rjtech.common.utils;

public enum TaxTypes {
    INDIVIDUAL("Individual"), COMPANY("Company");
    private final String value;

    private TaxTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
