package com.rjtech.projsettings.req;

import com.rjtech.common.dto.ProjectTO;

public class ProvinenceGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1165124423124218730L;
    private Long countryId;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

}
