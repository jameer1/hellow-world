package com.rjtech.centrallib.dto;

import com.rjtech.common.dto.ClientTO;

public class BusinessActivityTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long id;
    private String code;
    private String desc;

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

}