package com.rjtech.common.utils;

public enum ApprovalStatus {
    OPEN(1, "Open"), IN_PROCESS(2, "In_Process"), ON_HOLD(3, "On_Hold"), SEND_FOR_APPROVAL(4, "Send_For_approval"),
    SEND_FOR_REQUEST(5, "Send_For_Request"), APPROVED(6, "APPROVED");

    private final Integer value;
    private final String desc;

    private ApprovalStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
