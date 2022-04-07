package com.rjtech.timemanagement.workdairy.dto;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyMaterialSourceTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}