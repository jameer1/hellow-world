package com.rjtech.common.dto;

public class YearsTO extends ClientTO {

    private static final long serialVersionUID = -2495038031946189879L;

    private Long value;
    private String name;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
