package com.rjtech.common.utils;

public enum PayPeriodCycles {
    WEEKLY("WEEKLY"), FORTNIGHTLY("FORT NIGHTLY"), MONTHLY("MONTHLY"), QUARTERLY("QUARTLEY"),
    HALF_YEARLY("HALF YEARLY"), YEARLY("YEARLY");
    private final String value;

    private PayPeriodCycles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
