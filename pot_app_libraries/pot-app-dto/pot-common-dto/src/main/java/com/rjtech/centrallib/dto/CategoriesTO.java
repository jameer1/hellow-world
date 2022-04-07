package com.rjtech.centrallib.dto;

import com.rjtech.common.dto.ClientTO;

public class CategoriesTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private String code;
    private String desc;

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
