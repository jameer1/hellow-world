package com.rjtech.common.utils;

public enum WorkFlowStatus {
    DRAFT(1, "Draft"), IN_PROCESS(2, "Pending"), SEND_BACK_REQUEST(3, "Sent_To_Requestor"), ON_HOLD(4, "OnHold"),
    APPROVED(5, "APPROVED"), REJECTED(6, "REJECTED");

    private final Integer value;
    private final String desc;

    private WorkFlowStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescValue(Integer value) {
        for (WorkFlowStatus workFlowStatus : WorkFlowStatus.values()) {
            if (workFlowStatus.getValue().equals(value)) {
                return workFlowStatus.getDesc();
            }
        }
        return null;
    }

}
