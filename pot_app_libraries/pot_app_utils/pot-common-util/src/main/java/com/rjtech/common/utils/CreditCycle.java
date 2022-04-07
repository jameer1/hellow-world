package com.rjtech.common.utils;

public enum CreditCycle {
    MONTHLY("Monthly"), QUARTERLY("Quarterly"), HALF_YEARLY("Half Yearly"), YEARLY("Yearly");
    private final String name;

    private CreditCycle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
