package com.rjtech.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProvinceCodes extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    @JsonProperty("ISO3166_2")
    private String provisionCode;

    public String getProvisionCode() {
        return provisionCode;
    }

    public void setProvisionCode(String provisionCode) {
        this.provisionCode = provisionCode;
    }


}