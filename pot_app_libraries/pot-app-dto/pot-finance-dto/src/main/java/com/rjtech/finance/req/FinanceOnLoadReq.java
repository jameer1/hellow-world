package com.rjtech.finance.req;

import com.rjtech.common.dto.ClientTO;

public class FinanceOnLoadReq extends ClientTO {

    private static final long serialVersionUID = -6149173001410840368L;
    private Long countryId;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

}
