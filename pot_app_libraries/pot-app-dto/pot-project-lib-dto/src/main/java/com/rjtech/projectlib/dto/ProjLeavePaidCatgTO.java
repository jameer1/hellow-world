package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ClientTO;

public class ProjLeavePaidCatgTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 8188336917785357841L;
    private long id;
    private String code;
    private Integer status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
