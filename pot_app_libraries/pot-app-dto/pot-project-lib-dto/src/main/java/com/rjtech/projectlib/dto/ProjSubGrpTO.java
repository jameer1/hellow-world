package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ClientTO;

public class ProjSubGrpTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 8021375212055246864L;
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
