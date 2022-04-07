package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpPayQuestionMstrTO extends ProjectTO {

    private static final long serialVersionUID = 4768660206431955600L;
    private Long id;
    private String desc;
    private boolean type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

}
