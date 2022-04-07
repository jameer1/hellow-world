package com.rjtech.common.utils;

public enum Years {

    JAN(1L, "Jan"), FEB(2L, "Feb"), MAR(3L, "Mar"), APR(4L, "Apr"), MAY(5L, "May"), JUN(6L, "Jun"), JUL(7L, "Jul"),
    AUG(8L, "Aug"), SEP(9L, "Sep"), OCT(10L, "Oct"), NOV(11L, "Nov"), DEC(12L, "Dec");

    private final Long value;
    private final String name;

    private Years(Long value, String name) {
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
