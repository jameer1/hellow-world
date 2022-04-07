package com.rjtech.common.utils;

public enum ProcurmentStageStatus {
    STAGE1_REQUEST("Stage 1 Request"), STAGE1_APPROVAL("Stage 1 Approval"), RFQ_TENDERING("RFQ/Tendering"),
    BID_ANALYSIS("Bidding Analysis"), STAGE2_REQUEST("Stage 2 Request"), STAGE2_APPROVAL("Stage 2 Approval"),
    STAGE2_APPROVED("Stage 2 Approved"),REPEAT_PO_APPROVAL("Repeat PO Approval"),
    REPEAT_PO_APPROVED("Repeat PO Approved"),
    REJECTED("Rejected"), PO_ISSUED("PO Issued"), PO_ORDER("Purchase Order");
    private final String desc;

    private ProcurmentStageStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
