package com.rjtech.common.dto;

public class WorkFlowStatusTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private Integer value;
    private String desc;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}