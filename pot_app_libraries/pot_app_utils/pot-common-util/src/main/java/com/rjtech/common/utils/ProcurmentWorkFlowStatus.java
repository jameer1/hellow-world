package com.rjtech.common.utils;

public enum ProcurmentWorkFlowStatus {
    DRAFT(1, "Draft"), UNDER_REVIEW(2, "Under Review"), RETURNED_WITH_COMMENTS(3, "Returned With Comments"),
    ON_HOLD_WITH_APPROVER(4, "On Hold With Approver"), APPROVED(5, "Approved"), REJECTED(6, "Rejected");
    private final Integer value;
    private final String desc;

    private ProcurmentWorkFlowStatus(Integer value, String desc) {
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
        for (ProcurmentWorkFlowStatus workFlowStatus : ProcurmentWorkFlowStatus.values()) {
            if (workFlowStatus.getValue().equals(value)) {
                return workFlowStatus.getDesc();
            }
        }
        return null;
    }

}
