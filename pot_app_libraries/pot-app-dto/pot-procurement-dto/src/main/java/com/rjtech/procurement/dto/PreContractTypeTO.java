package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class PreContractTypeTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -8357640555229640366L;

    private Long id;
    private String code;

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
