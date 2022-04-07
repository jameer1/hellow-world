package com.rjtech.common.utils;

public enum ActionCodes {

    VIEW(1, "VIEW"), ADD(2, "ADD"), DEACTIVATE(3, "DEACTIVATE"), SUBMIT(4, "SUBMIT"), APPROVE(5, "APPROVE");

    private final Integer value;
    private final String desc;

    private ActionCodes(Integer value, String desc) {
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
