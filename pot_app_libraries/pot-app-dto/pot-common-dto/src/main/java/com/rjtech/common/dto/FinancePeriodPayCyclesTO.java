package com.rjtech.common.dto;

public class FinancePeriodPayCyclesTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String desc;
    private String paycycleId;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPaycycleId() {
        return paycycleId;
    }

    public void setPaycycleId(String paycycleId) {
        this.paycycleId = paycycleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
