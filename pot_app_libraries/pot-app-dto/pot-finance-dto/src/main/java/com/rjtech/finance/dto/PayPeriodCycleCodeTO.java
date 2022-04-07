package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class PayPeriodCycleCodeTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1614362001531874027L;

    private Long id;
    private String code;
    private String desc;
    private String payCycleByCount;
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

    public String getPayCycleByCount() {
        return payCycleByCount;
    }

    public void setPayCycleByCount(String payCycleByCount) {
        this.payCycleByCount = payCycleByCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
