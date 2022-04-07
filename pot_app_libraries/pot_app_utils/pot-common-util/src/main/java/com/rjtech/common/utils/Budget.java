package com.rjtech.common.utils;

public enum Budget {

    BUDGET(1L, "ORIGINALBUDGET"), REVISEDBUDGET(2L, "REVISEDBUDGET"), ACTUALBUDGET(3L, "ACTUALBUDGET"),
    REMININGBUDGET(4L, "REMININGBUDGET"), ESTIMATETOCOMPLETE(5L, "ESTIMATETOCOMPLETE"),
    ESTIMATEATCOMPLETIONQTY(6L, "ESTIMATEATCOMPLETIONQTY"), VARAINCE(7L, "VARAINCE");

    private final Long value;
    private final String name;

    private Budget(Long value, String name) {
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
