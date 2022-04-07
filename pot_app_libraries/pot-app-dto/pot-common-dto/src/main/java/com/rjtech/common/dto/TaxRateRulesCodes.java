package com.rjtech.common.dto;

import java.io.Serializable;
import java.util.Date;

public class TaxRateRulesCodes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String code;

    private String description;
    private String applicableFor;

    private String creditRunCycle;
    private String creditRunDueDate;
    private String taxRateRules;
    private String taxRateRulesDisplayId;

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

    public String getApplicableFor() {
        return applicableFor;
    }

    public void setApplicableFor(String applicableFor) {
        this.applicableFor = applicableFor;
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

    public String getTaxRateRules() {
        return taxRateRules;
    }

    public void setTaxRateRules(String taxRateRules) {
        this.taxRateRules = taxRateRules;
    }

    public String getTaxRateRulesDisplayId() {
        return taxRateRulesDisplayId;
    }

    public void setTaxRateRulesDisplayId(String taxRateRulesDisplayId) {
        this.taxRateRulesDisplayId = taxRateRulesDisplayId;
    }
}
