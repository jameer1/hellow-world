package com.rjtech.common.utils;

public enum TaxableTypes {

    YES("Yes"), NO("No");
    private final String name;

    private TaxableTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
