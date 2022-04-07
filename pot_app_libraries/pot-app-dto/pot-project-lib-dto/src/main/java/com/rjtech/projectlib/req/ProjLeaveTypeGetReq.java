package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ClientTO;

public class ProjLeaveTypeGetReq extends ClientTO {

    private static final long serialVersionUID = 1932182680014335677L;

    private String countryCode;
    private String effectiveFrom;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

}
