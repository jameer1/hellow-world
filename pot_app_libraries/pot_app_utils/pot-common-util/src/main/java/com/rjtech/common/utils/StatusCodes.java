package com.rjtech.common.utils;

import java.util.HashMap;
import java.util.Map;

public enum StatusCodes {
    DEFAULT(0), ACTIVE(1), DEACIVATE(2), DELETE(3);
    private final Integer value;

    private static final Map<Integer, StatusCodes> MY_MAP = new HashMap<Integer, StatusCodes>();
    static {
        for (StatusCodes myEnum : values()) {
            MY_MAP.put(myEnum.getValue(), myEnum);
        }
    }

    private StatusCodes(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static StatusCodes getByValue(int value) {
        return MY_MAP.get(value);
    }

}
