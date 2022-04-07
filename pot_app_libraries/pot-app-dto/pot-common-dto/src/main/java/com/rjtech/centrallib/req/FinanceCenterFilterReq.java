package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class FinanceCenterFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String countryCode;
    private String provisionCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvisionCode() {
        return provisionCode;
    }

    public void setProvisionCode(String provisionCode) {
        this.provisionCode = provisionCode;
    }

}
