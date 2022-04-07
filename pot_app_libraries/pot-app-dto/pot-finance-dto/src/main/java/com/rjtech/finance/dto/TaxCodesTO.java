package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class TaxCodesTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5967261178899872946L;
    private Long id;
    private String code;
    private String name;
    private String taxType;

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

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

}
