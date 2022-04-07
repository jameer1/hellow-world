package com.rjtech.common.dto;

import java.io.Serializable;

public class UnitOfPayRate implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    
    private Long id;
    
    private String code;

    private String name;

    private String notes;
    
    private String UnitOfPayDisplayId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getUnitOfPayDisplayId() {
        return UnitOfPayDisplayId;
    }

    public void setUnitOfPayDisplayId(String unitOfPayDisplayId) {
        UnitOfPayDisplayId = unitOfPayDisplayId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
