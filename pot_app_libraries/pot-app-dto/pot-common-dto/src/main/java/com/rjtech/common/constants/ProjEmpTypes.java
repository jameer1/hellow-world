package com.rjtech.common.constants;

public enum ProjEmpTypes {
    DIRECT(1, "Direct"), INDIRECT(2, "In-Direct");
    private final Integer value;
    private final String name;

    private ProjEmpTypes(Integer value, String name) {
        this.value = value;
        this.name = name;

    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getNameByValue(Integer value) {
        for (ProjEmpTypes catagoryTO : values()) {
            if (catagoryTO.getValue().equals(value)) {
                return catagoryTO.getName();
            }
        }
        return null;
    }

}
