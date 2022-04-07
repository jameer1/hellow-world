package com.rjtech.common.utils;

public enum TimeManagentStatus {
    SUBMITTED(1, "Submitted"), SUBMIT_FOR_APPROVAL(2, "SubmittedForApproval"), APPROVED(3, "Approved"),
    SUBMIT_FOR_CLIENT_APPROVAL(4, "SubmittedForClientApproval"), CLIENT_APPROVED(5, "Client Approved"),
    UNDER_PREPARATION(6, "Under Preparation");

    private final Integer value;
    private final String name;

    private TimeManagentStatus(Integer value, String name) {
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
