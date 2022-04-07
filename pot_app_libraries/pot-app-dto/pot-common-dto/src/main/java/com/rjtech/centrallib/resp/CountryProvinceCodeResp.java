package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.dto.CountryProvinceCodeTo;
import com.rjtech.common.resp.AppResp;

public class CountryProvinceCodeResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<CountryProvinceCodeTo> countryProvinceCodeToTOs = new ArrayList<CountryProvinceCodeTo>();
    public List<CountryProvinceCodeTo> getCountryProvinceCodeToTOs() {
        return countryProvinceCodeToTOs;
    }
    public void setCountryProvinceCodeToTOs(List<CountryProvinceCodeTo> countryProvinceCodeToTOs) {
        this.countryProvinceCodeToTOs = countryProvinceCodeToTOs;
    }

}
