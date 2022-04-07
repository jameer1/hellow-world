package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class CodeTypesTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5967261178899872946L;
    private Long id;
    private Long taxCountryProvsionId;
    private String financeCodeType;
    private String taxType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxCountryProvsionId() {
        return taxCountryProvsionId;
    }

    public void setTaxCountryProvsionId(Long taxCountryProvsionId) {
        this.taxCountryProvsionId = taxCountryProvsionId;
    }

    public String getFinanceCodeType() {
        return financeCodeType;
    }

    public void setFinanceCodeType(String financeCodeType) {
        this.financeCodeType = financeCodeType;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

}
