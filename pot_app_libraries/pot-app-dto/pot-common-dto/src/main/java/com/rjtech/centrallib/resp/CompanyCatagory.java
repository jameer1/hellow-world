package com.rjtech.centrallib.resp;

import java.io.Serializable;

public class CompanyCatagory implements Serializable {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long catagoryId;
    private String code;
    private String desc;

    public Long getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(Long catagoryId) {
        this.catagoryId = catagoryId;
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