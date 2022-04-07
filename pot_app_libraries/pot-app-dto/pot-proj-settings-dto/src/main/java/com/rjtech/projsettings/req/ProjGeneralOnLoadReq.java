package com.rjtech.projsettings.req;

import com.rjtech.common.dto.ProjectTO;

public class ProjGeneralOnLoadReq extends ProjectTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1427702264422272048L;
    private Long countryId;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

}
