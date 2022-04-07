package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpRegisterTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

}