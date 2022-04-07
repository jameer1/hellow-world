package com.rjtech.common.req;

import com.rjtech.common.dto.ClientTO;

public class ProvisionFilterReq extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long countryId;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

}