package com.rjtech.common.utils;

public enum StatusType {

    PENDING(1L, "Pending"), APPROVED(2L, "Approved"), REJECT(3L, "Rejected"),
    AddtionalTimeRequest(4L, "Addtional Time For Request"), AddtionalTimeApproved(5L, "Addtional Time For Approved"),
    ClientApproved(6L, "Client Approved");

    private final Long value;
    private final String name;

    private StatusType(Long value, String name) {
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
