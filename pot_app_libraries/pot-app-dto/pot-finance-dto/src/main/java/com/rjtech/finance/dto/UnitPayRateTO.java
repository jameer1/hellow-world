package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class UnitPayRateTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5028694241940242365L;

    private Long id;
    private String code;
    private String name;
    private String notes;

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

}
