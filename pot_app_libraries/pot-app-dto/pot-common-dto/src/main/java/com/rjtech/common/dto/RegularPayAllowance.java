package com.rjtech.common.dto;

import java.io.Serializable;

public class RegularPayAllowance implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String code;

    private String description;
    
    private String isTaxable;

    private String notes;
    
   private String regularPayCycleDisplayId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(String isTaxable) {
        this.isTaxable = isTaxable;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRegularPayCycleDisplayId() {
        return regularPayCycleDisplayId;
    }

    public void setRegularPayCycleDisplayId(String regularPayCycleDisplayId) {
        this.regularPayCycleDisplayId = regularPayCycleDisplayId;
    }
}
