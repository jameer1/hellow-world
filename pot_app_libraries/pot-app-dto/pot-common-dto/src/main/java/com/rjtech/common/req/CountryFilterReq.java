package com.rjtech.common.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class CountryFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8054854110376498823L;
    private String countryName;
    private String countryCode;
    private Long geonameId;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Long geonameId) {
        this.geonameId = geonameId;
    }

}
