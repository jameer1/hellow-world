package com.rjtech.common.dto;

import java.io.Serializable;

public class PayDeductionCodes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String code;

    private String description;

    private String payDeductionsDisplayId;

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

    public String getPayDeductionsDisplayId() {
        return payDeductionsDisplayId;
    }

    public void setPayDeductionsDisplayId(String payDeductionsDisplayId) {
        this.payDeductionsDisplayId = payDeductionsDisplayId;
    }
}
