package com.rjtech.common.dto;

import java.io.Serializable;
import java.util.Date;

public class SuperProvidentFund implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String code;

    private String description;


    private String isTaxable;

    private String creditRunCycle;
    
    private String creditRunDueDate;

    private String superProvidentFundDisplayId;

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

    public String getCreditRunCycle() {
        return creditRunCycle;
    }

    public void setCreditRunCycle(String creditRunCycle) {
        this.creditRunCycle = creditRunCycle;
    }

    public String getCreditRunDueDate() {
        return creditRunDueDate;
    }

    public void setCreditRunDueDate(String creditRunDueDate) {
        this.creditRunDueDate = creditRunDueDate;
    }

    public String getSuperProvidentFundDisplayId() {
        return superProvidentFundDisplayId;
    }

    public void setSuperProvidentFundDisplayId(String superProvidentFundDisplayId) {
        this.superProvidentFundDisplayId = superProvidentFundDisplayId;
    }

}
