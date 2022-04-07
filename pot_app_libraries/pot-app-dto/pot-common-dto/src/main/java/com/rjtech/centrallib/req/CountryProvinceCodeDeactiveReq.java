package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.CountryProvinceCodeTo;

public class CountryProvinceCodeDeactiveReq extends CountryProvinceCodeTo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Long> countryProvinceIds = new ArrayList<Long>();
    public List<Long> getCountryProvinceIds() {
        return countryProvinceIds;
    }
    public void setCountryProvinceIds(List<Long> countryProvinceIds) {
        this.countryProvinceIds = countryProvinceIds;
    }


}
