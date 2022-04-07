package com.rjtech.common.utils;

public enum ClaimePeriod {

    WEEKLY(1L, "Weekly"), MONTHLY(2L, "Monthly"), FORTNIGHTLY(3L, "Fortnightly");

    private Long value;
    private String claimPeriod;

    private ClaimePeriod(Long value, String claimPeriod) {
        this.value = value;
        this.claimPeriod = claimPeriod;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getClaimeperiod() {
        return claimPeriod;
    }

    public void setClaimeperiod(String claimPeriod) {
        this.claimPeriod = claimPeriod;
    }

}
